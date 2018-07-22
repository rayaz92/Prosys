package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class AdsType {
	private int ID = 0;
	private String Description = "";
	private int AdsGroupID = 0;

	public AdsType() {
		// TODO Auto-generated constructor stub
	}

	public AdsType(int id, String Description) {
		this.ID = id;
		this.Description = Description;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetDescription(String Description) {
		this.Description = Description;
	}

	public void SetAdsGroupID(int AdsGroupID) {
		this.AdsGroupID = AdsGroupID;
	}

	public int GetID() {
		return this.ID;
	}

	public String GetDescription() {
		return this.Description;
	}

	public int GetAdsGroupID() {
		return this.AdsGroupID;
	}

	public List<AdsType> getAdsType() {
		List<AdsType> list = null;
		String OPERATION_NAME = "AdsType";
		String SOAP_ACTION = "http://tempuri.org/AdsType";
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
				list = new ArrayList<AdsType>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					AdsType item = new AdsType();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetDescription(obj3.getProperty("Description")
							.toString());
					item.SetAdsGroupID(Integer.parseInt(obj3.getProperty(
							"AdsGroupID").toString()));
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In AdsType ", e.getMessage());
			
			CatchMsg.WriteMSG("AdsType", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Description;
	}
}
