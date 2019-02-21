package com.hwqgooo.jetpack;

import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.Worker;

public class AWorker extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {
        System.out.println("key is " + getInputData().getString("key", "nullvalue"));
        setOutputData(new Data.Builder().putString("key", "goooo").build());
        return WorkerResult.SUCCESS;
    }
}
