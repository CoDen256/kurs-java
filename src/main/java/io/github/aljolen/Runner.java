package io.github.aljolen;

import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import io.github.aljolen.tasks.T1;


public class Runner {
    private final Config config;

    public Runner(Config config) {
        this.config = config;
    }


     // Запуск потоків
    public void run() throws InterruptedException {
        SyncPoints points = new SyncPoints(config.P);
        SharedResources resources = new SharedResources(config.N, config.P);

        Thread t1 = new T1(config, resources, points);

        t1.start();


        t1.join();
    }
}