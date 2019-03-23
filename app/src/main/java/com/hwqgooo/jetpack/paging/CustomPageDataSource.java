package com.hwqgooo.jetpack.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.List;


public class CustomPageDataSource extends PageKeyedDataSource<Integer, DataBean> {
    public DataRepository dataRepository;

    public CustomPageDataSource(DataRepository dataRepository) {
        super();
        this.dataRepository = dataRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, DataBean> callback) {
        Logger.i("initial size:%d", params.requestedLoadSize);
        List<DataBean> data = dataRepository.loadData(params.requestedLoadSize);
        callback.onResult(data, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, DataBean> callback) {
        Logger.i("loadBefore size:%d page:%d", params.requestedLoadSize, params.key);
        List<DataBean> data = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (data != null) {
            callback.onResult(data, params.key - 1);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, DataBean> callback) {
        Logger.i("loadAfter size:%d page:%d", params.requestedLoadSize, params.key);
        List<DataBean> data = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (data != null) {
            callback.onResult(data, params.key + 1);
        }
    }
}
