package sca.ml.models;

import sca.ml.util.Matrix;

public class LinearRegression {
    static float ALPHA = 0.01f;
    static double threshold = 0.001d;
    static int ITERATIONS = 100000;

    //assume single variable
    static double[] theta = {1, 1};

    public static void main(String[] args)
    {
        Matrix data = new Matrix(7, 2);

        data.setRow(0, new Double[]{1.17, 78.93});
        data.setRow(1, new Double[]{2.97, 58.2});
        data.setRow(2, new Double[]{3.26, 67.47});
        data.setRow(3, new Double[]{4.69, 37.47});
        data.setRow(4, new Double[]{5.83, 45.65});
        data.setRow(5, new Double[]{6.00, 32.92});
        data.setRow(6, new Double[]{6.41, 29.97});

        regress(data, ALPHA, ITERATIONS);

        System.out.println(theta[0]+" + " + theta[1] + "x");
    }

    public static double[] regress(final Matrix data, final float alpha, int iterations)
    {
        for (int i = 0; i < iterations; i++)
        {
            theta[0] -= alpha * (jPartialDeriv(0, data));
            theta[1] -= alpha * (jPartialDeriv(1, data));
            //System.out.println(theta[0]+" + " + theta[1] + "x");
        }

        return theta;
    }

    private static double jPartialDeriv(final int t, final Matrix data) {
        int m = data.getNumRows();
        double sum = 0;
        for (int i = 0; i < data.getNumRows(); i++)
        {
            double y = data.get(i, 1);
            double iterValue = evaluateHypothesis(data, i, 0) - y;
            iterValue = t == 1 ? iterValue * data.get(i,0) : iterValue;

            sum += iterValue;
        }

        return sum / m;
    }

    private static double evaluateHypothesis(final Matrix matrix, final int row, final int col)
    {
        return theta[0] + theta[1]*(matrix.get(row, col));
    }


}
