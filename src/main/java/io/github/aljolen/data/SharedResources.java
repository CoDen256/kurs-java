package io.github.aljolen.data;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedResources {
    public int[] B;
    public int[] C;
    public int[] D;
    public int[] Z;

    public int[][] MX;
    public int[][] MR;

    public final AtomicInteger a = new AtomicInteger(0);
    public final AtomicInteger b = new AtomicInteger(0);

    public SharedResources(int N) {
        B = new int[N];
        C = new int[N];
        D = new int[N];
        Z = new int[N];

        MX = new int[N][N];
        MR = new int[N][N];
    }
}
