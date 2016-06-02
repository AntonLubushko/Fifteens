package ru.programmistschool.anton.fifteen;


import java.util.HashSet;
import java.util.Random;


public class Fifteens {

    static void printField(int[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.printf("%3d", field[i][j]);
            }
            System.out.println();
        }
    }

    static void createRandomNewField(int[][] field) {
        HashSet<Integer> numbers = new HashSet<>();
        int maxNumber = field.length * field.length;
        for (int i = 0; i < maxNumber; i++) {
            numbers.add(i);
        }
        Random random = new Random();
        int value = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                do {
                    value = random.nextInt(maxNumber);
                    if (numbers.contains(value)) {
                        field[i][j] = value;
                    }
                } while (!numbers.contains(value));
                numbers.remove(value);
            }
        }
    }

    static int[][] createFieldByPlaying(int dificulty) {

        int[][] field = new int[4][4];
        int count = 1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = count++;
            }
        }
        int zeroX = field.length - 1;
        int zeroY = field.length - 1;
        int[] step = new int[4];
        Random rnd = new Random();
        if (dificulty < 10) {
            dificulty = 10;
        }
        if (dificulty > 999) {
            dificulty = 999;
        }
        for (int i = 0; i < dificulty; i++) {
            int variants = 0;
            if (zeroX - 1 >= 0) {
                step[variants++] = 1;
            }
            if (zeroX + 1 < field.length) {
                step[variants++] = 2;
            }
            if (zeroY - 1 >= 0) {
                step[variants++] = 3;
            }
            if (zeroY + 1 < field.length) {
                step[variants++] = 4;
            }
            variants = rnd.nextInt(variants);
            switch (step[variants]) {
                case 1:
                    field[zeroX][zeroY] = field[zeroX - 1][zeroY];
                    field[zeroX - 1][zeroY] = 4 * 4;
                    zeroX -= 1;
                    break;
                case 2:
                    field[zeroX][zeroY] = field[zeroX + 1][zeroY];
                    field[zeroX + 1][zeroY] = 4 * 4;
                    zeroX += 1;
                    break;
                case 3:
                    field[zeroX][zeroY] = field[zeroX][zeroY - 1];
                    field[zeroX][zeroY - 1] = 4 * 4;
                    zeroY -= 1;
                    break;
                case 4:
                    field[zeroX][zeroY] = field[zeroX][zeroY + 1];
                    field[zeroX][zeroY + 1] = 4 * 4;
                    zeroY += 1;
                    break;
            }
        }
        return field;
    }

    static boolean isCorrectField(int[][] field) {
        int count = 1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] != count++) {
                    return false;
                }
            }
        }
        return true;
    }
}
