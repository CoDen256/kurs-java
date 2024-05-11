package io.github.aljolen.tasks;


import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public class TP extends AbstractTask {


    public TP(Config config, SharedResources r, SyncPoints sync) {
        super(config, r, sync);
    }

    @Override
    protected void tryRun() throws InterruptedException, BrokenBarrierException {
        r.d = 2; //Введення d
        r.MM = Calculator.createMatrix(config.N, config.N, config.fillValue); // Введення MM

        s.B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних

        int d1 = r.copyD();  // Копіювання d
        float[] Ch = r.computeCh(d1, r.B, r.Z, r.MM, r.MX, 0, config.H);  // Обчислення Ch
        r.sortAndInsertQuarterD(r.D, Ch, 0);   // Сортування Dн
        s.S1.acquire();                               // Чекати на завершення сортування в Т2
        r.sortAndInsertHalfD(r.D, 0);         // Сортування D2н
        s.S3.acquire();                               // Чекати на завершення сортування D2н в Т3
        r.sortAndInsertWholeD(r.D);                     // Сортування D
        float a1 = r.computeMaxBh(r.B, 0, config.H); // Обчислення а1 = Max(Bh)
        r.updateMaxA(a1);                             // Обчислення а
        s.B2.await();                                 // Сигнал задачам та чекати на сигнал про завершення обчислень а

        int e1 = r.copyE(); // Копіювання е
        a1 = r.a.get();     // Копіювання а
        float[] Ah1 = r.computeAh(e1, a1, r.D, r.Z, 0, config.H); // Обчислення Аh
        Calculator.insertVectorChunk(r.A, Ah1, 0);

        s.S4.release();  // Сигнал задачі Т4 про завершення обчислень А
    }

}