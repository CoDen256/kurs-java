package io.github.aljolen.tasks;

import io.github.aljolen.Calculator;
import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public abstract class AbstractTask extends Thread{

    protected final Config config;
    protected final SharedResources r;
    protected final SyncPoints sync;
    protected final int threadNum;

    public AbstractTask(Config config, SharedResources r, SyncPoints sync, int threadNum) {
        this.config = config;
        this.r = r;
        this.sync = sync;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        try {
            tryRun();
        } catch (InterruptedException | BrokenBarrierException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract void tryRun() throws InterruptedException, BrokenBarrierException;

    public int computeAi(int[] D, int chunkNum, int chunkSize) {
        return Calculator.min(Calculator.getVectorChunk(D, chunkNum, chunkSize));
    }

    public int computeBi(int[] B, int[] C, int chunkNum, int chunkSize) {
        return Calculator.vectorByVector(
                Calculator.getVectorChunk(B, chunkNum, chunkSize),
                Calculator.getVectorChunk(C, chunkNum, chunkSize)
        );
    }

    public int[][] computeMAh(int[][] MX, int[][] MR, int chunkNum, int chunkSize) {

        int[][] MRh = Calculator.getMatrixChunk(MR, chunkNum, chunkSize); // (N x H)

        int[][] MXxMRh = Calculator.matrixByMatrix(MX, MRh);             // (N x N) * (N x H) -> (N x H)
        return Calculator.transpose(MXxMRh);                             // (H x N)
    }

    public int[] computeAh(int bi, int[] Z, int[] D, int[][] MAh, int ai, int chunkNum, int chunkSize) {

        int[] Zh = Calculator.getVectorChunk(Z, chunkNum, chunkSize);   // (H x 1)
        int[] left = Calculator.scalarByVector(bi, Zh);                 // (H x 1)

        int[] DxMAh = Calculator.matrixByVector(MAh, D);                // (H x N) * (N x 1) -> (H x 1)
        int[] right = Calculator.scalarByVector(ai, DxMAh);             // (H x 1)

        return Calculator.sumVector(left, right);                       // (H x 1)
    }
}
