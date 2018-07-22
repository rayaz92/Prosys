package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;


import com.example.amir.core.Distribution.alghadversion1.CatchMsg;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private int ID = 0;
	private boolean IsNew = false;
	private int EntryUserID = 0;
	private boolean IsPosted = false;
	private int ClientGroup = 0;
	private int ClientClassification = 0;
	private int ClientID = 0;
	private int ContractID = 0;
	
	private int DeviceID = 0;
	private int UserID = 0;
	private int BuildingNo = 0;
	private Double Latitude = 0.0;
	private Double Longtiude = 0.0;
	private String Notification = "";
	private String ClientName = "";
	private String ClientPhone = "";
	private String ClientMobile = "";
	private String ClientEmail = "";
	private String ClientNote = "";
	private String ClientNationalNo = "";
	private String ClientCompanyName = "";
	private String Address = "";
	private String AddressComments = "";
	private String EntryDate = "";
	private Boolean ClientBlock = false;
	
	
	public void setClientID(int ClientID) {
		this.ClientID = ClientID;
	}

	public void setBuildingNo(int BuildingNo) {
		this.BuildingNo = BuildingNo;
	}

	public int GetBuildingNo() {
		return this.BuildingNo;
	}

	public void setDeviceID(int DeviceID) {
		this.DeviceID = DeviceID;
	}

	public void setLatitude(Double Latitude) {
		this.Latitude = Latitude;
	}

	public Double GetLatitude() {
		return this.Latitude;
	}

	public void setLongtiude(Double Longtiude) {
		this.Longtiude = Longtiude;
	}

	public Double GetLongtiude() {
		return this.Longtiude;
	}

	public void setUserId(int UserId) {
		this.UserID = UserId;
	}
	
	public int GetUserId() {
		return this.UserID;
	}

	public int GetDeviceID() {
		return this.DeviceID;
	}

	public void setNotification(String Notification) {
		this.Notification = Notification;
	}

	public String GetNotification() {
		return this.Notification;
	}

	public void setContractID(int ContractID) {
		this.ContractID = ContractID;
	}

	public int getClientID() {
		return this.ClientID;
	}

	public int getContractID() {
		return this.ContractID;
	}

	public void setID(int Id) {
		this.ID = Id;
	}

	public void setIsNew(boolean IsNew) {
		this.IsNew = IsNew;
	}

	public void setEntryUserID(int EntryUserID) {
		this.EntryUserID = EntryUserID;
	}

	public void setIsPosted(boolean IsPosted) {
		this.IsPosted = IsPosted;
	}

	public void setClientGroup(int ClientGroup) {
		this.ClientGroup = ClientGroup;
	}

	public void setClientClassification(int ClientClassification) {
		this.ClientClassification = ClientClassification;
	}

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void setClientMobile(String ClientMobile) {
		this.ClientMobile = ClientMobile;
	}

	public void setClientPhone(String ClientPhone) {
		this.ClientPhone = ClientPhone;
	}

	public void setClientEmail(String ClientEmail) {
		this.ClientEmail = ClientEmail;
	}

	public void setClientNationalNo(String ClientNationalNo) {
		this.ClientNationalNo = ClientNationalNo;
	}

	public void setClientCompanyName(String ClientCompanyName) {
		this.ClientCompanyName = ClientCompanyName;
	}

	public void setClientNote(String ClientNote) {
		this.ClientNote = ClientNote;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public void setAddressComments(String AddressComments) {
		this.AddressComments = AddressComments;
	}

	public void setEntryDate(String EntryDate) {
		this.EntryDate = EntryDate;
	}
	
	public void ClientBlock(boolean ClientBlock) {
		this.ClientBlock = ClientBlock;
	}


	public int getID() {
		return this.ID;
	}

	public boolean getIsNew() {
		return this.IsNew;
	}

	public int getEntryUserID() {
		return this.EntryUserID;
	}

	public boolean getIsPosted() {
		return this.IsPosted;
	}

	public int getClientGroup() {
		return this.ClientGroup;
	}

	public int getClientClassification() {
		return this.ClientClassification;
	}

	public String getClientName() {
		return this.ClientName;
	}

	public String getClientMobile() {
		return this.ClientMobile;
	}

	public String getClientPhone() {
		return this.ClientPhone;
	}

	public String getClientEmail() {
		return this.ClientEmail;
	}

	public String getClientNationalNo() {
		return this.ClientNationalNo;
	}

	public String getClientCompanyName() {
		return this.ClientCompanyName;
	}

	public String getClientNote() {
		return this.ClientNote;
	}

	public String getAddress() {
		return this.Address;
	}

	public String getAddressComments() {
		return this.AddressComments;
	}

	public String getEntryDate() {
		return this.EntryDate;
	}
	
	public boolean getClientBlock() {
		return this.ClientBlock;
	}

	public List<Client> SelectClientByNameAndPhone(String Name, String Phone) {
		List<Client> list = null;
		String OPERATION_NAME = "PDASelectClientByNameAndPhone";

		String SOAP_ACTION = "http://tempuri.org/PDASelectClientByNameAndPhone";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		try {
			PropertyInfo pi = new PropertyInfo();
			pi.setName("ClientName");
			pi.setValue(Name);
			pi.setType(String.class);
			request.addProperty(pi);

			PropertyInfo pi2 = new PropertyInfo();
			pi2.setName("ClientPhone");
			pi2.setValue(Phone);
			pi2.setType(String.class);
			request.addProperty(pi2);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();
//			Log.d("result SelectClientByNameAndPhone ", " " + response + " ");
			if (response.toString().equals("anyType{}") || response == null) {
				// list = null;
			} else {
				list = new ArrayList<Client>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
//				Log.d("SelectClientByNameAndPhone ", "" + resultsRequestSOAP
//						+ "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Client objClient = new Client();
					obj3 = (SoapObject) obj2.getProperty(i);

					objClient.setID((Integer.parseInt(obj3.getProperty("ID")
							.toString())));// value
					// objClient.setBuildingNo((Integer.parseInt(obj3.getProperty(
					// "BuildingNo").toString())));
					objClient.setIsNew((Boolean.getBoolean(obj3.getProperty(
							"IsNew").toString())));// value
					objClient.setEntryUserID((Integer.parseInt(obj3
							.getProperty("EntryUserID").toString())));// value
					objClient.setIsPosted((Boolean.getBoolean(obj3.getProperty(
							"IsPosted").toString())));// value
					objClient.setClientGroup((Integer.parseInt(obj3
							.getProperty("ClientGroup").toString())));// value
					objClient.setClientClassification((Integer.parseInt(obj3
							.getProperty("ClientClassification").toString())));// value
					objClient.setClientName(String.valueOf(IsEmpty(obj3
							.getProperty("ClientName").toString())));
					objClient.setClientMobile(String.valueOf(IsEmpty(obj3
							.getProperty("ClientMobile").toString())));
					objClient.setClientPhone(String.valueOf(IsEmpty(obj3
							.getProperty("ClientPhone").toString())));
					objClient.setClientEmail(String.valueOf(IsEmpty(obj3
							.getProperty("ClientEmail").toString())));
					objClient.setClientNationalNo(String.valueOf(IsEmpty(obj3
							.getProperty("ClientNationalNo").toString())));
					objClient.setClientCompanyName(String.valueOf(IsEmpty(obj3
							.getProperty("ClientCompanyName").toString())));
					objClient.setClientNote(String.valueOf(IsEmpty(obj3
							.getProperty("ClientNote").toString())));
					objClient.setEntryDate(String.valueOf(IsEmpty(obj3
							.getProperty("EntryDate").toString())));
					objClient.setAddress(String.valueOf(IsEmpty(obj3
							.getProperty("Address").toString())));
					objClient.setAddressComments(String.valueOf(IsEmpty(obj3
							.getProperty("AddressComments").toString())));
					objClient.setLatitude((Double.parseDouble(obj3
							.getProperty("Latitude").toString())));// value
					objClient.setLongtiude((Double.parseDouble(obj3
							.getProperty("Longitude").toString())));// value
					list.add(objClient);
				}// End for
			}// End Else
		}// End try
		catch (Exception e) {
//			Log.d("Error in SelectClientByNameAndPhone ", e.getMessage());
			// CatchMsg.WriteMSG("Client Profile ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;

	}// End SelectClientByNameAndPhone

	// public int InsertRemoteNotification(Client Notification, int DeviceID) {
	// String OPERATION_NAME = "InsertClientRemoteNotification";
	//
	// String SOAP_ACTION = "http://tempuri.org/PDAInsertClient";
	//
	// SoapObject request = new SoapObject(Connection.NAMESPACE,
	// OPERATION_NAME);
	// SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
	// SoapEnvelope.VER11);
	// envelope.setOutputSoapObject(request);
	// envelope.dotNet = true;
	// envelope.implicitTypes = true;
	// HttpTransportSE androidHttpTransport = new HttpTransportSE(
	// Connection.ADDRESS);
	// androidHttpTransport.debug = true;
	//
	// PropertyInfo propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientName");
	// propertyInfo.setValue(Notification.getClientName());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientPhone");
	// propertyInfo.setValue(Notification.getClientPhone());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientMobile");
	// propertyInfo.setValue(Notification.getClientMobile());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientNote");
	// propertyInfo.setValue(Notification.getClientNote());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientEmail");
	// propertyInfo.setValue(Notification.getClientEmail());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientCompanyName");
	// propertyInfo.setValue(Notification.getClientCompanyName());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientNationalNo");
	// propertyInfo.setValue(Notification.getClientNationalNo());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("IsNew");
	// propertyInfo.setValue(Notification.getIsNew());
	// propertyInfo.setType(Boolean.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("DeviceID");
	// propertyInfo.setValue(Notification.GetDeviceID());
	// propertyInfo.setType(int.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("EntryUserID");
	// propertyInfo.setValue(Notification.GetUserId());
	// propertyInfo.setType(int.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("IsPosted");
	// propertyInfo.setValue(Notification.getIsPosted());
	// propertyInfo.setType(Boolean.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ClientID");
	// propertyInfo.setValue(Notification.getClientID());
	// propertyInfo.setType(int.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("ContractID");
	// propertyInfo.setValue(Notification.getContractID());
	// propertyInfo.setType(int.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("RemoteNotification");
	// propertyInfo.setValue(Notification.GetNotification());
	// propertyInfo.setType(String.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("Latitude");
	// propertyInfo.setValue(Notification.GetLatitude());
	// propertyInfo.setType(double.class);
	// request.addProperty(propertyInfo);
	//
	// propertyInfo = new PropertyInfo();
	// propertyInfo.setName("Longitude");
	// propertyInfo.setValue(Notification.GetLongtiude());
	// propertyInfo.setType(double.class);
	// request.addProperty(propertyInfo);
	// // androidHttpTransport.call(SOAP_ACTION, envelope);
	// // SoapObject response = (SoapObject) envelope.getResponse();
	//
	// envelope.setOutputSoapObject(request);
	//
	// HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
	// Object response = null;
	// int res = 0;
	// // AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
	// // Connection.ADDRESS);
	// try {
	// httpTransport.call(SOAP_ACTION, envelope);
	// response = envelope.getResponse().toString();
	// // Log.d("Insert Client  ", "" + response + "");
	// } catch (Exception exception) {
	// response = exception.toString();
	// Log.d("Error In Insert Notification   ", exception.getMessage());
	// CatchMsg.WriteMSG("Notification response Result ",
	// response.toString());
	// res = 0;
	// }// End try Catch
	//
	// if (Integer.valueOf(response.toString()) > 0) {
	//
	// res = Integer.valueOf(response.toString());
	// } else {
	// res = 0;
	// }
	// return res;
	// }// End Function

	public int InsertClient(Client objClient, String DeviceID,
                            String RemoteNotification) {
		String OPERATION_NAME = "PDAInsertClient";

		String SOAP_ACTION = "http://tempuri.org/PDAInsertClient";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientName");
		propertyInfo.setValue(objClient.getClientName());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientMobile");
		propertyInfo.setValue(objClient.getClientMobile());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientPhone");
		propertyInfo.setValue(objClient.getClientPhone());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientEmail");
		propertyInfo.setValue(objClient.getClientEmail());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientNationalNo");
		propertyInfo.setValue(objClient.getClientNationalNo());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientCompanyName");
		propertyInfo.setValue(objClient.getClientCompanyName());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientClassification");
		propertyInfo.setValue(objClient.getClientClassification());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientNote");
		propertyInfo.setValue(objClient.getClientNote());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("IsNew");
		propertyInfo.setValue(objClient.getIsNew());
		propertyInfo.setType(boolean.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("DeviceID");
		propertyInfo.setValue(DeviceID);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("EntryUserID");
		propertyInfo.setValue(EntryUserID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("IsPosted");
		propertyInfo.setValue(objClient.getIsPosted());
		propertyInfo.setType(boolean.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientID");
		propertyInfo.setValue(objClient.getClientID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ContractID");
		propertyInfo.setValue(objClient.getContractID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

//		propertyInfo = new PropertyInfo();
//		propertyInfo.setName("Latitude");
//		propertyInfo.setValue(objClient.GetLatitude());
//		propertyInfo.setType(string.class);
//		request.addProperty(propertyInfo);/* 10 */
//
//		propertyInfo = new PropertyInfo();
//		propertyInfo.setName("Longitude");
//		propertyInfo.setValue(objClient.GetLongtiude());
//		propertyInfo.setType(string.class);
//		request.addProperty(propertyInfo);/* 11 */

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("RemoteNotification");
		propertyInfo.setValue(RemoteNotification);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// SoapObject response = (SoapObject) envelope.getResponse();

		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		int res = 0;
		// AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
		// Connection.ADDRESS);
		try {
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse().toString();
			// Log.d("Insert Client  ", "" + response + "");
		} catch (Exception exception) {
			response = exception.toString();
//			Log.d("Error In Insert Client   ", exception.getMessage());
			CatchMsg.WriteMSG("Client response Result ", response.toString());
			res = 0;
		}// End try Catch

		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End Function

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.toString();
	}// End To String

	private Object IsEmpty(Object Obj) {

		if (Obj.equals("anyType{}")) {
			return " Empty ";
		}// End if
		else
			return Obj;
	}// End Is Empty
}// End Client
