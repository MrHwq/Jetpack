package com.hwqgooo.jetpack.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;
import io.reactivex.Single;

public class BWorker extends RxWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public BWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @Override
    public Single<Result> createWork() {
        Logger.i("BWorker key is " + getInputData().getString("key"));
        return Single.just(Result.success(new Data.Builder().
                putString("key", "hello , i'm rxjava")
                .build()));
    }
}
