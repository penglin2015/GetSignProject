package com.penglin.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppInfoAdapter extends RecyclerView.Adapter {


    Context context;



    public AppInfoAdapter(Context context) {
        this.context = context;
    }


    List<AppInfoModel> appInfoModels;

    public void setAppInfos(List<AppInfoModel> appInfos) {
        this.appInfoModels = appInfos;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app_info, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AppInfoViewHolder appInfoViewHolder = (AppInfoViewHolder) holder;
        final AppInfoModel appInfoModel = appInfoModels.get(position);
        appInfoViewHolder.appNameTv.setText(appInfoModel.name);
        appInfoViewHolder.appPacketNameTv.setText(appInfoModel.appPacketName);
        appInfoViewHolder.lunchNameTv.setText(appInfoModel.lunchName);
        appInfoViewHolder.signTv.setText(appInfoModel.sign);

        appInfoViewHolder.copeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(context,appInfoModel.sign);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfoModels.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class AppInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.appNameTv)
        TextView appNameTv;
        @BindView(R.id.appPacketNameTv)
        TextView appPacketNameTv;
        @BindView(R.id.lunchNameTv)
        TextView lunchNameTv;
        @BindView(R.id.signTv)
        TextView signTv;

        @BindView(R.id.copeTv)
        TextView copeTv;

        public AppInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 赋值签名
     *
     * @param context
     * @param signStr
     */
    private   void copy(Context context, String signStr) {
        ClipboardManager myClipboard;
        ClipData myClip;
        myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("sign", signStr);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
