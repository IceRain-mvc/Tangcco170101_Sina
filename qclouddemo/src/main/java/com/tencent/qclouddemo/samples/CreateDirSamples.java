package com.tencent.qclouddemo.samples;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.CreateDirRequest;
import com.tencent.cos.model.CreateDirResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class CreateDirSamples extends AsyncTask<BizServer,Void,String>{
    protected String resultStr;
    protected TextView detailText;
    public CreateDirSamples(TextView detailText){
        this.detailText = detailText;
    }
    @Override
    protected String doInBackground(BizServer... bizServers) {
        /** CreateDirRequest 请求对象 */
        CreateDirRequest createDirRequest = new CreateDirRequest();
        /** 设置Bucket */
        createDirRequest.setBucket(bizServers[0].getBucket());
        /** 设置cosPath :远程路径*/
        createDirRequest.setCosPath(bizServers[0].getFileId());
        /** 设置biz_attr（一般不需要设置） :文件夹属性*/
        String biz_attr = "文件夹属性";
        createDirRequest.setBizAttr(biz_attr);
        /** 设置sign: 签名，此处使用多次签名 */
        createDirRequest.setSign(bizServers[0].getSign());
        /** 设置listener: 结果回调 */
        createDirRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                CreateDirResult createDirResult = (CreateDirResult) cosResult;
                resultStr = "目录创建： ret=" + createDirResult.code + "; msg=" + createDirResult.msg
                        + "ctime = " + createDirResult.ctime;
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "目录创建失败：ret=" + cosResult.code  + "; msg =" + cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        bizServers[0].getCOSClient().createDir(createDirRequest);
        return resultStr;
    }

    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
