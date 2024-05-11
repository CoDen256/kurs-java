package io.github.aljolen;

import java.util.Arrays;


public class Calculator {
    //Методи для обчислень
    public static float max(float[] vector) {
        float maxVal = vector[0];
        for (float v : vector) {
            if (v > maxVal) {
                maxVal = v;
            }
        }
        return maxVal;
    }

    public static float[][] createMatrix(int rows, int cols, int value) {
        float[][] result = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = value;
            }
        }
        return result;
    }

    public static float[] createVector(int rows, int value) {
        float[] result = new float[rows];
        for (int i = 0; i < rows; i++) {
            result[i] = value;
        }
        return result;
    }

    public static float[] sortVector(float[] vector) {

        float[] result = Arrays.copyOf(vector, vector.length);

        if (vector.length <= 1) {
            return result;
        }

        for (int j = 0; j < vector.length - 1; j++) {

            for (int i = 0; i < vector.length - 1; i++) {
                if (result[i] > result[i + 1]) {
                    float resultI = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = resultI;
                }
            }
        }
        return result;
    }

    public static float[] concatVectors(float[] vec1, float[] vec2) {
        float[] result = Arrays.copyOf(vec1, vec1.length + vec2.length);
        for (int i = 0; i < vec2.length; i++) {
            result[vec1.length + i] = vec2[i];
        }
        return result;
    }


    public static float[][] scalarByMatrix(int scalar, float[][] m) {
        float[][] result = new float[m.length][m[0].length];
        for (int row = 0; row < m.length; ++row) {
            for (int col = 0; col < m[0].length; ++col) {
                result[row][col] = scalar * m[row][col];
            }
        }
        return result;
    }

    public static float[] scalarByVector(float scalar, float[] m) {
        float[] result = new float[m.length];
        for (int row = 0; row < m.length; ++row) {
            result[row] = scalar * m[row];
        }
        return result;
    }

    public static float[][] sumMatrix(float[][] m1, float[][] m2) {
        float[][] result = new float[m1.length][m1[0].length];
        for (int row = 0; row < m1.length; ++row) {
            for (int col = 0; col < m1[0].length; ++col) {
                result[row][col] = m1[row][col] + m2[row][col];
            }
        }
        return result;
    }

    public static float[] sumVector(float[] v1, float[] v2) {
        float[] result = new float[v1.length];
        for (int row = 0; row < v1.length; ++row) {
            result[row] = v1[row] + v2[row];
        }
        return result;
    }

    public static float[][] matrixByMatrix(float[][] m1, float[][] m2) {
        float[][] result = new float[m1.length][m2[0].length];
        for (int row = 0; row < m1.length; ++row) {
            for (int col = 0; col < m2[0].length; ++col) {
                float sum = 0;
                for (int inner = 0; inner < m1.length; ++inner) {
                    sum += m1[row][inner] * m2[inner][col];
                }
                result[row][col] = sum;
            }
        }
        return result;
    }

    public static float[] matrixByVector(float[][] m, float[] vector) {
        float[] result = new float[m.length];
        if (m[0].length != vector.length) {
            throw new IllegalArgumentException("VECTOR LENGTH != MATRIX LENGTH");
        }

        for (int row = 0; row < m.length; row++) {
            float sum = 0.0f;
            for (int col = 0; col < vector.length; col++) {
                sum += vector[col] * m[row][col];
            }
            result[row] = sum;
        }

        return result;
    }

    public static float[] getVectorChunk(float[] v, int chunkNum, int chunkSize) {
        float[] chunkOfVector = new float[chunkSize];

        for (int i = 0; i < chunkSize; i++) {
            chunkOfVector[i] = v[chunkNum * chunkSize + i];
        }

        return chunkOfVector;
    }


    public static float[][] getMatrixChunk(float[][] m, int chunkNum, int chunkSize) {
        float[][] chunkOfMatrix = new float[m.length][chunkSize];
        for (int row = 0; row < m.length; ++row) {
            for (int col = chunkSize * chunkNum; col < chunkSize * chunkNum + chunkSize; col++) {
                chunkOfMatrix[row][col - chunkSize * chunkNum] = m[row][col];
            }
        }
        return chunkOfMatrix;
    }

    public static void insertMatrixChunk(float[][] m, float[][] chunk, int chunkNum) {

        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < chunk[0].length; ++j) {
                m[i][chunk[0].length * chunkNum + j] = chunk[i][j];
            }
        }
    }

    public static void insertVectorChunk(float[] m, float[] chunk, int chunkNum) {
        ;
        for (int i = 0; i < chunk.length; ++i) {
            m[chunk.length * chunkNum + i] = chunk[i];
        }
    }

    public static float[][] transpose(float[][] m) {
        float[][] transposedMatrix = new float[m[0].length][m.length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                transposedMatrix[j][i] = m[i][j];
            }
        }
        return transposedMatrix;
    }

    public static void outputMatrix(float[][] m) {
        System.out.println(" [ ");
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                System.out.print("\t" + m[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println(" ] ");
    }

    public static void outputVector(float[] m) {
        System.out.print("[");
        for (int j = 0; j < m.length; ++j) {
            System.out.print("\t" + m[j] + "\t");
        }
        System.out.print("]");
        System.out.println();
    }
}