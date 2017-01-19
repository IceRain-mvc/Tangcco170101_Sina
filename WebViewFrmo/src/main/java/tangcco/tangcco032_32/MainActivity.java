package tangcco.tangcco032_32;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button button;
    private Handler mHandler;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);

        //1:加载一个自己拼接的html页面  loadData
//        StringBuffer builder = new StringBuffer();
//        builder.append("<html>");
//        builder.append("<head>");
//        builder.append("<title>");
//        builder.append("webview显示的Html页面");
//        builder.append("</title>");
//        builder.append("</head>");
//        builder.append("<body>");
//        builder.append("<h1>");
//        builder.append("hello webview");
//        builder.append("</h1>");
//        builder.append("</body>");
//        builder.append("</html>");
//
//        mWebView.loadData(builder.toString(), "text/html", "utf-8");
//

        //2:加载assets目录中的界面

//        getAssets().open()

//        mWebView.loadUrl("file:///android_asset/index.html");
        //加载一个网址
//        mWebView.loadUrl("http://www.baidu.com");


        /**
         * 在客户端加载网页
         */
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });


        mWebView.loadUrl("file:///android_asset/demo.html");
        WebSettings settings = mWebView.getSettings();
        //可以与js调用
        settings.setJavaScriptEnabled(true);

        mHandler = new Handler();

        button = (Button) findViewById(R.id.btn_load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //通过android中的按钮 调用js的方法
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

        mWebView.addJavascriptInterface(new MyInterface(), "demo");


        //弹框
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);

            }
        });

    }

    class MyInterface {
        public MyInterface() {
        }

        //在SDK4.2版本以前 不用加
        //4.2以后 就要加注解

        @JavascriptInterface
        public void callAndroid() {
            //通过webview里面的点击事件 调用此方法
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "通过webview点击事件空时java代码", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }



    @Override//监听按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当按键是返回键 并且webview可以返回
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            //返回上一个webview界面
            mWebView.goBack();
            return true;
        }
        //默认
        return super.onKeyDown(keyCode, event);
    }
}
