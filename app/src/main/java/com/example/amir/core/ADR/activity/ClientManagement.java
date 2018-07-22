package com.example.amir.core.ADR.activity;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Ads_Category;
import com.example.amir.core.ADR.activity.Classes.Agancy;
import com.example.amir.core.ADR.activity.Classes.Category;
import com.example.amir.core.ADR.activity.Classes.ClassActivity;
import com.example.amir.core.ADR.activity.Classes.Client;
import com.example.amir.core.ADR.activity.Classes.EnglishNameWithAgency;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;


public class ClientManagement extends Activity
		 {
	public enum TextMode {
		Agancy
	}

	public TextMode currenttext;
	Context context = this;
	AutoCompleteTextView AutoNameEn, AutoNameAr, AutoActivity, AutoCategory;
	EditText TxTMobile, TxTPhone, TxTNote, TxTAddress, TxTEmail, TxTFax,
			TxTContact, TxTGroup, TxTAcountNo;
	Spinner SpinAdsCategory, SpinAgency;
	CheckBox ChPublic;
	Boolean IsClick = false;// if user click one of option menu
	Button imgbtnClientAdd , imgbtnClientSave;

	// ArrayAdapter<EnglishNameWithAgency> ArrayClient = null;

	List<ClassActivity> activities;
	ArrayAdapter<ClassActivity> adapterActivity;
	List<Category> Categorelist;
	ArrayAdapter<Category> adapterCategory;
	List<Ads_Category> Ads_Categorieslist;
	ArrayAdapter<String> adapterAgency;
	Agancy objAgancy = new Agancy();
	int AgencyID, CategoryID, ActivityID, AdsCategoryID, ClientID;


	Client ObjClient = new Client();

	public enum CurrentMode {
		Add, Edit, Delete
	};

	public CurrentMode Mode;
	ProgressDialog progressDialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			if (android.os.Build.VERSION.SDK_INT > 8) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.build();
				StrictMode.setThreadPolicy(policy);
			}// End if

			super.onCreate(savedInstanceState);
			setContentView(R.layout.adrclientmanagement);
			InitialView();
			Disable();
			// new fillSpinner().execute();
			new FillSpinners(FillSpinners.ActivityMode.ClientManagement, this, this)
					.execute();
			new Thread().execute();
			AutoNameEn.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
										  int arg3) {
					if (Mode == CurrentMode.Add) {
						return;
					} else
						AutoNameEn.addTextChangedListener(new TextChange(
								ClientManagement.this, AutoNameEn.getId(),
								AutoNameEn));

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
											  int arg2, int arg3) {

				}

				@Override
				public void afterTextChanged(Editable arg0) {

				}
			});

			AutoNameEn.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long arg3) {
					Toast.makeText(ClientManagement.this,
							"" + arg0.getItemAtPosition(arg2), Toast.LENGTH_LONG)
							.show();
					String s = arg0.getItemAtPosition(arg2).toString();
					String arra[] = s.split("-");
					AutoNameEn.setText(arra[0]);
					ClientID = Integer.valueOf(arra[1].toString());
					InfoClient(ClientID);
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void InitialView() {
		// ---------Auto Complete ---------//
		AutoNameEn = (AutoCompleteTextView) findViewById(R.id.txtAutoClientNameEn);
		// AutoNameEn.setThreshold(3);
		AutoActivity = (AutoCompleteTextView) findViewById(R.id.txtAuotClientmManagement_Activity);
		AutoActivity.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AutoActivity.showDropDown();
				return false;
			}
		});
		AutoActivity.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ActivityID = adapterActivity.getItem(position).GetID();
//				Log.d("Activity ID ", "" + ActivityID);
			}
		});

		AutoCategory = (AutoCompleteTextView) findViewById(R.id.txtAutoClientManagement_Category);
		AutoCategory.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				AutoCategory.showDropDown();
				return false;
			}
		});
		AutoCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				CategoryID = adapterCategory.getItem(position).GetID();
			}
		});
		AutoNameAr = (AutoCompleteTextView) findViewById(R.id.txtAutoClientNameAr);
		AutoNameAr.setThreshold(3);

		// -------- Edit text--------7//
		TxTAcountNo = (EditText) findViewById(R.id.txt_ClientManagement_AccountNo);
		TxTAcountNo.setEnabled(false);

		// AgencyList = new Agancy().GetAgency(TxTAgency.getText().toString());
		TxTAddress = (EditText) findViewById(R.id.TxTAddress);
		TxTEmail = (EditText) findViewById(R.id.TxTEMail);
		TxTFax = (EditText) findViewById(R.id.TxTFax);
		TxTGroup = (EditText) findViewById(R.id.txt_ClientManagement_group);
		TxTMobile = (EditText) findViewById(R.id.TxTMobile);
		TxTNote = (EditText) findViewById(R.id.TxTNote);
		TxTPhone = (EditText) findViewById(R.id.TxTTelephone);
		TxTContact = (EditText) findViewById(R.id.txtContact);

		imgbtnClientAdd =(Button) findViewById(R.id.imgbtnClientAdd) ;
		imgbtnClientSave=(Button) findViewById(R.id.imgbtnClientSave) ;

		imgbtnClientAdd.setEnabled(true);

		imgbtnClientAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Mode = CurrentMode.Add ;
				IsClick = true;
				Enable();
			}
		});

//        Mode = CurrentMode.Add ;
//        IsClick = true;
//        Enable();

		imgbtnClientSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				SaveClient();
				new InsertClient().execute();
			}
		});
		// -------- Spinner--------//

		SpinAdsCategory = (Spinner) findViewById(R.id.SpinnerNewContract_AdsCategory);
//		SpinAdsCategory.setOnItemSelectedListener(this);
		SpinAdsCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				try{
					String ItemSelected;
					ItemSelected = adapterView.getItemAtPosition(i).toString();
					GetAdsCategoryID(ItemSelected);

				}catch (Exception e){
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		SpinAgency = (Spinner) findViewById(R.id.Spinner_ClientManagement_Agency);

		// AgencyList.add( "زبائن قداما");
		// AgencyList.add();

		adapterAgency = new ArrayAdapter<String>(ClientManagement.this,
				android.R.layout.simple_spinner_dropdown_item);
//		adapterAgency.add("زبائن قداما");
//		adapterAgency.add("long Term Contract");
		adapterAgency.add("إيرادات إعلانات مدفوعة مقدما");
		SpinAgency.setAdapter(adapterAgency);

//		SpinAgency.setOnItemSelectedListener(this);
		SpinAgency.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				List<Agancy> lista = objAgancy.GetAgency(SpinAgency
						.getSelectedItem().toString());
				if(lista != null) {
					for (Agancy e : lista) {
						objAgancy.SetAccn_no(e.GetAccn_no());
						objAgancy.SetID(e.GetID());
						objAgancy.SetName(e.GetName());
						TxTAcountNo.setText("" + e.GetAccn_no());
						break;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		// -------- Check Box--------//
		ChPublic = (CheckBox) findViewById(R.id.CheckBoxPublic);

		// for (Agancy e : AgencyList) {
		// if (e.GetName().equals(objAgancy.GetName())) {
		// objAgancy.SetID(e.GetID());
		// objAgancy.SetAccn_no(e.GetAccn_no());
		// }
		// }// End for
		TxTContact.setText("" + objAgancy.GetAccn_no());

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (!(IsClick)) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.adrclientmune, menu);
		} else {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.adrsaveandrevert, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ClientManagementAdd:
			Mode = CurrentMode.Add;
			IsClick = true;
			Enable();
			// AutoAgancy.setEnabled(false);
			return true;
			// case R.id.ClientManagementEdit:
			// Mode = CurrentMode.Edit;
			// IsClick = true;
			// Enable();
			// return true;
		case R.id.ClientManagementDelete:
			return true;
		case R.id.MuneRevert:
			Disable();
			IsClick = false;
			return true;
		case R.id.MuneSave:
//			SaveClient();
			// Disable();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void InfoClient(int Clientid) {
		Client objClient = new Client();

		objClient = objClient.SelectClientByID(Clientid);
		TxTAddress.setText(objClient.GetAddress());
		TxTContact.setText(objClient.GetContact());
		TxTEmail.setText(objClient.GetEmail());
		TxTFax.setText(objClient.GetFax());
		TxTMobile.setText(objClient.GetMobile());
		TxTNote.setText(objClient.GetNote());
		TxTPhone.setText(objClient.GetTelephone());
		for (int i = 0; i < adapterCategory.getCount(); i++) {
			if (objClient.GetCategoryID() == adapterCategory.getItem(i).GetID()) {
				AutoCategory.setText(adapterCategory.getItem(i).GetName());
				break;
			}// End If
		}// End For Category ID
			// AutoAgancy
			// .setText(objAgancy.SelectAgancyByID(objClient.GetActivityID()));

		AutoNameAr.setText(objClient.GetNameArabic());
		// if (objClient.GetAgencyID() == 0)
		// AutoAgancy.setHint("Please Select agancy");
		// else
		// AutoAgancy.setText(objAgancy.SelectAgancyByID(objClient
		// .GetAgencyID()));
		//
		// SpinActivity.setSelection(getIndexActivity(objClient
		// .GetActivityID()));
		// SpinCategory.setSelection(getIndexCategory(objClient
		// .GetCategoryID()));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void SaveClient() {
		Client ObjClient = new Client();
		ObjClient.SetActivityID(ActivityID);
		ObjClient.SetAddress(TxTAddress.getText().toString());
		if (objAgancy.GetID() == 0) {
			GetToast.Toast(context, getString(R.string.SelectAgency));
			return;
		} else
			ObjClient.SetAgencyID(objAgancy.GetID());
		if (AdsCategoryID == 0) {
			GetToast.Toast(context,
					getString(R.string.PleaseSelectAds_Category));
			return;
		} else
			ObjClient.SetAdsCategoryID(AdsCategoryID);
		ObjClient.SetCashContNo("");
		ObjClient.SetCategoryID(CategoryID);
		ObjClient.SetContact(TxTContact.getText().toString());
		ObjClient.SetEmail(TxTEmail.getText().toString());
		ObjClient.SetFax(TxTFax.getText().toString());
		if (AutoNameAr.getText().toString().trim().equals("")) {
			GetToast.Toast(context, getString(R.string.Please_Enter_Name_Ar));
			AutoNameAr.setHint(getString(R.string.Enter_Name));
			AutoNameAr.setHintTextColor(Color.RED);
			return;
		} else
			ObjClient.SetNameArabic(AutoNameAr.getText().toString());
		if (TxTMobile.getText().toString().trim().equals("")) {
			GetToast.Toast(context, getString(R.string.PleaseEnterMobile));
			TxTMobile.setHint(getString(R.string.EnterMobile));
			TxTMobile.setHintTextColor(Color.RED);
			return;
		} else
			ObjClient.SetMobile(TxTMobile.getText().toString());
		ObjClient.SetNameEn(AutoNameEn.getText().toString());
		ObjClient.SetNote(TxTNote.getText().toString());
		ObjClient.SetPOBox("");
//		ObjClient.SetSalesPersonID(Main.UserID);
		ObjClient.SetSalesPersonID(CoreLogInActivity.UserID);
		ObjClient.SetTelephone(TxTPhone.getText().toString());
		ObjClient.SetTypeID(1);
//		ObjClient.SetUserID(Main.UserID);
		ObjClient.SetUserID(CoreLogInActivity.UserID);
		ObjClient.SetZCode("");
		ObjClient.SetAccn_no(TxTAcountNo.getText().toString());
		switch (Mode) {
		case Add:
			if (!IsNameValid()) {
				GetToast.Toast(context, getString(R.string.ClientAlreadyExists));
				return;
			}
			// ObjClient.SetAccn_no();
			if (ObjClient.InsertClient(ObjClient) > 0) {
				GetToast.Toast(context, getString(R.string.InsertDone));
				this.finish();
			} else
				GetToast.Toast(context, getString(R.string.ErrorInInsert));
			break;
		case Edit:
			if (this.ClientID == 0) {
				GetToast.Toast(context, getString(R.string.PleaseSelectClient));
				return;
			}
			// if (ObjClient.UpdateClient(ObjClient, ClientID)) {
			// Toast("Update Done ");
			// this.finish();
			// }
			else {
				// Toast("Error In Update ");
			}
			break;
		}
	}

	private boolean IsNameValid() {
		EnglishNameWithAgency objEnglishNameWithAgency = new EnglishNameWithAgency();
		List<EnglishNameWithAgency> list = objEnglishNameWithAgency
				.GetEnglishNameWithAgency(AutoNameEn.getText().toString());
		if (list == null)
			return true;
		return false;
	}

	private void GetAdsCategoryID(String itemSelected) {
		if (Ads_Categorieslist != null) {
			for (Ads_Category e : Ads_Categorieslist)
				if (e.GetSectorName() == itemSelected)
					this.AdsCategoryID = e.GetID();
		}
	}

	// private void getActivityID(String itemSelected) {
	// for (ClassActivity e : activities) {
	// if (((Classes.ClassActivity) e).GetName() == itemSelected)
	// ActivityID = e.GetID();
	// }
	//
	// }

	private void Enable() {
		AutoNameAr.setEnabled(true);
		AutoNameEn.setEnabled(true);
		// AutoAgancy.setEnabled(true);
		TxTAddress.setEnabled(true);
		TxTEmail.setEnabled(true);
		TxTFax.setEnabled(true);
		TxTMobile.setEnabled(true);
		TxTNote.setEnabled(true);
		TxTPhone.setEnabled(true);
		ChPublic.setEnabled(true);
		imgbtnClientSave.setEnabled(true);
	}

	private void Disable() {
		AutoNameAr.setEnabled(false);
		AutoNameEn.setEnabled(false);
		// AutoAgancy.setEnabled(false);

		TxTAddress.setEnabled(false);
		TxTEmail.setEnabled(false);
		TxTFax.setEnabled(false);
		TxTMobile.setEnabled(false);
		TxTNote.setEnabled(false);
		TxTPhone.setEnabled(false);
		TxTGroup.setEnabled(false);
		TxTContact.setEnabled(false);
		ChPublic.setEnabled(false);
	}

//	@Override
//	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
//			long arg3) {
//		String ItemSelected;
//		switch (parent.getId()) {
//		case R.id.SpinnerNewContract_AdsCategory:
//			ItemSelected = parent.getItemAtPosition(position).toString();
//			GetAdsCategoryID(ItemSelected);
//			break;
//		case R.id.Spinner_ClientManagement_Agency:
//			List<Agancy> lista = objAgancy.GetAgency(SpinAgency
//					.getSelectedItem().toString());
//			for (Agancy e : lista) {
//				objAgancy.SetAccn_no(e.GetAccn_no());
//				objAgancy.SetID(e.GetID());
//				objAgancy.SetName(e.GetName());
//				TxTAcountNo.setText("" + e.GetAccn_no());
//				break;
//			}
//			break;
//		}
//	}

	private int getIndexActivity(Integer ID) {
		int i = 0;
		try {
			for (ClassActivity e : activities) {
				i += 1;
				Log.d("Activity  Name", e.GetName());
				Log.d("Activity  ID", "" + ID + "**" + e.GetID());

				if (e.GetID() == ID) {
					Log.d("Activity  Name", e.GetName());
					break;
				}
			}// End For
		} catch (Exception e) {
			Log.d("Error In Get Index ", e.getMessage());
		}
		return (i - 1);
	}

//	@Override
//	public void onNothingSelected(AdapterView<?> arg0) {
//		// TODO Auto-generated method stub
//
//	}

	private class Thread extends AsyncTask<Void, Void, Void> {
		ShowProgressDialog showProgressDialog;

		@Override
		protected void onPreExecute() {
			showProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Wait));
			showProgressDialog.ShowDialog();
			// progressDialog = new ProgressDialog(ClientManagement.this);
			// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// progressDialog.setMessage();
			// progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			try{
			activities = new ClassActivity().FillAcivity();
			Categorelist = new Category().GetCategory();

			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			showProgressDialog.EndDialog();
			// progressDialog.dismiss();
			if (activities == null) {
				AutoActivity.setError(getString(R.string.ErrorInData));

				GetToast.Toast(context, getString(R.string.NoData));
			}// end If null
			else {
				adapterActivity = new ArrayAdapter<ClassActivity>(
						ClientManagement.this,
						android.R.layout.simple_dropdown_item_1line, activities);
				AutoActivity.setAdapter(adapterActivity);
				AutoActivity.setThreshold(1);

			}
			if (Categorelist == null) {
				AutoCategory.setError(getString(R.string.ErrorInData));
				GetToast.Toast(context, getString(R.string.NoData));
			} else {
				adapterCategory = new ArrayAdapter<Category>(
						ClientManagement.this,
						android.R.layout.simple_dropdown_item_1line,
						Categorelist);
				AutoCategory.setAdapter(adapterCategory);
				AutoCategory.setThreshold(1);
			}

			super.onPostExecute(result);
		}

	}// End Class Thread

	class InsertClient extends AsyncTask<String, String, String> {
				 ShowProgressDialog objProgressDialog;


				 @Override
				 protected void onPreExecute() {
					 // super.onPreExecute();
					 objProgressDialog = new ShowProgressDialog(ClientManagement.this,
							 getString(R.string.Please_Wait), getString(R.string.Wait),0);
					 objProgressDialog.ShowDialog();

				 }

				 @Override
				 protected String doInBackground(String... params) {
					 String x = "";
					 try {
						 if(FillClient()) {

							int NewID =  ObjClient.InsertClient(ObjClient);
							 if(NewID > 0){
								 x = "Done";
							 }
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
					 if(result.toString().equals("Done")) {
						 GetToast.Toast(context, getString(R.string.InsertDone));
						 ClientManagement.this.finish();
					 }
				 }
			 }

	Boolean FillClient(){

				  Boolean Valid = true;
				  Integer Errors = 0 ;
				 try{
					 ObjClient.SetActivityID(ActivityID);
					 ObjClient.SetAddress(TxTAddress.getText().toString());
					 ObjClient.SetAddress(TxTAddress.getText().toString());
					 if (objAgancy.GetID() == 0) {
						 GetToast.Toast(context, getString(R.string.SelectAgency));
						 Errors +=1 ;
					 } else
						 ObjClient.SetAgencyID(objAgancy.GetID());
					 if (AdsCategoryID == 0) {
						 GetToast.Toast(context,
								 getString(R.string.PleaseSelectAds_Category));
						 Errors +=1 ;
					 } else
						 ObjClient.SetAdsCategoryID(AdsCategoryID);
					 ObjClient.SetCashContNo("");
					 ObjClient.SetCategoryID(CategoryID);
					 ObjClient.SetContact(TxTContact.getText().toString());
					 ObjClient.SetEmail(TxTEmail.getText().toString());
					 ObjClient.SetFax(TxTFax.getText().toString());
					 if (AutoNameAr.getText().toString().trim().equals("")) {
						 GetToast.Toast(context, getString(R.string.Please_Enter_Name_Ar));
						 AutoNameAr.setHint(getString(R.string.Enter_Name));
						 AutoNameAr.setHintTextColor(Color.RED);
						 Errors +=1 ;
					 } else
						 ObjClient.SetNameArabic(AutoNameAr.getText().toString());
					 if (TxTMobile.getText().toString().trim().equals("")) {
						 GetToast.Toast(context, getString(R.string.PleaseEnterMobile));
						 TxTMobile.setHint(getString(R.string.EnterMobile));
						 TxTMobile.setHintTextColor(Color.RED);
						 Errors +=1 ;
					 } else
						 ObjClient.SetMobile(TxTMobile.getText().toString());
					 ObjClient.SetNameEn(AutoNameEn.getText().toString());
					 ObjClient.SetNote(TxTNote.getText().toString());
					 ObjClient.SetPOBox("");
					 ObjClient.SetSalesPersonID(CoreLogInActivity.UserID);
					 ObjClient.SetTelephone(TxTPhone.getText().toString());
					 ObjClient.SetTypeID(1);
					 ObjClient.SetUserID(CoreLogInActivity.UserID);
					 ObjClient.SetZCode("");
					 ObjClient.SetAccn_no(TxTAcountNo.getText().toString());

							if (!IsNameValid()) {
								 GetToast.Toast(context, getString(R.string.ClientAlreadyExists));
								 Errors +=1 ;
							 }
							 // ObjClient.SetAccn_no();
				 }catch (Exception e){
					 e.printStackTrace();
					 Valid = false;
				 }
				 if(Errors == 0){
					 Valid = true;
				 }else {
					 Valid = false;
				 }
				 return Valid;
			 }
}
