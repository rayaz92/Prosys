package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ClientTypeGroup {
	private int ID = 0;
	private String NameAr = "";
	private String NameEn = "";
	private int CLientTypeID = 0;
	private String CLientTypeNameEn = "";

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void setNameEn(String NameEn) {
		this.NameEn = NameEn;
	}

	public void setCLientTypeID(int CLientTypeID) {
		this.CLientTypeID = CLientTypeID;
	}

	public void setCLientTypeNameEn(String CLientTypeNameEn) {
		this.CLientTypeNameEn = CLientTypeNameEn;
	}

	public int getID() {
		return this.ID;
	}

	public String getNameAr() {
		return this.NameAr;
	}

	public String getNameEn() {
		return this.NameEn;
	}

	public int getCLientTypeID() {
		return this.CLientTypeID;
	}

	public String getCLientTypeNameEn() {
		return this.CLientTypeNameEn;
	}

	public List<ClientTypeGroup> getClientTypeGroupByClientTypeID(int CallTypeID) {
		List<ClientTypeGroup> list = null;
		String OPERATION_NAME = "SelectClientTypeGroupByClientTypeID";

		String SOAP_ACTION = "http://tempuri.org/SelectClientTypeGroupByClientTypeID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo pi = new PropertyInfo();
		pi.setName("CallTypeID");
		pi.setValue(CallTypeID);
		pi.setType(int.class);
		request.addProperty(pi);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<ClientTypeGroup>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("SelectClientTypeGroupByClientTypeID", ""
						+ resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ClientTypeGroup item = new ClientTypeGroup();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.setID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.setNameEn(obj3.getProperty("NameEn").toString());
					// value of column 2 NameEn
					item.setNameAr(obj3.getProperty("NameAr").toString());
					// value of column 3 NameAr
					item.setCLientTypeID(Integer.valueOf((obj3
							.getProperty("CLientTypeID").toString())));
					item.setCLientTypeNameEn(obj3.getProperty(
							"CLientTypeNameEn").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In SelectClientTypeGroupByClientTypeID  ",
					e.getMessage());
			// CatchMsg.WriteMSG("Client Type Group ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End ClientTypeGroupByClientTypeID

	@Override
	public String toString() {

		return NameEn.toString();
	}// End to String
}// End Class ClientTypeGroup
