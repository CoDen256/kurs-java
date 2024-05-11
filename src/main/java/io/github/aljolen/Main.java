package io.github.aljolen;

import io.github.aljolen.data.Config;
import java.util.Scanner;

/**
 * Бариніна Аліна Лр №3 ІО-13 А
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
        System.out.println("Input a fill value of matrix: ");
        int value = sc.nextInt();
        System.out.println();
        return value;
    }

    public static int getAmountOfElements() {
        System.out.println("Input the amount of elements: ");
        int n = sc.nextInt();
        System.out.println();
        return n;
    }

    public static int getThreadNum() {
        System.out.println("Input the amount of elements: ");
        int p = sc.nextInt();
        System.out.println();
        return p;
    }

}