package com.younchen.chat.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private ThreadPoolExecutor executor;

    private static ThreadPool pool;

    public static ThreadPool getInstance() {
        if (pool == null)
            pool = new ThreadPool();
        return pool;
    }

    private ThreadPool() {
        executor = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3)
        );
    }

    public void execute(HandlerRunnable runnable) {
        runnable.future = executor.submit(runnable);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
