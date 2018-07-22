package com.example.amir.core.Collector.Collector;

import java.util.ArrayList;
import java.util.Locale;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.amir.core.Collector.Classes.Connection;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.Collector.Classes.User;
import com.example.amir.core.R;

public class LoginActivity extends Activity {
	EditText TxtUserName, TxTpassword;
	Button BtnLogin;
//	public static int coll_id = 0;
//	public static String coll_name = "";
	Context context = this;
	User Objuser;
	Spinner SpinLang,SpinPrinter;
	Locale myLocale;
	public static String PrinterName ="P3Inches";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.build();
			StrictMode.setThreadPolicy(policy);
		}// End if
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colllogin);
	
		InitialView();

		String UserName = "" , Password = "";
		UserName =  getIntent().getStringExtra("UserName");
		Password =  getIntent().getStringExtra("Password");

		if(UserName.trim().length() > 0 && Password.trim().length() > 0)
		new login().execute(UserName,Password);
	
	}// on Create
	
	@Override
	public void onBackPressed() {
		System.exit(1);
	}

	private void InitialView() {
		TxTpassword = (EditText) findViewById(R.id.TxTPassword);
		TxtUserName = (EditText) findViewById(R.id.TxTUserName);
		BtnLogin = (Button) findViewById(R.id.BtnLogin);
		SpinLang=(Spinner) findViewById(R.id.spinnerLang);
		SpinPrinter=(Spinner) findViewById(R.id.spinnerPrinter);
		BtnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				new login().execute(TxtUserName.getText().toString().trim(),TxTpassword.getText().toString());
			}
		});
		 ArrayList<String> PrinterType = new ArrayList<String>();
		 PrinterType.add(getString(R.string.SelectPrinter));
		 PrinterType.add(getString(R.string.P2inches));
		 PrinterType.add(getString(R.string.P3inches));
		
		 SpinPrinter = (Spinner) findViewById(R.id.spinnerPrinter);
		 SpinPrinter.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, PrinterType));
		 SpinPrinter.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
//
//
//					 if (position == 1){
//						PrinterName = "P2Inches";
//					}
//					 else if (position == 2){
//							PrinterName = "P3Inches";
//					}
//					 else {
//						 PrinterName = "P2Inches";
//					 }
						
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
//					 PrinterName = "P2Inches";
				}
			});
		 ArrayList<String> languages = new ArrayList<String>();
		 	languages.add(getString(R.string.SelectLanguage));
		    languages.add("English");
		    languages.add("�������");
		 SpinLang.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, languages));
		   
		    SpinLang.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	                
	                    if(position == 1){
	                       
	                        setLocale("en");
	                    }
	                    else if(position == 2)
	                    {
	                         setLocale("ar");
	                    }                             
	            }
	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	                // TODO Auto-generated method stub
	            }

	         });
			
	}// initial view
	 public void setLocale(String lang) {
		 
		   myLocale = new Locale(lang);
	        Resources res = getResources();
	        DisplayMetrics dm = res.getDisplayMetrics();
	        Configuration conf = res.getConfiguration();
	        conf.locale = myLocale;
	        res.updateConfiguration(conf, dm);
	        Intent refresh = new Intent(this, LoginActivity.class);
	        startActivity(refresh);
	        finish();
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(Connection.GetProsyURL());
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class login extends AsyncTask<String, Void, Void> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.Loading));
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			Objuser = new User().LogIn(params[0], params[1]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			if (Objuser == null) {
				new GetToast(context, getString(R.string.pleasecheckUserNameAndPassword));
				TxTpassword.setText("");
				TxtUserName.setText("");
			} else {
				
//				coll_id = Objuser.Getcoll_id();
//				coll_name = Objuser.getcoll_name();
				
//				if (!(coll_id == 0)) {
//					startActivity(new Intent(context, Main.class));
//					LoginActivity.this.finish();
//				}
			}
		}
	}// login
	
}// Main Activity
