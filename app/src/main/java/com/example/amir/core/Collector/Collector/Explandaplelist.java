package com.example.amir.core.Collector.Collector;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.amir.core.Collector.Adapter.AdapterExpandableList;
import com.example.amir.core.Collector.Classes.AgeAR;
import com.example.amir.core.Collector.Classes.Connection;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

public class Explandaplelist extends Activity {
	ExpandableListView expandableListData;
	AdapterExpandableList adapterExpandableList;
	List<AgeAR> listAgeARs;
	Context context = this;
	ArrayList<String> parent = new ArrayList<String>();
	ArrayList<Object> Child = new ArrayList<Object>();
	String ColName, Accn_no, ClientName, AgeAcco_NO;
	double Balance;// for RV
	String accn_no;// For RV
	Button btnExpAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collexpandablelist);

		btnExpAdd = (Button) findViewById(R.id.btnExpAdd);
		btnExpAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Balance == 0.0) {
					new GetToast(context, getString(R.string.PleaseSelectClient));
				} else {
					// Intent Rv = new Intent(context, ReceiptVoucher.class);
					// Rv.putExtra("ClientName", ClientName);
					// Rv.putExtra("Balance", Balance);
					// Rv.putExtra("accn_no", accn_no);
					// startActivity(Rv);
					AccountInfo();
				}

			}
		});
		expandableListData = (ExpandableListView) findViewById(R.id.expandableListView_Data);
		expandableListData.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View view,
					int groupPosition, int childPosition, long id) {
				view.setBackgroundColor(Color.GRAY);
				try {
					ClientName = parent.get(groupPosition).toString();
					Balance = listAgeARs.get(groupPosition).getBalance();
					accn_no = listAgeARs.get(groupPosition).getaccn_no();
					
					new GetToast(context,getString(R.string.ClientNameIs)+ " " + ClientName
							+ getString(R.string.BalanceIs) + Balance);
				} catch (Exception e) {
//					Log.d("Error In Get Client Name ", e.getMessage());
				}
				return false;
			}
		});
		Intent i = getIntent();
		Accn_no = i.getStringExtra("accn_no");
		ColName = i.getStringExtra("ColName");
		AgeAcco_NO = i.getStringExtra("AgeAcco_NO");
		new FillExpandable().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.collexpandablelist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(Connection.GetProsyURL());
			break;
		case R.id.mune_Expan_Cancel:
			this.finish();
			break;
		case R.id.mune_Expan_RV:
			if (Balance == 0.0) {
				new GetToast(context, getString(R.string.PleaseSelectClient));
			} else {
				// Intent Rv = new Intent(context, ReceiptVoucher.class);
				// Rv.putExtra("ClientName", ClientName);
				// Rv.putExtra("Balance", Balance);
				// Rv.putExtra("accn_no", accn_no);
				// startActivity(Rv);
				AccountInfo();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void AccountInfo() {
		try {
			Intent intent = new Intent(context, AccountInfo.class);
			intent.putExtra("ClientName", ClientName);
			intent.putExtra("Balance", Balance);
			intent.putExtra("accn_no", accn_no);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private class FillExpandable extends AsyncTask<Void, Void, Void> {
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
		protected Void doInBackground(Void... params) {
			if (ColName.equals(getString(R.string.All)))
				listAgeARs = new AgeAR().GetARAge_With_Coll_Details(
						CoreLogInActivity.UserID, Accn_no);
//						LoginActivity.coll_id, Accn_no);
			else
				listAgeARs = new AgeAR().GetARAge_With_Coll_Details(
						CoreLogInActivity.UserID, Accn_no, ColName);
//						LoginActivity.coll_id, Accn_no, ColName);//,AgeAcco_NO);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			if (listAgeARs == null) {
				new GetToast(context, getString(R.string.NoData));
				return;
			} else
				AddToExpandList();
		}

	}

	private void AddToExpandList() {

		if (ColName.equals(getString(R.string.All))) {
			for (AgeAR e : listAgeARs) {
				ArrayList<Object> child = new ArrayList<Object>();
				parent.add(e.getName());
				child.add(e.getaccn_no());
				child.add(e.getBalance());
				child.add(e.getA0_30());
				child.add(e.getA31_60());
				child.add(e.getA61_90());
				child.add(e.getA91_120());
				child.add(e.getA121_150());
				child.add(e.getA151_180());
				child.add(e.getA181());
				Child.add(child);
			}
			adapterExpandableList = new AdapterExpandableList(context, parent,
					Child);
		} else {
			for (AgeAR e : listAgeARs) {
				ArrayList<Object> child = new ArrayList<Object>();
				parent.add(e.getName());
				child.add(e.getaccn_no());
				child.add(e.getColName());
				child.add(e.getBalance());
				Child.add(child);
			}// End For
			adapterExpandableList = new AdapterExpandableList(context, parent,
					Child, ColName);
		}
		expandableListData.setAdapter(adapterExpandableList);
	}// End Function

}// End Class
