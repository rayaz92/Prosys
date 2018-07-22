package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Ads_Category {

	private int ID = 0;
	private String SectorName = "";

	public Ads_Category(int id, String SectorName) {
		this.ID = id;
		this.SectorName = SectorName;
	}

	public Ads_Category() {
		// TODO Auto-generated constructor stub
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetSectorName(String SectorName) {
		this.SectorName = SectorName;
	}

	public int GetID() {
		return this.ID;
	}

	public String GetSectorName() {
		return this.SectorName;
	}

	public List<Ads_Category> GetAds_Category() {
		List<Ads_Category> list = null;
		String OPERATION_NAME = "GetAds_Sector";
		String SOAP_ACTION = "http://tempuri.org/GetAds_Sector";
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
				list = new ArrayList<Ads_Category>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Ads_Category item = new Ads_Category();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetSectorName(obj3.getProperty("SectorName")
							.toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			new CatchMsg();
			 Log.d("Error In Ads_Category", e.getMessage());
			CatchMsg.WriteMSG("Ads_Category", e.getMessage());
			list = null;
		}// End try Catch

		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return SectorName;
	}
}// End Class
