package com.example.amir.core.Collector.Collector;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amir.core.Collector.Adapter.AdapterAccountInfo;
import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.R;

public class AccountInfo extends Activity {
	TextView tvAccountName, tvAccountNo, tvSer,tvTY;
	GNPAY objGnpay;
	AdapterAccountInfo adapterAccountInfo;
	ArrayList<GNPAY> arrayListGnpay;
	Context context = this;
	ListView listView;
	int serno ;
	List<GNPAY> listGnpays ;
	Button btnAccnInfoAdd ;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collaccount_info);
		InitialView();
		InitialValue();
	}

	private void InitialView() {
		try {
			tvAccountName = (TextView) findViewById(R.id.tvAccountName_AccountInfo);
			tvAccountNo = (TextView) findViewById(R.id.tvAcountNo_AcountInfo);
			tvSer=(TextView)findViewById(R.id.tvSerNo);
			tvTY = (TextView) findViewById(R.id.tvTY) ;
			listView = (ListView) findViewById(R.id.list_AccountInfo);
			btnAccnInfoAdd = (Button) findViewById(R.id.btnAccnInfoAdd);
			btnAccnInfoAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ReceiptVouocher();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void InitialValue() {
		try {
			Intent i = getIntent();
			tvAccountName.setText(i.getStringExtra("ClientName"));
			tvAccountNo.setText(i.getStringExtra("accn_no"));
//			objGnpay = new GNPAY();
//			List<GNPAY> listGnpays = objGnpay.SelectAccountInfo_DR(tvAccountNo
//					.getText().toString());
			new SelectAccountInfo_DR().execute();
//			if (listGnpays == null) {
//				new GetToast(context,getString(R.string.NoDate));
//				return;
//			} else
//				adapterAccountInfo = new AdapterAccountInfo(context, listGnpays);
//			listView.setAdapter(adapterAccountInfo);
//			objGnpay.getserno();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.collaccountinfo, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mune_PutSerilizable:
			ReceiptVouocher();
			break;
		case R.id.mune_accountInfo_Cancel:
			Intent mune_acc_cancel = new Intent(context, ReceiptVoucher.class);
			startActivity(mune_acc_cancel);
			finish();

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}// on option

	private void ReceiptVouocher() {
		try {
			double x = 0.0;
			int y = 0;
			arrayListGnpay = adapterAccountInfo.getlistChecked();
			
			for (GNPAY e : arrayListGnpay) {
				x = x + e.getDR();
//				y = y + e.getser();
				y =e.getser();
				tvSer.setText("" + y);
				tvTY.setText(String.valueOf(e.getTY()));
			}
			if (arrayListGnpay.size() > 0) { 
				Intent mune_expn_rv = new Intent(context, ReceiptVoucher.class);
				mune_expn_rv.putExtra("AccountName", tvAccountName.getText()
						.toString());
				mune_expn_rv.putExtra("accn_no", tvAccountNo.getText()
						.toString());
				mune_expn_rv.putExtra("ser", tvSer.getText());

				mune_expn_rv.putExtra("TY", tvTY.getText());

				startActivity(mune_expn_rv);

			} else
				new GetToast(context,getString(R.string.PleaseSelectOne));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	class SelectAccountInfo_DR extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;
		String AccountNo = tvAccountNo.getText().toString();


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(AccountInfo.this,
					getString(R.string.Please_Wait), getString(R.string.Wait));
			objProgressDialog.ShowDialog();


		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				objGnpay = new GNPAY();
				 listGnpays = objGnpay.SelectAccountInfo_DR(AccountNo);


			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			if (listGnpays == null) {
				new GetToast(context,getString(R.string.NoDate));
				return;
			} else
				adapterAccountInfo = new AdapterAccountInfo(context, listGnpays);
			listView.setAdapter(adapterAccountInfo);

		}
	}
}// Accountinfo
