package com.tencent.qclouddemo.samples;

import android.os.AsyncTask;
import android.widget.TextView;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.RemoveEmptyDirRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class RemoveEmptyDirSample extends AsyncTask<BizServer, Void, String> {
    protected String resultStr;
    protected TextView detailText;

    public RemoveEmptyDirSample(TextView detailText) {
        this.detailText = detailText;
    }

    @Override
    protected String doInBackground(BizServer... bizServers) {
        /** RemoveEmptyDirRequest 请求对象，只能删除空文件夹，其他无效 */
        RemoveEmptyDirRequest removeEmptyDirRequest = new RemoveEmptyDirRequest();
        /** 设置Bucket */
        removeEmptyDirRequest.setBucket(bizServers[0].getBucket());
        /** 设置cosPath :远程路径*/
        removeEmptyDirRequest.setCosPath(bizServers[0].getFileId());
        /** 设置sign: 签名，此处使用单次签名 */
        removeEmptyDirRequest.setSign(bizServers[0].getOnceSign());
        /** 设置listener: 结果回调 */
        removeEmptyDirRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "code =" + cosResult.code + "; msg =" + cosResult.msg;
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "code =" + cosResult.code + "; msg =" + cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        bizServers[0].getCOSClient().removeEmptyDir(removeEmptyDirRequest);
        return resultStr;
    }

    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
