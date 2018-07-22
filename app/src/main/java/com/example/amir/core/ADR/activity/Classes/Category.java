package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Category {
	private int ID = 0;
	private String Name = "";
	private String MSG = "";

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(int id, String Name) {
		this.ID = id;
		this.Name = Name;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetName(String Name) {
		this.Name = Name;
	}

	public int GetID() {
		return this.ID;
	}

	public String GetName() {
		return this.Name;
	}

	public List<Category> GetCategory() {
		List<Category> list = null;
		String OPERATION_NAME = "GetCategory";
		String SOAP_ACTION = "http://tempuri.org/GetCategory";
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
			Log.d("Category ID ", "" + response);
			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Category>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Category item = new Category();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetName(obj3.getProperty("Name").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			new CatchMsg();
			// Log.d("Error In Bank  ", e.getMessage());
			CatchMsg.WriteMSG("Category", e.getMessage());
			this.MSG = e.getMessage();
			list = null;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}
}// End Class
