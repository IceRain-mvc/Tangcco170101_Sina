package com.tangcco.android.TangccoAndroid030_25;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class MainActivity extends Activity {
    private WebView mWebView;
    private Button btnCallJs;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView);
//        StringBuffer html = new StringBuffer();
//        html.append("<html>");
//        html.append("<head>");
//        html.append("<title>");
//        html.append("webview显示的Html页面");
//        html.append("</title>");
//        html.append("<body>");
//        html.append("<h1>");
//        html.append("Hello,WebView");
//        html.append("</h1>");
//        html.append("</body>");
//        html.append("</html>");
//        mWebView.loadData(html.toString(),"text/html","utf-8");

        //mWebView.loadUrl("file:///android_asset/index.html");

        // mWebView.loadUrl("http://www.baidu.com");

        handler = new Handler();

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);//启用JavaScript功能
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebSettings.setDisplayZoomControls(true);
        mWebSettings.setSupportZoom(true);

        mWebView.loadUrl("file:///android_asset/demo.html");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });

        btnCallJs = (Button) findViewById(R.id.btnCallJs);
        btnCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (i % 2 == 0) {
                            mWebView.loadUrl("javascript:wave()");
                        } else {
                            mWebView.loadUrl("javascript:waveBack()");
                        }
                        i++;
                    }
                });
            }
        });

        mWebView.addJavascriptInterface(new MyDemoInterface(), "demo");

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(
                        MainActivity.this).setTitle("标题")
                        .setMessage(message)
                        .setPositiveButton("ok", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                result.confirm();
                                // MainActivity.this.finish();
                            }

                        }).setCancelable(false);
                b2.create().show();
                return true;
            }
        });
    }

    int i = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyDemoInterface {
        public MyDemoInterface() {
        }

        @JavascriptInterface
        public void callAndroid() {
            handler.post(new Runnable() {
                public void run() {
                    if (i % 2 == 0) {
                        mWebView.loadUrl("javascript:wave()");
                    } else {
                        mWebView.loadUrl("javascript:waveBack()");
                    }
                    i++;
                }
            });
        }
    }
}
