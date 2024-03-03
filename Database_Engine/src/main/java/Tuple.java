import java.io.Serializable;
import java.util.Hashtable;

public class Tuple {
    Hashtable<String, Object> tableTupleHash;

//    <name,new String("gassser")>
//    <age,new Integer(20)>
//    <gender,new String("male")>

    public Tuple(Hashtable<String, Object> tableTupleHash) {
        this.tableTupleHash = tableTupleHash;
    }

    //todo:toString Method


}
