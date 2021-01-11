import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static symjava.symbolic.Symbol.*;
import symjava.relational.Eq;
import symjava.symbolic.*;

public class PCA {

    // Vertical (denotes Features) Means of Input 2D Array
    public static Float[] meanVertical(Float[][] X){
        List<Float> meanList = new ArrayList<Float>();

        for(int i=0; i<X[0].length; i++){
            float sumCol = 0;
            for(int j=0; j<X.length; j++){
                sumCol += X[j][i];
            }
            meanList.add((float) sumCol/X.length);
        }

        Float[] meanArray = new Float[meanList.size()];
        meanArray = meanList.toArray(meanArray);

        return meanArray;
    }

    // Subtract two 1D array with same shapes
    public static Float[] subtract(Float[] x1, Float[] x2){
        assert x1.length!=x2.length : "Mismatched Dimension Sizes";

        Float[] subResult = new Float[x1.length];

        for(int i=0; i<x1.length; i++){
            subResult[i] = x1[i]-x2[i];
        }

        return subResult;
    }

    // Reshape 1d array (n) -> if firstAxis return (1,n), else return (n,1)
    public static Float[][] reshape(Float[] X, boolean firstAxis){
        Float[][] transposedX;

        if(firstAxis){
            transposedX = new Float[1][X.length];

            for(int i=0; i<X.length; i++){
                transposedX[0][i] = X[i];
            }

        } else{
            transposedX = new Float[X.length][1];

            for(int i=0; i<X.length; i++){
                transposedX[i][0] = X[i];
            }
        }

        return transposedX;
    }

    // Apply Dot-Product to two arrays
    public static Float[][] dot(Float[][] x1, Float[][] x2){
        assert x1[0].length != x2.length : "Mismatched Dimension Sizes";
        int common_dim_size = x1[0].length;

        Float[][] dotResult = new Float[x1.length][x2[0].length];
        float datum1;
        float datum2;

        for(int i=0; i<x1.length; i++){
            for(int j=0; j<x2[0].length; j++){
                float sumDot = 0;

                for(int k=0; k<common_dim_size; k++){
                    datum1 = x1[i][k];
                    datum2 = x2[k][j];
                    sumDot += (float) datum1*datum2;
                }

                dotResult[i][j] = sumDot;
            }
        }

        return dotResult;
    }

    // Add two 2D array
    public static Float[][] add(Float[][] x1, Float[][] x2){
        assert x1.length != x2.length : "Mismatched Dimension Sizes in axis=0";
        assert x1[0].length != x2[0].length : "Mismatched Dimension Sizes in axis=1";

        Float[][] addResult = new Float[x1.length][x1[0].length];

        for(int i=0; i<x1.length; i++){
            for(int j=0; j<x1[0].length; j++){
                addResult[i][j] = x1[i][j] + x2[i][j];
            }
        }

        return addResult;
    }

    // Divide 2D array elements by specified integer
    public static Float[][] divide(Float[][] X, int N){
        Float[][] divResult = new Float[X.length][X[0].length];

        for(int i=0; i<X.length; i++){
            for(int j=0; j<X[0].length; j++){
                divResult[i][j] = (float) X[i][j] / N;
            }
        }

        return divResult;
    }

    // Create nxm sized 2D array with filled zeros
    public static Float[][] createZeros2D(int n, int m){
        Float[][] X = new Float[n][m];

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                X[i][j] = Float.valueOf(0);
            }
        }

        return X;
    }

    public static Float[][] covMatrix(Float[][] X){

        Float[][] covariance_matrix = createZeros2D(X[0].length, X[0].length);
        Float[] meanFeat = meanVertical(X);

        for(int i=0; i<X.length; i++){
            covariance_matrix = add(covariance_matrix, dot(reshape(subtract(X[i], meanFeat), false), reshape(subtract(X[i], meanFeat), true)));
        }
        covariance_matrix = divide(covariance_matrix, X.length-1);

        return covariance_matrix;
    }
    /*
    public HashMap<Float[], Float[]> eig(Float[][] X){
        PolynomialFunction polynomial = new PolynomialFunction(new double[]{ -4, 3, 1});
        LaguerreSolver laguerreSolver = new LaguerreSolver();
        double root = laguerreSolver.solve(100, polynomial, -100, 100);
        System.out.println("root = " + root);
    }
    */

}
