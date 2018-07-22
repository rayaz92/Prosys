package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Discription {
	private String Discription = "";
	private int ID = 0;
	private double Price = 0.0;

	public void SetDiscription(String Discription) {
		this.Discription = Discription;
	}

	public void SetID(int ID) {
		this.ID = ID;
	}

	public void SetPrice(Double Price) {
		this.Price = Price;
	}

	public String GetDiscription() {
		return this.Discription;
	}

	public int GetID() {
		return this.ID;
	}

	public double GetPrice() {
		return this.Price;
	}

	public List<Discription> SelectDiscriptionAndPriceByPartTypeID(
			int PartTypeID, String prefixText , int PublicationID) {
		List<Discription> list = null;
		String OPERATION_NAME = "Android_SelectDiscriptionAndPriceByPartID";
		String SOAP_ACTION = "http://tempuri.org/Android_SelectDiscriptionAndPriceByPartID";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		PropertyInfo PiPrefixText = new PropertyInfo();
		PiPrefixText.setName("prefixText");
		PiPrefixText.setValue(prefixText);
		PiPrefixText.setType(String.class);
		request.addProperty(PiPrefixText);

		PropertyInfo PiPartTypeID = new PropertyInfo();
		PiPartTypeID.setName("PartTypeID");
		PiPartTypeID.setValue(PartTypeID);
		PiPartTypeID.setType(int.class);
		request.addProperty(PiPartTypeID);

        PropertyInfo PiPublicationID = new PropertyInfo();
        PiPublicationID.setName("PublicationID");
        PiPublicationID.setValue(PublicationID);
        PiPublicationID.setType(int.class);
        request.addProperty(PiPublicationID);

		envelope.setOutputSoapObject(request);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Discription>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Discription item = new Discription();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetDiscription(obj3.getProperty("Discription")
							.toString().trim());
					item.SetPrice(Double.valueOf(obj3.getProperty("Price")
							.toString()));
					list.add(item);
				}// End for
			}
		} catch (Exception e) {
			list = null;
			Log.d("Error In Discription  ", e.getMessage());
			new CatchMsg().WriteMSG("Discription  ", e.getMessage());
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + Price + "***" + Discription;

	}
}// End Class
