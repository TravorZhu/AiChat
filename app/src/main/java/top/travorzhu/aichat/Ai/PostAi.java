package top.travorzhu.aichat.Ai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.travorzhu.aichat.Ai.Beans.AiPostBean;

public class PostAi {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static AiPostBean aiPostBean;

    private OkHttpClient client = new OkHttpClient();

    public void post(String text, final Handler handler) {
        System.out.println("Text:" + text);
        aiPostBean.setText(text);
        String bodyStr = new Gson().toJson(aiPostBean);
        RequestBody body = RequestBody.create(JSON, bodyStr);
        System.out.print(bodyStr);
        Request request = new Request.Builder()
                .url("http://openapi.tuling123.com/openapi/api/v2")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("访问出错");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsed = response.body().string();
                System.out.println("访问成功");
                System.out.println("访问结果为:" + responsed);
                Bundle bundle = new Bundle();
                bundle.putString("respose", responsed);
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
    }
}
