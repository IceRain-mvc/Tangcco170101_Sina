package com.tencent.qclouddemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.qclouddemo.samples.BizServer;
import com.tencent.qclouddemo.samples.CreateDirSamples;
import com.tencent.qclouddemo.samples.GetObjeceMetadataSamples;
import com.tencent.qclouddemo.samples.ListDirSamples;
import com.tencent.qclouddemo.samples.RemoveEmptyDirSample;
import com.tencent.qclouddemo.samples.UpdateObjectSamples;

/**
 * Created by bradyxiao on 2016/9/13.
 */
public class DirActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createDir, deleteDir, updateDir, statDir, listDir;
    private TextView pathText, detailText;
    BizServer bizServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir);
        createDir = (Button)findViewById(R.id.create);
        deleteDir = (Button)findViewById(R.id.delete);
        updateDir = (Button)findViewById(R.id.update);
        statDir = (Button)findViewById(R.id.stat);
        listDir = (Button)findViewById(R.id.listDir);

        pathText = (TextView)findViewById(R.id.cosPath);
        detailText = (TextView)findViewById(R.id.detail);

        createDir.setOnClickListener(this);
        deleteDir.setOnClickListener(this);
        updateDir.setOnClickListener(this);
        statDir.setOnClickListener(this);
        listDir.setOnClickListener(this);

        bizServer = BizServer.getInstance(getApplicationContext());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.create:
                createDir();
                break;
            case R.id.delete:
                deleteDir();
                break;
            case R.id.update:
                updateDir();
                break;
            case R.id.stat:
                statDir();
                break;
            case R.id.listDir:
                listDir();
                break;
        }
    }
    /**
     * 1)创建书写合法目录 dirName = test;
     * 2)创建书写不合法目录 dirName = test>
     * 3)创建长度非法的目录  dirName = 1234567890122345678901; dirName.length()>20
     * 4)创建子目录下的目录 dirName = test/test
     * 5)创建多级的目录 dirName = test/test/test/test/test
     * 6)创建已存在的目录 dirName = test
     * 7)创建已删除的目录
     */
    public void createDir(){
        final EditText editText = new EditText(DirActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(DirActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createDir(editText.getText().toString().trim());
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
    public void createDir(String dirName){
        if(TextUtils.isEmpty(dirName)){
            Toast.makeText(DirActivity.this,"文件夹名为空", Toast.LENGTH_SHORT).show();
            return;
         }
        if(!dirName.endsWith("/")){
            dirName = dirName + "/";
        }
        pathText.setText(dirName);
        bizServer.setFileId(dirName);
        CreateDirSamples createDirSamples = new CreateDirSamples(detailText);
        createDirSamples.execute(bizServer);
    }

    public void deleteDir(){
        final EditText editText = new EditText(DirActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(DirActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDir(editText.getText().toString().trim());
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
    public void deleteDir(String dirName){
        if(TextUtils.isEmpty(dirName)){
            Toast.makeText(DirActivity.this,"文件夹名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!dirName.endsWith("/")){
            dirName = dirName + "/";
        }
        pathText.setText(dirName);
        bizServer.setFileId(dirName);
        RemoveEmptyDirSample removeEmptyDirSample = new RemoveEmptyDirSample(detailText);
        removeEmptyDirSample.execute(bizServer);
    }

    /**
     * 1)目录更新：单级或多级目录
     *
     */
    public void updateDir(){
        final EditText editText = new EditText(DirActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(DirActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDir(editText.getText().toString().trim());
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
    public void updateDir(String dirName) {
        if (TextUtils.isEmpty(dirName)) {
            Toast.makeText(this, "路径为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!dirName.endsWith("/")){
            dirName = dirName + "/";
        }
        pathText.setText(dirName);
        pathText.setText(dirName);
        bizServer.setFileId(dirName);
        UpdateObjectSamples updateObjectSamples = new UpdateObjectSamples(detailText,false);
        updateObjectSamples.execute(bizServer);

    }
    public void statDir(){
        final EditText editText = new EditText(DirActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(DirActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(editText);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                statDir(editText.getText().toString().trim());
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
    public void statDir(String dirName){
        if (TextUtils.isEmpty(dirName)) {
            Toast.makeText(DirActivity.this, "文件夹名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!dirName.endsWith("/")) {
            dirName = dirName + "/";
        }
        pathText.setText(dirName);
        bizServer.setFileId(dirName);
        GetObjeceMetadataSamples getObjeceMetadataSamples = new GetObjeceMetadataSamples(detailText);
        getObjeceMetadataSamples.execute(bizServer);
    }
        /**
     * 1)bucket根目录自动展示   cosPath = "/"
     * 2）指定目录下的目录列表展示  cosPath = "test/"
     * 3）指定目录下存在文件和目录的展示   cosPath = "test/"
     * 4）指定目录下无目录和文件的展示     cosPath = "test/"
     * 5）单级木录下 或多级目录下的展示    cosPath = "test/"
     * 6）目录前缀查询，存在的关键子       cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 7）目录前缀查询，不存在的关键字     cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 8）目录前缀查询，单级和多级
     * 9）目录前缀查询，只存在子目录或只存在子文件或都存在   cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 10）设置显示数值，1, 10, 100
     */

    public void listDir(){
        final EditText editText = new EditText(DirActivity.this);
        final CheckBox checkBox = new CheckBox(DirActivity.this);
        final EditText perfixText = new EditText(DirActivity.this);
        perfixText.setEnabled(false);
        checkBox.setText("开启前缀查询");
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
                perfixText.setEnabled(true);
            }
        });
        LinearLayout linearLayout = new LinearLayout(DirActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);
        linearLayout.addView(checkBox);
        linearLayout.addView(perfixText);

        AlertDialog.Builder builder = new AlertDialog.Builder(DirActivity.this);
        builder.setTitle("请输入文件夹路径");
        builder.setView(linearLayout);
        builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String getDirName = editText.getText().toString().trim();
                String getPrefix = perfixText.getText().toString().trim();
                Log.w("XIAO","checkBox ==" + checkBox.isSelected());
                if(!TextUtils.isEmpty(getPrefix)){
                    listDir(getDirName,getPrefix);
                }else{
                    listDir(getDirName,null);
                }
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
    public void listDir(String dirName, String prefix){
        if(TextUtils.isEmpty(dirName)){
            dirName = "/";
        }else{
            if(!dirName.endsWith("/")){
                dirName = dirName + "/";
            }
        }
        pathText.setText(dirName);
        bizServer.setFileId(dirName);
        bizServer.setPrefix(prefix);
        ListDirSamples listDirSamples = new ListDirSamples(detailText);
        listDirSamples.execute(bizServer);
    }

}
