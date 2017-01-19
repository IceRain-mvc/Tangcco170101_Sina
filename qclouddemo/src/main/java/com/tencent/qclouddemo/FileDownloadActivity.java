package com.tencent.qclouddemo;

import android.os.Bundle;


import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tencent.qclouddemo.samples.BizServer;
import com.tencent.qclouddemo.samples.GetObjectSamples;

import java.io.File;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class FileDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button downloadBtn;
    private TextView urlText, localText, progressText,detailText;
    private BizServer bizServer;
    private String downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        downloadBtn = (Button) findViewById(R.id.download);


        urlText = (TextView) findViewById(R.id.url);
        localText = (TextView) findViewById(R.id.localPath);
        progressText = (TextView) findViewById(R.id.progress);
        detailText = (TextView) findViewById(R.id.detail);

        downloadBtn.setOnClickListener(this);


        downloadUrl = getIntent().getStringExtra("url");
        urlText.setText(downloadUrl);

        bizServer = BizServer.getInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.download:
                onDownload();
                break;
        }
    }
    public void onDownload(){
        String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "test_download";
        if (downloadUrl == null)
            downloadUrl = "http://m.dianping.com/oscms/content/%E5%A4%B4%E5%9B%BE3_meitu_1.jpg";
        urlText.setText(downloadUrl);
        localText.setText(savePath);

        bizServer.setDownloadUrl(downloadUrl);
        bizServer.setSavePath(savePath);

        GetObjectSamples getObjectSamples = new GetObjectSamples(detailText,progressText);
        getObjectSamples.execute(bizServer);
    }
}
