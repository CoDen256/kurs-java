package io.github.aljolen.tasks;


import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T extends AbstractTask {


    public T(Config config, SharedResources r, SyncPoints sync, int threadNum) {
        super(config, r, sync, threadNum);
    }

    @Override
    protected void tryRun() throws InterruptedException, BrokenBarrierException {
        log("awaiting B1");
        sync.B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних
        log("done B1");

        int ai = computeAi(r.D, threadNum, config.H);
        synchronized (sync.CS1){
            log("updating a: "+ai);
            r.a = Math.min(ai, r.a);
        }
        log("awaiting B2");
        sync.B2.await();
        log("done B2");

        int bi = computeBi(r.B, r.C, threadNum, config.H);
        synchronized (sync.CS2){
            log("updating b: "+bi);
            r.b = r.b + bi;
        }
        log("awaiting B3");
        sync.B3.await();
        log("done B3");

        int a = 0;
        synchronized (sync.CS3){
            a = r.a;
            log("copied a: "+a);
        }

        int b = 0;
        synchronized (sync.CS4){
            b = r.b;
            log("copied b: "+b);
        }

        int[][] MAh = computeMAh(r.MX, r.MR, threadNum, config.H);
        log("awaiting B4");
        sync.B4.await();
        log("done B4");


        int[] Ah = computeAh(b, r.Z, r.D, MAh, a, threadNum, config.H); // Обчислення Аh
        log("inserting Ah: "+ Arrays.toString(Ah));
        Calculator.insertVectorChunk(r.A, Ah, threadNum);
        log("inserted: "+Arrays.toString(r.A));

        log("awaiting B3");
        sync.B5.await();
        log("done B3");
    }

}