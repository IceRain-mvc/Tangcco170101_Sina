package com.tangcco.android.TangccoAndroid030_25;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class ActNewActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.imgbtn_left)
    private ImageButton imgbtn_left;
    @ViewInject(R.id.imgbtn_text)
    private ImageButton imgbtn_text;
    @ViewInject(R.id.imgbtn_right)
    private ImageButton imgbtn_right;
    @ViewInject(R.id.loading_view)
    private View loading_view;
    @ViewInject(R.id.txt_title)
    private TextView txt_title;


    @ViewInject(R.id.btn_left)
    private Button btn_left;
    @ViewInject(R.id.btn_right)
    private ImageButton btn_right;


    private WebView news_detail_wv;
    private WebSettings settings;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detail);

        ViewUtils.inject(this);
        url = "http://news.qq.com/";

        news_detail_wv = (WebView) findViewById(R.id.news_detail_wv);

        initTitleBar();
        initView();
    }

    private void initTitleBar() {
        // TODO Auto-generated method stub
        btn_left.setVisibility(View.GONE);
        btn_right.setVisibility(View.GONE);
        imgbtn_left.setImageResource(R.drawable.back);
        imgbtn_text.setVisibility(View.VISIBLE);
        imgbtn_text.setImageResource(R.drawable.icon_textsize);
        imgbtn_right.setImageResource(R.drawable.icon_share);
        imgbtn_right.setOnClickListener(this);
        imgbtn_text.setOnClickListener(this);
        imgbtn_left.setOnClickListener(this);
        txt_title.setText("腾讯新闻");
    }

    private void initView() {
        settings = news_detail_wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存

        news_detail_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                news_detail_wv.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading_view.setVisibility(View.GONE);
            }
        });

        news_detail_wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                txt_title.setText(title);
            }
        });

        news_detail_wv.loadUrl(url);
    }

    int i = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_left:
                finish();
                break;
            case R.id.imgbtn_text: {
                switch_text_size(i);
                i++;
                if (i > 3) {
                    i = 1;
                }
            }
            break;
            case R.id.imgbtn_right:
                Toast.makeText(this, "分享", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void switch_text_size(int text_size) {
        // TODO Auto-generated method stub
        switch (text_size) {
            case 1:
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 2:
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                settings.setTextSize(WebSettings.TextSize.LARGER);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && news_detail_wv.canGoBack()) {
            news_detail_wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
