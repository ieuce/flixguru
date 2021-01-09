import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {
        String line;

        String data_dir = "F:\\PROJECTS\\PROCESSING\\suggest2me\\data\\ml-25m\\";
        String movies_path = data_dir+"movies.csv";
        String genome_tags_path = data_dir+"genome-tags.csv";
        String genome_scores_path = data_dir+"genome-scores.csv";

        Map<Integer, String> movie_map = new HashMap<Integer, String>();
        Map<Integer, String> all_genome_map = new HashMap<Integer, String>();
        Map<Integer, double[]> movieGenomeScore_map = new HashMap<Integer, double[]>();

        // Movies Data Frame
        try {
            BufferedReader movies_df = new BufferedReader(new FileReader(movies_path));
            boolean first_line = true;
            while ((line = movies_df.readLine()) != null) {
                if(first_line){
                    first_line = false;
                } else {
                    String[] metadata = line.split(",");
                    movie_map.put(Integer.parseInt(metadata[0]), metadata[1]);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        // Genome Tags Data Frame
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

        // Genome Scores Data Frame
        try {
            BufferedReader genome_scores_df = new BufferedReader(new FileReader(genome_scores_path));
            line = genome_scores_df.readLine();
            String[] metadata = line.split(",");

            int row_counter = 0;
            double[] genomeTagScores = new double[all_genome_map.size()];
            while ((line = genome_scores_df.readLine()) != null) {
                if((row_counter % all_genome_map.size() == 0) && (row_counter != 0)){
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

    }
}