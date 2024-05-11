package io.github.aljolen.data;

public class SharedResources {
    public int[] A;
    public int[] B;
    public int[] C;
    public int[] D;
    public int[] Z;

    public int[][] MX;
    public int[][] MR;

    public int a = Integer.MAX_VALUE;
    public int b = 0;

    public SharedResources(int N) {
        A = new int[N];
        B = new int[N];
        C = new int[N];
        D = new int[N];
        Z = new int[N];

        MX = new int[N][N];
        MR = new int[N][N];
    }
}
