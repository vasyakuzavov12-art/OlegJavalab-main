package tasks;

public class MyTable {
    private int[][] data;
    private int rows;
    private int cols;

    public MyTable(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    public void setValue(int row, int col, int value) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            data[row][col] = value;
        } else {
            throw new IllegalArgumentException("Неверные индексы строки/столбца");
        }
    }

    public int getValue(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return data[row][col];
        } else {
            throw new IllegalArgumentException("Неверные индексы строки/столбца");
        }
    }

    public double average() {
        if (rows == 0 || cols == 0) return 0;

        int sum = 0;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
                count++;
            }
        }
        return (double) sum / count;
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%6d", data[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}