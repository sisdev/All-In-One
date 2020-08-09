package in.sisoft.all_in_one;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.sisoft.all_in_one.DbSvr.DbServPromoManageAsyncTask;
import in.sisoft.all_in_one.pojo.AppConstants;

public class MyBizPromoAddActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar cal1, cal2 ;
    SimpleDateFormat sdf;
    Date date1, date2 ;

    EditText et_promo_code, et_promo_text1, et_promo_text2;
    Button btn_promo_start_date, btn_promo_end_date, btn_save ;
    String strPromoCode, strPromoText1, strPromoText2, strPromoStartDate, strPromoEndDate, strCreatedBy ;
    String strBizId, strBizName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_biz_promo_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();


        et_promo_code = findViewById(R.id.promoCode);
        et_promo_text1 = findViewById(R.id.promoText1);
        et_promo_text2 = findViewById(R.id.promoText2);

        btn_promo_start_date=findViewById(R.id.promoStartDate);
        btn_promo_end_date=findViewById(R.id.promoEndDate);
        btn_save = findViewById(R.id.btn_promo_save);

        cal1 = Calendar.getInstance();
        date1 = cal1.getTime();
        sdf= new SimpleDateFormat(AppConstants.DATE_FORMAT);
        btn_promo_start_date.setText(sdf.format(date1));
        cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE,30);
        date2 = cal2.getTime();
        btn_promo_end_date.setText(sdf.format(date2));

        btn_promo_start_date.setOnClickListener(this);
        btn_promo_end_date.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        Intent i1 = getIntent() ; //call ing intent
        strBizId = i1.getStringExtra("BizId");
        strBizName = i1.getStringExtra("BizName");
        ab.setTitle("Add Promos:"+strBizName);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    boolean readValues(){
        boolean valid = true;

        strPromoCode = et_promo_code.getText().toString();
        strPromoText1 = et_promo_text1.getText().toString();
        strPromoText2 = et_promo_text2.getText().toString();
        strPromoStartDate = sdf.format(cal1.getTime());
        strPromoEndDate = sdf.format(cal2.getTime());
        strCreatedBy = AppConstants.getUserLogin(this);
        if (strPromoCode.length()==0){
            et_promo_code.setError("Promo Code Required");
            valid=false ;
        }
        if (strPromoText1.length()==0){
            et_promo_text1.setError("Promo Text1 Required");
            valid=false ;
        }
        if (strPromoText2.length()==0){
            et_promo_text2.setError("Promo Text2 Required");
            valid=false ;
        }
        return valid ;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.promoStartDate:
                DatePickerDialog.OnDateSetListener dsl1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yr, int mon, int day) {
                        cal1.set(yr,mon,day);
                        date1 = cal1.getTime();
                        btn_promo_start_date.setText(sdf.format(date1));
                    }
                } ;
                DatePickerDialog dpd1 = new DatePickerDialog(this, dsl1, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),
                        cal1.get(Calendar.DAY_OF_MONTH));
                dpd1.show();
                break ;
            case R.id.promoEndDate:
                DatePickerDialog.OnDateSetListener dsl2 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yr, int mon, int day) {
                        cal2.set(yr,mon,day);
                        date2 = cal2.getTime();
                        btn_promo_end_date.setText(sdf.format(date2));
                    }
                } ;
                DatePickerDialog dpd2 = new DatePickerDialog(this, dsl2, cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH),
                        cal2.get(Calendar.DAY_OF_MONTH));
                dpd2.show();
                break ;
            case R.id.btn_promo_save:
                if (readValues()){
                    DbServPromoManageAsyncTask p1 = new DbServPromoManageAsyncTask();
                    p1.execute("add",strBizId,strPromoCode, strPromoText1, strPromoText2,strPromoStartDate, strPromoEndDate, strCreatedBy);
                    Toast.makeText(this, "Promo Code Added:"+strPromoCode, Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }
}
