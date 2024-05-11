package io.github.aljolen.data;

import java.util.concurrent.CyclicBarrier;

public class SyncPoints {
    public final Object CS1 = new Object(); // Computing ai
    public final Object CS2 = new Object(); // Computing bi
    public final Object CS3 = new Object(); // Copying ai
    public final Object CS4 = new Object(); // Copying bi

    public final CyclicBarrier B1; // бар’єр 1 для синхронізації потоків після введення даних (T1: MX, B) + (TP: Z,D,C,MR)
    public final CyclicBarrier B2; // Computing a
    public final CyclicBarrier B3; // Computing b
    public final CyclicBarrier B4; // Computing MAh
    public final CyclicBarrier B5; // Computing Ah

    public SyncPoints(int P) {
        B1 = new CyclicBarrier(P);
        B2 = new CyclicBarrier(P);
        B3 = new CyclicBarrier(P);
        B4 = new CyclicBarrier(P);
        B5 = new CyclicBarrier(P);
    }
}
