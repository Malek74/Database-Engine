import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class Table {
    private ArrayList<Page> pages;
    private String tableName;
    private Page lastPage;
    public Hashtable<String, BplusTree> treesCreated = new Hashtable<>();

    public Table(String tableName) {
        pages = new ArrayList<Page>();
        this.tableName = tableName;
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

    // todo:use the value in appconfig
    public Page getInsertionPage(Comparable value, String compCol) {
        Vector<Tuple> tupleVector;
        // if table doesn't have pages (first entry)
        if (pages.size() == 0) {
            this.addPage(new Page(pages.size() + 1, tableName));
            return this.pages.get(0);
        }

        for (Page page : pages) {
            tupleVector = Helpers.deserializeTuple(page.getPath());
            // check if page is full
            if (tupleVector.size() == 200) {
                Tuple t = tupleVector.get(tupleVector.size() - 1);

                if (value.compareTo((Comparable) t.getValue(compCol)) >= 0) {
                    continue;
                } else {
                    return page;
                }
            } else {
                return page;
            }
        }
        this.addPage(new Page(pages.size() + 1, tableName));
        return this.pages.get(pages.size() - 1);
    }

    public void insert(Page p, Tuple t) {
        Vector<Tuple> tupleVector = Helpers.deserializeTuple(p.getPath());
        int i = pages.indexOf(p);

        tupleVector.add(t);
        Collections.sort(tupleVector, p.getComparetor());

        if (tupleVector.size() > 200) {
            if (pages.size() > i + 1) {
                insert(pages.get(i + 1),tupleVector.remove(tupleVector.size()-1));
            } else {
                Page newPage = new Page(pages.size() + 1, tableName);
                this.addPage(newPage);
                insert(newPage, tupleVector.remove(tupleVector.size()-1));
            }
        }
        Helpers.serializeTuple(tupleVector, p.getPath());
    }
}
