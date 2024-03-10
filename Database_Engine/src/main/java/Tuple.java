import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Tuple {
    Hashtable<String, Object> tableTupleHash;

    // <name,new String("gassser")>
    // <age,new Integer(20)>
    // <gender,new String("male")>

    public Tuple(Hashtable<String, Object> tableTupleHash) {
        this.tableTupleHash = tableTupleHash;
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


        Tuple t = new Tuple(h);
        System.out.println(t);
    }
}
