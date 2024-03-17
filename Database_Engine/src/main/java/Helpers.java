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
        String line;
        while (reader.ready()) {
            line=reader.readLine();
            if (line.contains(tableName) && line.contains(colName)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

   public static int insertionValid(String tableName,Hashtable<String,Object> values) throws IOException {
       FileReader fileReader = new FileReader("metadata.csv");
       BufferedReader reader = new BufferedReader(fileReader);
       ArrayList<String[]> tableData= new ArrayList<String[]>();
       String[] newLine;
       Object columnVal;

       //fetch all columns of table from metadata
       while (reader.ready() && tableData.size() != values.size()) {
           newLine = reader.readLine().split(",");
           if (newLine[0].equals(tableName)){
               tableData.add(newLine);
           }
       }

       //check that all columns have valid value and primary key doesn't contain null
       for(String[] column : tableData){
           columnVal= values.get(column[1]);

           // Check if the column is primary key and not null
           if(Boolean.parseBoolean(column[3]) && columnVal== null){
               return -1;
           }

           // Validate data type based on metadata
           if(columnVal instanceof Integer){
               if(!(column[2].equals("java.lang.Integer"))){
                   return -2;
               };
           }

           else if(columnVal instanceof String){
               if(!(column[2].equals("java.lang.String"))){
                   return -2;
               };
           }

           else if(columnVal instanceof Double){
               if(!(column[2].equals("java.lang.double"))){
                   return -2;
               };
           }
       }
       return 0;
   }

    public static Hashtable<String, String> getIndexes(String tableName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        Hashtable<String, String> indexes = new Hashtable<>();
        String[] newLine;
        while (reader.ready()) {
            newLine = reader.readLine().split(",");
            if (newLine[0].equals(tableName) && newLine[5].equals("B+tree")) {
                indexes.put(newLine[1], newLine[4]);
            }
        }
        reader.close();
        return indexes;
    }

    public static String getClusteringKey(String tableName) throws IOException {
        FileReader fileReader = new FileReader("metadata.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        String[] newLine;

        while (reader.ready()) {
            newLine = reader.readLine().split(",");
            if (newLine[0].equals(tableName) && newLine[3].equals("True")) {
                reader.close();
                return newLine[1];
            }
        }
        reader.close();
        return null;
    }

    public static void serializeTuple(Vector<Tuple> tupletoSer, String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tupletoSer);
            out.close();
            fileOut.close();
        } catch (IOException e) {

        }
    }


    public static Vector<Tuple> deserializeTuple(String path) {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            return (Vector<Tuple>) in.readObject();

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
