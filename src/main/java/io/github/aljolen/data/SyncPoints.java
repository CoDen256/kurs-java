package io.github.aljolen.data;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class SyncPoints {
    public final Semaphore S1 = new Semaphore(0); // Семафор для синхронізації потоків T1 та T2 після сортування Dн
    public final Semaphore S2 = new Semaphore(0); // Семафор для синхронізації потоків T3 та T4 після сортування Dн
    public final Semaphore S3 = new Semaphore(0); // семафор для синхронізації потоків T1 та T3 після сортування D2н
    public final Semaphore S4; // Семафор для синхронізації потоків після розрахунку Aн в кожному потоці

    public final CyclicBarrier B1; // бар’єр 1 для синхронізації потоків після введення даних
    public final CyclicBarrier B2; // бар’єр 2 для синхронізації потоків після обчислень a

    public SyncPoints(int P) {
        S4 = new Semaphore(-P + 1);
        B1 = new CyclicBarrier(P);
        B2 = new CyclicBarrier(P);
    }
}
