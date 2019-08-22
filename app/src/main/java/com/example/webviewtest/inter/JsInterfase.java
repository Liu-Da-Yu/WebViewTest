package com.example.webviewtest.inter;

import android.webkit.JavascriptInterface;

/**
 * author: JST - Dayu
 * date:   2019/8/22  13:56
 * context:
 */
@SuppressWarnings("all")
public class JsInterfase {

    private JsBridge jsBridge;

    public JsInterfase(JsBridge jsBridge){
        this.jsBridge = jsBridge;
    }

    public JsInterfase(){
    }

    @JavascriptInterface
    public void isLoginSuccess(String is){
        jsBridge.isLoginSuccess(is);
    }
}
