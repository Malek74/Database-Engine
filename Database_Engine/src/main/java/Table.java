import java.util.ArrayList;

public class Table {
    private ArrayList<Page> pages;
    
    public Table(){
        pages=new ArrayList<>();
    }
    
    //todo:add exception to check class type
    public void addPage(Page page){
        pages.add(page);
    }
    
    public void deletePage(Page page){
        pages.remove(page);
    }
}
