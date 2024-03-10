import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class Table {
    private ArrayList<Page> pages;
    private String tableName;

    public Hashtable<String, BplusTree> treesCreated = new Hashtable<>();


    public Table(String tableName) {

        pages = new ArrayList<Page>();
        this.tableName=tableName;
    }

    // todo:add exception to check class type
    public void addPage(Page page) {
        pages.add(page);
    }

    public void deletePage(Page page) {
        pages.remove(page);
    }

    public int getNumberOfPages() {
        return pages.size();
    }

    //todo:use the value in appconfig
    public Page getInsertionPage(Comparable value,String compCol){
        Vector<Tuple> tupleVector;
        //if table doesn't have pages (first entry)
        if(pages.size()==0){
            this.addPage(new Page(pages.size()+1,tableName));
            return this.pages.get(0);
        }

        for(Page page:pages){
             tupleVector=Helpers.deserializeTuple(page.getPath());
             //check if page is full

             if(tupleVector.size()==200){

                 if(tupleVector.get(199).tableTupleHash.)
             }
        }
        return null;
    }


}
