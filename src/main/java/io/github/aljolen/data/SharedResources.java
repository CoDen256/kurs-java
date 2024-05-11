package io.github.aljolen.data;

import io.github.aljolen.Calculator;
import java.util.concurrent.atomic.AtomicReference;

public class SharedResources {
    private final int H;

    public float[] D ;
    public float[] A ;
    public float[][] MM;
    public float[][] MX;

    public float[] B;
    public float[] Z;
    public int d;
    public int e;
    public final AtomicReference<Float> a = new AtomicReference<>(0f);

    public SharedResources(int N, int H) {
        D = new float[N];
        A = new float[N];
        this.H = H;
    }


    // Методи для спрощення алгоритму роботи потоків
    public void sortAndInsertWholeD(float[] D) {

        float[] halfD1 = Calculator.getVectorChunk(D, 0, 2*H);
        float[] halfD2 = Calculator.getVectorChunk(D, 1, 2*H);
        float[] wholeD = sort2Vectors(halfD1, halfD2);    // H
        Calculator.insertVectorChunk(D, wholeD, 0);
    }

    public void sortAndInsertHalfD(float[] D, int chunkNum) {

        float[] quarterD1 = Calculator.getVectorChunk(D, chunkNum, H);
        float[] quarterD2 = Calculator.getVectorChunk(D, chunkNum+1, H);

        float[] halfD = sort2Vectors(quarterD1, quarterD2);  // 2 * H
        Calculator.insertVectorChunk(D, halfD, chunkNum);
    }

    public void sortAndInsertQuarterD(float[] D, float[] Ch, int chunkNum) {

        float[] quarterD = sortCh(Ch);
        Calculator.insertVectorChunk(D, quarterD, chunkNum);
    }


    public void updateMaxA(float a0) {

        a.getAndUpdate(v -> Math.max(v, a0));
    }

    public float computeMaxBh(float[] B, int chunkNum,int chunkSize) {

        return Calculator.max(Calculator.getVectorChunk(B, chunkNum, chunkSize));
    }
    public float[] computeAh(int e0, float a0, float[] D, float[] Z, int chunkNum, int chunkSize) {

        float[] Dh = Calculator.getVectorChunk(D, chunkNum, chunkSize);
        float[] Zh = Calculator.getVectorChunk(Z, chunkNum, chunkSize);

        float[] left = Calculator.scalarByVector(e0, Dh);
        float[] right = Calculator.scalarByVector(a0, Zh);

        return Calculator.sumVector(left, right);
    }

    public float[] computeCh(int d0, float[] B, float[] Z, float[][] MM, float[][] MX, int chunkNum, int chunkSize) {

        float[] Bh = Calculator.getVectorChunk(B, chunkNum, chunkSize);        // (H x 1)
        float[][] MXh = Calculator.getMatrixChunk(MX, chunkNum, chunkSize);    // (N x H)

        float[] scalarByVector = Calculator.scalarByVector(d0, Bh);            // H x 1
        float[][] matrixByMatrix = Calculator.matrixByMatrix(MM, MXh);         // (N x N) * (N x H) = (N x H)
        float[][] transposed = Calculator.transpose(matrixByMatrix);           //  transpose (N x H) -> (HxN)

        float[] matrixByVector = Calculator.matrixByVector(transposed, Z);     // (H x N) * (N x 1) = (H x 1)
        return Calculator.sumVector(scalarByVector, matrixByVector);           // (H x 1) + (H x 1)
    }

    public float[] sortCh(float[] Ch) {

        return Calculator.sortVector(Ch);
    }

    public float[] sort2Vectors(float[] v1, float[] v2) {

        return Calculator.sortVector(Calculator.concatVectors(v1, v2));
    }

    public synchronized int copyD(){

        return d;
    }

    public synchronized int copyE(){

        return e;
    }
}
