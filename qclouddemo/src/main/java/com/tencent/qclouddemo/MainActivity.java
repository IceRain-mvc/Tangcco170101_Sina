package com.tencent.qclouddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class MainActivity extends AppCompatActivity {

    Button fileBtn;
    Button dirBtn;
    Button otherBtn;
    Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileBtn = (Button)findViewById(R.id.file);
        dirBtn = (Button)findViewById(R.id.dir);
        otherBtn = (Button)findViewById(R.id.other);
        downloadBtn = (Button)findViewById(R.id.download);

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","文件测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FileUploadActivity.class);
                startActivity(intent);

            }
        });
        dirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","文件夹测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DirActivity.class);
                startActivity(intent);
            }
        });
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","bucket测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","下载测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FileDownloadActivity.class);
                startActivity(intent);
            }
        });
    }
}
