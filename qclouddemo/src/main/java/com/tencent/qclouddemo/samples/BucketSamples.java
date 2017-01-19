package com.tencent.qclouddemo.samples;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.HeadBucketRequest;
import com.tencent.cos.model.HeadBucketResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2016/10/13.
 */
public class BucketSamples extends AsyncTask<BizServer,Void,String>{
    protected String resultStr;
    protected TextView detailText;

    public BucketSamples(TextView detailText){
        this.detailText = detailText;
    }
    @Override
    protected String doInBackground(BizServer... params) {
        /** HeadBucketRequest 请求对象 */
        HeadBucketRequest headBucketRequest = new HeadBucketRequest();
        /** 设置Bucket */
        headBucketRequest.setBucket(params[0].getBucket());
        /** 设置cosPath :远程路径*/
        headBucketRequest.setCosPath("/");
        /** 设置sign: 签名，此处使用多次签名 */
        headBucketRequest.setSign(params[0].getSign());
        /** 设置listener: 结果回调 */
        headBucketRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                HeadBucketResult result = (HeadBucketResult) cosResult;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("查询结果：\n" );
                stringBuilder.append("code =" + result.code + ";msg=" + result.msg + "\n");
                stringBuilder.append("authority =" + (result.authority == null ? "":  result.authority) + "\n");
                stringBuilder.append("ctime=" + result.ctime + "; mtime=" + result.mtime + "\n");
                stringBuilder.append("biz_attr =" + (result.biz_attr == null ? "": result.biz_attr) + "\n");
                stringBuilder.append("migrate_source_domain =" + (result.migrate_source_domain == null ? "null": result.migrate_source_domain) + "\n");
                stringBuilder.append("refers: ");
                if(result.refers != null){
                    for(int i = 0; i< result.refers.size(); i++){
                        stringBuilder.append(result.refers.get(i) + ";");
                    }
                    stringBuilder.append(";count=" +result.refers.size()+"\n");
                }else{
                    stringBuilder.append("null" + "\n");
                }
                stringBuilder.append("black_refers:");
                if(result.blackRefers != null){
                    for(int i= 0; i< result.blackRefers.size(); i++){
                        stringBuilder.append(result.blackRefers.get(i) + ";");
                    }
                    stringBuilder.append( ";count=" +result.blackRefers.size() + "\n");
                }else{
                    stringBuilder.append("null" + "\n");
                }
                stringBuilder.append("cname:");
                if(result.cname != null){
                    for(int i= 0; i< result.cname.size(); i++){
                        stringBuilder.append(result.cname.get(i) + ";");
                    }
                    stringBuilder.append(";count=" +result.cname.size()+"\n");
                }else{
                    stringBuilder.append("null" + "\n");
                }
                stringBuilder.append("跨域设置：" + (result.CORSConfiguration == null ? "null" : result.CORSConfiguration) + "\n");
                resultStr = stringBuilder.toString();
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                resultStr ="code =" + cosResult.code + "; msg =" + cosResult.msg;
            }
        });
        /** 发送请求：执行 */
        params[0].getCOSClient().headBucket(headBucketRequest);
        return resultStr;
    }

    @Override
    protected void onPostExecute(String s) {
        detailText.setText(s);
    }
}
