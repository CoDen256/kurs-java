package io.github.aljolen;

import io.github.aljolen.data.Config;
import java.util.Scanner;

/**
 * Бариніна Аліна
 **/

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        int N = getAmountOfElements();
        int fillValue =  getFillValueOfMatrix();
        int P =  getThreadNum();
        new Runner(new Config(fillValue, N, P)).run();
    }

       // Методи для введення з клавіатури
    public static int getFillValueOfMatrix() {
        System.out.print("Input a fill value of matrix: ");
        int value = sc.nextInt();
        return value;
    }

    public static int getAmountOfElements() {
        System.out.print("Input the amount of elements: ");
        int n = sc.nextInt();
        return n;
    }

    public static int getThreadNum() {
        System.out.print("Input the amount of threads: ");
        int p = sc.nextInt();
        return p;
    }

}