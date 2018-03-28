package com.example.leesnriud.mywebview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by lee.snriud on 2018/3/28.
 */

public class MyObject {

    private Context context;


    public MyObject(Context context) {
        this.context = context;
    }

    //将显示Toast和对话框的方法暴露给JS脚本调用
    @JavascriptInterface
    public void showToast(String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showDialog() {
        new AlertDialog.Builder(context)
                .setTitle("列表")
                .setItems(new String[]{"111", "222", "333", "444", "555"}, null)
                .setPositiveButton("确定", null).create().show();
    }
}
