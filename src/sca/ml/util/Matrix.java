package sca.ml.util;

public class Matrix {
    Double[][] values;

    public Matrix(int rows, int cols)
    {
        values = new Double[rows][cols];
    }

    public Double get(int row, int col)
    {
        return values[row][col];
    }

    public Double[][] setRow(int index, Double[] row)
    {
        values[index] = row;
        return this.getValues();
    }

    public Double[] getRow(int index) {
        return this.values[index];
    }

    public int getNumRows(){
        return values.length;
    }

    public int getNumCols() { return values[0].length; }

    public Double[][] getValues()
    {
        return this.values;
    }
}
