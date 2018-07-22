package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class EnglishNameWithAgency {
	private int ID = 0;
	private String NameEnglish = "";

	public void SetID(int ID) {
		this.ID = ID;
	}

	public void SetNameEnglish(String NameEnglish) {
		this.NameEnglish = NameEnglish;
	}

	public int GetID() {
		return this.ID;
	}

	public String GetNameEnglish() {
		return this.NameEnglish;
	}

	public List<EnglishNameWithAgency> GetEnglishNameWithAgency(String NameEn) {
		EnglishNameWithAgency objEnglishNameWithAgency = new EnglishNameWithAgency();
		List<EnglishNameWithAgency> list = null;
		String SOAP_ACTION = "http://tempuri.org/GetEnglishNameWithAgency";

		String OPERATION_NAME = "GetEnglishNameWithAgency";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi2 = new PropertyInfo();
		pi2.setName("prefixText");
		pi2.setValue(NameEn);
		pi2.setType(String.class);
		request.addProperty(pi2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<EnglishNameWithAgency>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objEnglishNameWithAgency = new EnglishNameWithAgency();
					obj3 = (SoapObject) obj2.getProperty(i);
					objEnglishNameWithAgency.SetID(Integer.parseInt(obj3
							.getProperty("ID").toString()));
					objEnglishNameWithAgency.SetNameEnglish((obj3.getProperty(
							"NameEnglish").toString().trim()));

					list.add(objEnglishNameWithAgency);
				}// End for
			}// End try
		} catch (Exception e) {
//			Log.d("Error English Name With Agency", e.getMessage());
			CatchMsg.WriteMSG("Name with Agency", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	public List<EnglishNameWithAgency> GetClientNameByAgencyId(int AgencyID,
			int SectorID, String NameBiref) {
		EnglishNameWithAgency objEnglishNameWithAgency = new EnglishNameWithAgency();
		List<EnglishNameWithAgency> list = null;
		String SOAP_ACTION = "http://tempuri.org/Android_SelectClientNameByAgencyID";

		String OPERATION_NAME = "Android_SelectClientNameByAgencyID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piAgencyID = new PropertyInfo();
		piAgencyID.setName("AgencyID");
		piAgencyID.setValue(AgencyID);
		piAgencyID.setType(int.class);
		request.addProperty(piAgencyID);

		PropertyInfo piNameBiref = new PropertyInfo();
		piNameBiref.setName("ClientBrief");
		piNameBiref.setValue(NameBiref);
		piNameBiref.setType(String.class);
		request.addProperty(piNameBiref);

		// PropertyInfo piSectorID = new PropertyInfo();
		// piSectorID.setName("SectorID");
		// piSectorID.setValue(-1);
		// request.addProperty(piSectorID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<EnglishNameWithAgency>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objEnglishNameWithAgency = new EnglishNameWithAgency();
					obj3 = (SoapObject) obj2.getProperty(i);
					objEnglishNameWithAgency.SetID(Integer.parseInt(obj3
							.getProperty("ID").toString()));
					objEnglishNameWithAgency.SetNameEnglish((obj3.getProperty(
							"NameEnglish").toString().trim()));

					list.add(objEnglishNameWithAgency);
				}// End for
			}// End try
		} catch (Exception e) {
			CatchMsg.WriteMSG("Name with Agency", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameEnglish;// + "||" + ID;// + "-" + ID;
	}
}
