package com.example.empireclickers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateMoneyExecutorPool {


    /**
     * A class that represents a pool of threads for growing radii of background circle shapes.
     */

        final ExecutorService pool;

        public UpdateMoneyExecutorPool() {
            final int cpuCores = Math.max(Runtime.getRuntime().availableProcessors() - 1, 1);
            pool = Executors.newFixedThreadPool(cpuCores);
        }

        public void submit(final Runnable task) {
            pool.submit(task);
        }

}
