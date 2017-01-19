package com.tencent.qclouddemo.samples;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.GetObjectRequest;
import com.tencent.cos.task.listener.IDownloadTaskListener;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class GetObjectSamples extends AsyncTask<BizServer,Long,String>{

    protected TextView detailText;
    protected TextView progressText;
    protected String resultStr;
    public GetObjectSamples(TextView detailText,TextView progressText){
        this.detailText = detailText;
        this.progressText = progressText;
    }
    @Override
    protected String doInBackground(BizServer... params) {
        /** GetObjectRequest 请求对象 */
        GetObjectRequest getObjectRequest = new GetObjectRequest(params[0].getDownloadUrl(),params[0].getSavePath());
        //若是设置了防盗链则需要签名；否则，不需要
        /** 设置listener: 结果回调 */
        getObjectRequest.setListener(new IDownloadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long currentSize, long totalSize) {
                long progress = (long) ((100.00 * currentSize) / totalSize);
                publishProgress(progress);
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "cancel =" + cosResult.msg;
            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "code =" + cosResult.code + "; msg =" + cosResult.msg;
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr ="code =" + cosResult.code + "; msg =" + cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        params[0].getCOSClient().getObject(getObjectRequest);
        return resultStr;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        progressText.setText("下载进度 :" + values[0].intValue() + "%");
    }

    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
