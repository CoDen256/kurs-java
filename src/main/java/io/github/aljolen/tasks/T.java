package io.github.aljolen.tasks;


import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public class T extends AbstractTask {


    public T(Config config, SharedResources r, SyncPoints sync, int threadNum) {
        super(config, r, sync, threadNum);
    }

    @Override
    protected void tryRun() throws InterruptedException, BrokenBarrierException {
        sync.B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних

        int ai = computeAi(r.D, threadNum, config.H);
        synchronized (sync.CS1){
            r.a = Math.min(ai, r.a);
        }
        sync.B2.await();

        int bi = computeBi(r.B, r.C, threadNum, config.H);
        synchronized (sync.CS2){
            r.b = r.b + bi;
        }
        sync.B3.await();

        int a = 0;
        synchronized (sync.CS3){
            a = r.a;
        }

        int b = 0;
        synchronized (sync.CS4){
            b = r.b;
        }

        int[][] MAh = computeMAh(r.MX, r.MR, threadNum, config.H);
        sync.B4.await();


        int[] Ah = computeAh(b, r.Z, r.D, MAh, a, threadNum, config.H); // Обчислення Аh
        Calculator.insertVectorChunk(r.A, Ah, threadNum);
        sync.B5.await();
    }

}