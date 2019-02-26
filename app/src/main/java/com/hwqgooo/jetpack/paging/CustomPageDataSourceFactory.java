package com.hwqgooo.jetpack.paging;

import android.arch.paging.DataSource;

public class CustomPageDataSourceFactory extends DataSource.Factory<Integer, DataBean> {
    DataRepository dataRepository;

    public CustomPageDataSourceFactory(DataRepository dataRepository) {
        super();
        this.dataRepository = dataRepository;
    }

    @Override
    public DataSource<Integer, DataBean> create() {
        return new CustomPageDataSource(this.dataRepository);
    }
}
