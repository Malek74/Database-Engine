import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Helpers {
    public static boolean tableExists(String tableName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);

        while (reader.ready()) {
            if (reader.readLine().contains(tableName)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static boolean tableExists(String tableName, String colName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);

        while (reader.ready()) {
            if (reader.readLine().contains(tableName) & reader.readLine().contains(colName)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static ArrayList<String> getIndexes(String tableName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        ArrayList<String> indexes = new ArrayList<>();
        String[] newLine;
        while (reader.ready()) {
            newLine = reader.readLine().split(",");
            if (newLine[0].equals(tableName) && newLine[5].equals("B+tree")) {
                indexes.add(newLine[4]);
            }
        }
        reader.close();
        return indexes;
    }
}
