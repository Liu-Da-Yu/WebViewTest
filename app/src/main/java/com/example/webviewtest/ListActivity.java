package com.example.webviewtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.webviewtest.utils.L;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Call;

import java.io.File;
import java.io.IOException;

/**
 * author: JST - Dayu
 * date:   2019/8/23  14:25
 * context:
 */
@SuppressWarnings("all")
public class ListActivity extends AppCompatActivity {

    private TextView textView;
    OkHttpClient okHttpClient =  new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persion_info_list);

        //一般都是创建时初始化页面元素，然后设成全局变量

        String userPhone = getIntent().getStringExtra("userPhone");
        textView = findViewById(R.id.persionInfoListTv);
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
        System.out.println("getDate");
        try {
            //getTest();
            //postTest();
            doPostFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //使用OKHttp发送post请求
    public void postTest() throws IOException{

        Request.Builder builder = new Request.Builder();

        //构造一个requestBody
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        RequestBody build = formEncodingBuilder.add("username", "2").add("password", "2").build();

        //构造一个request
        Request request = builder.url("http://192.168.14.20:8899/postTest").post(build).build();

        //或者传递一个json
        //RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"), "{username:123}");


        excuteQueue(request);
    }

    public void doPostFile() throws IOException {

        Request.Builder builder = new Request.Builder();

        //需要设置访问权限
        File file = new File(Environment.getExternalStorageDirectory(),"banner2.jpg");
        if(!file.exists()){
            L.e("路径不存在");
            return;
        }

        //类型可以百度搜索 mime type    （如果是又有图片，又有表单需要使用 MultipartBuilder 详见截图 ）
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = builder.url("http://192.168.14.20:8899/postTest").post(requestBody).build();
        excuteQueue(request);
    }

    //下载文件
    public void doDownLode(){

    }

    /**
     *  author:  daYu
     *  date:    2019/8/26 16:11
     *  context: session问题
     *
     *
     * */



    //使用OKHttp发送get请求
    public void getTest() throws IOException {

        //全局的配置,执行者，设置比如超时，cookie等
        OkHttpClient okHttpClient =  new OkHttpClient();

        //构造一个请求(request)
        Request.Builder builder = new Request.Builder();

        //构造者模式
        Request request = builder
                .get()
                .url("http://192.168.14.20:8899/getTest?text")
                .build();

        excuteQueue(request);
    }


    private void excuteQueue(Request request){
        //封装为一个call,执行对象。
        Call call = okHttpClient.newCall(request);

        //同步执行，返回Response
        //Response response = call.execute();

        //或者异步执行，提供一个回调函数Callback()
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.e("onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                //获取返回的字符串
                final String string = response.body().string();
                L.e("onResponse:" + string);

                //返回的字符串设置到textView上(这是不对的，子线程不能设置ui)
                //

                /**
                 * 此处必须是子线程，因为如果下载文件，子线程中可以读写操作，不会占用太多内存。
                 * 只会占用buffer的大小。
                 * */
                //InputStream inputStream = response.body().byteStream();

                //此处可以使用handle切回ui线程，也可以直接使用ui线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(string);
                    }
                });
            }
        });
    }

    //安卓的弹框演示及使用
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
