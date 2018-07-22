package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private int ID = 0;

	private String Name = "";
	private String Pass = "";
	private String MSG = "";

	public String GetMSG() {
		return this.MSG;
	}

	public void setID(int id) {
		this.ID = id;

	}// End SetID

	public void setName(String Name) {
		this.Name = Name;

	}// End setName

	public void setPass(String Pass) {
		this.Pass = Pass;

	}// End setPass


	public int getID() {
		return this.ID;
	}// End Get ID

	public String getName() {
		return this.Name;
	}// End Get Name

	public String getPass() {
		return this.Pass;
	}

	@SuppressLint("NewApi")
	public int LogIn(String Name, String Pass) {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		Users objUser = new Users();
		List<Users> list = null;
		String SOAP_ACTION = "http://tempuri.org/UserLogin";

		String OPERATION_NAME = "UserLogin";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piUserName = new PropertyInfo();
		piUserName.setName("UserName");
		piUserName.setValue(Name);
		piUserName.setType(String.class);
		request.addProperty(piUserName);

		PropertyInfo piPassword = new PropertyInfo();
		piPassword.setName("Password");
		piPassword.setValue(Pass);
		piPassword.setType(String.class);
		request.addProperty(piPassword);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<Users>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objUser = new Users();
					obj3 = (SoapObject) obj2.getProperty(i);
					objUser.setID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));

					objUser.setName(obj3.getProperty("UserName").toString());

					objUser.setPass(obj3.getProperty("Password").toString());

					list.add(objUser);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In User Log In   ", e.getMessage());
			//CatchMsg.WriteMSG("User Login ", e.getMessage());
			this.MSG = e.getMessage();
			list = null;

		}// End try Catch
		return objUser.getID();
	}// End LogIn

	public Boolean UpdatePassword(int ID, String Password) {
		String SOAP_ACTION = "http://tempuri.org/UpdatePasswordByID";

		String OPERATION_NAME = "UpdatePasswordByID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("ID");
		pi.setValue(ID);
		pi.setType(int.class);
		request.addProperty(pi);

		PropertyInfo pi2 = new PropertyInfo();
		pi2.setName("Password");
		pi2.setValue(Password);
		pi2.setType(String.class);
		request.addProperty(pi2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse();
			if (response.toString().equals("True"))
				return true;
			else
				return false;
		} catch (Exception e) {
			Log.d("Error In Update ", e.getMessage());
			this.MSG = e.getMessage();
			return false;
		}// End Try Catch
	}// End Update Pass

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}// End To String

}// End Calss
