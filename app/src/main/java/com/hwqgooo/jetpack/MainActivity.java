package com.hwqgooo.jetpack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidx.navigation.Navigation;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
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


//        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(AWorker.class,
//                10, TimeUnit.SECONDS)
//                .setInputData(dataBuilder.put)
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance()
                .getStatusById(oneTimeWorkRequest.getId())
                .observe(this, workStatus -> {
                    if (workStatus != null) {
                        System.out.println("work status is::" + workStatus.getState().toString());
                    } else {
                        System.out.println("work is null");
                    }
                });

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
