package com.tengxunyun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.CreateDirRequest;
import com.tencent.cos.model.CreateDirResult;
import com.tencent.cos.model.GetObjectRequest;
import com.tencent.cos.model.GetObjectResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.ICmdTaskListener;
import com.tencent.cos.task.listener.IDownloadTaskListener;
import com.tencent.cos.task.listener.IUploadTaskListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String bucket = "aaa";//"cos空间名称";/** appid的一个空间名称 */
    String cosPath = "test";//"远端路径，即存储到cos上的路径";
    String srcPath = Environment.getDownloadCacheDirectory().getAbsolutePath();//"本地文件的绝对路径";
    String sign = "签名，此处使用多次签名";
    private COSClient cos;
    private TextView progressText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressText = (TextView) findViewById(R.id.progressText);
        imageView = (ImageView) findViewById(R.id.img_iv);

        //创建COSClientConfig对象，根据需要修改默认的配置参数
        COSClientConfig config = new COSClientConfig();
        //设置园区
        config.setEndPoint(COSEndPoint.COS_GZ);

        Context context = getApplicationContext();
        String appid = "1253221480";//"腾讯云注册的appid";
        String peristenceId = "AKIDQjpqslZzwhFf7MqI2czA6vY0NmyygU6G";//"持久化Id";

        //创建COSlient对象，实现对象存储的操作
        cos = new COSClient(context, appid, config, peristenceId);

    }


    /**
     * 上传文件
     */
    public void upLoadData(View view) {
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(bucket);

        cosPath += "/"+"aa.jpg";
        putObjectRequest.setCosPath(cosPath);

        srcPath += "/aa.jpg";
        putObjectRequest.setSrcPath(srcPath);
        putObjectRequest.setSign(sign);
        putObjectRequest.setListener(new IUploadTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {

                PutObjectResult result = (PutObjectResult) cosResult;
                if (result != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" + result.msg + "\n");
                    stringBuilder.append(" access_url= " + result.access_url == null ? "null" : result.access_url + "\n");
                    stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" : result.resource_path + "\n");
                    stringBuilder.append(" url= " + result.url == null ? "null" : result.url);
                    Log.w("TEST", stringBuilder.toString());
                }
            }

            @Override
            public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
                Log.w("TEST", "上传出错： ret =" + cosResult.code + "; msg =" + cosResult.msg);
            }

            @Override
            public void onProgress(COSRequest cosRequest, final long currentSize, final long totalSize) {
                float progress = (float) currentSize / totalSize;
                progress = progress * 100;
                Log.w("TEST", "进度：  " + (int) progress + "%");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {

            }
        });
    }

    public void createDir(View view) {
        CreateDirRequest createDirRequest = new CreateDirRequest();
        createDirRequest.setBucket(bucket);
        createDirRequest.setCosPath(cosPath);
        //createDirRequest.setBiz_attr(biz_attr);
        createDirRequest.setSign(sign);
        createDirRequest.setListener(new ICmdTaskListener() {
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                final CreateDirResult createDirResult = (CreateDirResult) cosResult;
                Log.w("TEST", "目录创建成功： ret=" + createDirResult.code + "; msg=" + createDirResult.msg
                        + "ctime = " + createDirResult.ctime);
            }

            @Override
            public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
                Log.w("TEST", "目录创建失败： ret=" + cosResult.code + "; msg=" + cosResult.msg);
            }
        });

        CreateDirResult result = cos.createDir(createDirRequest);
    }

    /**
     * 下载文件
     * @param view
     */
    public void downloadData(View view) {
        String downloadURl = "http://aaa-1253221480.file.myqcloud.com/test/aa.jpg";
        //String savePath = "本地保存文件的路径";
        final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/vv.png";

//        File file = new File(savePath);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        savePath += "/vv.png";
        String sign = "开启token防盗链了，则需要签名；否则，不需要";


        GetObjectRequest getObjectRequest = new GetObjectRequest(downloadURl, savePath);
        getObjectRequest.setSign(null);
        getObjectRequest.setListener(new IDownloadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, final long currentSize, final long totalSize) {
                float progress = currentSize / (float) totalSize;
                progress = progress * 100;

                progressText.setText("progress =" + (int) (progress) + "%");
                Log.w("TEST", "progress =" + (int) (progress) + "%");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {

            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                Log.w("TEST", "code =" + cosResult.code + "; msg =" + cosResult.msg);
                //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/vv.png";
                File file = new File(savePath);
                if (!file.exists()) {
                    Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap bitmap = BitmapFactory.decodeFile(savePath);

                imageView.setImageBitmap(bitmap);


            }

            @Override
            public void onFailed(COSRequest COSRequest, COSResult cosResult) {
                Log.w("TEST", "code =" + cosResult.code + "; msg =" + cosResult.msg);
            }
        });
        GetObjectResult getObjectResult = cos.getObject(getObjectRequest);
    }
}
