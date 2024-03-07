import java.util.ArrayList;

public class Table {
    private ArrayList<String> pages;

    public Table() {
        pages = new ArrayList<>();
    }

    // todo:add exception to check class type
    public void addPage(String page) {
        pages.add(page);
    }

    public void deletePage(String page) {
        pages.remove(page);
    }

    public int getNumberOfPages() {
        return pages.size();
    }
}
