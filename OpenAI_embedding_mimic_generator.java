import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class OpenAI_embedding_mimic_generator {

    public static void main(String[] args) {
        String filename = "vector-dataset.txt";
        int numLines = 1000;
        int numFloatsPerLine = 1536;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            Random random = new Random();
            for (int i = 0; i < numLines; i++) {
                StringBuilder line = new StringBuilder();

                for (int j = 0; j < numFloatsPerLine; j++) {
                    float floatValue = random.nextFloat() * 2 - 1; // Generates float between -1 and 1
                    line.append(floatValue);
                    if (j < numFloatsPerLine - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
            writer.close();
            System.out.println("File generated successfully: " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
