package com.fragment01;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private FragmentJava fragmentJava;
    private FragmentJava02 fragmentJava02;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建Fragment
        fragmentJava = new FragmentJava();
        //初始化FragmentManager对象
        manager = getSupportFragmentManager();
        //创建一个FragmentTransaction对象 -->添加 替换 删除 隐藏Fragmennt
        //FragmentTransaction-->事务
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_layout, fragmentJava);
        //显示Fragment 提交
        transaction.commit();
    }

    public void click(View view) {
        if (fragmentJava == null) {
            fragmentJava = new FragmentJava();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        //第一个参数 放到容器中  第二个参数 将哪一个Fragment放进去
        transaction.replace(R.id.content_layout, fragmentJava);
        transaction.commit();

    }

    public void click02(View view) {
        if (fragmentJava02 == null) {
            fragmentJava02 = new FragmentJava02();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        //第一个参数 放到容器中  第二个参数 将哪一个Fragment放进去
        transaction.replace(R.id.content_layout, fragmentJava02);
        transaction.commit();
    }
}
