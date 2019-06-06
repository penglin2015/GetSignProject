package com.penglin.myapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.penglin.myapplication.util.AppSigning;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 代码内容:SeeSystemAppInfoActivity
 * 开发者:Lenovo
 * 时间:2019/6/6 15:15
 * 描述:查看所有的包名和签名
 **/
public class SeeSystemAppInfoActivity extends AppCompatActivity {
    public final static String TAG = SeeSystemAppInfoActivity.class.getSimpleName();

    @BindView(R.id.appInfoList)
    RecyclerView appInfoList;

    AppInfoAdapter appInfoAdapter;

    ListenerKeyBordUtil listenerKeyBordUtil;
    @BindView(R.id.inputAppName)
    EditText inputAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_system_app_info);
        ButterKnife.bind(this);
        appInfoList.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
        appInfoAdapter = new AppInfoAdapter(this);
        appInfoList.addItemDecoration(new SpaceItemVaDecoration(20));
        appInfoList.setAdapter(appInfoAdapter);

        loadApps();

        listenerKeyBordUtil = new ListenerKeyBordUtil(inputAppName) {
            @Override
            public void callback(int keyInfo, EditText editText) {
                //进行搜索
                changeSearch(editText.getText()+"");

            }
        }.keybordListener();
        listenerKeyBordUtil.setImeOptionsStyle("搜索", EditorInfo.IME_ACTION_SEARCH);
    }

    private void changeSearch(String s) {
        if(s==null||s.equals("")) {
            appInfoAdapter.setAppInfos(appInfoModels);
            return;
        }
        List<AppInfoModel> results=new ArrayList<>();
        for(AppInfoModel appInfoModel:appInfoModels){
            if(appInfoModel.name.contains(s)){
                results.add(appInfoModel);
            }
        }
        appInfoAdapter.setAppInfos(results);
    }


    //获取所有安卓的名称和包名

    public void getAllAppNames(View view) {
        PackageManager pm = getPackageManager();
        //PackageManager.GET_UNINSTALLED_PACKAGES==8192
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        //PackageManager.GET_SHARED_LIBRARY_FILES==1024
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //PackageManager.GET_META_DATA==128
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_META_DATA);
        //List<PackageInfo> list2=pm.getInstalledPackages(0);
        //List<PackageInfo> list2=pm.getInstalledPackages(-10);
        //List<PackageInfo> list2=pm.getInstalledPackages(10000);
        for (PackageInfo packageInfo : list2) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName = packageInfo.packageName;

            Log.e("第一种方式:" + appName, packageName);
        }
    }

    List<AppInfoModel> appInfoModels = new ArrayList<>();

    //获取所有的包名和app名称
    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        appInfoModels.clear();

        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            Log.e("第二种方式:", name + "----" + packageName + "----" + cls);
            String sign = AppSigning.getSignMd5Str(this, packageName);
            AppInfoModel appInfoModel = new AppInfoModel();
            appInfoModel.name = name.toString();
            appInfoModel.appPacketName = packageName;
            appInfoModel.lunchName = cls.toString();
            appInfoModel.sign = sign;
            appInfoModels.add(appInfoModel);
        }
        appInfoAdapter.setAppInfos(appInfoModels);
    }
}
