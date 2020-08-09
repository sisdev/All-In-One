package in.sisoft.all_in_one;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import in.sisoft.all_in_one.DbSvr.DbServLoginNotifyAyncTask;
import in.sisoft.all_in_one.DbSvr.DbServPromoManageAsyncTask;
import in.sisoft.all_in_one.pojo.AppConstants;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class GoogleLoginActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 99;
    String TAG = "GLoginAct";

    TextView name, email ;
    ImageView imageProfile ;
    SignInButton signInButton;
    Button btn_proceed ;
    Button btn_sign_out ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageProfile = findViewById(R.id.imageProfile);
        name = findViewById(R.id.namePerson);
        email = findViewById(R.id.emailPerson);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(this);
        btn_sign_out = findViewById(R.id.btn_signout);
        btn_sign_out.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("695909795283-p0rsnkvkp6nfhar2d8dk2qc003tesepu.apps.googleusercontent.com")
                .build();

        // Build a GoogleSignItnClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Log.d(TAG,"Last Login Found");
            signInButton.setVisibility(View.GONE);
            updateUI(acct);
        } else {
           Log.d(TAG,"No Previous Login");
            name.setVisibility(View.GONE);
            imageProfile.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            btn_proceed.setVisibility(View.GONE);
            btn_sign_out.setVisibility(View.GONE);
// Set the dimensions of the sign-in button.

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.btn_proceed:
                startActivity(new Intent(this, MyBizEstabActivity.class));
                break;
            case R.id.btn_signout:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("Deals Local");
                alertBuilder.setMessage("Logout from Google Account:");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGoogleSignInClient.signOut()
                                .addOnCompleteListener(GoogleLoginActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "Signout from Google Completed");
                                        Toast.makeText(getApplicationContext(), "Sign Out Completed", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                        dialog.cancel();
                    }

                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog ad = alertBuilder.create();
                ad.show();
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        Log.d(TAG,"Response from Google Login");
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
//            btn_proceed.performClick();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
            Toast.makeText(getApplicationContext(),"Sign In Completed:"+account.getEmail(), Toast.LENGTH_LONG).show();
            String strGId = account.getId();
            String strName = account.getDisplayName();
            String strEmail = account.getEmail();
            String strGImgUrl = account.getPhotoUrl().toString();
            DbServLoginNotifyAyncTask dbLoginNotify = new DbServLoginNotifyAyncTask(this);
            dbLoginNotify.execute(strGId,strName,strEmail,"NA",strGImgUrl);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

           void updateUI(GoogleSignInAccount acct){

               signInButton.setVisibility(View.GONE);
               name.setVisibility(View.VISIBLE);
               imageProfile.setVisibility(View.VISIBLE);
               email.setVisibility(View.VISIBLE);
               btn_proceed.setVisibility(View.VISIBLE);
               btn_sign_out.setVisibility(View.VISIBLE);

               String personName = acct.getDisplayName();
               String personGivenName = acct.getGivenName();
               String personFamilyName = acct.getFamilyName();
               String personEmail = acct.getEmail();
               String personId = acct.getId();
               Uri personPhoto = acct.getPhotoUrl();
               Log.d(TAG,personName+":"+personGivenName+":"+personFamilyName+":"+personEmail+":"+personId+":"+personPhoto);
//               imageProfile.setImageURI(personPhoto);

         // Save data in Shared preferences for later reference -

               SharedPreferences sp1 = this.getSharedPreferences(AppConstants.SHARED_PREF_NAME1,Context.MODE_PRIVATE);
               SharedPreferences.Editor editor=sp1.edit();
               editor.putString(AppConstants.user_login,personEmail);
               editor.putString(AppConstants.user_name,personName);
               editor.putString(AppConstants.user_pic_url,personPhoto.toString());
               editor.commit();

               name.setText(personName);
               email.setText(personEmail);
               //try {
                   DownloadImageTask downldImage = new DownloadImageTask(imageProfile);
                   //URL imageURL = new URL(personPhoto.toString());
                   downldImage.execute(personPhoto.toString());
                   //Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(personPhoto.toString()).getContent());
                   //imageProfile.setImageBitmap(bitmap);
               //} catch (MalformedURLException e) {
               //    e.printStackTrace();
               //} catch (IOException e) {
               //    e.printStackTrace();
               //}
           }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}