package com.example.amir.core.ADR.activity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.example.amir.core.ADR.activity.Classes.AdsType;
import com.example.amir.core.ADR.activity.Classes.Ads_Category;
import com.example.amir.core.ADR.activity.Classes.Agancy;
import com.example.amir.core.ADR.activity.Classes.CatchMsg;
import com.example.amir.core.ADR.activity.Classes.Connection;
import com.example.amir.core.ADR.activity.Classes.ContactCategory;
import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.DesignRequest;
import com.example.amir.core.ADR.activity.Classes.EnglishNameWithAgency;
import com.example.amir.core.ADR.activity.Classes.Payment;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

public class NewContract extends Activity implements OnClickListener
		 {
	Context context = this;
	Button BtnAdd,btnSearchClient;
	AutoCompleteTextView AutoClientName;
	EditText TxTDate, TxTRefNo, TxTSubject, TxTAndroidClientName, TxTPh_Mobile;

	// EditText TxTContractNo;
	Spinner SpinAdsCategory, SpinContractCategory, SpinAdsType, SpinAgency, adrspinPaymentType , NewContractspinDesignID;
	List<Ads_Category> ListAds_category;
	Ads_Category ObjAds_Category;
	List<ContactCategory> ListContactCategory;
	ContactCategory ObjContactCategory;
	List<AdsType> ListAdsType;
	AdsType ObjAdsType;
	List<Payment> ListPayment;
	Payment ObjPayment;
	int SectorID, SalesID;
	int AgencyID, ClientID;


	ArrayAdapter<Ads_Category> arrayAdapterAds_Category;
	ArrayAdapter<ContactCategory> arrayAdapterContactCategory;
	ArrayAdapter<AdsType> arrayAdapterAdsType;
	ArrayAdapter<EnglishNameWithAgency> ArrayClient;
	List<EnglishNameWithAgency> listArrayClient ;//=  new List<EnglishNameWithAgency>();
	List<EnglishNameWithAgency> listEnglishNameWithAgency;
	ArrayAdapter<String> ArrayadapterAgancy;
	List<Agancy> Agancieslist;

             List<Payment> listPayment;
             ArrayAdapter<Payment> ArrayPayment;
             List<DesignRequest> DesignRequestlist;
             ArrayAdapter<DesignRequest> arrayAdapterDesignRequest;


	Contract ObjContract = new Contract();// To Save
    AlertDialog alertDialog1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrnewcontract);

		try{
		InitialView();
//		if (Connection.isConnectingToInternet(context)) {
			new FillSpinners(FillSpinners.ActivityMode.NewContract, this, this).execute();
            new GetPayment().execute();
//		} else {
//			Connection.showAlertDialog(context);
//			TextStatus(false);
//		}

	}catch (Exception e){
			e.printStackTrace();
	}
	}

	private void InitialView() {
		try {
			TxTDate = (EditText) findViewById(R.id.NewContract_TxTDate);
			TxTDate.setEnabled(false);
			TxTDate.setText(DateIsuee());
			TxTPh_Mobile = (EditText) findViewById(R.id.TxTPh_Mobile_NewContract);
			TxTAndroidClientName = (EditText) findViewById(R.id.TxTName_NewContract);
			// TxTContractNo = (EditText)
			// findViewById(R.id.NewContract_TxTContractNo);

			TxTRefNo = (EditText) findViewById(R.id.NewContract_TxTRefNo);
			TxTSubject = (EditText) findViewById(R.id.NewContract_TxTSubject);

			// /------------Btn----- //
			BtnAdd = (Button) findViewById(R.id.BtnNewContractAdd);
			BtnAdd.setOnClickListener(this);

			btnSearchClient = (Button) findViewById(R.id.btnSearchClient);
			btnSearchClient.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {

					if (AutoClientName.getText().toString().trim().length() > 2
							&& AutoClientName.getText().toString().trim()
							.length() < 25) {
						new GetEnglishNameWithAgency().execute();
//						AutgetView();
					}
				}
			});
			// -----------Spinner-----------//

			SpinAdsCategory = (Spinner) findViewById(R.id.SpinnerNewContract_AdsCategory);
			SpinAdsCategory.setPrompt(getString(R.string.Select_One));
			SpinAdsCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
					SectorID = arrayAdapterAds_Category.getItem(
							SpinAdsCategory.getSelectedItemPosition()).GetID();
				}

				@Override
				public void onNothingSelected(AdapterView<?> adapterView) {

				}
			});

			SpinContractCategory = (Spinner) findViewById(R.id.SpinnerContractCategory);
			SpinContractCategory.setPrompt(getString(R.string.Select_One));
//			SpinContractCategory.setOnItemSelectedListener(this);

			SpinAdsType = (Spinner) findViewById(R.id.SpinnerAdsType);
//			SpinAdsType.setOnItemSelectedListener(this);
			// ---------AuotComplete -----//

			SpinAgency = (Spinner) findViewById(R.id.NewContract_Spinner_Agency);
			ArrayadapterAgancy = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1);
			//ArrayadapterAgancy.add("long Term Contract");
            ArrayadapterAgancy.add("إيرادات إعلانات مدفوعة مقدما");
			// ArrayadapterAgancy.add("زبائن قداما");
			SpinAgency.setAdapter(ArrayadapterAgancy);
//			SpinAgency.setOnItemSelectedListener(new OnItemSelectedListener() {
//				@Override
//				public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//					List<Agancy> listaAgancy;
//					if (SpinAgency.getSelectedItem().toString() == ArrayadapterAgancy
//							.getItem(0).toString()) {
//						listaAgancy = new Agancy().GetAgency("long Term Contract");
//						AgencyID = listaAgancy.get(0).GetID();
//					}
//				}
//				@Override
//				public void onNothingSelected(AdapterView<?> adapterView) {
//
//				}
//			});



			AutoClientName = (AutoCompleteTextView) findViewById(R.id.NewContract_AutoTxTClientName);

//			AutoClientName.addTextChangedListener(new TextChange(this,
//					AutoClientName.getId(), AutoClientName, AgencyID, SectorID,
//					this));

//			AutoClientName.addTextChangedListener(new TextWatcher() {
//
//				@Override
//				public void onTextChanged(CharSequence s, int arg1, int arg2,
//										  int arg3) {
//					if (AutoClientName.getText().toString().trim().length() > 3
//							&& AutoClientName.getText().toString().trim()
//							.length() < 12) {
////						new GetEnglishNameWithAgency().execute();
//						AutgetView();
//					}
//				}
//				@Override
//				public void beforeTextChanged(CharSequence arg0, int arg1,
//											  int arg2, int arg3) {
//					// TODO Auto-generated method stub
//				}
//				@Override
//				public void afterTextChanged(Editable arg0) {
//					// TODO Auto-generated method stub
//				}
//			});// Auto TxTAccount Name
//
//			AutoClientName.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//										int position, long id) {
//					AutoClientName.setText(ArrayClient.getItem(position)
//							.GetNameEnglish().trim());
//					try {
//						ClientID = ArrayClient.getItem(position).GetID();
//						ObjContract.SetClientName(ArrayClient.getItem(position)
//								.GetNameEnglish());
//					} catch (Exception e) {
//						ClientID = 0;
//						CatchMsg.WriteMSG("" + context, e.getMessage());
//						Log.d("Enable To Client ID", e.getMessage());
//					}
//				}
//			});

            adrspinPaymentType = (Spinner) findViewById(R.id.adrspinPaymentType);
            NewContractspinDesignID = (Spinner) findViewById(R.id.NewContractspinDesignID);

			new GetAgency().execute();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	@SuppressLint("SimpleDateFormat")
	private String DateIsuee() {


		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//        NumberFormat nf = NumberFormat.getInstance(new Locale("en","US")); //or "nb","No" - for Norway
//        String sDistance = nf.format(sdf.format(cal.getTime()));
//        distanceTextView.setText(String.format(getString(R.string.distance), sDistance));

		return sdf.format(cal.getTime());
//        return String.format(sDistance);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.adrnewcontract, menu);
		return true;
	}

	private void TextStatus(boolean IsEnable) {
		TxTRefNo.setEnabled(IsEnable);
		TxTSubject.setEnabled(IsEnable);
		AutoClientName.setEnabled(IsEnable);

		SpinAdsType.setEnabled(IsEnable);
		SpinContractCategory.setEnabled(IsEnable);
		// SpinPayment.setEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.NewContractAdd:
			if (Connection.IsConnectedToInternet) {
				TextStatus(true);
			} else
				GetToast.Toast(context, getString(R.string.NoInternet));
			return true;
		case R.id.NewContractRevent:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1)
			this.finish();
		// super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		if (IsValid()) {
			fillObjectContract();
            Double Cm = arrayAdapterDesignRequest.getItem(
                    NewContractspinDesignID.getSelectedItemPosition()).getCm();
            Integer Col = arrayAdapterDesignRequest.getItem(
                    NewContractspinDesignID.getSelectedItemPosition()).getCol().intValue();;

			Intent Calculate = new Intent(NewContract.this, Calculate.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("ObjContract", (Serializable) ObjContract);
			Calculate.putExtras(bundle);
            Calculate.putExtra("DesignCm" , Cm);
            Calculate.putExtra("DesignCol" , Col);
			startActivityForResult(Calculate, 0);
		}// End If
			// startActivity(new Intent(NewContract.this, Calculate.class));
	}

	private void fillObjectContract() {
		try {
            int ContractTypeID = ArrayPayment.getItem(
                    adrspinPaymentType.getSelectedItemPosition()).GetID();

			ObjContract.setPh_Mobile(TxTPh_Mobile.getText().toString());
			ObjContract.setAndroidClientName(TxTAndroidClientName.getText()
					.toString());
			ObjContract.SetAdsSectorID(arrayAdapterAds_Category.getItem(
					SpinAdsCategory.getSelectedItemPosition()).GetID());// -1-
			ObjContract.SetAdsTypeID(arrayAdapterAdsType.getItem(
					SpinAdsType.getSelectedItemPosition()).GetID());// -2-
            if(ContractTypeID == 1) {
                ObjContract.SetAgencyID(AgencyID);
            }else{
                ObjContract.SetAgencyID(CoreLogInActivity.AgencyID);
            }// -3-
			ObjContract.SetClientID(ClientID);// -4-
			ObjContract.SetContractTypeID(ContractTypeID);// -5- Cash Contract
			ObjContract.SetRefNo(TxTRefNo.getText().toString().trim());// -6-
			ObjContract.SetContCategoryID(arrayAdapterContactCategory.getItem(
					SpinContractCategory.getSelectedItemPosition()).GetID());// -7-
			ObjContract.SetSubject(TxTSubject.getText().toString().trim());// -8-
			ObjContract.SetSerialNo(0);// -9- from data Base
//			ObjContract.SetSalesID(Main.SalesId);// -10-
			ObjContract.SetSalesID(CoreLogInActivity.SalesID);// -10-
			ObjContract.SetContractDate(TxTDate.getText().toString());// 11
//            if(arrayAdapterDesignRequest == null) {
//                ObjContract.setDesignID(0);
//            }else {
                ObjContract.setDesignID(arrayAdapterDesignRequest.getItem(
                        NewContractspinDesignID.getSelectedItemPosition()).getID());
//            }
		} catch (Exception e) {
			GetToast.Toast(context,
					getString(R.string.YouHaveaProblemPleaseCheckContractInfo));
		}
	}

	private boolean IsValid() {
		try {
            boolean Valid = true ;
            Integer Errors = 0 ;
			if (arrayAdapterAdsType.getItem(
					SpinAdsType.getSelectedItemPosition()).GetID() == 0) {
				GetToast.Toast(context, getString(R.string.PleaseSelectAdsType));
                Errors = Errors + 1;
//				return false;
			} else if (arrayAdapterAds_Category.getItem(
					SpinAdsCategory.getSelectedItemPosition()).GetID() == 0) {
				GetToast.Toast(context,
						getString(R.string.PleaseSelectAds_Category));
                Errors = Errors + 1;
//				return false;
			} else if (arrayAdapterContactCategory.getItem(
					SpinContractCategory.getSelectedItemPosition()).GetID() == 0) {
				GetToast.Toast(context,
						getString(R.string.PleaseSelectContractCategory));
                Errors = Errors + 1;
//				return false;
			}

			else if (AgencyID == 0) {
				GetToast.Toast(context, getString(R.string.SelectAgency));
                Errors = Errors + 1;
//				return false;
			} else if (ClientID == 0) {
				GetToast.Toast(context, getString(R.string.PleaseSelectClient));
                Errors = Errors + 1;
//				return false;
			}
			else if(arrayAdapterDesignRequest == null){
                GetToast.Toast(context,
                        getString(R.string.PleaseRequestDesignForThisUser));
                Errors = Errors + 1;
//                return false;
            }
			if(Errors > 0){
                return false ;
            }else{
                return true;
            }


		} catch (Exception e) {
			return false;
		}

	}

	class GetAgency extends AsyncTask<String, String, String> {
				 ShowProgressDialog objProgressDialog;
				 @Override
				 protected void onPreExecute() {
					 // super.onPreExecute();
					 objProgressDialog = new ShowProgressDialog(NewContract.this,
							 getString(R.string.Please_Wait), getString(R.string.Wait),0);
					 objProgressDialog.ShowDialog();

				 }

				 @Override
				 protected String doInBackground(String... params) {
					 String x = "";
					 try {
						 List<Agancy> listaAgancy;
						 listaAgancy = new Agancy().GetAgency("إيرادات إعلانات مدفوعة مقدما");

						 if(listaAgancy.size() > 0 || listaAgancy != null)
						 AgencyID = listaAgancy.get(0).GetID();


					 } catch (Exception e) {
						 e.printStackTrace();
//						 Log.d("Do In Back ", e.getMessage());
					 }
					 return x;
				 }

				 @Override
				 protected void onPostExecute(String result) {
					 // TODO Auto-generated method stub
					 objProgressDialog.EndDialog();


				 }
			 }

	class GetEnglishNameWithAgency extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;
		String Auto = AutoClientName.getText()
				.toString();


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(NewContract.this,
					getString(R.string.Please_Wait), getString(R.string.Wait),0);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				listArrayClient = new EnglishNameWithAgency().GetEnglishNameWithAgency(Auto);

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			AutgetView();
		}
	}

	private void AutgetView(){
		try{

//			listArrayClient = new EnglishNameWithAgency().GetEnglishNameWithAgency(AutoClientName.getText().toString().trim());

			ArrayClient = new ArrayAdapter<EnglishNameWithAgency>(context,
					android.R.layout.simple_spinner_dropdown_item,
					listArrayClient);
            onCreateDialogSingleChoice(ArrayClient);
//			Dialog AreaChoice=	onCreateDialogSingleChoice(ArrayClient);
//			AreaChoice.show();

		}catch (Exception e){
			e.printStackTrace();
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
                         EnglishNameWithAgency SelectedArea = (EnglishNameWithAgency)checkedItem;
                         Toast.makeText(getApplicationContext(),
                                 checkedItem.toString(), Toast.LENGTH_SHORT).show();
                         AutoClientName.setText(SelectedArea.GetNameEnglish());
                         ClientID =SelectedArea.GetID();
                         ObjContract.SetClientName(SelectedArea.GetNameEnglish());
						 alertDialog1.dismiss();
                         if(ClientID > 0)
                             new Android_SelectDesignRequest().execute();
                     }
                 });
                 alertDialog1 = builder.create();
                 alertDialog1.show();
             }

    class GetPayment extends AsyncTask<String, String, String> {
                 ShowProgressDialog objProgressDialog;
                 @Override
                 protected void onPreExecute() {
                     // super.onPreExecute();
                     objProgressDialog = new ShowProgressDialog(NewContract.this,
                             getString(R.string.Please_Wait), getString(R.string.Wait),0);
                     objProgressDialog.ShowDialog();
                 }
                 @Override
                 protected String doInBackground(String... params) {
                     String x = "";
                     try {

                         Payment objPayment = new Payment();

                         listPayment = objPayment.GetPayment();

                     } catch (Exception e) {
                         e.printStackTrace();
//						 Log.d("Do In Back ", e.getMessage());
                     }
                     return x;
                 }

                 @Override
                 protected void onPostExecute(String result) {
                     // TODO Auto-generated method stub
                     objProgressDialog.EndDialog();

                     if(listPayment != null){
                         ArrayPayment = new ArrayAdapter<Payment>(NewContract.this,
                                     android.R.layout.simple_spinner_item, listPayment);
                             ArrayPayment
                                     .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                             adrspinPaymentType.setAdapter(ArrayPayment);

                     }
                 }
             }

    class Android_SelectDesignRequest extends AsyncTask<String, String, String> {
                 ShowProgressDialog objProgressDialog;


                 @Override
                 protected void onPreExecute() {
                     // super.onPreExecute();
                     objProgressDialog = new ShowProgressDialog(NewContract.this,
                             getString(R.string.Please_Wait), getString(R.string.Wait), 0);
                     objProgressDialog.ShowDialog();

                 }

                 @Override
                 protected String doInBackground(String... params) {
                     String x = "";
                     try {

                         DesignRequest objDesignRequest = new DesignRequest();
                         DesignRequestlist = objDesignRequest.Android_SelectDesignRequest(ClientID);

                     } catch (Exception e) {
                         Log.d("Do In Back ", e.getMessage());
                     }
                     return x;
                 }

                 @Override
                 protected void onPostExecute(String result) {
                     // TODO Auto-generated method stub
                     objProgressDialog.EndDialog();
                     if (DesignRequestlist != null) {

                         arrayAdapterDesignRequest= new ArrayAdapter<DesignRequest>(NewContract.this,
                                 android.R.layout.simple_spinner_item, DesignRequestlist);
                         arrayAdapterDesignRequest
                                 .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                         NewContractspinDesignID.setAdapter(arrayAdapterDesignRequest);
                     }
                 }
             }
}// End Class
