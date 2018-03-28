package com.example.leesnriud.mywebview;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lee.snriud on 2018/3/28.
 */

public class DownLoadThread implements Runnable {

    private String dlUrl;

    public DownLoadThread(String dlUrl) {
        this.dlUrl = dlUrl;
    }

    @Override
    public void run() {
        Log.e("111", "开始下载");
        InputStream in = null;
        FileOutputStream fout = null;
        try {
            URL httpUrl = new URL(dlUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            in = conn.getInputStream();
            File downloadFile, sdFile;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.e("111","SD卡可写");
                downloadFile = Environment.getExternalStorageDirectory();
                sdFile = new File(downloadFile, "xxx.apk");
                fout = new FileOutputStream(sdFile);
            }else{
                Log.e("111","SD卡不存在或者不可读写");
            }
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                fout.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("111", "下载完毕");
    }
}
