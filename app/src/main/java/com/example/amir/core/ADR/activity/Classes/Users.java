package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Users {
	private String SalesName = "";
	private String UserName = "";
	private String Password = "";
	private int UserID = 0;
	private String EmpName = "";
	private int GroupID = 0;
	private int SalesId = 0;
	private String MSG = "";

	public void SetSalesId(int SalesId) {
		this.SalesId = SalesId;
	}

	public int GetSalesId() {
		return this.SalesId;
	}

	public void SetSalesName(String SalesName) {
		this.SalesName = SalesName;
	}

	public String GetSalesName() {
		return this.SalesName;
	}

	public void SetUserName(String Name) {
		this.UserName = Name;
	}

	public void SetPassword(String Password) {
		this.Password = Password;
	}

	public void SetUserID(int UserID) {
		this.UserID = UserID;
	}

	public void SetGroupID(int GroupID) {
		this.GroupID = GroupID;
	}

	public void SetEmpName(String EmpName) {
		this.EmpName = EmpName;
	}

	public int GetUserID() {
		return this.UserID;
	}

	public int GetFroupID() {
		return this.GroupID;
	}

	public String GetEmpName() {
		return this.EmpName;
	}

	public String getUserName() {
		return this.UserName;
	}

	public String getPassword() {
		return this.Password;
	}

	public List<Users> LogIn(String UserName, String Password) {
		Users objUser = new Users();
		List<Users> list = null;
		String SOAP_ACTION = "http://tempuri.org/UserLogin";

		String OPERATION_NAME = "UserLogin";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("UserName");
		pi.setValue(UserName);
		pi.setType(String.class);
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
			// SoapObject response1 = (SoapObject) envelope.getResponse();
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
					objUser.SetUserID(Integer.parseInt(obj3.getProperty(
							"UserID").toString()));
//					objUser.SetGroupID(Integer.parseInt(obj3.getProperty(
//							"GroupID").toString()));
//					objUser.SetEmpName(obj3.getProperty("EmpName").toString());
//					objUser.SetSalesName(obj3.getProperty("SalesName")
//							.toString());
					objUser.SetSalesId(Integer.valueOf(obj3.getProperty(
							"SalesId").toString()));
					list.add(objUser);
					break;
				}// End for
			}// End try
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error In User Log In   ", e.getMessage());
			CatchMsg.WriteMSG("Users", e.getMessage());
			// this.MSG = e.getMessage();
			list = null;
			// UserID = 0;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		return this.toString();
	}
}// End Class
