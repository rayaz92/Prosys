package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CallResultType {
	private int ID = 0;
	private String NameEn = "";
	private String NameAr = "";

	public void SetID(int id) {
		this.ID = id;
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

	public List<CallResultType> SelectCallResultType(int CallTypeID,
                                                     Boolean True)

	{
		List<CallResultType> list = null;
		String OPERATION_NAME = "SelectCallResultTypesByCallTypeIDAndIsVisble";

		String SOAP_ACTION = "http://tempuri.org/SelectCallResultTypesByCallTypeIDAndIsVisble";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		try {
			PropertyInfo PropertyInfo = new PropertyInfo();
			PropertyInfo.setName("CallTypeID");
			PropertyInfo.setValue(CallTypeID);
			PropertyInfo.setType(int.class);
			request.addProperty(PropertyInfo);

			PropertyInfo = new PropertyInfo();
			PropertyInfo.setName("IsVisible");
			PropertyInfo.setValue(true);
			PropertyInfo.setType(boolean.class);
			request.addProperty(PropertyInfo);
			envelope.setOutputSoapObject(request);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<CallResultType>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				// SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("CallResultType ", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					CallResultType objCallResultType = new CallResultType();
					obj3 = (SoapObject) obj2.getProperty(i);
					objCallResultType.SetID(Integer.parseInt(obj3.getProperty(
							"ID").toString()));
					// value of column 1 ID
					objCallResultType.SetNameEn(obj3.getProperty("NameEn")
							.toString());
					// value of column 2 NameEn
					objCallResultType.SetNameAr(obj3.getProperty("NameAr")
							.toString());
					// value of column 3 NameAr

					list.add(objCallResultType);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In CallResultType  ", e.getMessage());
			// e.printStackTrace();
			//CatchMsg.WriteMSG("CallResultType ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End Call Result type

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameEn;
	}// End To String

}// end Class
