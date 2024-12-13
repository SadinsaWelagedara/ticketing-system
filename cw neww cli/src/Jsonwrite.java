import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Jsonwrite {

    // Method to write configuration to a JSON file
    public void writeConfigToJson(Configuration configuration) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter("configuration.json"); // Changed to configuration.json
            gson.toJson(configuration, writer);
            writer.close();
            System.out.println("Configuration saved to JSON file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load configuration from a JSON file
    public Configuration loadConfigFromJson() {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("configuration.json"); // Changed to configuration.json
            Configuration configuration = gson.fromJson(reader, Configuration.class);
            reader.close();
            return configuration;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
