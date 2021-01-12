import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.*;
import java.util.ArrayList;
import java.util.List;

public class PrincipalComponentAnalysis {
    private static boolean _fitted = false;
    private static double[][] _eigenVectors;
    private static int _n_components;

    public PrincipalComponentAnalysis(int n_components){
        assert n_components >= 1 : "n_components should be equals or greater than 1";
        _n_components = n_components;
    }

    // Vertical (denotes Features) Means of Input 2D Array
    private static double[] meanVertical(double[][] X){
        List<Double> meanList = new ArrayList<Double>();

        for(int i=0; i<X[0].length; i++){
            double sumCol = Double.valueOf(0);
            for(int j=0; j<X.length; j++){
                sumCol += X[j][i];
            }
            meanList.add((double) sumCol/X.length);
        }

        Double[] meanArray = new Double[meanList.size()];
        meanArray = meanList.toArray(meanArray);

        return ArrayUtils.toPrimitive(meanArray);
    }

    // Subtract two 1D array with same shapes
    private static double[] subtract(double[] x1, double[] x2){

        double[] subResult = new double[x1.length];

        for(int i=0; i<x1.length; i++){
            subResult[i] = x1[i]-x2[i];
        }

        return subResult;
    }

    // Reshape 1d array (n) -> if firstAxis return (1,n), else return (n,1)
    public static double[][] expand_dims(double[] X, boolean firstAxis){
        double[][] transposedX;

        if(firstAxis){
            transposedX = new double[1][X.length];

            for(int i=0; i<X.length; i++){
                transposedX[0][i] = X[i];
            }

        } else{
            transposedX = new double[X.length][1];

            for(int i=0; i<X.length; i++){
                transposedX[i][0] = X[i];
            }
        }

        return transposedX;
    }

    // Apply Dot-Product to two arrays
    private static double[][] dot(double[][] x1, double[][] x2){

        int common_dim_size = x1[0].length;

        double[][] dotResult = new double[x1.length][x2[0].length];
        double datum1;
        double datum2;

        for(int i=0; i<x1.length; i++){
            for(int j=0; j<x2[0].length; j++){
                double sumDot = Double.valueOf(0);

                for(int k=0; k<common_dim_size; k++){
                    datum1 = x1[i][k];
                    datum2 = x2[k][j];
                    sumDot += (double) datum1*datum2;
                }

                dotResult[i][j] = sumDot;
            }
        }

        return dotResult;
    }

    // Add two 2D array
    private static double[][] add(double[][] x1, double[][] x2){

        double[][] addResult = new double[x1.length][x1[0].length];

        for(int i=0; i<x1.length; i++){
            for(int j=0; j<x1[0].length; j++){
                addResult[i][j] = x1[i][j] + x2[i][j];
            }
        }

        return addResult;
    }

    // Divide 2D array elements by specified integer
    private static double[][] divide(double[][] X, int N){
        double[][] divResult = new double[X.length][X[0].length];

        for(int i=0; i<X.length; i++){
            for(int j=0; j<X[0].length; j++){
                divResult[i][j] = (double) X[i][j] / N;
            }
        }

        return divResult;
    }

    // Create nxm sized 2D array with filled with zeros
    private static double[][] createZeros2D(int n, int m){
        double[][] X = new double[n][m];

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                X[i][j] = Double.valueOf(0);
            }
        }

        return X;
    }

    // Transpose 2D array
    private static double[][] transpose(double[][] X){
        double[][] X_transposed = new double[X[0].length][X.length];

        for(int i=0; i<X.length; i++){
            for(int j=0; j<X[0].length; j++){
                X_transposed[j][i] = X[i][j];
            }
        }

        return X_transposed;
    }

    // Create Covariance Matrix of Data
    private static double[][] covMatrix(double[][] X){
        double[][] covariance_matrix = createZeros2D(X[0].length, X[0].length);
        double[] meanFeat = meanVertical(X);

        for(int i=0; i<X.length; i++){
            covariance_matrix = add(covariance_matrix, dot(expand_dims(subtract(X[i], meanFeat), false), expand_dims(subtract(X[i], meanFeat), true)));
        }
        covariance_matrix = divide(covariance_matrix, X.length-1);

        return covariance_matrix;
    }

    // Compute EigenValues and EigenVectors
    private static void eig(double[][] X, int k){
        RealMatrix Xrm = MatrixUtils.createRealMatrix(X);
        EigenDecomposition eig = new EigenDecomposition(Xrm);

        List<double[]> eigComputations = new ArrayList<double[]>();

        double[] eigenValues = eig.getRealEigenvalues();
        double[] topK_eigenValues = new double[k];
        for(int i=0; i<k; i++){
            topK_eigenValues[i] = eigenValues[i];
        }

        double[] eigVec;
        double[][] topK_eigenVectors = new double[X.length][k];
        for(int i=0; i<k; i++){
            eigVec = eig.getEigenvector(i).toArray();
            for(int j=0; j<X.length; j++){
                topK_eigenVectors[j][i] = eigVec[j];
            }
        }

        _eigenVectors = topK_eigenVectors;
    }

    public static double[][] transform(double[][] X) {
        assert _fitted : "Before transformation of data, apply fit method -> fit(...);";

        return dot(X, _eigenVectors);
    }

    public static void fit(double[][] Xencodings){
        double[][] covMatrix_data = covMatrix(Xencodings);
        eig(covMatrix_data, _n_components);

        _fitted = true;
    }

    public static double[][] fit_transform(double[][] Xencodings){
        double[][] covMatrix_data = covMatrix(Xencodings);
        eig(covMatrix_data, _n_components);

        _fitted = true;

        return transform(Xencodings);
    }

}
