package com.example.amir.core.ADR.activity;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.example.amir.core.ADR.activity.Classes.AdsType;
import com.example.amir.core.ADR.activity.Classes.Ads_Category;
import com.example.amir.core.ADR.activity.Classes.ContactCategory;
import com.example.amir.core.ADR.activity.Classes.Contract_Offer;
import com.example.amir.core.ADR.activity.Classes.PageType;
import com.example.amir.core.R;

public class FillSpinners extends AsyncTask<Void, Void, Void> {

	NewContract Contract;
	ClientManagement Client;
	Calculate calculate;
	Context context;
	Calculate_Result calculate_Result;

	ShowProgressDialog objProgressDialog;

	public enum ActivityMode {
		NewContract, ClientManagement, Calculate, CalculateResult
	}

	private ActivityMode activityMode;

	public FillSpinners(ActivityMode mode, Activity activity, Context context) {
		this.activityMode = mode;
		this.context = context;
		switch (activityMode) {
		case ClientManagement:
			Client = (ClientManagement) activity;
			break;
		case Calculate:
			calculate = (Calculate) activity;
			break;
		case NewContract:
			Contract = (NewContract) activity;
			break;
		case CalculateResult:
			calculate_Result = (Calculate_Result) activity;
			break;
		}
	}

	@Override
	protected void onPreExecute() {

		objProgressDialog = new ShowProgressDialog(context,
				context.getString(R.string.Wait));

		// progressDialog = new ProgressDialog(context);
		// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// progressDialog.setMessage();
		// progressDialog.setCanceledOnTouchOutside(false);
		// // progressDialog.setCancelable(true);
		// // progressDialog.setIndeterminate(true);
		// progressDialog.show();
		objProgressDialog.ShowDialog();

	}

	@Override
	protected Void doInBackground(Void... params) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (activityMode) {
		case ClientManagement:
			Ads_Category objAds_Category = new Ads_Category();
			Client.Ads_Categorieslist = objAds_Category.GetAds_Category();
			break;
		case NewContract:
			Ads_Category objAds_Category1 = new Ads_Category();
			Contract.ListAds_category = objAds_Category1.GetAds_Category();

			ContactCategory objContactCategory = new ContactCategory();
			Contract.ListContactCategory = objContactCategory
					.GetContractCategory();

			AdsType objAdsType = new AdsType();
			Contract.ListAdsType = objAdsType.getAdsType();

			// Payment objPayment = new Payment();
			// Contract.ListPayment = objPayment.GetPayment();
			break;
		case Calculate:
			calculate.listPageType = new PageType().GetPartType();
			break;
		case CalculateResult:
			calculate_Result.listContract_Offer = new Contract_Offer()
					.GetContract_Offer();
			break;
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		switch (activityMode) {
		case ClientManagement:

			Client.Ads_Categorieslist.add(0, new Ads_Category(0, "Select One"));
			ArrayAdapter<Ads_Category> adapterAds_Categoris = new ArrayAdapter<Ads_Category>(
					Client, android.R.layout.simple_spinner_item,
					Client.Ads_Categorieslist);
			adapterAds_Categoris
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Client.SpinAdsCategory.setAdapter(adapterAds_Categoris);
			break;
		case NewContract:
			Contract.ListAds_category
					.add(0,
							new Ads_Category(0, context
									.getString(R.string.Select_One)));
			Contract.arrayAdapterAds_Category = new ArrayAdapter<Ads_Category>(
					context, android.R.layout.simple_spinner_item,
					Contract.ListAds_category);
			Contract.arrayAdapterAds_Category
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Contract.SpinAdsCategory
					.setAdapter(Contract.arrayAdapterAds_Category);

			Contract.ListContactCategory.add(
					0,
					new ContactCategory(0, Contract
							.getString(R.string.Select_One)));
			Contract.arrayAdapterContactCategory = new ArrayAdapter<ContactCategory>(
					context, android.R.layout.simple_spinner_item,
					Contract.ListContactCategory);
			Contract.arrayAdapterContactCategory
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Contract.SpinContractCategory
					.setAdapter(Contract.arrayAdapterContactCategory);

			Contract.ListAdsType.add(0,
					new AdsType(0, context.getString(R.string.Select_One)));
			Contract.arrayAdapterAdsType = new ArrayAdapter<AdsType>(context,
					android.R.layout.simple_spinner_item, Contract.ListAdsType);
			Contract.arrayAdapterAdsType
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Contract.SpinAdsType.setAdapter(Contract.arrayAdapterAdsType);

			break;
		case Calculate:
			calculate.arrayAdapterPageType = new ArrayAdapter<PageType>(
					context, android.R.layout.simple_spinner_item,
					calculate.listPageType);
			calculate.arrayAdapterPageType
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			calculate.SpinPageType.setAdapter(calculate.arrayAdapterPageType);

			break;
		case CalculateResult:
			calculate_Result.ArrayAdapterContract_Offer = new ArrayAdapter<Contract_Offer>(
					context, android.R.layout.simple_spinner_item,
					calculate_Result.listContract_Offer);
			calculate_Result.listContract_Offer.add(0, new Contract_Offer(0,
					calculate_Result.getString(R.string.Select_One)));
			calculate_Result.ArrayAdapterContract_Offer
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			calculate_Result.SpinOffer
					.setAdapter(calculate_Result.ArrayAdapterContract_Offer);
			break;
		}
		// progressDialog.dismiss();
		objProgressDialog.EndDialog();
	}
}
