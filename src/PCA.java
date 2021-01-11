import java.util.ArrayList;
import java.util.List;

public class PCA {

    // Vertical (denotes Features) Means of Input 2D Array
    public Float[] meanVertical(Float[][] X){
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
    public Float[] subtract(Float[] x1, Float[] x2){
        assert x1.length!=x2.length : "Mismatched Dimension Sizes";

        Float[] subResult = new Float[x1.length];

        for(int i=0; i<x1.length; i++){
            subResult[i] = x1[i]-x2[i];
        }

        return subResult;
    }

    // Reshape 1d array (n) -> if firstAxis return (1,n), else return (n,1)
    public Float[][] reshape(Float[] X, boolean firstAxis){
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
    public Float[][] dot(Float[][] x1, Float[][] x2){
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
    public Float[][] add(Float[][] x1, Float[][] x2){
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
    public Float[][] divide(Float[][] X, int N){
        Float[][] divResult = new Float[X.length][X[0].length];

        for(int i=0; i<X.length; i++){
            for(int j=0; j<X[0].length; j++){
                divResult[i][j] = (float) X[i][j] / N;
            }
        }

        return divResult;
    }

    public Float[][] covMatrix(Float[][] X){

        Float[][] covariance_matrix = new Float[X[0].length][X[0].length];
        Float[] meanFeat = meanVertical(X);

        for(int i=0; i<X.length; i++){
            covariance_matrix = add(covariance_matrix, dot(reshape(subtract(X[i], meanFeat), false), reshape(subtract(X[i], meanFeat), true)));
        }

        covariance_matrix = divide(covariance_matrix, X.length-1);
        return  covariance_matrix;
    }

}
