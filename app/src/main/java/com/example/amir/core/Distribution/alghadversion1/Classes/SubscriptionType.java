package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionType {
	private int ID = 0;
	private String NameAr = "";
	private String NameEn = "";
	private double Price = 0.0;
	private int Period = 0;

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void SetNameEn(String NameEN) {
		this.NameEn = NameEn;
	}

	public void SetPrice(double Price) {
		this.Price = Price;
	}

	public void SetPeriod(int Period) {
		this.Period = Period;
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

	public double getPrice() {
		return this.Price;
	}

	public int getPeriod() {
		return this.Period;
	}

	public List<SubscriptionType> getSubscriptionType() {

		List<SubscriptionType> list = null;
		String OPERATION_NAME = "SelectSubscriptionType";

		String SOAP_ACTION = "http://tempuri.org/SelectSubscriptionType";

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
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<SubscriptionType>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					SubscriptionType item = new SubscriptionType();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.SetNameEn(obj3.getProperty("NameEn").toString());
					// value of column 2 NameEn
					item.SetNameAr(obj3.getProperty("NameAr").toString());
					// value of column 3 NameAr
					item.SetPrice(Double.valueOf(obj3.getProperty("Price")
							.toString()));
					item.SetPeriod(Integer.parseInt(obj3.getProperty("Period")
							.toString()));
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			//Log.d("Error In getSubscriptionType  ", e.getMessage());
			//CatchMsg.WriteMSG("Get Subscription Type ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End Function getSubscriptionType

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameAr;
	}// End To String

}// End Class
