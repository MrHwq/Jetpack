package com.hwqgooo.jetpack.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.hwqgooo.jetpack.paging.CustomAdapter;
import com.hwqgooo.jetpack.paging.DataBean;

public class PageViewModel extends ViewModel {
    public CustomAdapter<DataBean> ca;

    public PageViewModel() {
        super();
        ca = new CustomAdapter<DataBean>(CustomAdapter.DIFF_CALLBACK);
    }
}
