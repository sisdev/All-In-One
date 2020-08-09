package in.sisoft.all_in_one.DbSvr;

/**
 * Created by vijay on 26-Nov-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import in.sisoft.all_in_one.pojo.BizCategory;
import in.sisoft.all_in_one.pojo.BizEstablishment;


/**
 * Created by Dell on 19-09-2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    static final int DB_VERSION = 1 ;

    public DbHelper(Context context)
    {
        super(context, "All_In_One", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE `biz_category` (\n" +
                "\t`bcat_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`bcat_name`\tTEXT,\n" +
                "\t`disp_status`\tTEXT\n" +
                ")");

        db.execSQL("CREATE TABLE `biz_establishment` (\n" +
                "\t`biz_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`biz_name`\tTEXT,\n" +
                "\t`biz_street`\tTEXT,\n" +
                "\t`biz_city`\tINTEGER,\n" +
                "\t`biz_district`\tTEXT,\n" +
                "\t`biz_state`\tTEXT,\n" +
                "\t`biz_pin`\tTEXT,\n" +
                "\t`biz_phone1`\tTEXT,\n" +
                "\t`biz_phone2`\tTEXT,\n" +
                "\t`biz_country`\tTEXT,\n" +
                "\t`biz_details`\tTEXT,\n" +
                "\t`biz_logo_image_name`\tTEXT,\n" +
                "\t`bcat_id`\tTEXT,\n" +
                "\t`disp_status`\tTEXT\n" +
                ")");


        db.execSQL("insert into biz_category('bcat_name')values('Advertizing')");
        db.execSQL("insert into biz_category('bcat_name')values('Doctors')");
        db.execSQL("insert into biz_category('bcat_name')values('Parlour')");
        db.execSQL("insert into biz_category('bcat_name')values('Training Institute')");
        db.execSQL("insert into biz_establishment('biz_name','biz_street','biz_city','biz_district','biz_state','biz_pin'," +
                "'biz_phone1','biz_phone2','biz_country','biz_details','biz_logo_image_name','bcat_id' )" +
                "values('Sisoft','Shipra Riviera Bazar, Gyan Khand-3, Indirapuram','Ghaziabad','Ghaziabad','UP','201014','9999283283','9540283283'," +
                "'India','Computer Training Institute', 'None', 1)");
        db.execSQL("insert into biz_establishment('biz_name','biz_street','biz_city','biz_district','biz_state','biz_pin'," +
                "'biz_phone1','biz_phone2','biz_country','biz_details','biz_logo_image_name','bcat_id' )" +
                "values('ApTech','Shipra Riviera Bazar, Gyan Khand-3, Indirapuram','Ghaziabad','Ghaziabad','UP','201014','9999283283','9540283283'," +
                "'India','Computer Training Institute', 'None', 1)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // ALTER TABLE `biz_category` ADD `disp_seq` INT NOT NULL DEFAULT '99'

    }

    void insert_biz_category(BizCategory bc1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bcat_id", bc1.id);
        cv.put("bcat_name", bc1.name);
        cv.put("disp_status", bc1.disp_status);
        db.insert("biz_category", null, cv);
        db.close();
    }

    void update_biz_category(BizCategory bc1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bcat_id", bc1.id);
        cv.put("bcat_name", bc1.name);
        cv.put("disp_status", bc1.disp_status);
        String whereClause = "bcat_id=?";
        String[] whereArgs = {String.valueOf(bc1.id)};
        db.update("biz_category",cv,whereClause,whereArgs);
        db.close();

    }


    public void insert_update_biz_category(BizCategory bc1) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String sql ="SELECT bcat_id FROM biz_category WHERE bcat_id="+bc1.id;
        cursor= db.rawQuery(sql,null);
        int row_count = cursor.getCount();
        db.close();
        Log.d("Row Existence : ", row_count+":"+bc1.id+":"+bc1.name);

        if (row_count>0)
            update_biz_category(bc1);
        else
            insert_biz_category(bc1);
    }

    public Cursor query_biz_category() {
        SQLiteDatabase db = getReadableDatabase();
        String[] args={"Y"};
        Cursor c1 = db.rawQuery("SELECT * FROM biz_category where disp_status=? ORDER BY RANDOM()", args);
        return c1;
    }

    public ArrayList<BizCategory> query_biz_category_array(int sort_order) {
        ArrayList<BizCategory> al_bc = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] args={"Y"};
        Cursor c1 ;
        if (sort_order == 0) {
            c1 = db.rawQuery("SELECT * FROM biz_category where disp_status=? ORDER BY bcat_name", args);
        }
        else
        {
            c1 = db.rawQuery("SELECT * FROM biz_category where disp_status=? ORDER BY bcat_name desc", args);
        }

        if (c1.moveToFirst()) {
            do {
                String id = c1.getString(c1.getColumnIndex("bcat_id"));
                String biz_name = c1.getString(c1.getColumnIndex("bcat_name"));
                BizCategory bcat = new BizCategory(Integer.parseInt(id), biz_name);
                al_bc.add(bcat);
            } while (c1.moveToNext());
        }
        db.close();
        return al_bc ;
    }

    void insert_biz_establishment(BizEstablishment be1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("biz_id", be1.biz_id);
        cv.put("biz_name", be1.biz_name);
        cv.put("biz_street", be1.biz_street);
        cv.put("biz_city", be1.biz_city);
        cv.put("biz_district", be1.biz_district);
        cv.put("biz_state", be1.biz_state);
        cv.put("biz_pin", be1.biz_pin);
        cv.put("biz_phone1", be1.biz_phone1);
        cv.put("biz_phone2", be1.biz_phone2);
        cv.put("biz_country", be1.biz_country);
        cv.put("biz_details", be1.biz_details);
        cv.put("biz_logo_image_name", be1.biz_logo_image_loc);
        cv.put("bcat_id", be1.bcat_id);
        cv.put("disp_status", be1.disp_status);
        db.insert("biz_establishment", null, cv);
        db.close();
    }

    void update_biz_establishment(BizEstablishment be1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("biz_id", be1.biz_id);
        cv.put("biz_name", be1.biz_name);
        cv.put("biz_street", be1.biz_street);
        cv.put("biz_city", be1.biz_city);
        cv.put("biz_district", be1.biz_district);
        cv.put("biz_state", be1.biz_state);
        cv.put("biz_pin", be1.biz_pin);
        cv.put("biz_phone1", be1.biz_phone1);
        cv.put("biz_phone2", be1.biz_phone2);
        cv.put("biz_country", be1.biz_country);
        cv.put("biz_details", be1.biz_details);
        cv.put("biz_logo_image_name", be1.biz_logo_image_loc);
        cv.put("bcat_id", be1.bcat_id);
        cv.put("disp_status", be1.disp_status);

        String whereClause = "biz_id=?";
        String[] whereArgs = {String.valueOf(be1.biz_id)};
        db.update("biz_establishment",cv,whereClause,whereArgs);
        db.close();
    }


    public void insert_update_biz_establishment(BizEstablishment be1) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String sql ="SELECT biz_id FROM biz_establishment WHERE biz_id="+be1.biz_id;
        cursor= db.rawQuery(sql,null);
        int row_count = cursor.getCount();
        db.close();
        Log.d("Row Existence : ", row_count+":"+be1.bcat_id+":"+be1.biz_name);

        if (row_count>0)
            update_biz_establishment(be1);
        else
            insert_biz_establishment(be1);
    }

    public ArrayList<BizEstablishment> query_biz_establishments(int biz_cat) {
        ArrayList<BizEstablishment> al_bc = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] args={String.valueOf(biz_cat),"Y"};
        Cursor c1 = db.rawQuery("SELECT * FROM biz_establishment where bcat_id=? and disp_status=?", args);
        if (c1.moveToFirst()) {
            do {
                BizEstablishment bizEst = new BizEstablishment();
                bizEst.biz_id = Integer.parseInt(c1.getString(c1.getColumnIndex("biz_id")));
                bizEst.biz_name = c1.getString(c1.getColumnIndex("biz_name"));
                bizEst.biz_street = c1.getString(c1.getColumnIndex("biz_street"));
                bizEst.biz_city = c1.getString(c1.getColumnIndex("biz_city"));
                bizEst.biz_district = c1.getString(c1.getColumnIndex("biz_district"));
                bizEst.biz_state = c1.getString(c1.getColumnIndex("biz_state"));
                bizEst.biz_pin = c1.getString(c1.getColumnIndex("biz_pin"));
                bizEst.biz_phone1 = c1.getString(c1.getColumnIndex("biz_phone1"));
                bizEst.biz_phone2 = c1.getString(c1.getColumnIndex("biz_phone2"));
                bizEst.biz_country = c1.getString(c1.getColumnIndex("biz_country"));
                bizEst.biz_details = c1.getString(c1.getColumnIndex("biz_details"));
                bizEst.biz_logo_image_loc = c1.getString(c1.getColumnIndex("biz_logo_image_name"));
                bizEst.bcat_id = Integer.parseInt(c1.getString(c1.getColumnIndex("bcat_id")));
                bizEst.disp_status = c1.getString(c1.getColumnIndex("disp_status"));

                al_bc.add(bizEst);
            } while (c1.moveToNext());
        }
        db.close();
        return al_bc ;
    }


    public ArrayList<BizEstablishment> search_biz_establishments(String qry_arg) {
        ArrayList<BizEstablishment> al_bc = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String qry_str ="%"+qry_arg+"%"; // add % in start and end
        String[] args={qry_str,qry_str,"Y"};
        Cursor c1 = db.rawQuery("SELECT * FROM biz_establishment where (biz_name like ? OR bcat_id in (Select bcat_id from biz_category where bcat_name like ?)) and disp_status=?", args);
        if (c1.moveToFirst()) {
            do {
                BizEstablishment bizEst = new BizEstablishment();
                bizEst.biz_id = Integer.parseInt(c1.getString(c1.getColumnIndex("biz_id")));
                bizEst.biz_name = c1.getString(c1.getColumnIndex("biz_name"));
                bizEst.biz_street = c1.getString(c1.getColumnIndex("biz_street"));
                bizEst.biz_city = c1.getString(c1.getColumnIndex("biz_city"));
                bizEst.biz_district = c1.getString(c1.getColumnIndex("biz_district"));
                bizEst.biz_state = c1.getString(c1.getColumnIndex("biz_state"));
                bizEst.biz_pin = c1.getString(c1.getColumnIndex("biz_pin"));
                bizEst.biz_phone1 = c1.getString(c1.getColumnIndex("biz_phone1"));
                bizEst.biz_phone2 = c1.getString(c1.getColumnIndex("biz_phone2"));
                bizEst.biz_country = c1.getString(c1.getColumnIndex("biz_country"));
                bizEst.biz_details = c1.getString(c1.getColumnIndex("biz_details"));
                bizEst.biz_logo_image_loc = c1.getString(c1.getColumnIndex("biz_logo_image_name"));
                bizEst.bcat_id = Integer.parseInt(c1.getString(c1.getColumnIndex("bcat_id")));
                bizEst.disp_status = c1.getString(c1.getColumnIndex("disp_status"));

                al_bc.add(bizEst);
            } while (c1.moveToNext());
        }
        db.close();
        return al_bc ;
    }

}
