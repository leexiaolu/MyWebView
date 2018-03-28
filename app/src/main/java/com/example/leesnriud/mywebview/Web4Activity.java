package com.example.leesnriud.mywebview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Web4Activity extends AppCompatActivity {

    @BindView(R.id.wv_web4)
    WebView wvWeb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web4);
        ButterKnife.bind(this);
        WebSettings settings = wvWeb4.getSettings();
        settings.setJavaScriptEnabled(true);//设置支持js
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放

        wvWeb4.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
        });

        wvWeb4.loadUrl("http://sj.qq.com/myapp/");

//        //通过其他浏览器下载
//        wvWeb4.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
//                Log.e("111","开始下载");
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
//            }
//        });

        //自己下载
        wvWeb4.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Log.e("111","onDownloadStart被调用：下载链接：" + url);
                new Thread(new DownLoadThread(url)).start();
            }
        });
    }

}
