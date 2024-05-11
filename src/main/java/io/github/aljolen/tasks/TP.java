package io.github.aljolen.tasks;


import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public class TP extends T {


    public TP(Config config, SharedResources r, SyncPoints sync, int threadNum) {
        super(config, r, sync, threadNum);
    }

    @Override
    protected void tryRun() throws InterruptedException, BrokenBarrierException {
        r.Z = Calculator.createVector(config.N, config.fillValue); // Введення Z
        r.D = Calculator.createVector(config.N, config.fillValue); // Введення D
        r.C = Calculator.createVector(config.N, config.fillValue); // Введення C
        r.MR = Calculator.createMatrix(config.N, config.N, config.fillValue); // Введення MR

        super.tryRun();
    }

}