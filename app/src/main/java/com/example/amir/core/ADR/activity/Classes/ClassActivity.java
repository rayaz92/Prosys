package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ClassActivity {
	private String Name = "";
	private int ID = 0;

	public ClassActivity() {
		// TODO Auto-generated constructor stub
	}

	public ClassActivity(int id, String Name) {
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

	public List<ClassActivity> FillAcivity() {
		List<ClassActivity> list = null;
		String OPERATION_NAME = "GetActivity";
		String SOAP_ACTION = "http://tempuri.org/GetActivity";
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
				list = new ArrayList<ClassActivity>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ClassActivity item = new ClassActivity();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetName(obj3.getProperty("Name").toString().trim());

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			CatchMsg.WriteMSG("Calss activity", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function Get Bank Name

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}
}
