package com.example.marina.userauthentication;

public class DTW {

    int n, m, step = 0, dtw = 0;

    public int calculateDTW(int[] firstRow, int[] secondRow) {
        step = 0;
        n = firstRow.length;
        m = secondRow.length;
        int[][] d = this.accountDistance(firstRow, secondRow);
        d = calculationStrainMatrix(d);
        dtw = this.calculationDTW(d);
        return dtw;
    }

    public int getDTW(){
        return dtw;
    }

    private int calculationDTW(int[][] d) {
        int dtw = d[n-1][m-1];
        int i = n-1, j= n-1;
        while (i!=0 && j!=0){
            if (i-1 >=0 && j-1>=0) {
                if (d[i - 1][j - 1] < Math.min(d[i][j - 1], d[i - 1][j])) {
                    dtw += d[i - 1][j - 1];
                    step++;
                    i--;
                    j--;
                }else
                if (d[i][j - 1] < Math.min(d[i - 1][j - 1], d[i - 1][j])) {
                    dtw += d[i][j - 1];
                    step++;
                    j--;
                } else
                if (d[i - 1][j] < Math.min(d[i - 1][j - 1], d[i - 1][j - 1])) {
                    dtw += d[i - 1][j];
                    step++;
                    i--;
                }
            }
            if (i == 0 && j-1>=0) {
                    dtw += d[i][j - 1];
                    step++;
                    j--;
            }
            if (j == 0 && i-1>=0) {
                dtw += d[i-1][j];
                step++;
                i--;
            }
        }
        return dtw/step;
    }

    private int[][] calculationStrainMatrix(int[][] d) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j != 0) {
                    d[i][j] = d[i][j] + d[i][j - 1];
                }
                if (i != 0 && j == 0) {
                    d[i][j] = d[i][j] + d[i - 1][j];
                }
                if (i != 0 && j != 0) {
                    if (d[i - 1][j - 1] < Math.min(d[i][j - 1], d[i - 1][j])) {
                        d[i][j] += d[i - 1][j - 1];
                    } else {
                        d[i][j] += Math.min(d[i][j - 1], d[i - 1][j]);
                    }
                }
            }
        }
        return d;
    }

    private int[][] accountDistance(int[] firstRow, int[] secondRow) {
        int[][] result = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = Math.abs(firstRow[i] - secondRow[j]);
            }
        }
        return result;
    }
}
