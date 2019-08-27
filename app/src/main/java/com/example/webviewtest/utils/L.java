package com.example.webviewtest.utils;

import android.util.Log;

/**
 * author:  dayu
 * date:   2019/8/26  11:51
 * context:
 */
@SuppressWarnings("all")
public class L {

    private static boolean debug =  true ;
    private static String TAG = "okhttp_test_tag";

    public static void e(String msg){
        if(debug){
            Log.e(TAG,msg);
        }
    }
}
