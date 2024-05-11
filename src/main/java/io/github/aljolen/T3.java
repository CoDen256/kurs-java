package io.github.aljolen;

import static io.github.aljolen.Runner.A;
import static io.github.aljolen.Runner.B;
import static io.github.aljolen.Runner.B1;
import static io.github.aljolen.Runner.B2;
import static io.github.aljolen.Runner.D;
import static io.github.aljolen.Runner.H;
import static io.github.aljolen.Runner.MM;
import static io.github.aljolen.Runner.MX;
import static io.github.aljolen.Runner.S2;
import static io.github.aljolen.Runner.S3;
import static io.github.aljolen.Runner.S4;
import static io.github.aljolen.Runner.Z;
import static io.github.aljolen.Runner.a;
import static io.github.aljolen.Runner.computeAh;
import static io.github.aljolen.Runner.computeCh;
import static io.github.aljolen.Runner.computeMaxBh;
import static io.github.aljolen.Runner.copyD;
import static io.github.aljolen.Runner.copyE;
import static io.github.aljolen.Runner.sortAndInsertHalfD;
import static io.github.aljolen.Runner.sortAndInsertQuarterD;
import static io.github.aljolen.Runner.updateMaxA;

import java.util.concurrent.BrokenBarrierException;


public class T3 extends Thread {


    public T3() {
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
        B1.await(); // Чекати на сигнали від задач про введення даних
        int d3 = copyD(); // Копіювання d
        float[] Ch = computeCh(d3, B, Z, MM, MX, 2, H); // Обчислення Ch
        sortAndInsertQuarterD(D, Ch, 2); // Сортування Dh
        S2.acquire();                             //Чекати на завершення сортування Dh в задачі Т4
        sortAndInsertHalfD(D, 1);        // Сортування D2н
        S3.release();                             //Сигнал задачі Т1 про завершення сортування D2н

        float a3 = computeMaxBh(B, 2, H); // Обчислення а3 = Max(Bh)
        updateMaxA(a3);                              // Обчислення а
        B2.await();                                 // Сигнал задачам та чекати на сигнал про завершення обчислень а

        int e3 = copyE();        // Копіювання е
        a3 = a.get();            // Копіювання а
        float[] Ah3 = computeAh(e3, a3, D, Z, 2, H); // Обчислення Аh
        Calculator.insertVectorChunk(A, Ah3, 2);

        S4.release();  // Сигнал задачі Т4 про завершення обчислень А
    }
}