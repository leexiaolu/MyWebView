package com.example.leesnriud.mywebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_web1, R.id.bt_web2, R.id.bt_web3, R.id.bt_web4, R.id.bt_web5, R.id.bt_web6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_web1:
                intent = new Intent(MainActivity.this,Web1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_web2:
                intent = new Intent(MainActivity.this,Web2Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_web3:
                intent = new Intent(MainActivity.this,Web3Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_web4:
                intent = new Intent(MainActivity.this,Web4Activity.class);
                startActivity(intent);
                break;
            case R.id.bt_web5:
                break;
            case R.id.bt_web6:
                break;
        }
    }
}
