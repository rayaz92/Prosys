package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CallResultReason {
	private int ID = 0;
	private String NameEn = "";
	private String NameAr = "";
	private int CallResultTypeID = 0;
	private String CallResultTypeNameEn = "";
	private String CallResultTypeNameAr = "";

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetNameEn(String NameEn) {
		this.NameEn = NameEn;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void SetCallResultTypeID(int CallResultTypeID) {
		this.CallResultTypeID = CallResultTypeID;
	}

	public void SetCallResultTypeNameEn(String CallResultTypeNameEn) {
		this.CallResultTypeNameEn = CallResultTypeNameEn;
	}

	public void SetCallResultTypeNameAr(String CallResultTypeNameAr) {
		this.CallResultTypeNameAr = CallResultTypeNameAr;
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

	public int GetCallResultTypeID() {
		return this.CallResultTypeID;
	}

	public String GetCallResultTypeNameEn() {
		return this.CallResultTypeNameEn;
	}

	public String GetCallResultTypeNameAr() {
		return this.CallResultTypeNameAr;
	}

	public List<CallResultReason> SelectCallResultReason(int CallResutlyTypeID) {
		List<CallResultReason> list = null;
		String OPERATION_NAME = "SelectCallResultReason";

		String SOAP_ACTION = "http://tempuri.org/SelectCallResultReason";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo pi = new PropertyInfo();
		pi.setName("CallResultTypeID");
		pi.setValue(CallResutlyTypeID);
		pi.setType(int.class);
		request.addProperty(pi);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<CallResultReason>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {

					CallResultReason item = new CallResultReason();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetNameEn(obj3.getProperty("NameEn").toString());
					list.add(item);
				}// End for
			}// End try

		} catch (Exception e) {
			 Log.d("Error In CallResultReason  ", e.getMessage());
			
			//CatchMsg.WriteMSG("CallResultReason", e.getMessage());
			// e.printStackTrace();
			list = null;
			return list;
		}// End try Catch
		return list;
	}// end function CallResultReason

	@Override
	public String toString() {
		return NameEn;
	}// End To String
}// End Class
