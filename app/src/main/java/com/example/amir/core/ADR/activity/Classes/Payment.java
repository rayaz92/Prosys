package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class Payment {
	private int ID = 0;
	private String Name = "";
	private int SerialNo = 0;
	private int AndroudCount = 0;

	public Payment(int id, String Name) {
		this.ID = id;
		this.Name = Name;
	}

	public void SetAndroidCount(int AndroudCount) {
		this.AndroudCount = AndroudCount;
	}

	public int GetAndroudCount() {
		return this.AndroudCount;
	}

	public void SetSerialNo(int SerialNo) {
		this.SerialNo = SerialNo;
	}

	public int GetSerialNo() {
		return this.SerialNo;
	}

	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public void SetName(String Name) {
		this.Name = Name;
	}

	public void SetID(int ID) {
		this.ID = ID;
	}

	public String GetName() {
		return this.Name;
	}

	public int GetID() {
		return this.ID;
	}

	public List<Payment> GetPayment() {
		List<Payment> list = null;
		String OPERATION_NAME = "Payment";
		String SOAP_ACTION = "http://tempuri.org/Payment";
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
				list = new ArrayList<Payment>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Payment item = new Payment();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.SetName(obj3.getProperty("Name").toString());
					item.SetSerialNo(Integer.valueOf(obj3.getProperty(
							"SerialNo").toString()));
//					item.SetAndroidCount(Integer.valueOf(obj3.getProperty(
//							"AndroidCount").toString()));
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			new CatchMsg().WriteMSG("Payment ", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End get payment

	public boolean InsertSerialNo(int PaymentTypeID) {
		String OPERATION_NAME = "InsertSerialNo";
		String SOAP_ACTION = "http://tempuri.org/InsertSerialNo";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo PiPaymentTypeID = new PropertyInfo();

		PiPaymentTypeID.setName("PaymentTypeID");
		PiPaymentTypeID.setValue(PaymentTypeID);
		PiPaymentTypeID.setType(int.class);
		request.addProperty(PiPaymentTypeID);
		boolean Result = false;

		androidHttpTransport.debug = true;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Log.d("Payment Type", "" + envelope.getResponse());
			Object response = (Object) envelope.getResponse().toString().trim();
			Result = Boolean.getBoolean(response.toString());

		} catch (Exception e) {
			new CatchMsg();
			CatchMsg.WriteMSG("Payment ", e.getMessage());
			Result = false;
		}

		if (Result) {
			return true;
		} else
			return false;
	}// End InsertSerialNo

	@SuppressWarnings("static-access")
	public Integer GetSerialNoByTypeID(int PaymentTypeID) {
		String OPERATION_NAME = "Android_SerialNoByPaymentTypeID";
		String SOAP_ACTION = "http://tempuri.org/Android_SerialNoByPaymentTypeID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo PiPaymentTypeID = new PropertyInfo();

		PiPaymentTypeID.setName("PaymentTypeID");
		PiPaymentTypeID.setValue(PaymentTypeID);
		PiPaymentTypeID.setType(int.class);
		request.addProperty(PiPaymentTypeID);

		androidHttpTransport.debug = true;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse().toString().trim();
			return Integer.valueOf(response.toString());
		} catch (Exception e) {
			new CatchMsg().WriteMSG("Payment ", e.getMessage());
			return 0;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}
}
