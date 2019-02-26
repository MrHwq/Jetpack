package com.hwqgooo.jetpack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hwqgooo.jetpack.workmanager.AWorker;
import com.hwqgooo.jetpack.workmanager.BWorker;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import androidx.navigation.Navigation;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constraints.Builder constBuilder = new Constraints.Builder();
        constBuilder.setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder dataBuilder = new Data.Builder().putString("key", "hello world");
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(AWorker.class)
                .setConstraints(constBuilder.build())
                .setInputData(dataBuilder.build())
                .build();

        OneTimeWorkRequest oneTimeWorkRequestBBB = new OneTimeWorkRequest.Builder(BWorker.class)
                .setInputData(dataBuilder.build())
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(BWorker.class,
                15, TimeUnit.MINUTES)
                .setInputData(dataBuilder.build())
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);
        WorkManager.getInstance().beginWith(oneTimeWorkRequest)
                .then(oneTimeWorkRequestBBB).enqueue();
        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this,
                        workInfo -> {
                            if (workInfo != null) {
                                Logger.d("Awork status is::" + workInfo.getState().toString());
                                Logger.d("" + workInfo.getOutputData().getString("key"));
                            }
                        });
        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(oneTimeWorkRequestBBB.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        Logger.d("bwork status is::" + workInfo.getState().toString());
                        Logger.d("" + workInfo.getOutputData().getString("key"));
                    }
                });

        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        Logger.d("bpppwork status is::" + workInfo.getState().toString());
                        Logger.d("" + workInfo.getOutputData().getString("key"));
                    }
                });
//                .observe(this, workStatus -> {
//                    if (workStatus != null) {
//
//                    } else {
//                        System.out.println("work is null");
//                    }
//                });

//        WorkManager.getInstance()
//                .beginWith(workA)
//                .then(workB)
//                .then(workC)
//                .enqueue()

//        WorkManager.getInstance()
//                .beginWith(workA,workB)
//                .then(workC)
//                .enqueue()
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.host_fragment).navigateUp();
    }
}
