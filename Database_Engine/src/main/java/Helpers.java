import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

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

    public static Hashtable<String,String> getIndexes(String tableName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        Hashtable<String,String> indexes = new Hashtable<>();
        String[] newLine;
        while (reader.ready()) {
            newLine = reader.readLine().split(",");
            if (newLine[0].equals(tableName) && newLine[5].equals("B+tree")) {
                indexes.put(newLine[1],newLine[4]);
            }
        }
        reader.close();
        return indexes;
    }
    public static void serializeTuple(Vector<Tuple> tupletoSer,String path){
        try{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tupletoSer);
            out.close();
            fileOut.close();
        }
        catch(IOException e){

        }
    }


    public static Vector<Tuple> deserializeTuple(String path){
        try {
            FileInputStream fileIn = new FileInputStream("malek_1.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            return (Vector<Tuple>) in.readObject();

        }catch( IOException e){

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
