package com.penglin.myapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.penglin.myapplication.util.AppSigning;

import java.util.List;

public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener {


    EditText inputPackageNameEt;
    TextView liveSignTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        findViewById(R.id.copeSignButton).setOnClickListener(this);
        findViewById(R.id.getSignButton).setOnClickListener(this);
        findViewById(R.id.seeAllAppButton).setOnClickListener(this);
        inputPackageNameEt = findViewById(R.id.inputPackageNameEt);

        liveSignTv = findViewById(R.id.liveSignTv);
    }


    /**
     * 赋值签名
     *
     * @param context
     * @param signStr
     */
    public static void copy(Context context, String signStr) {
        ClipboardManager myClipboard;
        ClipData myClip;
        myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("sign", signStr);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getSignButton:
                sign();
                break;
            case R.id.copeSignButton:
                copySignStr();
                break;
            case R.id.seeAllAppButton:
                jumpToAllAppInfo();
                break;
        }
    }

    private void jumpToAllAppInfo() {
        startActivity(new Intent(this,SeeSystemAppInfoActivity.class));
    }

    //赋值
    private void copySignStr() {
        if (sign == null || sign.equals(""))
            return;
        copy(this, sign);
    }

    //获得签名
    String sign;

    private void sign() {
        String packageName = inputPackageNameEt.getText() + "";
        if (packageName.equals("")) {
            return;
        }
        String sign = AppSigning.getSignMd5Str(this, packageName);
        this.sign = sign;
        liveSignTv.setText(sign);
    }
}
