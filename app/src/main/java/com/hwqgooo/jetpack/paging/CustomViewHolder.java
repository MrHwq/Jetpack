package com.hwqgooo.jetpack.paging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hwqgooo.jetpack.R;
import com.orhanobut.logger.Logger;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    private TextView nameView;
    private DataBean item;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.name);
    }

    void bindTo(DataBean item) {
        this.item = item;
        if (item != null) {
            Logger.i("%d %s", item.id, item.name);
            nameView.setText(item.name);
        }
    }
}
