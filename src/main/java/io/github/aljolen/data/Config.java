package io.github.aljolen.data;

public class Config {
    // Оголошуємо змінні
    public int P;
    public int N;
    public int H;
    public int fillValue;

    public Config(int fillValue, int N, int P) {
        this.fillValue = fillValue;
        this.N = N;
        this.P = P;
        H = N / P;
    }
}
