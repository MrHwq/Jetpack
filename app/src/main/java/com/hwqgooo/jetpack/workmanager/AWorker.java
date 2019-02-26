package com.hwqgooo.jetpack.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AWorker extends Worker {
    public AWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Logger.i("Aworker key is " + getInputData().getString("key"));
        return Result.success(new Data.Builder().putString("key", "goooo out put").build());
    }
}
