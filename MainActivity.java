package com.sachin.nseanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.net.Uri;
import android.content.Intent;
import android.webkit.ValueCallback;

public class MainActivity extends Activity {
    WebView web; ValueCallback<Uri[]> fileCallback;
    @Override public void onCreate(Bundle b){ super.onCreate(b);
        web=new WebView(this); setContentView(web);
        WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setAllowFileAccess(true); s.setAllowContentAccess(true);
        web.setWebViewClient(new WebViewClient()); web.setWebChromeClient(new WebChromeClient(){
            @Override public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> cb, FileChooserParams p){
                fileCallback=cb; Intent i=p.createIntent(); try{startActivityForResult(i,42);}catch(Exception e){fileCallback=null; return false;} return true;
            }
        }); web.loadUrl("file:///android_asset/index.html");
    }
    @Override protected void onActivityResult(int r,int c,Intent d){ super.onActivityResult(r,c,d); if(r==42&&fileCallback!=null){ fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(c,d)); fileCallback=null; } }
    @Override public void onBackPressed(){ if(web.canGoBack()) web.goBack(); else super.onBackPressed(); }
}
