package com.tangcco.android.TangccoAndroid030_25;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class WebViewBrowserActivity extends Activity implements View.OnClickListener {
    private WebView mWebView;
    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_webview_browser);
        mWebView = (WebView) findViewById(R.id.webView6);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 设置支持JavaScript脚本
        webSettings.setAllowFileAccess(true); // 设置可以访问文件
        webSettings.setBuiltInZoomControls(true); // 设置支持缩放


        mButton = (Button) findViewById(R.id.Button01);
        mEditText = (EditText) findViewById(R.id.EditText01);
        mEditText.setText("http://www.baidu.com");

        // 设置WebViewClient
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//     getCacheDir().getAbsolutePath();//data/data/包名/cache

        mButton.setOnClickListener(this);

        // 设置WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {//0-100
                super.onProgressChanged(view, newProgress);
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
               // setProgress(newProgress * 100);//0-10000
            }
        });
    }

    @Override
    public void onClick(View v) {
        // 取得编辑框中我们输入的内容
        String url = mEditText.getText().toString();
        // 判断输入的内容是不是网址
        if (URLUtil.isNetworkUrl(url)) {
            // 装载网址
            mWebView.loadUrl(url);
        } else {
            mEditText.setText("输入网址错误，请重新输入");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
