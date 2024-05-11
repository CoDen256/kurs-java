package io.github.aljolen;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;


public class Runner {
    // Оголошуємо змінні
    public static int P;
    public static int N;
    public static int H;
    public static int fillValue;


    public static  float[] D;
    public static float[] A;
    public static float[][] MM;
    public static float[][] MX;

    public static float[] B;
    public static float[] Z;
    public static int d;
    public static int e;
    public static final AtomicReference<Float> a = new AtomicReference<>(0f);

    public static final Semaphore S1 = new Semaphore(0); // Семафор для синхронізації потоків T1 та T2 після сортування Dн
    public static final Semaphore S2 = new Semaphore(0); // Семафор для синхронізації потоків T3 та T4 після сортування Dн
    public static final Semaphore S3 = new Semaphore(0); // семафор для синхронізації потоків T1 та T3 після сортування D2н
    public static final Semaphore S4 = new Semaphore(-P + 1); // Семафор для синхронізації потоків після розрахунку Aн в кожному потоці

    public static final CyclicBarrier B1 = new CyclicBarrier(P); // бар’єр 1 для синхронізації потоків після введення даних
    public static final CyclicBarrier B2 = new CyclicBarrier(P); // бар’єр 2 для синхронізації потоків після обчислень a


    public Runner(int N, int fillValue, int P) {
        Runner.N = N;
        Runner.P = P;
        Runner.fillValue = fillValue;

        H = N / P;
        D = new float[N];
        A = new float[N];
    }


     // Запуск потоків
    public void run() throws InterruptedException {
        Thread t1 = new T1();
        Thread t2 = new T2();
        Thread t3 = new T3();
        Thread t4 = new T4();

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }


    // Методи для спрощення алгоритму роботи потоків
    public static void sortAndInsertWholeD(float[] D) {

        float[] halfD1 = Calculator.getVectorChunk(D, 0, 2*H);
        float[] halfD2 = Calculator.getVectorChunk(D, 1, 2*H);
        float[] wholeD = sort2Vectors(halfD1, halfD2);    // H
        Calculator.insertVectorChunk(D, wholeD, 0);
    }

    public static void sortAndInsertHalfD(float[] D, int chunkNum) {

        float[] quarterD1 = Calculator.getVectorChunk(D, chunkNum, H);
        float[] quarterD2 = Calculator.getVectorChunk(D, chunkNum+1, H);

        float[] halfD = sort2Vectors(quarterD1, quarterD2);  // 2 * H
        Calculator.insertVectorChunk(D, halfD, chunkNum);
    }

    public static void sortAndInsertQuarterD(float[] D, float[] Ch, int chunkNum) {

        float[] quarterD = sortCh(Ch);
        Calculator.insertVectorChunk(D, quarterD, chunkNum);
    }


    public static void updateMaxA(float a0) {

        a.getAndUpdate(v -> Math.max(v, a0));
    }

    public static float computeMaxBh(float[] B, int chunkNum,int chunkSize) {

        return Calculator.max(Calculator.getVectorChunk(B, chunkNum, chunkSize));
    }
    public static float[] computeAh(int e0, float a0, float[] D, float[] Z, int chunkNum, int chunkSize) {

        float[] Dh = Calculator.getVectorChunk(D, chunkNum, chunkSize);
        float[] Zh = Calculator.getVectorChunk(Z, chunkNum, chunkSize);

        float[] left = Calculator.scalarByVector(e0, Dh);
        float[] right = Calculator.scalarByVector(a0, Zh);

        return Calculator.sumVector(left, right);
    }

    public static float[] computeCh(int d0, float[] B, float[] Z, float[][] MM, float[][] MX, int chunkNum, int chunkSize) {

        float[] Bh = Calculator.getVectorChunk(B, chunkNum, chunkSize);        // (H x 1)
        float[][] MXh = Calculator.getMatrixChunk(MX, chunkNum, chunkSize);    // (N x H)

        float[] scalarByVector = Calculator.scalarByVector(d0, Bh);            // H x 1
        float[][] matrixByMatrix = Calculator.matrixByMatrix(MM, MXh);         // (N x N) * (N x H) = (N x H)
        float[][] transposed = Calculator.transpose(matrixByMatrix);           //  transpose (N x H) -> (HxN)

        float[] matrixByVector = Calculator.matrixByVector(transposed, Z);     // (H x N) * (N x 1) = (H x 1)
        return Calculator.sumVector(scalarByVector, matrixByVector);           // (H x 1) + (H x 1)
    }

    public static float[] sortCh(float[] Ch) {

        return Calculator.sortVector(Ch);
    }

    public static float[] sort2Vectors(float[] v1, float[] v2) {

        return Calculator.sortVector(Calculator.concatVectors(v1, v2));
    }

    public static synchronized int copyD(){

        return d;
    }

    public static synchronized int copyE(){

        return e;
    }
}