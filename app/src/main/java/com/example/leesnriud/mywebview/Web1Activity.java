package com.example.leesnriud.mywebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Web1Activity extends AppCompatActivity {

    @BindView(R.id.wv_webview1)
    MyWebView wvWebview1;
    @BindView(R.id.bt_top)
    Button btTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);
        ButterKnife.bind(this);

        WebSettings settings = wvWebview1.getSettings();
        settings.setJavaScriptEnabled(true);//设置支持js
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放

        //设置webview的cookies数据
//        CookieSyncManager.createInstance(Web1Activity.this);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.setCookie("url", "cookies");  //cookies是要设置的cookie字符串
//        CookieSyncManager.getInstance().sync();

        wvWebview1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //获取webview的cookie
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                Log.e("111", "Cookies = " + CookieStr);
                super.onPageFinished(view, url);
            }
        });

        wvWebview1.loadUrl("https://www.baidu.com");

        wvWebview1.setOnScrollChangedCallback(new MyWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                if (dy > 0) {
                    btTop.setVisibility(View.VISIBLE);
                }else {
                    btTop.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.tv_back, R.id.tv_flush, R.id.tv_top,R.id.bt_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_flush:
                wvWebview1.reload();
                break;
            case R.id.tv_top:
                wvWebview1.setScrollY(0);
                break;
            case R.id.bt_top:
                wvWebview1.setScrollY(0);
                btTop.setVisibility(View.GONE);
                break;
        }
    }


}
