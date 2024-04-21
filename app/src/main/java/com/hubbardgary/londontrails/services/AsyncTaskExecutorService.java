package com.hubbardgary.londontrails.services;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Replacement for the deprecated AsyncTask.
 * Taken from https://techblogs.42gears.com/replacement-of-deprecated-asynctask-in-android/
 */
public abstract class AsyncTaskExecutorService<Params, Progress, Result>  {
    private final ExecutorService executor;
    private Handler handler;

    protected AsyncTaskExecutorService() {
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public Handler getHandler() {
        if (handler == null) {
            synchronized(AsyncTaskExecutorService.class) {
                handler = new Handler(Looper.getMainLooper());
            }
        }
        return handler;
    }

    // Override this method whenever you want to perform task before background execution get started
    protected void onPreExecute() {}

    protected abstract Result doInBackground(Params params);

    // Override this method whenever you want to perform task after background execution has completed
    protected void onPostExecute(Result result) {}

    // Override this method whenever you want update a progress result
    protected void onProgressUpdate(@NotNull Progress value) {}

    // used for providing progress reports to UI
    public void publishProgress(@NotNull Progress value) {
        getHandler().post(() -> onProgressUpdate(value));
    }

    public void execute() {
        execute(null);
    }

    public void execute(Params params) {
        getHandler().post(() -> {
            onPreExecute();
            executor.execute(() -> {
                Result result = doInBackground(params);
                getHandler().post(() -> onPostExecute(result));
            });
        });
    }

    public void shutDown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public boolean isCancelled() {
        return executor == null || executor.isTerminated() || executor.isShutdown();
    }
}