package com.fragment01;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/5.
 * support.v4.app.Fragment 兼容性
 */


public class FragmentXml extends Fragment {
    /**
     *
     * @param inflater 布局填充器
     * @param container 父容器
     * @param savedInstanceState  savedInstanceState保存状态
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xml_layout, container, false);
        return view;
    }
}
