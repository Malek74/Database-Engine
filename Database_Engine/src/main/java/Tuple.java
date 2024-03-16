import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Tuple implements Serializable {
    Hashtable<String, Object> tableTupleHash;
    String clusteringKey;
    // <name,new String("gassser")>
    // <age,new Integer(20)>
    // <gender,new String("male")>

    public Tuple(Hashtable<String, Object> tableTupleHash, String clusteringKey) {
        this.tableTupleHash = tableTupleHash;
        this.clusteringKey = clusteringKey;
    }

    public Object getValue(String colName) {
        return tableTupleHash.get(colName);
    }

    // todo:toString Method
    @Override
    public String toString() {
        String out = "";
        Set<String> keyNames = tableTupleHash.keySet();
        Iterator<String> itr = keyNames.iterator();
        while (itr.hasNext()) {
            out += tableTupleHash.get(itr.next());
            if (itr.hasNext())
                out += ",";
        }
        return out;
    }

    public static void main(String[] args) {
        Hashtable<String, Object> h = new Hashtable<>();
        h.put("age", 20);
        h.put("address", "Rehab");
        h.put("name", "Ahmed");

        Hashtable<String, Object> h2 = new Hashtable<>();
        h2.put("age", 22);
        h2.put("address", "Rehab");
        h2.put("name", "Ahmed");

        Hashtable<String, Object> h3 = new Hashtable<>();
        h3.put("age", 2);
        h3.put("address", "Rehab");
        h3.put("name", "Ahmed");

        Tuple t = new Tuple(h, "age");
        Tuple t2 = new Tuple(h2, "age");
        Tuple t3 = new Tuple(h3, "age");

        Vector<Tuple> v = new Vector<Tuple>();
        v.add(t2);
        v.add(t);

        TupleComparetor comp = new TupleComparetor();
        System.out.println(v);
        Collections.sort(v, comp);
        System.out.println(v);
        v.add(t3);
        System.out.println(v);
        Collections.sort(v, comp);
        System.out.println(v);
    }
}

class TupleComparetor implements Comparator<Tuple> {
    @Override
    public int compare(Tuple o1, Tuple o2) {
        return ((Comparable) o1.getValue(o1.clusteringKey)).compareTo((Comparable) o2.getValue(o2.clusteringKey));
    }

}
