package com.example.amir.core.ADR.activity;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.amir.core.ADR.activity.Classes.Connection;
import com.example.amir.core.ADR.activity.Classes.Users;
import com.example.amir.core.R;

public class Main extends Activity implements OnClickListener {
	List<Users> listUsers;
	Button btnlogin;
	EditText TxTUserName, TxTPassword;

//	public static int UserID = 0;
//	public static String SalesName = "";
//	public static int SalesId = 0;
	ProgressDialog progressDialog;

	@SuppressLint({ "NewApi", "InlinedApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrmain);
		InitialView();
	}

	private void InitialView() {
		btnlogin = (Button) findViewById(R.id.BtnLogin);
		btnlogin.setOnClickListener(this);
		TxTUserName = (EditText) findViewById(R.id.TxTUserName);
		TxTPassword = (EditText) findViewById(R.id.TxTPassWord);
		GetToast.Toast(getApplicationContext(), Locale.getDefault()
				.getDisplayLanguage());

		String UserName = "" , Password = "";
		UserName =  getIntent().getStringExtra("UserName");
		Password =  getIntent().getStringExtra("Password");

		new login().execute(UserName,Password);
	}

	@Override
	public void onClick(View v) {
		if (Connection.isConnectingToInternet(Main.this)) {
			if (IsValid())
				new login().execute(TxTUserName.getText().toString().trim(),
						TxTPassword.getText().toString().trim());
		} else
			Connection.showAlertDialog(Main.this);

	}

	private boolean IsValid() {
		Boolean result = false;
		if (TxTPassword.getText().toString().trim().equals("")
				|| TxTUserName.getText().toString().trim().equals(""))
			result = false;
		else
			result = true;
		return result;
	}

	private void GetUserID(List<Users> listUsers2) {
		for (Users e : listUsers2) {
//			UserID = e.GetUserID();
//			SalesName = e.GetSalesName();
//			SalesId = e.GetSalesId();
			break;
		}
	}

	class login extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Main.this,
					getString(R.string.LogIn), getString(R.string.Wait),
					R.drawable.login_icon1);
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
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();

//			if (UserID > 0) {
//				Main.this.finish();
//				startActivity(new Intent(Main.this, ShowMain.class));
//			} else {
//				GetToast.Toast(getApplicationContext(),
//						getString(R.string.InValidUserNameOrPassword));
//				TxTPassword.setText("");
//				TxTUserName.setText("");
			}
		}
//	}// End Class

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
}
