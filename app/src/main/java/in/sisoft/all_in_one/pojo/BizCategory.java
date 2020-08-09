package in.sisoft.all_in_one.pojo;

/**
 * Created by vijay on 26-Nov-17.
 */
public class BizCategory {
    public  int id;
    public String name;
    public String disp_status ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisp_status() {
        return disp_status;
    }

    public void setDisp_status(String disp_status) {
        this.disp_status = disp_status;
    }

    public BizCategory(){

    }
    public BizCategory(int id, String nm) {
        this.id = id;
        this.name = nm;
    }

}
