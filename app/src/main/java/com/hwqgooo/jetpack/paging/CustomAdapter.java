package com.hwqgooo.jetpack.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hwqgooo.jetpack.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class CustomAdapter<T> extends PagedListAdapter<DataBean, CustomViewHolder> {

    public CustomAdapter(@NonNull DiffUtil.ItemCallback<DataBean> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
        holder.bindTo(getItem(position));
        Logger.i("bind item:%d", position);
    }

    public static DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<DataBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataBean oldBean, @NonNull DataBean newBean) {
            return oldBean.id == newBean.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataBean oldBean, @NonNull DataBean newBean) {
            return TextUtils.equals(oldBean.name, newBean.name);
        }
    };
}
