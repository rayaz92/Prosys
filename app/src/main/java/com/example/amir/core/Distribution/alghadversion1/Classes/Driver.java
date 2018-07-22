package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	private int DriverID = 0;
	private String Name = "";
	private int SalesID = 0;
	private Boolean IsSalesContract = false;

	public void setDriverID(int id) {
		this.DriverID = id;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setSalesID(int SalesID) {
		this.SalesID = SalesID;
	}

	public void setIsSalesContract(Boolean IsSales) {
		this.IsSalesContract = IsSales;
	}

	public int getDriverID() {
		return this.DriverID;
	}

	public String getName() {
		return this.Name;
	}

	public int getSalesID() {
		return this.SalesID;
	}

	public Boolean getisSales() {
		return this.IsSalesContract;
	}

	public List<Driver> getDriverIdByUserId(int UserId) {

		Driver objDriver = new Driver();
		List<Driver> list = null;
		String SOAP_ACTION = "http://tempuri.org/SelectDriverIDByUserID";

		String OPERATION_NAME = "SelectDriverIDByUserID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("UserID");
		pi.setValue(UserId);
		pi.setType(int.class);
		request.addProperty(pi);

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
				list = new ArrayList<Driver>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objDriver = new Driver();
					obj3 = (SoapObject) obj2.getProperty(i);
					objDriver.setDriverID(Integer.parseInt(obj3.getProperty(
							"ID").toString()));

					objDriver.setName(obj3.getProperty("NameEn").toString());

					objDriver.setSalesID(Integer.valueOf(obj3.getProperty(
							"SalesID").toString()));
					objDriver.setIsSalesContract(Boolean.valueOf(obj3
							.getProperty("IsSalesContract").toString()));

					list.add(objDriver);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In Driver  ", e.getMessage());
			// CatchMsg.WriteMSG("Driver  ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End Function

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.toString();
	}// End To Strong
}// End Class
