package in.sisoft.all_in_one.pojo;

public class Promotions {
    public int promo_id ;
    public int biz_id ;
    public String promo_code ;
    public String promo_text1 ;
    public String promo_text2 ;
    public String promo_start_dt ;
    public String promo_end_dt ;
    public String created_by ;
    public String creation_dtm ;

    public String biz_name ;
    public String biz_address ;
    public String biz_phone ;

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getPromo_text1() {
        return promo_text1;
    }

    public void setPromo_text1(String promo_text1) {
        this.promo_text1 = promo_text1;
    }

    public String getPromo_text2() {
        return promo_text2;
    }

    public void setPromo_text2(String promo_text2) {
        this.promo_text2 = promo_text2;
    }

    public String getPromo_start_dt() {
        return promo_start_dt;
    }

    public void setPromo_start_dt(String promo_start_dt) {
        this.promo_start_dt = promo_start_dt;
    }

    public String getPromo_end_dt() {
        return promo_end_dt;
    }

    public void setPromo_end_dt(String promo_end_dt) {
        this.promo_end_dt = promo_end_dt;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreation_dtm() {
        return creation_dtm;
    }

    public void setCreation_dtm(String creation_dtm) {
        this.creation_dtm = creation_dtm;
    }
}
