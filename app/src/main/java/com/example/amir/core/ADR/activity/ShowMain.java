package com.example.amir.core.ADR.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

public class ShowMain extends Activity {
	public static String DeviceID;
	TelephonyManager telephonyManager;
	EditText TxTSalesName;
	LinearLayout llADRAddClient , llADRAddContract ,llADRDailyWork , llADRIssueContract,llADRDesignRequest,llADRDeviceID ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrshowmain);
		InitialView();
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		DeviceID = telephonyManager.getDeviceId().toString();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private void InitialView() {
		TxTSalesName = (EditText) findViewById(R.id.ShowMain_TxTSalesRep);
//		TxTSalesName.setText(Main.SalesName);
		TxTSalesName.setText(CoreLogInActivity.UserName);

		llADRAddClient = (LinearLayout) findViewById(R.id.llADRAddClient);
		llADRAddContract = (LinearLayout) findViewById(R.id.llADRAddContract);
		llADRDailyWork = (LinearLayout) findViewById(R.id.llADRDailyWork);
        llADRIssueContract = (LinearLayout) findViewById(R.id.llADRIssueContract);
        llADRDesignRequest = (LinearLayout) findViewById(R.id.llADRDesignRequest);
		llADRDeviceID= (LinearLayout) findViewById(R.id.llADRDeviceID);

		llADRAddClient.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ShowMain.this, ClientManagement.class));
			}
		});

		llADRAddContract.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ShowMain.this, NewContract.class));
			}
		});

        llADRIssueContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowMain.this, IssueContract.class));
            }
        });
		llADRDailyWork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ShowMain.this, DailyWork.class));
			}
		});

        llADRDesignRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowMain.this , DesignRequestActivity.class));
            }
        });

		llADRDeviceID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				GetToast.Toast(ShowMain.this, DeviceID);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.adrmain, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ClientManagement:
			startActivity(new Intent(ShowMain.this, ClientManagement.class));
			return true;
		case R.id.Contract:
			startActivity(new Intent(ShowMain.this, NewContract.class));
			return true;
		case R.id.DeviceID:
			GetToast.Toast(ShowMain.this, DeviceID);
			return true;
		case R.id.Daily_Work:
			startActivity(new Intent(ShowMain.this, DailyWork.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}// End Show Main
