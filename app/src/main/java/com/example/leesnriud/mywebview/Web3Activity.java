package com.example.leesnriud.mywebview;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Web3Activity extends AppCompatActivity {

    @BindView(R.id.wv_web3)
    WebView wvWeb3;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web3);
        ButterKnife.bind(this);

        wvWeb3.getSettings().setJavaScriptEnabled(true);
        wvWeb3.getSettings().setSaveFormData(false);
        wvWeb3.getSettings().setSavePassword(false);
        wvWeb3.getSettings().setSupportZoom(false);
        wvWeb3.getSettings().setDefaultTextEncodingName("UTF-8");
        wvWeb3.addJavascriptInterface(new SharpJS(), "sharp");
        wvWeb3.loadUrl("file:///android_asset/leesnriud.html");
    }

    //自定义一个Js的业务类,传递给JS的对象就是这个,调用时直接javascript:sharp.personlist()
    public class SharpJS {
        @JavascriptInterface
        public void personlist() {
            wvWeb3.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("personlist()方法执行了！");
                        String json = buildJson(getContacts());
                        wvWeb3.loadUrl("javascript:show('" + json + "')");
                    } catch (Exception e) {
                        System.out.println("设置数据失败" + e);
                    }
                }
            });
        }

        @JavascriptInterface
        public void call(String phone) {
            System.out.println("call()方法执行了！");
            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(it);
        }
    }

    //将获取到的联系人集合写入到JsonObject对象中,再添加到JsonArray数组中
    public String buildJson(List<Person> person)throws Exception
    {
        JSONArray array = new JSONArray();
        for(Person persons:person)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", persons.getId());
            jsonObject.put("name", persons.getName());
            jsonObject.put("phone", persons.getPhone());
            array.put(jsonObject);
        }
        return array.toString();
    }

    //定义一个获取联系人的方法,返回的是List<Contact>的数据
    public List<Person> getContacts()
    {
        List<Person> Contacts = new ArrayList<Person>();
        //①查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor.moveToNext())
        {
            Person person = new Person();
            //获取联系人姓名,手机号码
            person.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            person.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            person.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            Contacts.add(person);
        }
        cursor.close();
        return Contacts;
    }
}
