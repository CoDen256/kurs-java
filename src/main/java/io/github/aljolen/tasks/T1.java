package io.github.aljolen.tasks;


import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public class T1 extends T {

    public T1(Config config, SharedResources r, SyncPoints sync, int threadNum) {
        super(config, r, sync, threadNum);
    }

    @Override
    protected void tryRun() throws InterruptedException, BrokenBarrierException {
        r.MX = Calculator.createMatrix(config.N, config.N, config.fillValue); // Введення MX
        r.B = Calculator.createVector(config.N, config.fillValue); // Введення B

        super.tryRun();

        outputVector(r.A);
    }

}