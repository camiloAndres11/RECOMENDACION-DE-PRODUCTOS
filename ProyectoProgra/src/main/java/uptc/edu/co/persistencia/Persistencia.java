package uptc.edu.co.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uptc.edu.co.modelo.Nodo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Persistencia {
    private static final Gson gson = new Gson();
    private static final String DEFAULT_FILE_PATH = "Productos.json";

    public static <T> List<T> readJSON(String filePath, Type type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        }
    }

    public static List<Nodo> readJSON(String filePath) throws IOException {
        Type listType = new TypeToken<List<Nodo>>(){}.getType(); // Usar TypeToken para especificar el tipo exacto
        return readJSON(filePath, listType);
    }

    public static <T> void writeJSON(String filePath, List<T> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        }
    }

    public static void writeJSON(List<Nodo> data) throws IOException {
        writeJSON(DEFAULT_FILE_PATH, data);
    }
}
