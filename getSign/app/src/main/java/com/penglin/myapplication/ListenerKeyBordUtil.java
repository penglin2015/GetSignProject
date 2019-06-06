package com.penglin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.support.v7.widget.TintContextWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class ListenerKeyBordUtil {

    EditText editText;
    Activity activity;

    public ListenerKeyBordUtil(EditText editText) {
        this.editText = editText;
        Object object=editText.getContext();
        if(object instanceof TintContextWrapper){
            activity= (Activity) ((TintContextWrapper) object).getBaseContext();
        }else{
            activity = (Activity) editText.getContext();
        }
    }

    /**
     * 监听键盘按钮搜索
     */
    public ListenerKeyBordUtil keybordListener() {
        editText.setSingleLine();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    hideKeyBord();
                    callback(i, editText);
                    return true;
                }
                return false;
            }
        });
        return this;
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyBord() {
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager == null)
            return;
        View v = activity.getCurrentFocus();
        if (v == null)
            return;
        IBinder iBinder = v.getWindowToken();
        if (iBinder == null)
            return;
        methodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void callback(int keyInfo, EditText editText) {

    }


    /**
     * 设置右下角动作标签
     * @param imeName
     * @return
     */
    public ListenerKeyBordUtil setImeOptionsStyle(String imeName,int imeAction){
        editText.setImeOptions(imeAction);
        editText.setImeActionLabel(imeName,imeAction);
        keybordListener();
        return this;
    }

}
