import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

public class Page {
    private Vector<Tuple> tupleVector;
    private TupleComparetor comparetor;

    public TupleComparetor getComparetor() {
        return comparetor;
    }

    private String path;

    public Page(int n, String name) {
        tupleVector = new Vector<>();
        path = name + "_" + n + ".ser";
        Helpers.serializeTuple(tupleVector, path);
        comparetor = new TupleComparetor();
    }

    public int getRecordsNumber() {
        return 0;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        String out = "[";
        for (int i = 0; i < tupleVector.size() - 1; i++) {
            out += tupleVector.get(i) + ",";
        }
        out += tupleVector.get(tupleVector.size() - 1) + "]";
        return out;
    }

    public static void main(String[] args) {
        Page t = new Page(1, "malek");

        Hashtable<String, Object> h = new Hashtable<>();
        h.put("age", 20);
        h.put("address", "Rehab");
        h.put("name", "Ahmed");

    }
}
