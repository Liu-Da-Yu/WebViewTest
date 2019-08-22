package com.example.webviewtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webviewtest.db.MySql;
import com.example.webviewtest.inter.JsInterfase;
import com.example.webviewtest.inter.JsBridge;

public class MainActivity extends AppCompatActivity implements JsBridge {

    private WebView webView;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MySql().connDB();

        //判断是否有用户信息 有用户信息直接进入
        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String currentUser = settings.getString("currentUser", "");
        if( currentUser==null || "".equals(currentUser) ) {
            setContentView(R.layout.activity_main);
            initLoginView(savedInstanceState);
        }else{
            setContentView(R.layout.persion_info_list);
            TextView viewById = findViewById(R.id.persionInfoListTv);
            viewById.setText("欢迎您:"+currentUser);
        }
    }

    private void initLoginView(Bundle savedInstanceState) {
        webView = findViewById(R.id.login_WebView);

        mHandler = new Handler();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterfase(this),"Login");
        webView.loadUrl("file:///android_asset/login.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isLoginSuccess(final String is) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String[] split = is.split(";");
                if(Boolean.parseBoolean(split[0])){
                    Toast.makeText(MainActivity.this,"登录成功！欢迎您："+split[1],Toast.LENGTH_SHORT).show();

                    //登录成功保存用户信息
                    setContentView(R.layout.persion_info_list);
                    TextView persionInfoListTv = findViewById(R.id.persionInfoListTv);
                    persionInfoListTv.setText("欢迎您:"+split[1]);
                    SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("currentUser",split[1]);
                    editor.commit();
                    setContentView(R.layout.persion_info_list);

                }else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误，请重新登录！",Toast.LENGTH_SHORT).show();
                    //setContentView(R.layout.activity_main);
                }
            }
        });
    }
}
