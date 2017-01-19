package com.tencent.qclouddemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;
import com.tencent.cos.utils.FileUtils;
import com.tencent.qclouddemo.samples.BizServer;
import com.tencent.qclouddemo.samples.DeleteObjectSamples;
import com.tencent.qclouddemo.samples.GetObjeceMetadataSamples;
import com.tencent.qclouddemo.samples.PutObjectSamples;
import com.tencent.qclouddemo.samples.UpdateObjectSamples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class FileUploadActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * UI
     */
    private Button addBtn, uploadBtn, deleteBtn, queryBtn, updateBtn, uploadListBtn, uploadConcurrentBtn, uploadSliceBtn;
    private TextView urlText, detailText, pathText, progreesText;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final int OPENFILE_CODE = 1;

    private String currentPath = null;
    BizServer bizServer;
    String getCosPahtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        addBtn = (Button) findViewById(R.id.add);
        uploadBtn = (Button) findViewById(R.id.upload);
        deleteBtn = (Button) findViewById(R.id.delete);
        queryBtn = (Button) findViewById(R.id.stat);
        updateBtn = (Button) findViewById(R.id.update);
        uploadListBtn = (Button) findViewById(R.id.uploadlist);
        uploadConcurrentBtn = (Button) findViewById(R.id.uploadConcurrent);
        uploadSliceBtn = (Button) findViewById(R.id.uploadSlice);

        urlText = (TextView) findViewById(R.id.url);
        detailText = (TextView) findViewById(R.id.detail);
        pathText = (TextView) findViewById(R.id.srcPath);
        progreesText = (TextView) findViewById(R.id.progress);




        addBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        uploadListBtn.setOnClickListener(this);
        uploadConcurrentBtn.setOnClickListener(this);
        uploadSliceBtn.setOnClickListener(this);
        urlText.setOnClickListener(this);

        bizServer = BizServer.getInstance(getApplicationContext());


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.add:
                onAdd();
                break;
            case R.id.upload:
                upload();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.stat:
                query();
                break;
            case R.id.update:
                update();
                break;
            case R.id.url:
                download();
                break;
            case R.id.uploadlist:
                uploadList();
                break;
            case R.id.uploadConcurrent:
                uploadConcurrent();
                break;
            case R.id.uploadSlice:
                uploadSlice();
            default:
                break;
        }
    }

    /**
     * 1)根目录下上传单个文件   filename
     * 2）子目录下上传单个文件  test/filename
     * 3）依次上传多个文件       参考一键上传
     * 4）同目录下上传相同的文件
     * 5）删除文件后上传相同的文件
     */

     public void upload(){
         final EditText editText = new EditText(FileUploadActivity.this);
         AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this);
         builder.setTitle("请输入cos文件夹路径");
         builder.setView(editText);
         builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                upload(editText.getText().toString().trim());
            }
        });
         builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
         AlertDialog dialog = builder.create();
         dialog.show();
     }
     public void upload(String getCosPahtName){
         pathText.setText(currentPath);
         if(TextUtils.isEmpty(currentPath)){
             Toast.makeText(FileUploadActivity.this,"请选择文件", Toast.LENGTH_SHORT).show();
             return;
         }
         String filename = FileUtils.getFileName(currentPath);
         String cosPath;
         if(TextUtils.isEmpty(getCosPahtName)){
             cosPath = filename;
         }else{
             cosPath = getCosPahtName + "/" + filename;
         }

         bizServer.setFileId(cosPath);
         bizServer.setSrcPath(currentPath);
         PutObjectSamples putObjectSamples = new PutObjectSamples(detailText,progreesText, PutObjectSamples.PUT_TYPE.SAMPLE);
         putObjectSamples.execute(bizServer);

     }
    public void delete(){
        final EditText editText = new EditText(FileUploadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(editText.getText().toString().trim());

            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void delete( String getCosPahtName){
        if(TextUtils.isEmpty(getCosPahtName)){
            Toast.makeText(FileUploadActivity.this,"文件为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!getCosPahtName.startsWith("/")){
            bizServer.setFileId("/" + getCosPahtName);
        }else{
            bizServer.setFileId(getCosPahtName);
        }

        DeleteObjectSamples deleteObjectSamples = new DeleteObjectSamples(detailText);
        deleteObjectSamples.execute(bizServer);

    }
    public void update(){
        final EditText editText = new EditText(FileUploadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(editText.getText().toString().trim());
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void update(String getCosPahtName){
        if(TextUtils.isEmpty(getCosPahtName)){
            Toast.makeText(FileUploadActivity.this,"文件名为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!getCosPahtName.startsWith("/")){
            bizServer.setFileId("/" + getCosPahtName);
        }else{
            bizServer.setFileId(getCosPahtName);
        }

        UpdateObjectSamples updateObjectSamples = new UpdateObjectSamples(detailText,true);
        updateObjectSamples.execute(bizServer);

    }

    public void query(){
        final EditText editText = new EditText(FileUploadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                getCosPahtName = editText.getText().toString().trim();
                query(getCosPahtName);
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void query(String getCosPahtName){
        if(TextUtils.isEmpty(getCosPahtName)){
            Toast.makeText(FileUploadActivity.this,"文件名为空", Toast.LENGTH_SHORT).show();
            return;
        }

        bizServer.setFileId(getCosPahtName);
        GetObjeceMetadataSamples getObjeceMetadataSamples = new GetObjeceMetadataSamples(detailText);
        getObjeceMetadataSamples.execute(bizServer);
    }
    public void download(){
        String url = urlText.getText().toString().trim();
        if(TextUtils.isEmpty(url)){
            Toast.makeText(this,"下载地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("url",url);
        intent.setClass(this,FileDownloadActivity.class);
        startActivity(intent);
    }
    public void uploadList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> listPath = new ArrayList<String>();
                String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
                String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                Cursor imagecursor = FileUploadActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
                if (imagecursor != null) {
                    Log.w("XIAO", "count =" + imagecursor.getCount());
                    int len = imagecursor.getCount() > 10 ? 10 : imagecursor.getCount();
                    int index = 0;
                    for (int i = 0; i < len; i++) {
                        imagecursor.moveToPosition(i);
                        index = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        listPath.add(imagecursor.getString(index));
                    }
                    imagecursor.close();
                }
                bizServer.setListPath(listPath);
                bizServer.setFileId("test_list");
                PutObjectSamples putObjectSamples = new PutObjectSamples(detailText,progreesText, PutObjectSamples.PUT_TYPE.LIST);
                putObjectSamples.execute(bizServer);
            }
        }).start();

    }

     public void uploadConcurrent() {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 List<String> listPath = new ArrayList<>();
                 String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
                 String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                 Cursor imagecursor = FileUploadActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null,orderBy + " DESC");
                 if(imagecursor != null){
                     Log.w("XIAO","count =" + imagecursor.getCount());
                     int len = imagecursor.getCount() > 4 ? 4 : imagecursor.getCount();
                     int index = 0;
                     for(int i = 0; i< len; i++){
                         imagecursor.moveToPosition(i);
                         index = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                         listPath.add(imagecursor.getString(index));
                     }
                     imagecursor.close();
                 }
                 String getCosPahtName = "test_test";
                 for(int i = 0; i< listPath.size(); i ++){
                     upload(listPath.get(i),getCosPahtName);
                 }
             }
         }).start();
     }
     public void upload(final String srcPath, final String getCosPahtName){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 if(TextUtils.isEmpty(srcPath)){
                     Toast.makeText(FileUploadActivity.this,"请选择文件", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 String filename = FileUtils.getFileName(srcPath);
                 String cosPath;
                 if(TextUtils.isEmpty(getCosPahtName)){
                     cosPath = filename;
                 }else{
                     cosPath = getCosPahtName + "/" + filename;
                 }

                 String sign = bizServer.getSign();
                 PutObjectRequest putObjectRequest = new PutObjectRequest();
                 putObjectRequest.setBucket(bizServer.getBucket());
                 putObjectRequest.setCosPath(cosPath);
                 putObjectRequest.setSign(sign);
                 putObjectRequest.setBiz_attr(null);
                 putObjectRequest.setSrcPath(srcPath);
                 putObjectRequest.setListener(new IUploadTaskListener(){
                     @Override
                     public void onSuccess(COSRequest cosRequest, COSResult cosResult) {

                         PutObjectResult result = (PutObjectResult) cosResult;
                         if(result != null){
                             StringBuilder stringBuilder = new StringBuilder();
                             stringBuilder.append("上传结果： ret=" + result.code + "; msg =" +result.msg + "\n");
                             stringBuilder.append(" access_url= " + result.access_url == null ? "null" :result.access_url + "\n");
                             stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" :result.resource_path + "\n");
                             stringBuilder.append(" url= " + result.url == null ? "null" :result.url);
                             final String strResult = stringBuilder.toString();
                             final String url = result.access_url == null ? "null" :result.access_url;
                             mainHandler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     Log.w("XIAO","url =" + url);
                                 }
                             });
                         }
                     }

                     @Override
                     public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
                         mainHandler.post(new Runnable() {
                             @Override
                             public void run() {
                                 Log.w("XIAO","上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg);
                             }
                         });
                     }

                     @Override
                     public void onProgress(final COSRequest cosRequest, final long currentSize, final long totalSize) {
                         mainHandler.post(new Runnable() {
                             @Override
                             public void run() {
                                 float progress = (float)currentSize/totalSize;
                                 progress = progress *100;
                                 Log.w("XIAO","进度：  " + (int)progress + "%" + cosRequest.getCosPath());
                             }
                         });
                     }

                     @Override
                     public void onCancel(COSRequest cosRequest, COSResult cosResult) {

                     }
                 });
                 PutObjectResult putObjectResult = bizServer.getCOSClient().putObject(putObjectRequest);
             }}).start();
     }
    public void uploadSlice(){
        final EditText editText = new EditText(FileUploadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this);
        builder.setTitle("请输入cos文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadSlice(editText.getText().toString().trim());
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void uploadSlice(String getCosPahtName){
        String srcPath = currentPath;
        if(srcPath == null)srcPath = Environment.getExternalStorageDirectory() + File.separator + "download/r.apk";
        if(TextUtils.isEmpty(srcPath)){
            Toast.makeText(FileUploadActivity.this,"请选择文件", Toast.LENGTH_SHORT).show();
            return;
        }
        String filename = FileUtils.getFileName(srcPath);
        String cosPath;
        if(TextUtils.isEmpty(getCosPahtName)){
            cosPath = filename;
        }else{
            cosPath = getCosPahtName + "/" + filename;
        }
        bizServer.setFileId(cosPath);
        bizServer.setSrcPath(srcPath);
        PutObjectSamples putObjectSamples = new PutObjectSamples(detailText,progreesText, PutObjectSamples.PUT_TYPE.SLICE);
        putObjectSamples.execute(bizServer);

    }


    public void onAdd(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,OPENFILE_CODE);
    }
    public String getPath(Context context, Uri uri){
        if("content".equalsIgnoreCase(uri.getScheme())){
            String[] projection = {MediaStore.MediaColumns.DATA};
            String colum_name = "_data";
            Cursor cursor = null;
            try{
                cursor = context.getContentResolver().query(uri,projection,null,null,null);
                Log.w("XIAO","count =" +cursor.getCount());
                if(cursor != null && cursor.moveToFirst()){
                    int colum_index = cursor.getColumnIndex(colum_name);
                    return cursor.getString(colum_index);
                }
            }catch (Exception e){
                Log.w("XIAO",e.getMessage(),e);
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }else {
            Toast.makeText(this, "选择文件路径为空", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }
        switch(requestCode){
            case OPENFILE_CODE:
                Uri uri = data.getData();
                currentPath = getPath(this, uri);
                pathText.setText(currentPath);
                break;
            default:
                break;
        }
    }
}



