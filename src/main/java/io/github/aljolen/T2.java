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
import static io.github.aljolen.Runner.S4;
import static io.github.aljolen.Runner.Z;
import static io.github.aljolen.Runner.a;
import static io.github.aljolen.Runner.computeAh;
import static io.github.aljolen.Runner.computeCh;
import static io.github.aljolen.Runner.computeMaxBh;
import static io.github.aljolen.Runner.copyD;
import static io.github.aljolen.Runner.copyE;
import static io.github.aljolen.Runner.e;
import static io.github.aljolen.Runner.fillValue;
import static io.github.aljolen.Runner.sortAndInsertQuarterD;
import static io.github.aljolen.Runner.updateMaxA;

import java.util.concurrent.BrokenBarrierException;

public class T2 extends Thread {


    public T2() {
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
        e = 2; // Введення е
        B = Calculator.createVector(N, fillValue); // Введення  B
        MX = Calculator.createMatrix(N, N, fillValue); // Введення MX

        B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних
        int d2 = copyD(); // Копіювання d
        float[] Ch = computeCh(d2, B, Z, MM, MX, 1, H); // Обчислення Ch
        sortAndInsertQuarterD(D, Ch, 1); // Сортування Dн
        S1.release();  // Сигнал задачі Т1 про завершення сортування  Dн

        float a2 = computeMaxBh(B, 1, H); // Обчислення а2 = Max(Bh)
        updateMaxA(a2); // Обчислення а
        B2.await();  // Сигнал задачам та чекати на сигнал про завершення обчислень а

        int e2 = copyE(); // Копіювання е
        a2 = a.get();     // Копіювання а
        float[] Ah2 = computeAh(e2, a2, D, Z, 1, H);  // Обчислення Аh
        Calculator.insertVectorChunk(A, Ah2, 1);

        S4.release();  // Сигнал задачі Т4 про завершення обчислень А
    }
}