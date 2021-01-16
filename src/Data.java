import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.*;

public class Data {
    public static Map<Integer, String> genomeID_Tag(String genome_tags_path){
        Map<Integer, String> all_genome_map = new LinkedHashMap<Integer, String>();
        String line;
        try {
            BufferedReader genome_tags_df = new BufferedReader(new FileReader(genome_tags_path));
            boolean first_line = true;
            while ((line = genome_tags_df.readLine()) != null) {
                if(first_line){
                    first_line = false;
                } else {
                    String[] metadata = line.split(",");
                    all_genome_map.put(Integer.parseInt(metadata[0]), metadata[1]);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return all_genome_map;
    }

    public static Map<Integer, double[]> movieID_Encodings(String genome_scores_path, String genome_tags_path){
        Map<Integer, String> all_genome_map = genomeID_Tag(genome_tags_path);
        Map<Integer, double[]> movieGenomeScore_map = new LinkedHashMap<Integer, double[]>();
        String line;
        try {
            BufferedReader genome_scores_df = new BufferedReader(new FileReader(genome_scores_path));
            line = genome_scores_df.readLine();
            String[] metadata = line.split(",");

            int row_counter = 0;
            double[] genomeTagScores = new double[all_genome_map.size()];
            while ((line = genome_scores_df.readLine()) != null) {
                if((row_counter % all_genome_map.size() == all_genome_map.size()-1)){
                    movieGenomeScore_map.put(Integer.parseInt(metadata[0]), genomeTagScores);
                    genomeTagScores = new double[all_genome_map.size()];
                }
                metadata = line.split(",");
                genomeTagScores[row_counter % all_genome_map.size()] = Double.parseDouble(metadata[2]);
                row_counter++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return movieGenomeScore_map;
    }

    public static double[][] get_encodings(String genome_scores_path, String genome_tags_path){
        Map<Integer, double[]> movieGenomeScore_map = movieID_Encodings(genome_scores_path, genome_tags_path);
        double[][] encodings = new double[movieGenomeScore_map.keySet().size()][];

        int ith = 0;
        for(int key: movieGenomeScore_map.keySet()){
            encodings[ith] = movieGenomeScore_map.get(key);
            ith++;
        }
        return encodings;
    }

    public static void main(String[] args) {
        final int n_components = 2;

        String data_dir = "F:\\PROJECTS\\PROCESSING\\suggest2me\\data\\ml-25m\\";
        String genome_tags_path = data_dir+"genome-tags.csv";
        String genome_scores_path = data_dir+"genome-scores.csv";

        Map<Integer, double[]> movieGenomeScore_map = movieID_Encodings(genome_scores_path, genome_tags_path);

        double[][] encodings = get_encodings(genome_scores_path, genome_tags_path);
        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis(n_components);
        pca.fit(encodings);

        double[][] transformed_encodings1 = pca.transform(pca.expand_dims(movieGenomeScore_map.get(4), true)); //4,Waiting to Exhale (1995),Comedy|Drama|Romance
        System.out.println(ArrayUtils.toString(transformed_encodings1[0]));
        double[][] transformed_encodings2 = pca.transform(pca.expand_dims(movieGenomeScore_map.get(11), true)); // 11,"American President, The (1995)",Comedy|Drama|Romance
        System.out.println(ArrayUtils.toString(transformed_encodings2[0]));
        double[][] transformed_encodings3 = pca.transform(pca.expand_dims(movieGenomeScore_map.get(2), true)); // 2,Jumanji (1995),Adventure|Children|Fantasy
        System.out.println(ArrayUtils.toString(transformed_encodings3[0]));

        double pos_dist = Math.pow(Math.pow(transformed_encodings1[0][0]-transformed_encodings2[0][0], 2) + Math.pow(transformed_encodings1[0][1]-transformed_encodings2[0][1], 2), 0.5);
        System.out.println("positive distance 4-11: " + pos_dist);

        double neg_dist = Math.pow(Math.pow(transformed_encodings1[0][0]-transformed_encodings3[0][0], 2) + Math.pow(transformed_encodings1[0][1]-transformed_encodings3[0][1], 2), 0.5);
        System.out.println("negative distance 2-4: " + neg_dist);

    }
}