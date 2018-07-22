package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ContractType {
	private int ID = 0;
	private String NameAr = "";
	private String NameEn = "";
	private double Price = 0.0;
	private int Period = 0;
	private Boolean IsDaily = false;
	private int PublicationID = 0;
	private String PublicationNameEn = "";
	private String PublicationNameAr = "";
	private int Rating = 0;

	public void SetIsDaily(Boolean IsDaily) {
		this.IsDaily = IsDaily;
	}

	public Boolean getIsDaily() {
		return this.IsDaily;
	}

	public String getPublicationNameEn() {
		return this.PublicationNameEn;
	}

	public String getPublicationNameAr() {
		return this.PublicationNameAr;
	}

	public void SetPublicationNameEn(String PublicationNameEn) {
		this.PublicationNameEn = PublicationNameEn;
	}

	public void SetPublicationNameAr(String PublicationNameAr) {
		this.PublicationNameAr = PublicationNameAr;
	}

	public void SetPublicationID(int PublicationID) {
		this.PublicationID = PublicationID;
	}

	public void SetRating(int Rating) {
		this.Rating = Rating;
	}

	public int getPublicationID() {
		return this.PublicationID;
	}

	public int getRating() {
		return this.Rating;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void SetNameEn(String NameEN) {
		this.NameEn = NameEN;
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

	public List<ContractType> getContractType() {

		List<ContractType> list = null;
		
		String OPERATION_NAME = "SelectContractType";

		String SOAP_ACTION = "http://tempuri.org/SelectContractType";

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
				list = new ArrayList<ContractType>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ContractType item = new ContractType();
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
					item.SetIsDaily(Boolean.valueOf(obj3.getProperty("IsDaily")
							.toString()));
					item.SetRating(Integer.parseInt(obj3.getProperty("Rating")
							.toString()));
					item.SetPublicationID(Integer.parseInt(obj3.getProperty(
							"PublicationID").toString()));
					item.SetPublicationNameEn(obj3.getProperty(
							"PublicationNameEn").toString());
					item.SetPublicationNameAr(obj3.getProperty(
							"PublicationNameAr").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In ContractType  ", e.getMessage());
			// CatchMsg.WriteMSG("Contract Type ", e.getMessage());
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
