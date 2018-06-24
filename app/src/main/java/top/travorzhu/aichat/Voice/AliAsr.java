package top.travorzhu.aichat.Voice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.idst.nls.realtime.NlsClient;
import com.alibaba.idst.nls.realtime.NlsListener;
import com.alibaba.idst.nls.realtime.StageListener;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsRequest;
import com.alibaba.idst.nls.realtime.internal.protocol.NlsResponse;

import java.util.HashMap;


public class AliAsr {

    private Handler handler;
    private EditText editText;
    private boolean isRecognizing = false;
    private NlsClient mNlsClient;
    private NlsRequest mNlsRequest;
    private HashMap<Integer, String> resultMap = new HashMap<Integer, String>();
    private int sentenceId = 0;
    private Context context;
    private NlsListener mRecognizeListener = new NlsListener() {

        @Override
        public void onRecognizingResult(int status, NlsResponse result) {
            switch (status) {
                case NlsClient.ErrorCode.SUCCESS:
                    if (result != null) {
                        if (result.getResult() != null) {
                            //获取句子id对应结果。
                            if (sentenceId != result.getSentenceId()) {
                                sentenceId = result.getSentenceId();
                            }
                            resultMap.put(sentenceId, result.getText());

                            Log.i("asr", "[demo] callback onRecognizResult :" + result.getResult().getText());

                            System.out.println(JSON.toJSONString(result.getResult()));

                            if (result.getEndTime() != -1) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Result", result.getText());
                                Message msg = new Message();
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                                initStopRecognizing();
                            } else {
                                editText.setText(result.getText());
                            }

                        }
                    } else {
                        Log.i("asr", "[demo] callback onRecognizResult finish!");
                    }
                    break;
                case NlsClient.ErrorCode.RECOGNIZE_ERROR:
                    Toast.makeText(context, "recognizer error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.RECORDING_ERROR:
                    Toast.makeText(context, "recording error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.NOTHING:
                    Toast.makeText(context, "nothing", Toast.LENGTH_LONG).show();
                    break;
            }
        }


    };
    private StageListener mStageListener = new StageListener() {
        @Override
        public void onStartRecognizing(NlsClient recognizer) {
            super.onStartRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
            editText.setText("请说话，我在听");
            //editText.setFocusable(false);
            Log.e("AliAsr", "onStartRecognizing: start");
        }

        @Override
        public void onStopRecognizing(NlsClient recognizer) {
            super.onStopRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
            mNlsClient.stop();
            isRecognizing = false;
            editText.setFocusable(true);
            editText.setText("");
            Log.e("AliAsr", "onStopRecognizing: stop");
        }

        @Override
        public void onStartRecording(NlsClient recognizer) {
            super.onStartRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStopRecording(NlsClient recognizer) {
            super.onStopRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onVoiceVolume(int volume) {
            super.onVoiceVolume(volume);
        }

    };

    public AliAsr(Activity context, EditText editText, Handler handler) {
        this.context = context;
        this.editText = editText;
        this.handler = handler;

        String appkey = "nls-service-shurufa16khz";

        mNlsRequest = new NlsRequest();
        mNlsRequest.setAppkey(appkey);
        mNlsRequest.setResponseMode("streaming");//流式语音

        mNlsRequest.setVocabularyId("8551d717a9d64200b320e6f6f0ab66dd");//自定义词库


        NlsClient.openLog(true);
        NlsClient.configure(context.getApplicationContext());
        mNlsClient = NlsClient.newInstance(context, mRecognizeListener, mStageListener, mNlsRequest);

        mNlsClient.setMaxRecordTime(60000);
        mNlsClient.setMaxStallTime(200);
        mNlsClient.setMinRecordTime(100);
        mNlsClient.setRecordAutoStop(true);  //设置VAD
        mNlsClient.setMinVoiceValueInterval(200); //设置音量回调时长
    }

    public void initStartRecognizing() {
        isRecognizing = true;
        mNlsRequest.authorize("LTAIcjRDkiNx4Ff5", "VWHA1Ab8hIyjVOBGSjSpLHx98qYdc9");
        mNlsClient.start();
    }

    public void initStopRecognizing() {
        editText.setFocusable(true);
        editText.setText("");
        mNlsClient.stop();
    }

    public boolean isRecognizing() {
        return isRecognizing;
    }
}
