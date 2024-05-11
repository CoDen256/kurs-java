package io.github.aljolen;


import static io.github.aljolen.Runner.A;
import static io.github.aljolen.Runner.B;
import static io.github.aljolen.Runner.B1;
import static io.github.aljolen.Runner.B2;
import static io.github.aljolen.Runner.D;
import static io.github.aljolen.Runner.H;
import static io.github.aljolen.Runner.MM;
import static io.github.aljolen.Runner.MX;
import static io.github.aljolen.Runner.N;
import static io.github.aljolen.Runner.S1;
import static io.github.aljolen.Runner.S3;
import static io.github.aljolen.Runner.S4;
import static io.github.aljolen.Runner.Z;
import static io.github.aljolen.Runner.a;
import static io.github.aljolen.Runner.computeAh;
import static io.github.aljolen.Runner.computeCh;
import static io.github.aljolen.Runner.computeMaxBh;
import static io.github.aljolen.Runner.copyD;
import static io.github.aljolen.Runner.copyE;
import static io.github.aljolen.Runner.d;
import static io.github.aljolen.Runner.fillValue;
import static io.github.aljolen.Runner.sortAndInsertHalfD;
import static io.github.aljolen.Runner.sortAndInsertQuarterD;
import static io.github.aljolen.Runner.sortAndInsertWholeD;
import static io.github.aljolen.Runner.updateMaxA;

import java.util.concurrent.BrokenBarrierException;

public class T1 extends Thread {


    public T1() {
    }

    @Override
    public void run() {
        try {
            tryRun();
        } catch (InterruptedException | BrokenBarrierException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void tryRun() throws InterruptedException, BrokenBarrierException {
        d = 2; //Введення d
        MM = Calculator.createMatrix(N, N, fillValue); // Введення MM

        B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних

        int d1 = copyD();  // Копіювання d
        float[] Ch = computeCh(d1, B, Z, MM, MX, 0, H);  // Обчислення Ch
        sortAndInsertQuarterD(D, Ch, 0);   // Сортування Dн
        S1.acquire();                               // Чекати на завершення сортування в Т2
        sortAndInsertHalfD(D, 0);         // Сортування D2н
        S3.acquire();                               // Чекати на завершення сортування D2н в Т3
        sortAndInsertWholeD(D);                     // Сортування D
        float a1 = computeMaxBh(B, 0, H); // Обчислення а1 = Max(Bh)
        updateMaxA(a1);                             // Обчислення а
        B2.await();                                 // Сигнал задачам та чекати на сигнал про завершення обчислень а

        int e1 = copyE(); // Копіювання е
        a1 = a.get();     // Копіювання а
        float[] Ah1 = computeAh(e1, a1, D, Z, 0, H); // Обчислення Аh
        Calculator.insertVectorChunk(A, Ah1, 0);

        S4.release();  // Сигнал задачі Т4 про завершення обчислень А
    }

}