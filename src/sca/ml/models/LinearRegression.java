package sca.ml.models;

import sca.ml.util.Matrix;

public class LinearRegression {
    static float ALPHA = 0.01f;
    static double threshold = 0.001d;
    static int ITERATIONS = 100000;

    static double[] thetas;

    public static void main(String[] args)
    {
        Matrix data = new Matrix(7, 3);

        data.setRow(0, new Double[]{1.0, 1.17, 78.93});
        data.setRow(1, new Double[]{1.0, 2.97, 58.2});
        data.setRow(2, new Double[]{1.0, 3.26, 67.47});
        data.setRow(3, new Double[]{1.0, 4.69, 37.47});
        data.setRow(4, new Double[]{1.0, 5.83, 45.65});
        data.setRow(5, new Double[]{1.0, 6.00, 32.92});
        data.setRow(6, new Double[]{1.0, 6.41, 29.97});

        thetas = new double[data.getNumCols() - 1];

        for (int i = 0; i < thetas.length; i++) {
            thetas[i] = 1;
        }

        regress(data, ALPHA, ITERATIONS);

        for (int i = 0; i < thetas.length; i++) {
            System.out.println("Theta_" + i + ": " + thetas[i]);
        }
    }

    public static void regress(final Matrix data, final float alpha, int iterations)
    {
        double[] tempTheta = new double[thetas.length];
        for (int iter = 0; iter < iterations; iter++)
        {
            for (int thetaIndex = 0; thetaIndex < thetas.length; thetaIndex++){
                tempTheta[thetaIndex] = thetas[thetaIndex] - (alpha * (jPartialDeriv(thetaIndex, data)));
            }
            thetas = tempTheta;
        }
    }

    private static double jPartialDeriv(final int j, final Matrix data) {
        int m = data.getNumRows();
        double sum = 0;
        for (int i = 0; i < data.getNumRows(); i++)
        {
            double y = data.get(i, data.getNumCols() - 1);
            double rowHypothesis = evaluateHypothesis(data, i);


            sum += (rowHypothesis - y) * data.get(i, j);
        }

        return sum / m;
    }

    private static double evaluateHypothesis(final Matrix matrix, final int row)
    {
        double h = 0.0;
        for (int i = 0; i < thetas.length; i++) {
            h += thetas[i] * matrix.get(row, i);
        }

        return h;
    }


}
