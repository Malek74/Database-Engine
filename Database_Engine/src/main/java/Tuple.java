import java.io.Serializable;
import java.util.Hashtable;

public class Tuple implements Serializable {
    Hashtable<String, Object> tableTupleHash;

    public Tuple(Hashtable<String, Object> tableTupleHash) {
        this.tableTupleHash = tableTupleHash;
    }

}
