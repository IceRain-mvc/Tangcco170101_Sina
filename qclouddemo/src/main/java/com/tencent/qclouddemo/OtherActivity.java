package com.tencent.qclouddemo;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.qclouddemo.samples.BizServer;
import com.tencent.qclouddemo.samples.BucketSamples;
/**
 * Created by bradyxiao on 2016/9/13.
 */
public class OtherActivity extends AppCompatActivity implements View.OnClickListener {

        private Button bucketBtn;
        private TextView detailText;
        BizServer bizServer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_other);
                bucketBtn = (Button) findViewById(R.id.bucket);
                detailText = (TextView) findViewById(R.id.detail);
                bucketBtn.setOnClickListener(this);
                bizServer = BizServer.getInstance(getApplicationContext());
        }

        @Override
        public void onClick(View v) {
                int id = v.getId();
                if (R.id.bucket == id) {
                        statBucket();
                        return;
                }
        }

        public void statBucket() {
                BucketSamples bucketSamples = new BucketSamples(detailText);
                bucketSamples.execute(bizServer);
        }
}