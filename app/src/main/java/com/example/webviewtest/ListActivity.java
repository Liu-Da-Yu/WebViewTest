package com.example.webviewtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * author: JST - Dayu
 * date:   2019/8/23  14:25
 * context:
 */
@SuppressWarnings("all")
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persion_info_list);

        String userPhone = getIntent().getStringExtra("userPhone");
        TextView textView = findViewById(R.id.persionInfoListTv);
        textView.setText(userPhone);
        Button quit = findViewById(R.id.quitBtn);
        quit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                edit.remove("currentUser");
                edit.commit();

                //切换到登录页面
                Intent i = new Intent();
                i.setClass(ListActivity.this,MainActivity.class);
                ListActivity.this.startActivity(i);

            }
        });
    }

    //获取数据
    public void getData(View view) {
        //RestTemplate restTemplate = new RestTemplate();
       // restTemplate.
    }

    //安卓弹框
    public void clickDuihua(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("世界上最遥远的距离是没有网");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ListActivity.this,"点了确定",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ListActivity.this,"点了取消",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}
