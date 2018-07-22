package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ClientTypeGroupClassification {
	private int ID = 0;
	private String NameAr = "";
	private String NameEn = "";
	private int GroupID = 0;
	private String GroupNameEn = "";
	private int ClientTypeID = 0;

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void setNameEn(String NameEn) {
		this.NameEn = NameEn;
	}

	public void setGroupID(int GroupID) {
		this.GroupID = GroupID;
	}

	public void setClientTypeID(int ClientTypeID) {
		this.ClientTypeID = ClientTypeID;
	}

	public void setGroupNameEn(String GroupNameEn) {
		this.GroupNameEn = GroupNameEn;
	}

	public int getID() {
		return this.ID;
	}

	public int getGroupID() {
		return this.GroupID;
	}

	public int getClientTypeID() {
		return this.ClientTypeID;
	}

	public String getNameAr() {
		return this.NameAr;
	}

	public String getNameEn() {
		return this.NameEn;
	}

	public String getGroupNameEn() {
		return this.GroupNameEn;
	}

	public List<ClientTypeGroupClassification> getClientTypeGroupClassificationByGroupID(
			int GroupID) {
		List<ClientTypeGroupClassification> list = null;
		String OPERATION_NAME = "SelectClientTypeGroupClassificationByGroupID";

		String SOAP_ACTION = "http://tempuri.org/SelectClientTypeGroupClassificationByGroupID";

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
		pi.setName("GroupID");
		pi.setValue(GroupID);
		pi.setType(int.class);
		request.addProperty(pi);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<ClientTypeGroupClassification>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("getClientTypeGroupClassificationByGroupID", ""
						+ resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ClientTypeGroupClassification item = new ClientTypeGroupClassification();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.setID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.setNameEn(obj3.getProperty("NameEN").toString());
					// value of column 2 NameEn
					item.setNameAr(obj3.getProperty("NameAr").toString());
					// value of column 3 NameAr
					item.setGroupID(Integer.valueOf((obj3
							.getProperty("GroupID").toString())));
					item.setGroupNameEn(obj3.getProperty("GroupNameEn")
							.toString());
					item.setClientTypeID(Integer.valueOf(obj3.getProperty(
							"ClientTypeID").toString()));
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In SelectClientTypeGroupByClientTypeID  ",
					e.getMessage());
			// e.printStackTrace();
			// CatchMsg.WriteMSG("Client Type Group Class", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End ClientTypeGroupByClientTypeID

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameEn.toString();
	}// End To String
}// End Class
