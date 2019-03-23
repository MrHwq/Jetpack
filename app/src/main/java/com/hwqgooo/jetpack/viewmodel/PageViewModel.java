package com.hwqgooo.jetpack.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.hwqgooo.jetpack.BR;
import com.hwqgooo.jetpack.R;
import com.hwqgooo.jetpack.utils.recyclerview.CommonAdapter;
import com.hwqgooo.jetpack.paging.DataBean;
import com.hwqgooo.jetpack.paging.DataBeanImage;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;

public class PageViewModel extends ViewModel {
    public ItemViewArg itemView = ItemViewArg.of(ItemViewClassSelector.builder()
            .put(DataBean.class, BR.item, R.layout.item_text)
            .put(DataBeanImage.class, BR.item, R.layout.item_image)
            .build());
    public CommonAdapter<DataBean> adapter = new CommonAdapter<DataBean>(itemView,
            DIFF_CALLBACK);
    private static DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<DataBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataBean oldBean, @NonNull DataBean newBean) {
            return oldBean.id == newBean.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataBean oldBean, @NonNull DataBean newBean) {
            return TextUtils.equals(oldBean.name, newBean.name);
        }
    };

    public PageViewModel() {
        super();
    }
}
