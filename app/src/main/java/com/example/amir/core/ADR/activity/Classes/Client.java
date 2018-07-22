package com.example.amir.core.ADR.activity.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

//9 integer  --- 12 String
public class Client {
	private String NameEnglish = "";
	private String NameArabic = "";
	private int AgencyID = 0;
	private String Accn_no = "";
	private int TypeID = 0;
	private int ActivityID = 0;
	private int SalesPersonID = 0;
	private int CategoryID = 0;
	private int ID = 0;
	private int AdsCategoryID = 0;
	private String Contact = "";
	private String Telephone = "";
	private String Fax = "";
	private String Mobile = "";
	private String POBox = "";
	private String ZCode = "";
	private String Address = "";
	private String Note = "";
	private String CashContNo = "";// Allows Empty
	private String Email = "";
	// ------Date From Web Service------//
	private int UserID = 0;

	public void SetID(int id) {
		this.ID = id;
	}

	public void SetNameEn(String NameEn) {
		this.NameEnglish = NameEn;
	}

	public void SetNameArabic(String NameArabic) {
		this.NameArabic = NameArabic;
	}

	public void SetContact(String Contact) {
		this.Contact = Contact;
	}

	public void SetTelephone(String Telephone) {
		this.Telephone = Telephone;
	}

	public void SetFax(String Fax) {
		this.Fax = Fax;
	}

	public void SetMobile(String Mobile) {
		this.Mobile = Mobile;
	}

	public void SetPOBox(String POBox) {
		this.POBox = POBox;
	}

	public void SetZCode(String ZCode) {
		this.ZCode = ZCode;
	}

	public void SetAddress(String Address) {
		this.Address = Address;
	}

	public void SetNote(String Note) {
		this.Note = Note;
	}

	public void SetCashContNo(String CashContNo) {
		this.CashContNo = CashContNo;
	}

	public void SetEmail(String Email) {
		this.Email = Email;
	}

	public void SetAgencyID(int AgencyID) {
		this.AgencyID = AgencyID;
	}

	public void SetAccn_no(String Accn_no) {
		this.Accn_no = Accn_no;
	}

	public void SetTypeID(int TypeID) {
		this.TypeID = TypeID;
	}

	public void SetActivityID(int ActivityID) {
		this.ActivityID = ActivityID;
	}

	public void SetSalesPersonID(int SalesPersonID) {
		this.SalesPersonID = SalesPersonID;
	}

	public void SetCategoryID(int CategoryID) {
		this.CategoryID = CategoryID;
	}

	public void SetAdsCategoryID(int AdsCategoryID) {
		this.AdsCategoryID = AdsCategoryID;
	}

	public void SetUserID(int UserID) {
		this.UserID = UserID;
	}

	public String GetNameEn() {
		return this.NameEnglish;
	}

	public String GetNameArabic() {
		return this.NameArabic;
	}

	public String GetContact() {
		return this.Contact;
	}

	public String GetTelephone() {
		return this.Telephone;
	}

	public String GetFax() {
		return this.Fax;
	}

	public String GetMobile() {
		return this.Mobile;
	}

	public String GetPOBox() {
		return this.POBox;
	}

	public String GetZCode() {
		return this.ZCode;
	}

	public String GetAddress() {
		return this.Address;
	}

	public String GetNote() {
		return this.Note;
	}

	public String GetCashContNo() {
		return this.CashContNo;
	}

	public String GetEmail() {
		return this.Email;
	}

	public int GetId() {
		return this.ID;
	}

	public int GetAgencyID() {
		return this.AgencyID;
	}

	public String GetAccn_no() {
		return this.Accn_no;
	}

	public int GetTypeID() {
		return this.TypeID;
	}

	public int GetActivityID() {
		return this.ActivityID;
	}

	public int GetSalesPersonID() {
		return this.SalesPersonID;
	}

	public int GetCategoryID() {
		return this.CategoryID;
	}

	public int GetAdsCategoryID() {
		return this.AdsCategoryID;
	}

	public int GetUserID() {
		return this.UserID;
	}

	public Integer InsertClient(Client objClient) {
		String SOAP_ACTION = "http://tempuri.org/Insert_Client";

		String OPERATION_NAME = "Insert_Client";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("AccNo");
		propertyInfo.setValue(objClient.GetAccn_no());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ActivityID");
		propertyInfo.setValue(objClient.GetActivityID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("AgencyID");
		propertyInfo.setValue(objClient.GetAgencyID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("CategoryID");
		propertyInfo.setValue(objClient.GetCategoryID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("SalesPersonID");
		propertyInfo.setValue(objClient.GetSalesPersonID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("TypeID");
		propertyInfo.setValue(objClient.GetTypeID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("AdsCategoryID");
		propertyInfo.setValue(objClient.GetAdsCategoryID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

//		propertyInfo = new PropertyInfo();
//		propertyInfo.setName("UserID");
//		propertyInfo.setValue(objClient.GetUserID());
//		propertyInfo.setType(int.class);
//		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("NameEnglish");
		propertyInfo.setValue(objClient.GetNameEn());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("NameArabic");
		propertyInfo.setValue(objClient.GetNameArabic());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Address");
		propertyInfo.setValue(objClient.GetAddress());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

//		propertyInfo = new PropertyInfo();
//		propertyInfo.setName("CashContNo");
//		propertyInfo.setValue(objClient.GetCashContNo());
//		propertyInfo.setType(String.class);
//		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Contact");
		propertyInfo.setValue(objClient.GetContact());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Email");
		propertyInfo.setValue(objClient.GetEmail());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Fax");
		propertyInfo.setValue(objClient.GetFax());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Mobile");
		propertyInfo.setValue(objClient.GetMobile());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Note");
		propertyInfo.setValue(objClient.GetNote());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("POBox");
		propertyInfo.setValue(objClient.GetPOBox());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Telephone");
		propertyInfo.setValue(objClient.GetTelephone());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ZCode");
		propertyInfo.setValue(objClient.GetZCode());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

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

			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			new CatchMsg().WriteMSG("Client .Insert", exception.getMessage());
			return 0;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {
			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}

	// public boolean UpdateClient(Client objClient, int ClientID) {
	// String SOAP_ACTION = "http://tempuri.org/UpDate_Client";
	//
	// String OPERATION_NAME = "UpDate_Client";
	//
	// SoapObject request = new SoapObject(Connection.NAMESPACE,
	// OPERATION_NAME);
	//
	// PropertyInfo piAccn_no = new PropertyInfo();
	// piAccn_no.setName("Accn_no");
	// piAccn_no.setValue(objClient.GetAccn_no());
	// piAccn_no.setType(int.class);
	// request.addProperty(piAccn_no);
	//
	// PropertyInfo piActivityID = new PropertyInfo();
	// piActivityID.setName("ActivityID");
	// piActivityID.setValue(objClient.GetActivityID());
	// piActivityID.setType(int.class);
	// request.addProperty(piActivityID);
	//
	// PropertyInfo piAgencyID = new PropertyInfo();
	// piAgencyID.setName("AgencyID");
	// piAgencyID.setValue(objClient.GetAgencyID());
	// piAgencyID.setType(int.class);
	// request.addProperty(piAgencyID);
	//
	// PropertyInfo piCategoryID = new PropertyInfo();
	// piCategoryID.setName("CategoryID");
	// piCategoryID.setValue(objClient.GetCategoryID());
	// piCategoryID.setType(int.class);
	// request.addProperty(piCategoryID);
	//
	// PropertyInfo piSalesPersonID = new PropertyInfo();
	// piSalesPersonID.setName("SalesPersonID");
	// piSalesPersonID.setValue(objClient.GetSalesPersonID());
	// piSalesPersonID.setType(int.class);
	// request.addProperty(piSalesPersonID);
	//
	// PropertyInfo piTypeID = new PropertyInfo();
	// piTypeID.setName("TypeID");
	// piTypeID.setValue(objClient.GetTypeID());
	// piTypeID.setType(int.class);
	// request.addProperty(piTypeID);
	//
	// PropertyInfo piAdsCategoryID = new PropertyInfo();
	// piAdsCategoryID.setName("AdsCategoryID");
	// piAdsCategoryID.setValue(objClient.GetAdsCategoryID());
	// piAdsCategoryID.setType(int.class);
	// request.addProperty(piAdsCategoryID);
	//
	// PropertyInfo piUserID = new PropertyInfo();
	// piUserID.setName("UserID");
	// piUserID.setValue(objClient.GetUserID());
	// piUserID.setType(int.class);
	// request.addProperty(piUserID);
	//
	// PropertyInfo piNameEn = new PropertyInfo();
	// piNameEn.setName("NameEnglish");
	// piNameEn.setValue(objClient.GetNameEn());
	// piNameEn.setType(String.class);
	// request.addProperty(piNameEn);
	//
	// PropertyInfo NameAr = new PropertyInfo();
	// NameAr.setName("NameArabic");
	// NameAr.setValue(objClient.GetNameArabic());
	// NameAr.setType(String.class);
	// request.addProperty(NameAr);
	//
	// PropertyInfo piAddress = new PropertyInfo();
	// piAddress.setName("Address");
	// piAddress.setValue(objClient.GetAddress());
	// piAddress.setType(String.class);
	// request.addProperty(piAddress);
	//
	// PropertyInfo piCashContNo = new PropertyInfo();
	// piCashContNo.setName("CashContNo");
	// piCashContNo.setValue(objClient.GetCashContNo());
	// piCashContNo.setType(String.class);
	// request.addProperty(piCashContNo);
	//
	// PropertyInfo piContact = new PropertyInfo();
	// piContact.setName("Contact");
	// piContact.setValue(objClient.GetContact());
	// piContact.setType(String.class);
	// request.addProperty(piContact);
	//
	// PropertyInfo piEmail = new PropertyInfo();
	// piEmail.setName("Email");
	// piEmail.setValue(objClient.GetEmail());
	// piEmail.setType(String.class);
	// request.addProperty(piEmail);
	//
	// PropertyInfo piFax = new PropertyInfo();
	// piFax.setName("Fax");
	// piFax.setValue(objClient.GetFax());
	// piFax.setType(String.class);
	// request.addProperty(piFax);
	//
	// PropertyInfo piMobile = new PropertyInfo();
	// piMobile.setName("Mobile");
	// piMobile.setValue(objClient.GetMobile());
	// piMobile.setType(String.class);
	// request.addProperty(piMobile);
	//
	// PropertyInfo piNote = new PropertyInfo();
	// piNote.setName("Note");
	// piNote.setValue(objClient.GetNote());
	// piNote.setType(String.class);
	// request.addProperty(piNote);
	//
	// PropertyInfo piPOBox = new PropertyInfo();
	// piPOBox.setName("POBox");
	// piPOBox.setValue(objClient.GetPOBox());
	// piPOBox.setType(String.class);
	// request.addProperty(piPOBox);
	//
	// PropertyInfo piTelephone = new PropertyInfo();
	// piTelephone.setName("Telephone");
	// piTelephone.setValue(objClient.GetTelephone());
	// piTelephone.setType(String.class);
	// request.addProperty(piTelephone);
	//
	// PropertyInfo piZCode = new PropertyInfo();
	// piZCode.setName("ZCode");
	// piZCode.setValue(objClient.GetZCode());
	// piZCode.setType(String.class);
	// request.addProperty(piZCode);
	//
	// PropertyInfo piClientID = new PropertyInfo();
	// piClientID.setName("ID");
	// piClientID.setValue(ClientID);
	// piClientID.setType(int.class);
	// request.addProperty(piClientID);
	//
	// SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
	// SoapEnvelope.VER11);
	//
	// envelope.dotNet = true;
	// envelope.setOutputSoapObject(request);
	//
	// HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
	// Object response = null;
	//
	// AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
	// Connection.ADDRESS);
	//
	// try {
	// httpTransport.call(SOAP_ACTION, envelope);
	// response = envelope.getResponse().toString();
	// Log.d("Update  result ", "" + response + "");
	//
	// } catch (Exception exception) {
	// response = exception.toString();
	// Log.d("Error In UpDate Client ", "" + response);
	// return false;
	// }// End try Catch
	// if (response.equals("True"))
	// return true;
	// else
	// return false;
	// }

	// Select Client By ID
	public Client SelectClientByID(int ID) {
		Client ObjClient = new Client();
		String SOAP_ACTION = "http://tempuri.org/SelectClientByID";

		String OPERATION_NAME = "SelectClientByID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piID = new PropertyInfo();
		piID.setName("ID");
		piID.setValue(ID);
		piID.setType(int.class);
		request.addProperty(piID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);

		// boolean re = false;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {

			} else {
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {

					obj3 = (SoapObject) obj2.getProperty(i);
					ObjClient.SetAccn_no(obj3.getProperty(
							"Accn_no").toString());
					ObjClient.SetActivityID(Integer.valueOf(obj3.getProperty(
							"ActivityID").toString()));
					ObjClient.SetAddress(IsEmpty(obj3.getProperty("Address")
							.toString()));
					// ObjClient.SetAdsCategoryID(Integer.valueOf(obj3
					// .getProperty("AdsCategory").toString()));
					ObjClient.SetAgencyID(Integer.valueOf(obj3.getProperty(
							"AgencyID").toString()));
					ObjClient.SetID(Integer.valueOf(obj3.getProperty("ID")
							.toString()));
					ObjClient.SetNameEn(obj3.getProperty("NameEnglish")
							.toString());
					ObjClient.SetNameArabic(obj3.getProperty("NameArabic")
							.toString());
					ObjClient.SetTypeID(Integer.valueOf(obj3.getProperty(
							"TypeID").toString()));
					ObjClient.SetSalesPersonID(Integer.valueOf(obj3
							.getProperty("SalesPersonID").toString()));
					ObjClient.SetCategoryID(Integer.valueOf(obj3.getProperty(
							"CategoryID").toString()));
					ObjClient.SetContact(IsEmpty(obj3.getProperty("Contact")
							.toString()));
					ObjClient.SetTelephone(IsEmpty(obj3
							.getProperty("Telephone").toString()));
					ObjClient
							.SetFax(IsEmpty(obj3.getProperty("Fax").toString()));
					ObjClient.SetMobile(IsEmpty(obj3.getProperty("Mobile")
							.toString()));
					ObjClient.SetEmail(IsEmpty(obj3.getProperty("Email")
							.toString()));
					ObjClient.SetPOBox(IsEmpty(obj3.getProperty("POBox")
							.toString()));
					ObjClient.SetZCode(IsEmpty(obj3.getProperty("ZCode")
							.toString()));
					ObjClient.SetNote(IsEmpty(obj3.getProperty("Note")
							.toString()));
					break;
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Select Client By ID", e.getMessage());
			new CatchMsg().WriteMSG("Client. Select ", e.getMessage());

		}// End try Catch

		return ObjClient;
	}

	private String IsEmpty(Object value) {
		if (value.equals("anyType{}"))
			return "";
		else
			return value.toString();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NameEnglish;
	}
}
