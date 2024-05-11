package io.github.aljolen;

import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import io.github.aljolen.tasks.T;
import io.github.aljolen.tasks.T1;
import io.github.aljolen.tasks.TP;


public class Runner {
    private final Config config;

    public Runner(Config config) {
        this.config = config;
    }


    // Запуск потоків
    public void run() throws InterruptedException {
        Thread[] threads = createThreads();

        for (Thread thread : threads)
            thread.start();


        for (Thread thread : threads)
            thread.join();

    }

    private Thread[] createThreads() {
        SyncPoints sync = new SyncPoints(config.P);
        SharedResources resources = new SharedResources(config.N);
        Thread[] threads = new Thread[config.P];

        threads[0] = new T1(config, resources, sync, 0);
        for (int i = 1; i < config.P - 1; i++) {
            threads[i] = new T(config, resources, sync, i);
        }
        threads[config.P - 1] = new TP(config, resources, sync, config.P - 1);

        return threads;
    }
}