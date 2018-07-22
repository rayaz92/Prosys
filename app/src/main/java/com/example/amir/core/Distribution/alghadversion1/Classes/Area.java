package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Area {
	private int ID = 0;
	private String NameEn = "";
	private String NameAr = "";
	private int CityID = 0;
	private String FilterArea ="";
	

	public void SetID(int ID) {
		this.ID = ID;
	}

	public void SetNameEn(String NameEn) {
		this.NameEn = NameEn;
	}

	public void SetNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void SetCityID(int CityID) {
		this.CityID = CityID;
	}

	public void SetFilterArea(String FilterArea) {
		this.FilterArea = FilterArea ;
	}
	
	public int GetID() {
		return this.ID;
	}

	public String GetNameEn() {
		return this.NameEn;
	}

	public String GetNameAr() {
		return this.NameAr;
	}

	public int GetCityID() {
		return this.CityID;
	}
	
	public String GetFilterArea() {
		return this.FilterArea;
	}
	
	public List<Area> getArea(int ID) {
		List<Area> list = null;
		String OPERATION_NAME = "GetAreasByCityID";

		String SOAP_ACTION = "http://tempuri.org/GetAreasByCityID";

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
			PropertyInfo  pi = new PropertyInfo();
			pi.setName("CityID");
			pi.setValue(ID);
			pi.setType(String.class);
			request.addProperty(pi);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Area>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Area item = new Area();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.SetNameEn(obj3.getProperty("NameEn").toString());

					// value of column 2 NameEn
					item.SetNameAr(obj3.getProperty("NameAr").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			// Log.d("Error In Bank  ", e.getMessage());
			Log.e("Bank", e.getMessage());
			// CatchMsg.WriteMSG("Bank", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function Get Bank Name

	//@Override
	//public String toStrings() {
		// TODO Auto-generated method stub
		//return NameAr;
	//}// end To Strong


	public List<Area> getFilterArea(int ID , String FilterArea) {
		List<Area> list = null;
		String OPERATION_NAME = "GetFilterAreas";

		String SOAP_ACTION = "http://tempuri.org/GetFilterAreas";

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
			PropertyInfo  pi = new PropertyInfo();
			pi.setName("CityID");
			pi.setValue(ID);
			pi.setType(int.class);
			request.addProperty(pi);
			
			PropertyInfo  pi2 = new PropertyInfo();
			pi2.setName("FilterArea");
			pi2.setValue(FilterArea);
			pi2.setType(String.class);
			request.addProperty(pi2);
			
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Area>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Area item = new Area();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
					item.SetNameEn(obj3.getProperty("NameEn").toString());
					
					// value of column 2 NameEn
					item.SetNameAr(obj3.getProperty("NameAr").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			// Log.d("Error In Bank  ", e.getMessage());
			Log.e("Bank", e.getMessage());
			// CatchMsg.WriteMSG("Bank", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function Get Bank Name

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameAr;
	}// end To Strong

}
