package in.sisoft.all_in_one.pojo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppConstants {
   int  requestIdToken ;
   public static String catURL = "http://www.sisoft.in/all_in_one/ws_bcat.php?max_id=0"; //HARD CODED
   public static String estabURL = "http://www.sisoft.in/all_in_one/ws_best.php?max_id=0"; //HARD CODED

   public static String bizAddURL = "http://www.sisoft.in/all_in_one/ws_my_bizAdd.php"; //HARD CODED

   public static String myBizList = "http://www.sisoft.in/all_in_one/ws_my_bizList.php"; //HARD CODED

   public static String bizManagePromo = "http://www.sisoft.in/all_in_one/ws_my_bizPromoManage.php"; //HARD CODED

   public static String myBizPromoList = "http://www.sisoft.in/all_in_one/ws_my_bizPromoList.php"; //HARD CODED


   public static String loginNotify = "http://www.sisoft.in/all_in_one/ws_LoginNotify.php"; //HARD CODED

   public static String CheckMsg = "http://www.sisoft.in/all_in_one/ws_CheckMessages.php"; //HARD CODED

   // Display all promotion to end user
   public static String allPromoList = "http://www.sisoft.in/all_in_one/ws_allPromoList.php";


   public static final String user_login="User_Login";  //email
   public static final String user_name="User_Name";
   public static final String user_pic_url="User_Pic_Url";

   public static final String SHARED_PREF_NAME1="login_details";

   public static final String DATE_FORMAT="YYYY-MM-dd";

   public static int hourOfDay = 3 ;

   public static String getUserLogin(Activity a1){
      SharedPreferences sp = a1.getSharedPreferences(AppConstants.SHARED_PREF_NAME1, Context.MODE_PRIVATE);
      String strUserLogin = sp.getString(AppConstants.user_login,"No Data");
      return strUserLogin ;
   }

}
