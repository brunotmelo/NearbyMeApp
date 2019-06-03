package com.brunotmelo.placesnearby.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppBackgroundExecutor {

    private static AppBackgroundExecutor instance = null;

    private Executor executor;

    public static AppBackgroundExecutor getInstance() {
        if(instance == null){
            instance = new AppBackgroundExecutor();
        }

        return instance;
    }

    private AppBackgroundExecutor(){
        executor = Executors.newSingleThreadExecutor();
    }

    public void queueBackground(Runnable runnable){
        executor.execute(runnable);
    }


}
