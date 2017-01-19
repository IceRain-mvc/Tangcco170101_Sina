package com.tangcco.android.TangccoAndroid030_31;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tangcco.android.TangccoAndroid030_31.okhttp_util.OkHttpClientManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OkHttpActivity extends Activity {
    String host = "192.168.1.165";
    String url = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void get(View view) {
        url = "http://" + host + ":8080/TangccoAndroidService030/login.jsp";

        new Thread(new Runnable() {
            //2.7
//            RequestBody body = new FormEncodingBuilder()
//                    .add("name", "admin")
//                    .add("password", "admin")
//                    .build();
            //3.2
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("name", "admin")
//                    .add("password", "admin")
//                    .build();

            @Override
            public void run() {
//                //1:
//                OkHttpClient client = new OkHttpClient();
//                //2:
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(body)
//                        .build();
//                //3:
//                Call call = client.newCall(request);
                //4:
                //同步思想
                //Response response = call.execute();
                //5:
//                    final String result = response.body().string();
//
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(OkHttpActivity.this, result, Toast.LENGTH_LONG).show();
//                        }
//                    });

                //异步思想
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        Toast.makeText(OkHttpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(final Response response) throws IOException {
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Toast.makeText(OkHttpActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                });

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "admin");
                params.put("password", "admin");
//                final String result = OkHttpUtil.http_post(url,null);
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(OkHttpActivity.this, result, Toast.LENGTH_LONG).show();
//                    }
//                });


//                OkHttpUtil.http_post_asyn(url, params, new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        Toast.makeText(OkHttpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                    @Override
//                    public void onResponse(final Response response) throws IOException {
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Toast.makeText(OkHttpActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                    }
//                });


                OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(OkHttpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OkHttpActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, params);
            }
        }).start();
    }

    public void upload(View view){
        String url = "http://" + host
                + ":8080/TangccoAndroidService/uploadServlet";

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "abcd.jpg";
        File file = new File(filePath);

        try {
            OkHttpClientManager.postAsyn(url,new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(String response) {
                    Toast.makeText(OkHttpActivity.this,"上传文件成功，请查看文件！",Toast.LENGTH_LONG).show();
                }
            },file,"file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download(View view){
        String imageUrl = "http://" + host + ":8080/TangccoAndroidService/image/meinv.png";
        OkHttpClientManager
                .downloadAsyn(
                        imageUrl,
                        Environment.getExternalStorageDirectory()
                                .getAbsolutePath(),
                        new OkHttpClientManager.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                // 文件下载成功，这里回调的reponse为文件的absolutePath
                                Log.i("OkHttp", response);
                                Toast.makeText(OkHttpActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        });
    }

    public void show(View view){
        String imageUrl = "http://" + host + ":8080/TangccoAndroidService/image/meinv.png";
        try {
            OkHttpClientManager.displayImage(imageView,imageUrl,android.R.drawable.ic_delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
