package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PaymentTypes {
	private int ID = 0;
	private String NameAr = "";
	private String NameEn = "";
	private String AccountNo = "";
	private String CRAccountNo = "";

	private Boolean IsPaid = false;
	private Boolean ShowInSettelement = false;
	private Boolean IsCash = false;
	private Boolean IsVisibleToPDA = false;
	private Boolean IsRequiredFinanceaccount = false;

	public void SetIsPaid(Boolean IsPaid) {
		this.IsPaid = IsPaid;
	}

	public void SetShowInSettelement(Boolean ShowInSettelement) {
		this.ShowInSettelement = ShowInSettelement;
	}

	public void SetIsCash(Boolean IsCash) {
		this.IsCash = IsCash;
	}

	public void SetIsVisibleToPDA(Boolean IsVisibleToPDA) {
		this.IsVisibleToPDA = IsVisibleToPDA;
	}

	public void SetIsRequiredFinanceaccount(Boolean IsRequiredFinanceaccount) {
		this.IsRequiredFinanceaccount = IsRequiredFinanceaccount;
	}

	public Boolean getIsPaid() {
		return this.IsPaid;
	}

	public Boolean getShowInSettelement() {
		return this.ShowInSettelement;
	}

	public Boolean getIsCash() {
		return this.IsCash;
	}

	public Boolean getIsVisibleToPDA() {
		return this.IsVisibleToPDA;
	}

	public Boolean getIsRequiredFinanceaccount() {
		return this.IsRequiredFinanceaccount;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void SetAccountNo(String AccountNo) {
		this.AccountNo = AccountNo;
	}

	public void SetCRAccountNo(String CRAccountNo) {
		this.CRAccountNo = CRAccountNo;
	}

	public void SetNameEn(String NameEN) {
		this.NameEn = NameEN;
	}

	public int getID() {
		return this.ID;
	}

	public String getNameAr() {
		return this.NameAr;
	}

	public String getNameEn() {
		return this.NameEn;
	}

	public List<PaymentTypes> getPayment() {
		List<PaymentTypes> list = null;
		String OPERATION_NAME = "SelectPaymentTypesByIsPDAVisible";

		String SOAP_ACTION = "http://tempuri.org/SelectPaymentTypesByIsPDAVisible";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		try {
			PropertyInfo pi = new PropertyInfo();
			pi.setName("IsVisibleToPDA");
			pi.setValue("true");
			pi.setType(Boolean.class);
			request.addProperty(pi);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<PaymentTypes>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					PaymentTypes item = new PaymentTypes();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.SetNameEn(obj3.getProperty("NameEn").toString());
					// value of column 2 NameEn
					item.SetNameAr(obj3.getProperty("NameAr").toString());
					item.SetAccountNo(obj3.getProperty("AccountNo").toString());
					item.SetCRAccountNo(obj3.getProperty("CRAccountNo")
							.toString());
					item.SetIsPaid(Boolean.valueOf(obj3.getProperty("IsPaid")
							.toString()));
					item.SetShowInSettelement(Boolean.valueOf(obj3.getProperty(
							"ShowInSettelement").toString()));
					item.SetIsCash(Boolean.valueOf(obj3.getProperty("IsCash")
							.toString()));
					item.SetIsVisibleToPDA(Boolean.valueOf(obj3.getProperty(
							"IsVisibleToPDA").toString()));
					item.SetIsRequiredFinanceaccount(Boolean
							.valueOf(obj3.getProperty(
									"IsRequiredFinanceaccount").toString()));

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			// CatchMsg.WriteMSG("Payment Type", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End Function Get Payment

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameAr;
	}// End To String
}// End Class
