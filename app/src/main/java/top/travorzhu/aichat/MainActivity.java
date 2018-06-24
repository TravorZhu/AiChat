package top.travorzhu.aichat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import top.travorzhu.aichat.Ai.Beans.AiPostBean;
import top.travorzhu.aichat.Ai.Beans.AiReciveBean;
import top.travorzhu.aichat.Ai.PostAi;
import top.travorzhu.aichat.ChatListView.ChatListViewAdapter;
import top.travorzhu.aichat.ChatListView.ChatListViewBean;
import top.travorzhu.aichat.Location.Location;
import top.travorzhu.aichat.Voice.AliAsr;

public class MainActivity extends AppCompatActivity {
    ImageButton voiceButton;
    ImageButton sendButton;
    ListView listView;
    EditText editText;
    ChatListViewAdapter adapter;
    List<ChatListViewBean> messageList;
    Context context;
    Handler handler;

    AliAsr aliAsr;
    Location location;

    SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.getInstance();


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //百度语音初始化
        speechSynthesizer.setContext(this);
        speechSynthesizer.setAppId("11216449");
        speechSynthesizer.setApiKey("cFtzsCqBxkrK7oRPEWk2uhPg", "GY2Xfwp3Q7AryYPoBoo4Bs7MknLLeFU4");
        speechSynthesizer.auth(TtsMode.ONLINE);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_PCM);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_PCM);
        speechSynthesizer.initTts(TtsMode.ONLINE);
//定位初始化
        PostAi.aiPostBean = new AiPostBean(null, null, null, null);
        PostAi.aiPostBean.getPerception().getSelfInfo().setLocation(null);

        location = new Location(this);
        location.FindLocation();
//元素获取
        voiceButton = findViewById(R.id.voice);
        sendButton = findViewById(R.id.send);
        editText = findViewById(R.id.edittext);
        listView = findViewById(R.id.list_view_chat);

        messageList = new ArrayList<>();
        adapter = new ChatListViewAdapter(this, messageList);
        listView.setAdapter(adapter);

        context = getApplicationContext();
//获取权限
        new PermissionCheck().getAll(this);

        Handler handler_voice = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String s = msg.getData().getString("Result");
                if (Objects.equals(s, ""))
                    return;
                addBean(s, 0);
                try {
                    new PostAi().post(s, handler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        aliAsr = new AliAsr(MainActivity.this, editText, handler_voice);

        initMsgs();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String s = msg.getData().getString("respose");
                Gson gson = new Gson();
                AiReciveBean reciveBean = gson.fromJson(s, AiReciveBean.class);
                System.out.println("ReciveBean:" + reciveBean);
                addBean(reciveBean.getResults().get(0).getValues().getText(), 1);
            }
        };

        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliAsr.initStartRecognizing();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sendbutton on click");
                Log.i("SendButton", "onClick: ");
                if (aliAsr.isRecognizing()) {
                    Log.i("SendButton", "onClick:语音识别已经开启 ");
                    aliAsr.initStopRecognizing();
                }
                String text = editText.getText().toString();
                if (Objects.equals(text, ""))
                    return;
                if (Objects.equals(text, "请说话，我在听")) {
                    editText.setText("");
                    return;

                }
                addBean(text, 0);
                try {
                    new PostAi().post(text, handler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initMsgs() {
        ChatListViewBean bean2 = new ChatListViewBean(1, "欢迎使用人工智能助手", BitmapFactory.decodeResource(getResources(), R.drawable.mic));
        messageList.add(bean2);
    }

    public void addBean(final String s, int type) {
        Log.i("addBean", "addBean: S:" + s + " type:" + type);
        ChatListViewBean beanAdd = new ChatListViewBean(type, s, BitmapFactory.decodeResource(getResources(), R.drawable.mic));
        System.out.print(beanAdd);
        messageList.add(beanAdd);
        adapter.notifyDataSetChanged();//如果数据来就更新adapter;
        listView.setSelection(messageList.size() - 1);//定位到最后一行信息记录
        if (type == 1) {
            int status;

//            百度语音播放
            status = speechSynthesizer.speak(s);
            Log.e("BaiduSpeak", "Error code: " + status);

        }
        editText.setText("");//清空edittext的数据
    }
}
