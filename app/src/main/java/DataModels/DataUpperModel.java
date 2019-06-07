package DataModels;

import java.util.List;

public class DataUpperModel {
    private String page;
    private String pages;
    private String perpage;
    private String total;
    private List<DataModel> photo;
    private String stat;

    public DataUpperModel(String page, String pages, String perpage, String total, List<DataModel> photos, String stat) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
        this.stat = stat;
    }

    public String getPage() {
        return page;
    }

    public String getPages() {
        return pages;
    }

    public String getPerpage() {
        return perpage;
    }

    public String getTotal() {
        return total;
    }

    public List<DataModel> getPhoto() {
        return photo;
    }

    public String getStat() {
        return stat;
    }
}
