package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CheckIn {
	private int ID = 0;
	private String NameAr = "";
	private String MSG = "";
	private int PDAEntryUserID = 0;
	private String DeviceID = "";
	private Double latitude = 0.0;
	private Double longitude = 0.0;
	private String SubsName = "";
	private String Phone = "";
	private String PosName = "";
	private String Note = "";
	private String PosAddress = "";
	private String PosNameCheck = "";
	private String NoteCheck = "";
	private String PosAddressCheck = "";
	private String Street ="";
	private String FilterArea="";
	private int City =0;
	private int area =0;
	private int Country=0;
	private int RefID = 0;
	private int CountNamePos = 0;

	public void setPDAEntryUserID(int PDAEntryUserID) {
		this.PDAEntryUserID = PDAEntryUserID;
	}

	public void setNameAr(String NameAr) {
		this.NameAr = NameAr;
	}

	public void setStreet(String Street) {
		this.Street = Street;
	}
	
	public String GetStreet()
	{
		return this.Street;
	}
	
	public void setCity(int City) {
		this.City = City;
	}
	
	public int GetCity()
	{
		return this.City;
	}
	
	
	public void setArea(int Area) {
		this.area = Area;
	}
	
	public int GetArea()
	{
		return this.area;
	}
	
	public void setFilterArea(String Area) {
		this.FilterArea = FilterArea;
	}
	
	public String GetFilterArea()
	{
		return this.FilterArea;
	}
	
	public void setCountry(int Country) {
		this.Country = Country;
	}
	
	public int GetCountry()
	{
		return this.Country;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}

	public int GetID() {
		return this.ID;
	}

	public void setPosAddress(String PosAddress) {
		this.PosAddress = PosAddress;
	}

	public String GetPosAddress() {
		return this.PosAddress;
	}

	public void setPosAddressCheck(String PosAddressCheck) {
		this.PosAddressCheck = PosAddressCheck;
	}

	public String GetPosAddressCheck() {
		return this.PosAddressCheck;
	}

	public String GetNameAr() {
		return this.NameAr;
	}

	public void setDeviceID(String DeviceID) {
		this.DeviceID = DeviceID;
	}

	public void setPosName(String PosName) {
		this.PosName = PosName;
	}

	public void setSubsName(String SubsName) {
		this.SubsName = SubsName;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public void setPosNameCheck(String PosNameCheck) {
		this.PosNameCheck = PosNameCheck;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	public void setNoteCheck(String NoteCheck) {
		this.NoteCheck = NoteCheck;
	}

	public void setlatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setlongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setRefID(int RefID) {
		this.RefID = RefID;
	}

	public int GetRefID() {
		return this.RefID;
	}

	public void setCountPosName(int CountPosName) {
		this.CountNamePos = CountPosName;
	}

	public int GetCountPosName() {
		return this.CountNamePos;
	}

	public int getPDAEntryUserID() {
		return this.PDAEntryUserID;

	}

	public String GetPosName() {
		return this.PosName;
	}

	public String GetSubsName() {
		return this.SubsName;
	}

	public String GetPhoneName() {
		return this.Phone;
	}

	public String GetPosNameCheck() {
		return this.PosNameCheck;
	}

	public String GetNote() {
		return this.Note;
	}

	public String GetNoteCheck() {
		return this.NoteCheck;
	}

	public String getDeviceID() {
		return this.DeviceID;
	}

	public Double getlatitude() {
		return this.latitude;
	}

	public Double getlongitude() {
		return this.longitude;
	}

	public String GetMSG() {
		return this.MSG;
	}

	@SuppressLint("NewApi")
	public CheckIn checkcountposname(String PosName) {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		CheckIn objCheckIn = new CheckIn();
		// List<CheckIn> list = null;
		String SOAP_ACTION = "http://tempuri.org/GetCheckCountPOSName";

		String OPERATION_NAME = "GetCheckCountPOSName";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piUserName = new PropertyInfo();
		piUserName.setName("PosName");
		piUserName.setValue(PosName);
		piUserName.setType(String.class);
		request.addProperty(piUserName);

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
				// list = null;
			} else {
				// list = new ArrayList<CheckIn>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					obj3 = (SoapObject) obj2.getProperty(i);

					obj3 = (SoapObject) obj2.getProperty(i);
					objCheckIn.setID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					objCheckIn.setPosNameCheck(String.valueOf((obj3
							.getProperty("PosName").toString())));
					objCheckIn.setPosAddressCheck(String.valueOf((obj3
							.getProperty("PosAddress").toString())));
					objCheckIn.setNoteCheck(String.valueOf((obj3
							.getProperty("Note").toString())));
					objCheckIn.setCountPosName(Integer.parseInt(obj3
							.getProperty("CountPostName").toString()));

					// list.add(objCheckIn);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In User Log In   ", e.getMessage());
			// CatchMsg.WriteMSG("User Login ", e.getMessage());
			this.MSG = e.getMessage();
			// list = null;

		}// End try Catch
		return objCheckIn;
	}// End LogIn



	public List<CheckIn> SelectRefernce() {

		List<CheckIn> list = null;
		String OPERATION_NAME = "Getrefernce";
		String SOAP_ACTION = "http://tempuri.org/Getrefernce";
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
				list = new ArrayList<CheckIn>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {

					CheckIn item = new CheckIn();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.setID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					item.setNameAr(obj3.getProperty("NameAr").toString());
					list.add(item);
				}// End for

			}// End try
		} catch (Exception e) {

			Log.d("Error In Select Reference", e.getMessage());

			list = null;
		}// End try Catch

		return list;
	}

	public String InsertCheckIn(String Lat, String Lon) {
		String OPERATION_NAME = "InsertAndroidCheckIn";

		String SOAP_ACTION = "http://tempuri.org/InsertAndroidCheckIn";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		PropertyInfo pLatitude = new PropertyInfo();
		pLatitude.setName("Latitude");
		pLatitude.setValue(Lat);
		pLatitude.setType(String.class);
		request.addProperty(pLatitude);

		PropertyInfo pLongitude = new PropertyInfo();
		pLongitude.setName("Longitude");
		pLongitude.setValue(Lon);
		pLongitude.setType(String.class);
		request.addProperty(pLongitude);
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		// Object response;
		// SoapObject response = null;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Log.d("result check in", envelope.getResponse().toString());
			return (String) envelope.getResponse().toString();
			// this.MSG = (String) response;
		} catch (Exception e) {
			Log.d("Error In android Check In   ", e.getMessage());
			// CatchMsg.WriteMSG("android Check In", e.getMessage());
			this.MSG = e.getMessage();
			return "";
		}// End try Catch

	}

	public Integer InsertPosLocation(CheckIn objCheckin) {

		String SOAP_ACTION = "http://tempuri.org/InsertPosLocationcheckin";

		String OPERATION_NAME = "InsertPosLocationcheckin";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("PDAEntryUserID");
		propertyinfo.setValue(objCheckin.getPDAEntryUserID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 00 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DeviceID");
		propertyinfo.setValue(objCheckin.getDeviceID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 01 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude ");
		propertyinfo.setValue(objCheckin.getlatitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(objCheckin.getlongitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 03 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("SubscriberName");
		propertyinfo.setValue(objCheckin.GetSubsName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 04 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Phone");
		propertyinfo.setValue(objCheckin.GetPhoneName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 05 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PosName");
		propertyinfo.setValue(objCheckin.GetPosName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 06 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PosAddress");
		propertyinfo.setValue(objCheckin.GetPosAddress());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 07 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("RefID");
		propertyinfo.setValue(objCheckin.GetRefID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 08 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Note");
		propertyinfo.setValue(objCheckin.GetNote());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 09 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Street");
		propertyinfo.setValue(objCheckin.GetStreet());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 10 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("City");
		propertyinfo.setValue(objCheckin.GetCity());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 11 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Area");
		propertyinfo.setValue(objCheckin.GetArea());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 12 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Country");
		propertyinfo.setValue(objCheckin.GetCountry());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 13 */

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		boolean re = false;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int res;
		try {
			new MarshalFloat().register(envelope);
			new MarshalBase64().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
			Log.d("Error In Insert Save Pos Location  ", "" + response);
			res = 0;
			// CatchMsg.WriteMSG("Insert Save SubscriberLocation ",
			// exception.getMessage()
			// + "\n" + response.toString());
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End SubscriberLocation

	public Integer InsertCheckinLocation_Club(CheckIn objCheckin) {

		String SOAP_ACTION = "http://tempuri.org/InsertClupLocationCheckin";

		String OPERATION_NAME = "InsertClupLocationCheckin";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("PDAEntryUserID");
		propertyinfo.setValue(objCheckin.getPDAEntryUserID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 00 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DeviceID");
		propertyinfo.setValue(objCheckin.getDeviceID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 01 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude ");
		propertyinfo.setValue(objCheckin.getlatitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(objCheckin.getlongitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 03 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("SubscriberName");
		propertyinfo.setValue(objCheckin.GetSubsName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 04 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Phone");
		propertyinfo.setValue(objCheckin.GetPhoneName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 05 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PosName");
		propertyinfo.setValue(objCheckin.GetPosName());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 06 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PosAddress");
		propertyinfo.setValue(objCheckin.GetPosAddress());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 07 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("RefID");
		propertyinfo.setValue(objCheckin.GetRefID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 08 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Note");
		propertyinfo.setValue(objCheckin.GetNote());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 09 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Street");
		propertyinfo.setValue(objCheckin.GetStreet());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 10 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("City");
		propertyinfo.setValue(objCheckin.GetCity());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 11 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Area");
		propertyinfo.setValue(objCheckin.GetArea());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 12 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Country");
		propertyinfo.setValue(objCheckin.GetCountry());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 13 */
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		boolean re = false;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int res;
		try {
			new MarshalFloat().register(envelope);
			new MarshalBase64().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
			res = 0;
			// CatchMsg.WriteMSG("Insert Save SubscriberLocation ",
			// exception.getMessage()
			// + "\n" + response.toString());
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End SubscriberLocation
	public Boolean UpdatecontractDetailsCoordinates(CheckIn objCheckin){//,Double Latitude,Double Longitude) {
		String SOAP_ACTION = "http://tempuri.org/UpdatecontractDetailsCoordinates";

		String OPERATION_NAME = "UpdatecontractDetailsCoordinates";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);


		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ID");
		propertyinfo.setValue(objCheckin.GetID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 01 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude ");
		propertyinfo.setValue(objCheckin.getlatitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(objCheckin.getlongitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 03 */

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(0);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse();
			if (response.toString().equals("True"))
				return true;
			else
				return false;
		} catch (Exception e) {
			Log.d("Error In Update ", e.getMessage());
			return false;
		}// End try catch
	}// End Update block
	public Integer InsertContractDetailsLocation(CheckIn objCheckin) {

		String SOAP_ACTION = "http://tempuri.org/InsertContractDetailsLocation";

		String OPERATION_NAME = "InsertContractDetailsLocation";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("ID");
		propertyinfo.setValue(objCheckin.GetID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 00 */


		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude ");
		propertyinfo.setValue(objCheckin.getlatitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(objCheckin.getlongitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 03 */



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		boolean re = false;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int res;
		try {
			new MarshalFloat().register(envelope);
			new MarshalBase64().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
			Log.d("Error In Insert Save Pos Location  ", "" + response);
			res = 0;
			// CatchMsg.WriteMSG("Insert Save SubscriberLocation ",
			// exception.getMessage()
			// + "\n" + response.toString());
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End SubscriberLocation


	@Override
	public String toString() {

		// TODO Auto-generated method stub
		return this.NameAr;
	}
}// End Class
