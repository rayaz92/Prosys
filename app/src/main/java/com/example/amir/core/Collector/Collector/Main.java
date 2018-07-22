package com.example.amir.core.Collector.Collector;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.Collector.Classes.Account_Name;
import com.example.amir.core.Collector.Classes.AgeAR;
import com.example.amir.core.Collector.Classes.Connection;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

public class Main extends Activity implements OnClickListener,
		OnItemSelectedListener {
	TextView tvUserName;
	Button BtnSearch,btnCollSearch,BtnRVEntry;
	EditText TxTAccount_No,TxTOldAccountNo;
	AutoCompleteTextView AutoTxTAccn_Name;
	Spinner spindate,spinAgentName;
	ArrayAdapter<String> AdapterPayTerms;
	ArrayList<String> listPayTerms;
	Context context = this;
	List<Account_Name> listAccount_Names;
	ArrayAdapter<Account_Name> AdapterAccount_Name;

	String ColName,AgeAcco_NO;
	TelephonyManager telephonyManager;
	List<AgeAR> listGloca;
	ArrayAdapter<AgeAR> AdapterAgeAr;
	public static String DeviceID = "";
	AgeAR AgeAr = new AgeAR();
	String getaccn = "";


    Button ttt ;
	AlertDialog alertDialog1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collmain);
		InitialView();
		FillSpinner();
		new GetglocaAgents().execute();
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		DeviceID = telephonyManager.getDeviceId().toString();
	}// on create

	@SuppressLint("InlinedApi")
	private void InitialView() {
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvUserName.setText(CoreLogInActivity.UserName);
//		tvUserName.setText(LoginActivity.coll_name);
		BtnSearch = (Button) findViewById(R.id.BtnSearch);
		BtnSearch.setOnClickListener(this);
        BtnRVEntry = (Button) findViewById(R.id.BtnRVEntry);

        BtnRVEntry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TxTAccount_No.getText().toString().trim().length() > 0) {
                    AgeAr.setaccn_no_agents(AdapterAgeAr.getItem(
                            spinAgentName.getSelectedItemPosition()).getaccn_no_agents());
                    getaccn = AgeAr.getaccn_no_agents().toString().substring(0, 6);

                    Intent intent = new Intent(context, RVEntry.class);
                    if (TxTAccount_No.getText().toString().trim().equals(""))
//				intent.putExtra("accn_no", "-1");
                        intent.putExtra("accn_no", getaccn);
                    else
                        intent.putExtra("accn_no", TxTAccount_No.getText().toString());
                    intent.putExtra("AgentAccnName", AutoTxTAccn_Name.getText().toString());

                    if (AdapterPayTerms.getItem(spindate.getSelectedItemPosition()).equals(getString(R.string.SelectOne))) {
                        new GetToast(context, getString(R.string.PleaseSelectPayTerms));
                        return;
                    }
                    intent.putExtra("ColName", AdapterPayTerms.getItem(spindate.getSelectedItemPosition()));
//			intent.putExtra("AgeAcco_NO", getaccn);

                    startActivity(intent);
                }else{
                    Toast.makeText(Main.this,getString(R.string.Please_Select_Client),Toast.LENGTH_SHORT).show();
                }
            }
        });

		btnCollSearch = (Button) findViewById(R.id.btnCollSearch);
		btnCollSearch.setOnClickListener(this);

		AutoTxTAccn_Name = (AutoCompleteTextView) findViewById(R.id.TxTAutoAccountName);
		TxTAccount_No = (EditText) findViewById(R.id.TxTAccountNo);
        TxTOldAccountNo = (EditText) findViewById(R.id.TxTOldAccountNo);
		
	
		spindate = (Spinner) findViewById(R.id.SpinDate);
		spindate.setOnItemSelectedListener(this);
		spinAgentName = (Spinner) findViewById(R.id.SpinAgentName);
		spinAgentName.setOnItemSelectedListener(this);
		
		spinAgentName.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					AutoTxTAccn_Name.setText("");
				//	listAccount_Names= null;
				
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					 
				}
			});
		
//		AutoTxTAccn_Name.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int arg1, int arg2,
//					int arg3) {
//				if (AutoTxTAccn_Name.getText().toString().trim().length() > 3
//						&& AutoTxTAccn_Name.getText().toString().trim()
//								.length() < 12) {
//					if (!(AdapterAgeAr == null)) {
//						AgeAr.setaccn_no_agents(AdapterAgeAr.getItem(
//								spinAgentName.getSelectedItemPosition()).getaccn_no_agents());
//
//						getaccn = AgeAr.getaccn_no_agents().toString().substring(0, 6);
//
//						GetDataAutoComplete(AutoTxTAccn_Name.getText().toString(), getaccn);
//					}else{
//						new GetToast(context,getString(R.string.NoData));
//					}
//				}
//			}
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				// TODO Auto-generated method stub
//			}
//		});// Auto TxTAccount Name

		AutoTxTAccn_Name.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
//				TxTAccount_No.setText(""+ AdapterAccount_Name.getItem(position).GetID());
//				AutoTxTAccn_Name.setText(AdapterAccount_Name.getItem(position).GetNameEnglish());
			}
		});
	}// initial view

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.BtnSearch:
				try {
					AgeAr.setaccn_no_agents(AdapterAgeAr.getItem(
							spinAgentName.getSelectedItemPosition()).getaccn_no_agents());
					getaccn = AgeAr.getaccn_no_agents().toString().substring(0, 6);

					Intent intent = new Intent(context, Explandaplelist.class);
					if (TxTAccount_No.getText().toString().trim().equals(""))
//				intent.putExtra("accn_no", "-1");
						intent.putExtra("accn_no", getaccn);
					else
						intent.putExtra("accn_no", TxTAccount_No.getText().toString());

					if (AdapterPayTerms.getItem(spindate.getSelectedItemPosition()).equals(getString(R.string.SelectOne))) {
						new GetToast(context, getString(R.string.PleaseSelectPayTerms));
						return;
					}
					intent.putExtra("ColName", AdapterPayTerms.getItem(spindate.getSelectedItemPosition()));
//			intent.putExtra("AgeAcco_NO", getaccn);

					startActivity(intent);

					break;
				}catch (Exception e){
					new GetToast(context , getString(R.string.Check));
					e.printStackTrace();
				}
					case R.id.btnCollSearch:

						if (AutoTxTAccn_Name.getText().toString().trim().length() > 3
								&& AutoTxTAccn_Name.getText().toString().trim()
								.length() < 12) {
							if (!(AdapterAgeAr == null)) {
									AgeAr.setaccn_no_agents(AdapterAgeAr.getItem(
											spinAgentName.getSelectedItemPosition()).getaccn_no_agents());

									getaccn = AgeAr.getaccn_no_agents().toString().substring(0, 6);

									GetDataAutoComplete(AutoTxTAccn_Name.getText().toString(), getaccn);

							}else{
								new GetToast(context,getString(R.string.NoData));
							}
						}

						break;

		}
	}// on click

	private void GetDataAutoComplete(String Name, String getaccn) {
//		listAccount_Names = new Account_Name().Getaccn_noByAccn_Name_New(Name,getaccn);
		new Getaccn_noByAccn_Name_New().execute(Name,getaccn);


//		if (listAccount_Names == null) {
//			return;
//		} else {
//			// AdapterAccount_Name = new ArrayAdapter<Account_Name>(context,
//			// android.R.layout.simple_list_item_1, listAccount_Names);
//			AdapterAccount_Name = new ArrayAdapter<Account_Name>(context,
//					android.R.layout.simple_spinner_dropdown_item,
//					listAccount_Names) {
//				public View getView(int position, View convertView,
//						ViewGroup parent) {
//					LayoutInflater infalInflater = (LayoutInflater) context
//							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//					convertView = infalInflater.inflate(R.layout.collauto, null);
//					TextView t = (TextView) convertView.findViewById(R.id.tv_auto);
//
//					t.setText(""+listAccount_Names.get(position).GetNameEnglish());
//
//					t.setGravity(Gravity.CENTER);
//
//					if (position % 2 == 0)
//						t.setBackgroundColor(Color.rgb(184, 212, 255));
//					return convertView;
//				}
//			};
//			AutoTxTAccn_Name.setAdapter(AdapterAccount_Name);
//		}
	}// Data Auto Complete
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(getString(R.string.Clear));
		menu.add(getString(R.string.DailyWork));
		return super.onCreateOptionsMenu(menu);
	}// on Create Options Menu

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		
		TxTAccount_No.setText("");
		AutoTxTAccn_Name.setText("");
		
		spindate.setSelection(0);
		spinAgentName.setSelection(0);
		super.onOptionsMenuClosed(menu);
	}// on Options Menu Closed

	private void FillSpinner() {
		try {
			listPayTerms = new ArrayList<String>();
//			listPayTerms.add(getString(R.string.SelectOne));
			listPayTerms.add(getString(R.string.All));
			listPayTerms.add("A0_30");
			listPayTerms.add("A31_60");
			listPayTerms.add("A61_90");
			listPayTerms.add("A91_120");
			listPayTerms.add("A121_150");
			listPayTerms.add("A151_180");
			listPayTerms.add("A181");
			if (listPayTerms == null) {
				new GetToast(context, getString(R.string.ErrorInGetPayTerms));
			} else {
				AdapterPayTerms = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_dropdown_item,
						listPayTerms);
				spindate.setAdapter(AdapterPayTerms);
			}
			
//			AgeAR objAgeAR= new AgeAR();
//			listGloca = objAgeAR.GetglocaAgents();
////			listGloca = objAgeAR.GetARAge_With_Coll_Details();
//			if(listGloca.size() > 0  && listGloca != null) {
//				AdapterAgeAr = new ArrayAdapter<AgeAR>(this,
//						android.R.layout.simple_spinner_item, listGloca);
//				AdapterAgeAr
//						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinAgentName.setAdapter(AdapterAgeAr);
//			}
		} catch (Exception e) {
			Log.d("error In Fill Spinner ", e.getMessage());
		}
	}// Fill Spinner
	
//	private int getIndex(Spinner contractType2, Integer contractTypeID2) {
//		int i = 0;
//		try {
//
//			for (AgeAR e : listGloca) {
//				i += 1;
//				if (e.getID() == contractTypeID2) {
//					Log.d("Contract Name", e.getNameAr());
//					break;
//				}
//			}// End For
//		} catch (Exception e) {
//			tvMSG.append(e.getMessage());
//			tvMSG.setTextColor(Color.RED);
//			Log.d("Error In Get Index ", e.getMessage());
//		}
//		return i;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(Connection.GetProsyURL());
			return true;
		default:
			break;
		}
		if (item.getTitle().equals(getString(R.string.DailyWork))) {
			startActivity(new Intent(context, DailyWork.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}


	class GetglocaAgents extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Main.this,
					getString(R.string.Please_Wait), getString(R.string.Wait));
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				AgeAR objAgeAR= new AgeAR();
				listGloca = objAgeAR.GetglocaAgents();

//				listGloca
//						.add(0,
//								new AgeAR("-1", "All"));
//			listGloca = objAgeAR.GetARAge_With_Coll_Details();


			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
				if(listGloca.size() > 0  && listGloca != null) {
					AdapterAgeAr = new ArrayAdapter<AgeAR>(Main.this,
							android.R.layout.simple_spinner_item, listGloca);
					AdapterAgeAr
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinAgentName.setAdapter(AdapterAgeAr);
				}
				}
			}


	class Getaccn_noByAccn_Name_New extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Main.this,
					getString(R.string.Please_Wait), getString(R.string.Wait));
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {
				 Account_Name objAccount_Name = new Account_Name();

				listAccount_Names = objAccount_Name.Getaccn_noByAccn_Name_New(params[0],params[1]);

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
            if(listAccount_Names != null) {
                AdapterAccount_Name = new ArrayAdapter<Account_Name>(context,
                        android.R.layout.simple_spinner_dropdown_item,
                        listAccount_Names);

                onCreateDialogSingleChoice(AdapterAccount_Name);
            }else{
                Toast.makeText(Main.this,getString(R.string.NoData),Toast.LENGTH_SHORT).show();
            }
		}
	}


	public void onCreateDialogSingleChoice(ArrayAdapter adapterArea){


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO Auto-generated method stub
				ListView lw = ((AlertDialog)dialog).getListView();
				Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
				Account_Name Selected = (Account_Name)checkedItem;
				Toast.makeText(getApplicationContext(),
						checkedItem.toString(), Toast.LENGTH_SHORT).show();
				AutoTxTAccn_Name.setText(Selected.GetNameEnglish());
				TxTAccount_No.setText(String.valueOf(Selected.GetID()));
                TxTOldAccountNo.setText(Selected.getOldAccountNo());
				alertDialog1.dismiss();
			}
		});
		alertDialog1 = builder.create();
		alertDialog1.show();

	}
}// MainClass







