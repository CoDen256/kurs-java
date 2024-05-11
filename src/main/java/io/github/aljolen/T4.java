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
import static io.github.aljolen.Runner.S2;
import static io.github.aljolen.Runner.S4;
import static io.github.aljolen.Runner.Z;
import static io.github.aljolen.Runner.a;
import static io.github.aljolen.Runner.computeAh;
import static io.github.aljolen.Runner.computeCh;
import static io.github.aljolen.Runner.computeMaxBh;
import static io.github.aljolen.Runner.copyD;
import static io.github.aljolen.Runner.copyE;
import static io.github.aljolen.Runner.fillValue;
import static io.github.aljolen.Runner.sortAndInsertQuarterD;
import static io.github.aljolen.Runner.updateMaxA;

import java.util.concurrent.BrokenBarrierException;


public class T4 extends Thread {


    public T4() {
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
        Z = Calculator.createVector(N, fillValue); //Введення  Z


        B1.await(); // Сигнал задачам та чекати на сигнали від задач про введення даних
        int d4 = copyD(); // Копіювання d
        float[] Ch = computeCh(d4, B, Z, MM, MX, 3, H); // Обчислення Ch
        sortAndInsertQuarterD(D, Ch, 3); // Сортування Dн
        S2.release(); // Сигнал задачі Т3 про завершення сортування Dн

        float a4 = computeMaxBh(B, 3, H); // Обчислення а4 = Max(Bh)
        updateMaxA(a4); // Обчислення а
        B2.await(); // Сигнал задачам та чекати на сигнал про завершення обчислень а

        int e4 = copyE();  // Копіювання е
        a4 = a.get();  // Копіювання а
        float[] Ah4 = computeAh(e4, a4, D, Z, 3, H); // Обчислення Аh
        Calculator.insertVectorChunk(A, Ah4, 3);
        S4.release(); // Чекати на завершення обчислень Aн в задачах Т1, Т2, Т3

        S4.acquire();
        Calculator.outputVector(A); // Вивід А
    }
}