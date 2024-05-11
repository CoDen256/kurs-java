package io.github.aljolen;

import java.util.Arrays;


public class Calculator {
    //Методи для обчислень
    public static int min(int[] vector) {
        int minVal = vector[0];
        for (int v : vector) {
            if (v < minVal) {
                minVal = v;
            }
        }
        return minVal;
    }

    public static int[][] createMatrix(int rows, int cols, int value) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = value;
            }
        }
        return result;
    }

    public static int[] createVector(int rows, int value) {
        int[] result = new int[rows];
        for (int i = 0; i < rows; i++) {
            result[i] = value;
        }
        return result;
    }

    public static int[] sortVector(int[] vector) {

        int[] result = Arrays.copyOf(vector, vector.length);

        if (vector.length <= 1) {
            return result;
        }

        for (int j = 0; j < vector.length - 1; j++) {

            for (int i = 0; i < vector.length - 1; i++) {
                if (result[i] > result[i + 1]) {
                    int resultI = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = resultI;
                }
            }
        }
        return result;
    }

    public static int[] concatVectors(int[] vec1, int[] vec2) {
        int[] result = Arrays.copyOf(vec1, vec1.length + vec2.length);
        for (int i = 0; i < vec2.length; i++) {
            result[vec1.length + i] = vec2[i];
        }
        return result;
    }


    public static int[][] scalarByMatrix(int scalar, int[][] m) {
        int[][] result = new int[m.length][m[0].length];
        for (int row = 0; row < m.length; ++row) {
            for (int col = 0; col < m[0].length; ++col) {
                result[row][col] = scalar * m[row][col];
            }
        }
        return result;
    }

    public static int[] scalarByVector(int scalar, int[] m) {
        int[] result = new int[m.length];
        for (int row = 0; row < m.length; ++row) {
            result[row] = scalar * m[row];
        }
        return result;
    }

    public static int[][] sumMatrix(int[][] m1, int[][] m2) {
        int[][] result = new int[m1.length][m1[0].length];
        for (int row = 0; row < m1.length; ++row) {
            for (int col = 0; col < m1[0].length; ++col) {
                result[row][col] = m1[row][col] + m2[row][col];
            }
        }
        return result;
    }

    public static int[] sumVector(int[] v1, int[] v2) {
        int[] result = new int[v1.length];
        for (int row = 0; row < v1.length; ++row) {
            result[row] = v1[row] + v2[row];
        }
        return result;
    }

    public static int[][] matrixByMatrix(int[][] m1, int[][] m2) {
        int[][] result = new int[m1.length][m2[0].length];
        for (int row = 0; row < m1.length; ++row) {
            for (int col = 0; col < m2[0].length; ++col) {
                int sum = 0;
                for (int inner = 0; inner < m1.length; ++inner) {
                    sum += m1[row][inner] * m2[inner][col];
                }
                result[row][col] = sum;
            }
        }
        return result;
    }

    public static int[] matrixByVector(int[][] m, int[] vector) {
        int[] result = new int[m.length];
        if (m[0].length != vector.length) {
            throw new IllegalArgumentException("VECTOR LENGTH != MATRIX LENGTH");
        }

        for (int row = 0; row < m.length; row++) {
            int sum = 0;
            for (int col = 0; col < vector.length; col++) {
                sum += vector[col] * m[row][col];
            }
            result[row] = sum;
        }

        return result;
    }

    public static int vectorByVector(int[] v0, int[] v1) {
        if (v0.length != v1.length) {
            throw new IllegalArgumentException("Vectors must be of the same size but was "+v0.length+" and" + v1.length)
        }
        int result = 0;
        for (int i = 0; i < v0.length; i++) {
            result += v0[i] * v1[i];
        }
        return result;
    }

    public static int[] getVectorChunk(int[] v, int chunkNum, int chunkSize) {
        int[] chunkOfVector = new int[chunkSize];

        for (int i = 0; i < chunkSize; i++) {
            chunkOfVector[i] = v[chunkNum * chunkSize + i];
        }

        return chunkOfVector;
    }


    public static int[][] getMatrixChunk(int[][] m, int chunkNum, int chunkSize) {
        int[][] chunkOfMatrix = new int[m.length][chunkSize];
        for (int row = 0; row < m.length; ++row) {
            for (int col = chunkSize * chunkNum; col < chunkSize * chunkNum + chunkSize; col++) {
                chunkOfMatrix[row][col - chunkSize * chunkNum] = m[row][col];
            }
        }
        return chunkOfMatrix;
    }

    public static void insertMatrixChunk(int[][] m, int[][] chunk, int chunkNum) {

        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < chunk[0].length; ++j) {
                m[i][chunk[0].length * chunkNum + j] = chunk[i][j];
            }
        }
    }

    public static void insertVectorChunk(int[] m, int[] chunk, int chunkNum) {
        ;
        for (int i = 0; i < chunk.length; ++i) {
            m[chunk.length * chunkNum + i] = chunk[i];
        }
    }

    public static int[][] transpose(int[][] m) {
        int[][] transposedMatrix = new int[m[0].length][m.length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                transposedMatrix[j][i] = m[i][j];
            }
        }
        return transposedMatrix;
    }

    public static void outputMatrix(int[][] m) {
        System.out.println(" [ ");
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                System.out.print("\t" + m[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println(" ] ");
    }

    public static void outputVector(int[] m) {
        System.out.print("[");
        for (int j = 0; j < m.length; ++j) {
            System.out.print("\t" + m[j] + "\t");
        }
        System.out.print("]");
        System.out.println();
    }
}