package com.example.amir.core;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Main;
import com.example.amir.core.ADR.activity.ShowMain;
import com.example.amir.core.Collector.Classes.Account_Name;
import com.example.amir.core.Collector.Classes.User;
import com.example.amir.core.Collector.Collector.HomeActivity;
import com.example.amir.core.Collector.Collector.LoginActivity;
import com.example.amir.core.CoreClasses.Users;
import com.example.amir.core.Distribution.alghadversion1.DistributionMain;
import com.example.amir.core.Distribution.alghadversion1.DistributionShowmain;
import com.example.amir.core.Distribution.alghadversion1.GetToast;
import com.example.amir.core.Distribution.alghadversion1.ShowProgressDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoreLogInActivity extends AppCompatActivity {

    EditText txtUserName, txtPassword ;

    String Module = "ADR" ;
    Integer ModuleInt = 1 ;
    public static int UserID = 0 ,SalesID = 0 , DriverID = 0  , AgencyID = 0 ,DefaultPublication = 0 ;
    public static double NewTax = 0.0;
    public static String UserName = "" ,  SalesName = "", DriverName = "" ,
            UserAccountNo = "" , UserAccountName = "" , AgencyAccountNo = "",VersionName = "" , TaxNo = "";
    Users objUsers = new Users();
    List<Users> listUsers ;
    RadioGroup rgModules;
    RadioButton rbADR , rbCollector , rbDistribution;
    CheckBox chbRememberMe ,chbRememberPassword ;
    TextView txtChangeLang,tvVersionName;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String languageToLoad = "";
    String LiveVersion = "";
    Boolean IsLocationAllowed = false ,IsStorageAllowed = false, IsPhoneAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_log_in);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        InitSpinner();
        txtUserName = (EditText) findViewById(R.id.txtMainUserName) ;
        txtPassword = (EditText) findViewById(R.id.txtMainPassWord);

        chbRememberMe = (CheckBox) findViewById(R.id.chbRememberMe);
        chbRememberPassword= (CheckBox) findViewById(R.id.chbRememberPassword);

        rgModules = (RadioGroup) findViewById(R.id.rgModules);
        rbADR = (RadioButton) findViewById(R.id.rbADR);
        rbCollector = (RadioButton) findViewById(R.id.rbCollector);
        rbDistribution= (RadioButton) findViewById(R.id.rbDistribution);
        txtChangeLang = (TextView) findViewById(R.id.txtChangeLang);
        tvVersionName = (TextView) findViewById(R.id.tvVersionName);
        Locale current = getResources().getConfiguration().locale;
        if (current.equals(Locale.US) ||current.equals(Locale.UK) ||  current.equals(Locale.ENGLISH))
            {
                txtChangeLang.setText("العربية");
                languageToLoad = "ar";
            }else
            {
                txtChangeLang.setText("English");
                languageToLoad = "en";
            }

        txtChangeLang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                Intent refresh = new Intent(CoreLogInActivity.this, CoreLogInActivity.class);
                startActivity(refresh);
                finish();
                return false;
            }
        });

        rgModules.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rbADR) {
                    Module = rbADR.getText().toString();
                    ModuleInt = 1;
                    Toast.makeText(CoreLogInActivity.this,Module,Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.rbCollector) {
                    Module = rbCollector.getText().toString();
                    ModuleInt = 2;
                    Toast.makeText(CoreLogInActivity.this,Module,Toast.LENGTH_SHORT).show();
                } else {
                    Module = rbDistribution.getText().toString();
                    ModuleInt = 3;
                    Toast.makeText(CoreLogInActivity.this,Module,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button BtnLogin = (Button) findViewById(R.id.BtnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IsLocationAllowed && IsStorageAllowed && IsPhoneAllowed) {
                    new LogIn().execute(txtUserName.getText().toString().trim(),
                            txtPassword.getText().toString());
                }else {
                    Toast.makeText(CoreLogInActivity.this , "Please Allow All Permissions" , Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(CoreLogInActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_PHONE_STATE},
                            1);
                }

            }
        });


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
//        if (restoredText != null) {
            String UserName = prefs.getString("UserName", "");
            String Password = prefs.getString("Password", "");
        if(UserName.length() > 0 )
            chbRememberMe.setChecked(true);
        if(Password.length() > 0 )
            chbRememberPassword.setChecked(true);

            txtUserName.setText(UserName);
            txtPassword.setText(Password);
//        }

        ActivityCompat.requestPermissions(CoreLogInActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE},
                1);

        GetAppVersion();
    }

//    private void InitSpinner(){
//        try {
//
//            final Spinner spModule = (Spinner) findViewById(R.id.spModule);
//
//            List<String> spinnerArray =  new ArrayList<String>();
//            spinnerArray.add("ADR");
//            spinnerArray.add("Distribution");
//            spinnerArray.add("Collector");
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                    this, android.R.layout.simple_spinner_item, spinnerArray);
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            Spinner sItems = (Spinner) findViewById(R.id.spModule);
//            sItems.setAdapter(adapter);
//
//            spModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    Module = spModule.getSelectedItem().toString();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    private void GetAppVersion(){
        try {
            PackageManager manager = CoreLogInActivity.this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    CoreLogInActivity.this.getPackageName(), 0);
            LiveVersion = info.versionName;
            tvVersionName.setText(" " + LiveVersion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    class LogIn extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(CoreLogInActivity.this,
                    getString(R.string.LogIn), getString(R.string.Please_Wait));
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                // x = LogIn(params[0], params[1]);
                listUsers = new Users().LogIn(params[0], params[1]);
                if (listUsers != null) {
                    GetUserID(listUsers);
                }

            } catch (Exception e) {
//                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();

            if(VersionName.toString().trim().equals(LiveVersion.toString().trim())){
                if (UserID > 0) {
                    SaveRemember(txtUserName.getText().toString().trim(),
                            txtPassword.getText().toString());
                    CoreLogInActivity.this.finish();
                    StartModule();
                } else {
                    GetToast.Toast(getApplicationContext(),
                            getString(R.string.Invalid_UserName_Password_Or_CheckGPS));
//                txtUserName.setText("");
//                txtPassword.setText("");
                }
            }else{
                GetToast.Toast(getApplicationContext(),
                        getString(R.string.PleaseInstallUpdate));
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(getString(R.string.ApplicationUpdateUrl)));
//                startActivity(browserIntent);
//                https://drive.google.com/folderview?id=0B8jRjkLzCJ4PaVlPemxJeUROTnc
            }
        }
    }// End Class

    private void GetUserID(List<Users> listUsers2) {
        for (Users e : listUsers2) {
            UserID = e.GetUserID();
            UserName = e.getUserName();
            SalesName = e.GetSalesName();
            SalesID = e.GetSalesId();
            DriverID = e.getDriverID();
            DriverName = e.getDriverName();
            UserAccountNo = e.getAccountNO();
            UserAccountName = e.getAccountName();
            AgencyID = e.getAgencyID();
            AgencyAccountNo = e.getAgencyAccountNo();
            VersionName = e.getVersionName();
            DefaultPublication = e.getDefaultPublication();
            NewTax = e.getTax();
            TaxNo = e.getTaxNo();

            break;
        }
    }

    private void StartModule(){
        try{

//            if(Module.equals(getString(R.string.ADR))) {
//                Intent intent = new Intent(CoreLogInActivity.this, ShowMain.class);
//                intent.putExtra("UserName", txtUserName.getText().toString());
//                intent.putExtra("Password", txtPassword.getText().toString());
//                startActivity(intent);
//
//            }else if(Module.equals(getString(R.string.Distribution))){
//                Intent intent = new Intent(CoreLogInActivity.this, DistributionMain.class);
//                intent.putExtra("UserName", txtUserName.getText().toString());
//                intent.putExtra("Password", txtPassword.getText().toString());
//                startActivity(intent);
//
//            }else if(Module.equals(getString(R.string.Collector))) {
//                Intent intent = new Intent(CoreLogInActivity.this, com.example.amir.core.Collector.Collector.Main.class);
//                intent.putExtra("UserName", txtUserName.getText().toString());
//                intent.putExtra("Password", txtPassword.getText().toString());
//                startActivity(intent);
//            }else {
//                GetToast.Toast(CoreLogInActivity.this, "Not Exist");
//            }

            if(ModuleInt == 1) {
                Intent intent = new Intent(CoreLogInActivity.this, ShowMain.class);
                intent.putExtra("UserName", txtUserName.getText().toString());
                intent.putExtra("Password", txtPassword.getText().toString());
                startActivity(intent);

            }else if(ModuleInt == 3){
                Intent intent = new Intent(CoreLogInActivity.this, DistributionShowmain.class);
                intent.putExtra("UserName", txtUserName.getText().toString());
                intent.putExtra("Password", txtPassword.getText().toString());
                startActivity(intent);

            }else if(ModuleInt == 2) {
                Intent intent = new Intent(CoreLogInActivity.this, HomeActivity.class);
                intent.putExtra("UserName", txtUserName.getText().toString());
                intent.putExtra("Password", txtPassword.getText().toString());
                startActivity(intent);
            }else {
                GetToast.Toast(CoreLogInActivity.this, "Not Exist");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void SaveRemember(String UserName , String Password){
        try{

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            if(chbRememberMe.isChecked()) {
                editor.putString("UserName", UserName);
                if (chbRememberPassword.isChecked())
                    editor.putString("Password", Password);
            }else{
                editor.putString("UserName", "");
                editor.putString("Password", "");
            }
            editor.apply();

        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    IsLocationAllowed = true;
                }else{
                    IsLocationAllowed = false;
//                    Toast.makeText(CoreLogInActivity.this,"Location",Toast.LENGTH_SHORT).show();
                }
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    IsStorageAllowed = true;
                } else{
                    IsStorageAllowed = false;
//                    Toast.makeText(CoreLogInActivity.this,"Storage",Toast.LENGTH_SHORT).show();
                }
                if(grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    IsPhoneAllowed = true;
                }else {
                    IsPhoneAllowed = false;
//                    Toast.makeText(CoreLogInActivity.this,"Phone",Toast.LENGTH_SHORT).show();
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);

    }
}
