import java.io.Serializable;
import java.util.Vector;

public class Page implements Serializable {
    private Vector<Tuple> tupleVector;

    public Page(){
        tupleVector=new Vector<>();
    }
    public Page(Tuple t){
        tupleVector=new Vector<>();
        tupleVector.add(t);
    }

    public int getRecordsNumber(){
        return tupleVector.size();
    }
}
