package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class Agancy {
	private String Name = "";
	private int ID = 0;
	private String Accn_no = "";

	public void SetAccn_no(String Accn_no) {
		this.Accn_no = Accn_no;
	}

	public String GetAccn_no() {
		return this.Accn_no;
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

	public List<Agancy> GetAgency(String prefixText) {
		List<Agancy> list = null;

		String SOAP_ACTION = "http://tempuri.org/GetAgency";
		String OPERATION_NAME = "GetAgency";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);


		PropertyInfo PiPrefixText = new PropertyInfo();
		PiPrefixText.setName("prefixText");
		PiPrefixText.setValue(prefixText);
		PiPrefixText.setType(String.class);
		request.addProperty(PiPrefixText);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Agancy>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Agancy item = new Agancy();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetName(obj3.getProperty("NameEnglish").toString());
					item.SetAccn_no(obj3.getProperty("Accn_no")
							.toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			new CatchMsg();
			CatchMsg.WriteMSG("Agancy Get Agancy", e.getMessage());
			Log.d("Error In Get Agency", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function

//	public String SelectAgancyByID(int id) {
//		String OPERATION_NAME = "GetAgencyByID";
//		String SOAP_ACTION = "http://tempuri.org/GetAgencyByID";
//		SoapObject request = new SoapObject(Connection.NAMESPACE,
//				OPERATION_NAME);
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		HttpTransportSE androidHttpTransport = new HttpTransportSE(
//				Connection.ADDRESS);
//
//		PropertyInfo PiID = new PropertyInfo();
//		PiID.setName("ID");
//		PiID.setValue(id);
//		PiID.setType(Integer.class);
//		request.addProperty(PiID);
//		String Result = "";
//
//		androidHttpTransport.debug = true;
//		envelope.setOutputSoapObject(request);
//		envelope.dotNet = true;
//		try {
//			androidHttpTransport.call(SOAP_ACTION, envelope);
//			Log.d("Agncy By Id 01", "" + "Donme");
//			Object response = (Object) envelope.getResponse().toString().trim();
//			Log.d("Agncy By Id ", "" + response);
//
//			Result = "" + response.toString();
//		} catch (Exception e) {
//			Log.d("error Agncy By Id ", e.getMessage());
//			CatchMsg.WriteMSG("Agancy Select AgencyByid", e.getMessage());
//			Result = "" + e.getMessage();
//		}
//		return Result;
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}
}
