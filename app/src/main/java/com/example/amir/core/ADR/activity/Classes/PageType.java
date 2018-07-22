package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class PageType {
	private int Part_ID = 0;
	private String Part_Name = "";

	public void SetPart_ID(int Paet_ID) {
		this.Part_ID = Paet_ID;
	}

	public void SetPart_Name(String Part_Name) {
		this.Part_Name = Part_Name;
	}

	public int GetPart_ID() {
		return this.Part_ID;
	}

	public String GetPart_Name() {
		return this.Part_Name;
	}

	public List<PageType> GetPartType() {
		List<PageType> list = null;
		String OPERATION_NAME = "PartType";
		String SOAP_ACTION = "http://tempuri.org/PartType";
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
				list = new ArrayList<PageType>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					PageType item = new PageType();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetPart_ID(Integer.parseInt(obj3
							.getProperty("Part_ID").toString()));
					item.SetPart_Name(obj3.getProperty("Part_Name").toString());

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In Part Type ", e.getMessage());
			new CatchMsg().WriteMSG("Page Type", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Part_Name;
	}
}// End Class
