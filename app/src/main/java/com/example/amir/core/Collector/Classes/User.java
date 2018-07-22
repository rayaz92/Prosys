package com.example.amir.core.Collector.Classes;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class User {
	private String coll_name = "";
	private String Password = "";
	private int coll_id = 0;

	public void Setcoll_name(String Name) {
		this.coll_name = Name;
	}

	public String getcoll_name() {
		return this.coll_name;
	}

	public void SetPassword(String Password) {
		this.Password = Password;
	}

	public void Setcoll_id(int UserID) {
		this.coll_id = UserID;
	}

	public int Getcoll_id() {
		return this.coll_id;
	}

	public String getPassword() {
		return this.Password;
	}

	public User LogIn(String UserName, String Password) {
		User objUser = new User();

		String SOAP_ACTION = "http://tempuri.org/Login";

		String OPERATION_NAME = "Login";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("UserName");
		propertyInfo.setValue(UserName);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Password");
		propertyInfo.setValue(Password);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		// AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
		// Connection.ADDRESS);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();
			if (response1.toString().equals("anyType{}") || response1 == null) {
			} else {
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objUser = new User();
					obj3 = (SoapObject) obj2.getProperty(i);
					objUser.Setcoll_id(Integer.parseInt(obj3.getProperty("coll_id").toString()));
					objUser.Setcoll_name((obj3.getProperty("coll_name").toString()));
					break;
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In User Log In   ", e.getMessage());
			// this.MSG = e.getMessage();
			CatchMsg.WriteMSG("LogIn", e.getMessage());
			objUser = null;
			// UserID = 0;
		}// End try Catch
		return objUser;
	}
}
