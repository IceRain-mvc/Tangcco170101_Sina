package com.tencent.qclouddemo.samples;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.GetObjectMetadataRequest;
import com.tencent.cos.model.GetObjectMetadataResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class GetObjeceMetadataSamples extends AsyncTask<BizServer,Void,String>{
    protected String resultStr;
    protected TextView detailText;
    public GetObjeceMetadataSamples(TextView detailText){
        this.detailText = detailText;
    }
    @Override
    protected String doInBackground(BizServer... params) {
        /** GetObjectMetadataRequest 请求对象 */
        GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest();
        /** 设置Bucket */
        getObjectMetadataRequest.setBucket(params[0].getBucket());
        /** 设置cosPath :远程路径*/
        getObjectMetadataRequest.setCosPath(params[0].getFileId());
        /** 设置sign: 签名，此处使用多次签名 */
        getObjectMetadataRequest.setSign(params[0].getSign());
        /** 设置listener: 结果回调 */
        getObjectMetadataRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                GetObjectMetadataResult getObjectMetadataResult = (GetObjectMetadataResult) cosResult;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("查询结果 = ")
                        .append(" code = ").append(getObjectMetadataResult.code)
                        .append(", msg =").append(getObjectMetadataResult.msg)
                        .append(", ctime =").append(getObjectMetadataResult.mtime)
                        .append(", mtime =").append( getObjectMetadataResult.ctime).append("\n")
                        .append(", filesize =").append(getObjectMetadataResult.filesize)
                        .append(", filesize =").append(getObjectMetadataResult.filesize)
                        .append(", access_url =").append(getObjectMetadataResult.access_url == null?"null":getObjectMetadataResult.access_url)
                        .append(", sha =").append(getObjectMetadataResult.sha == null?"null":getObjectMetadataResult.sha)
                        .append(", authority =").append(getObjectMetadataResult.authority == null?"null":getObjectMetadataResult.authority)
                        .append(", biz_attr =").append(getObjectMetadataResult.biz_attr == null?"null":getObjectMetadataResult.biz_attr);
                resultStr = stringBuilder.toString();
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr = "查询出错： ret =" + cosResult.code + "; msg =" + cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        params[0].getCOSClient().getObjectMetadata(getObjectMetadataRequest);
        return resultStr;
    }
    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
