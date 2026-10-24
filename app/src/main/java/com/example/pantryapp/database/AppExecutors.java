package com.example.pantryapp.database;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final int THREAD_COUNT = 3;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance() {
        return Holder.INSTANCE;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class Holder {
        private static final AppExecutors INSTANCE = new AppExecutors(
                Executors.newFixedThreadPool(THREAD_COUNT),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
