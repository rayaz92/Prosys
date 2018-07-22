package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Country {
	private int ID = 0;
	private String NameEn = "";
	private String NameAr = "";

	public void SetID(int ID) {
		this.ID = ID;
	}

	public void SetNameEn(String NameEn) {
		this.NameEn = NameEn;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}


	public int GetID() {
		return this.ID;
	}

	public String GetNameEn() {
		return this.NameEn;
	}

	public String GetNameAr() {
		return this.NameAr;
	}

	
	public List<Country> getCountry() {
		List<Country> list = null;
		String OPERATION_NAME = "GetCountry";

		String SOAP_ACTION = "http://tempuri.org/GetCountry";

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
				list = new ArrayList<Country>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Country item = new Country();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.SetNameEn(obj3.getProperty("NameEn").toString());
					
					// value of column 2 NameEn
					item.SetNameAr(obj3.getProperty("NameAr").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			// Log.d("Error In Bank  ", e.getMessage());
			Log.e("Bank", e.getMessage());
			// CatchMsg.WriteMSG("Bank", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function Get Bank Name

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameAr;
	}// end To Strong
}
