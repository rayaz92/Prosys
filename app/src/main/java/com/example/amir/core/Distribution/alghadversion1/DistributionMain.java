package com.example.amir.core.Distribution.alghadversion1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.amir.core.Distribution.alghadversion1.Classes.Driver;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.Users;
import com.example.amir.core.R;

import java.util.List;

public class DistributionMain extends Activity implements OnClickListener {
	Button btnLogIn;
	TextView TxTUserName, TxTPassword, TxTError;
	TextView txtURL;
	Users objUsers = new Users();
	Driver objDriver = new Driver();
	GPSTracker objGpsTracker;
	List<Driver> listDriver;
	Location location;
	ProgressDialog dialog;
	public static int DriverId, SalesID, UserID;
	public static String UserName, DriverName;
	Context Context = this;
	Spinner SpinnerLang;

	@SuppressLint({ "NewApi", "InlinedApi" })
	@Override

	protected void onCreate(Bundle savedInstanceState) {
//		if (android.os.Build.VERSION.SDK_INT > 8) {
//			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//					.build();
//			StrictMode.setThreadPolicy(policy);
//		}// End if
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributionmain);
		InitialView();

	}// End On Create

	private void InitialView() {
		try {

 			TxTPassword = (EditText) findViewById(R.id.TxTPassword);
			TxTUserName = (EditText) findViewById(R.id.TxTUserNameLogIn);
			txtURL = (TextView) findViewById(R.id.textURL);
			txtURL.setText(Html
					.fromHtml("<a href=\"https://www.prosysjo.com/\">Powerd By Prosys</a>"));
			txtURL.setMovementMethod(LinkMovementMethod.getInstance());
			TxTError = (TextView) findViewById(R.id.textErrorLogIn);
			btnLogIn = (Button) findViewById(R.id.BtnLogIn);
			btnLogIn.setOnClickListener(this);
			TxTError.setTextColor(Color.RED);

			String UserName , Password ;
			UserName =  getIntent().getStringExtra("UserName");
			Password =  getIntent().getStringExtra("Password");

			TxTUserName.setText(UserName);
			TxTUserName.setText(Password);

			new LogIn().execute("test",Password);

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		new LogIn().execute(TxTUserName.getText().toString().trim(),
				TxTPassword.getText().toString());

	}// End On Click

	private void GetNameFromDriverID(int id) {

		for (Driver obj : listDriver) {
			DriverName = obj.getName();
		}// End For

	}// end Function

	private class LogIn extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(DistributionMain.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(getString(R.string.Please_Wait));
			dialog.setCancelable(true);
			dialog.setIndeterminate(true);
			dialog.show();
			UserName = TxTUserName.getText().toString();
		}

		@Override
		protected Void doInBackground(String... params) {
			// user name ,pass
			UserID = objUsers.LogIn(params[0], params[1]);
			if (UserID == 0) {
				return null;
			}

			listDriver = objDriver.getDriverIdByUserId(UserID);

			for (Driver e : listDriver) {
				SalesID = e.getSalesID();
				DriverId = e.getDriverID();
				DriverName = e.getName();
			}// End for
			return null;
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			// objGpsTracker = new GPSTracker(DistributionMain.this);
			try {
				if (UserID == 0) {
					GetToast.Toast(
							DistributionMain.this,
							getString(R.string.Invalid_UserName_Password_Or_CheckGPS));
					TxTUserName.setText("");
					TxTPassword.setText("");
				} else {
					if ((UserID > 0 && DriverId > 0 && SalesID > 0)) {// GPSTracker.isGPSEnabled
						GetNameFromDriverID(DriverId);

						Intent objshowmain = new Intent(DistributionMain.this,
								DistributionShowmain.class);
						startActivity(objshowmain);
						DistributionMain.this.finish();
					} else {
						GetToast.Toast(DistributionMain.this,
								getString(R.string.Check_GPS_Or_Internet));
						dialog.dismiss();
					}
				}
				
			} catch (Exception e) {
				Log.e("DistributionMain", e.getMessage());
				// new CatchMsg().WriteMSG("DistributionMain", e.getMessage());
			}
		}// on post
	}// End get data
}
