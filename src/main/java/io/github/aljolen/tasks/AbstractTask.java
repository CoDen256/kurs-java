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
        super("Task-"+threadNum);
        this.config = config;
        this.r = r;
        this.sync = sync;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        try {
            log("\033[0;92mstarted\u001B[0m");
            tryRun();
            log("\033[0;92mcompleted\u001B[0m");
        } catch (InterruptedException | BrokenBarrierException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract void tryRun() throws InterruptedException, BrokenBarrierException;

    public int computeAi(int[] D, int chunkNum, int chunkSize) {
        log("computing ai");
        return Calculator.min(Calculator.getVectorChunk(D, chunkNum, chunkSize));
    }

    public int computeBi(int[] B, int[] C, int chunkNum, int chunkSize) {
        log("computing bi");
        return Calculator.vectorByVector(
                Calculator.getVectorChunk(B, chunkNum, chunkSize),
                Calculator.getVectorChunk(C, chunkNum, chunkSize)
        );
    }

    public int[][] computeMAh(int[][] MX, int[][] MR, int chunkNum, int chunkSize) {
        log("computing MAh");

        int[][] MRh = Calculator.getMatrixChunk(MR, chunkNum, chunkSize); // (N x H)

        int[][] MXxMRh = Calculator.matrixByMatrix(MX, MRh);             // (N x N) * (N x H) -> (N x H)
        return Calculator.transpose(MXxMRh);                             // (H x N)
    }

    public int[] computeAh(int bi, int[] Z, int[] D, int[][] MAh, int ai, int chunkNum, int chunkSize) {
        log("computing Ah");
        int[] Zh = Calculator.getVectorChunk(Z, chunkNum, chunkSize);   // (H x 1)
        int[] left = Calculator.scalarByVector(bi, Zh);                 // (H x 1)

        int[] DxMAh = Calculator.matrixByVector(MAh, D);                // (H x N) * (N x 1) -> (H x 1)
        int[] right = Calculator.scalarByVector(ai, DxMAh);             // (H x 1)

        return Calculator.sumVector(left, right);                       // (H x 1)
    }


    protected static synchronized void log(String action){
        System.out.println("[ \033[1;96m"+Thread.currentThread().getName()+"\u001B[0m ]: "+ action);
    }

    protected static synchronized void outputMatrix(int[][] m) {
        System.out.println(" [ ");
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                System.out.print("\t" + m[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println(" ] ");
    }

    protected static synchronized void outputVector(int[] m) {
        System.out.print("[");
        for (int j = 0; j < m.length; ++j) {
            System.out.print("\t" + m[j] + "\t");
        }
        System.out.print("]");
        System.out.println();
    }
}
