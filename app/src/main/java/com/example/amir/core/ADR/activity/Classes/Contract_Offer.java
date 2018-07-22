package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Contract_Offer {
	private int ID = 0;
	private String Offer_Name = "";
	private int Offer_No_Paid = 0;
	private int Offer_No_Free = 0;

	public Contract_Offer() {
		// TODO Auto-generated constructor stub
	}

	public Contract_Offer(int id, String OfferName) {
		this.ID = id;
		this.Offer_Name = OfferName;
		// TODO Auto-generated constructor stub
	}

	public void SetOffer_No_Paid(int Offer_No_Paid) {
		this.Offer_No_Paid = Offer_No_Paid;
	}

	public int GetOffer_No_Paid() {
		return this.Offer_No_Paid;
	}

	public void SetOffer_No_Free(int Offer_No_Free) {
		this.Offer_No_Free = Offer_No_Free;
	}

	public int GetOffer_No_Free() {
		return this.Offer_No_Free;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetOffer_Name(String Offer_Name) {
		this.Offer_Name = Offer_Name;
	}

	public int GetID() {
		return this.ID;
	}

	public String GetOffer_Name() {
		return this.Offer_Name;
	}

	public List<Contract_Offer> GetContract_Offer() {
		List<Contract_Offer> list = null;
		String OPERATION_NAME = "Android_Contract_Offer";
		String SOAP_ACTION = "http://tempuri.org/Android_Contract_Offer";
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
				list = new ArrayList<Contract_Offer>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Contract_Offer item = new Contract_Offer();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetOffer_Name(obj3.getProperty("Offer_Name")
							.toString());

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In Get Offer ", e.getMessage());
			new CatchMsg().WriteMSG("Contract_Off.getOffer", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	public Contract_Offer SelectNo_PaidAndNo_FreeByOfferID(int OfferID) {
		List<Contract_Offer> list = null;
		String OPERATION_NAME = "Android_SelectNoPaidAndNo_free";
		String SOAP_ACTION = "http://tempuri.org/Android_SelectNoPaidAndNo_free";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo PiID = new PropertyInfo();
		PiID.setName("ID");
		PiID.setValue(OfferID);
		PiID.setType(int.class);
		request.addProperty(PiID);
		Contract_Offer item = new Contract_Offer();// To Return this
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Contract_Offer>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {

					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetOffer_No_Paid(Integer.parseInt(obj3.getProperty(
							"Offer_No_Paid").toString()));
					item.SetOffer_No_Free(Integer.valueOf(obj3.getProperty(
							"Offer_No_Free").toString()));

					break;
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In Get Number By Id ", e.getMessage());
			new CatchMsg().WriteMSG("Contract_off.GetByID", e.getMessage());
			list = null;
		}// End try Catch
		return item;

	}// End Select By Id

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Offer_Name;
	}

}// End Class
