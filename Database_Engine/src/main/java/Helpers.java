import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Helpers {

    public static boolean tableExists(String tableName) throws IOException {
        FileReader fileReader= new FileReader("metadata.csv");
        BufferedReader reader= new BufferedReader(fileReader);

        while(reader.ready()){
            if(reader.readLine().contains(tableName)){
                return true;

            }
        }

        return false;
    }

}
