package com.example.amir.core.ADR.activity.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Log;

public class ContractPublishDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 1 */private int ID = 0;
	/* 2 */private int ContractID = 0;
	/* 3 */private int IssueID = 0;
	/* 4 */private int StatusID = 0;
	/* 5 */private int PageNo = 0;
	/* 6 */private double Cm = 0.0;
	/* 7 */private int Col = 0;
	/* 8 */private int SCm = 0;
	/* 9 */private int SCol = 0;
	/* 10 */private double GrossTotal = 0.0;
	/* 11 */private double NetAmount = 0.0;
	/* 12 */private double DiscountPer = 0.0;
	/* 13 */private double DiscountAmount = 0.0;
	/* 14 */private double PressTaxPer = 0.0;
	/* 15 */private double PressTaxAmount = 0.0;
	/* 16 */private double SalesTaxPer = 0.0;
	/* 17 */private double SalesTaxAmount = 0.0;
	/* 18 */private double CommisPer = 0.0;
	/* 19 */private double CommisAmount = 0.0;
	/* 20 */private double CRNotePer = 0.0;
	/* 21 */private double CRNote = 0.0;
	/* 22 */private String subject = "";
	/* 23 */private int detailedPageTypeID = 0;
	/* 24 */private int Page_Type = 0;

	// fOR UPDATE
	private String Paid = "";
	private double IssuePrice = 0.0;
	private String StatusName = "";
	private String PageName = "";
	private double PagePrice = 0.0;
	private String IssueDate = "";

	public void SetIssueDate(String IssueDate) {
		this.IssueDate = IssueDate;
	}

	public String GetIssueDate() {
		return this.IssueDate;
	}

	public void SetPagePrice(double PagePrice) {
		this.PagePrice = PagePrice;
	}

	public double GetPagePrice() {
		return this.PagePrice;
	}

	public void SetPageName(String string) {
		this.PageName = string;
	}

	public String GetPageName() {
		return this.PageName;
	}

	public void SetStatusName(String StatusName) {
		this.StatusName = StatusName;
	}

	public String GetStatusName() {
		return this.StatusName;
	}

	public void SetIssuePrice(Double IssuePrice) {
		this.IssuePrice = IssuePrice;
	}

	public double GetIssuePrice() {
		return this.IssuePrice;
	}

	public void SetPaid(String Paid) {
		this.Paid = Paid;
	}

	public String GetPaid() {
		return this.Paid;
	}

	public void Setsubject(String subject) {
		this.subject = subject;
	}

	public String Getsubject() {
		return this.subject;
	}

	// 10 integer
	public void SetID(int id) {
		this.ID = id;
	}

	public void SetContractID(int ContractID) {
		this.ContractID = ContractID;
	}

	public void SetIssueID(int IssueID) {
		this.IssueID = IssueID;
	}

	public void SetStatusID(int StatusID) {
		this.StatusID = StatusID;
	}

	public void SetPageNo(int PageNo) {
		this.PageNo = PageNo;
	}

	public void SetCol(int Col) {
		this.Col = Col;
	}

	public void SetSCm(int SCm) {
		this.SCm = SCm;
	}

	public void SetSCol(int SCol) {
		this.SCol = SCol;
	}

	public void SetdetailedPageTypeID(int detailedPageTypeID) {
		this.detailedPageTypeID = detailedPageTypeID;
	}

	public void SetPage_Type(int Page_Type) {
		this.Page_Type = Page_Type;
	}

	// 10 get integer
	public int GetID() {
		return this.ID;
	}

	public int GetStatusID() {
		return this.StatusID;
	}

	public int GetSCol() {
		return this.SCol;
	}

	public int GetSCm() {
		return this.SCm;
	}

	public int GetPageNo() {
		return this.PageNo;
	}

	public int GetPage_Type() {
		return this.Page_Type;
	}

	public int GetIssueID() {
		return this.IssueID;
	}

	public int GetdetailedPageTypeID() {
		return this.detailedPageTypeID;
	}

	public int GetContractID() {
		return this.ContractID;
	}

	public int GetCol() {
		return this.Col;
	}

	// 13 double
	public void SetGrossTotal(double GrossTotal) {
		this.GrossTotal = GrossTotal;
	}

	public void SetCm(double Cm) {
		this.Cm = Cm;
	}

	public void SetCommisAmount(double CommisAmount) {
		this.CommisAmount = CommisAmount;
	}

	public void SetCommisPer(double CommisPer) {
		this.CommisPer = CommisPer;
	}

	public void SetCRNote(double CRNote) {
		this.CRNote = CRNote;
	}

	public void SetCRNotePer(double CRNotePer) {
		this.CRNotePer = CRNotePer;
	}

	public void SetDiscountAmount(double DiscountAmount) {
		this.DiscountAmount = DiscountAmount;
	}

	public void SetDiscountPer(double DiscountPer) {
		this.DiscountPer = DiscountPer;
	}

	public void SetNetAmount(double NetAmount) {
		this.NetAmount = NetAmount;
	}

	public void SetPressTaxAmount(double PressTaxAmount) {
		this.PressTaxAmount = PressTaxAmount;
	}

	public void SetPressTaxPer(double PressTaxPer) {
		this.PressTaxPer = PressTaxPer;
	}

	public void SetSalesTaxAmount(double SalesTaxAmount) {
		this.SalesTaxAmount = SalesTaxAmount;
	}

	public void SetSalesTaxPer(double SalesTaxPer) {
		this.SalesTaxPer = SalesTaxPer;
	}

	// 13 get double
	public double GetGrossTotal() {
		return this.GrossTotal;
	}

	public double GetCm() {
		return this.Cm;
	}

	public double GetCommisAmount() {
		return this.CommisAmount;
	}

	public double GetCommisPer() {
		return this.CommisPer;
	}

	public double GetCRNote() {
		return this.CRNote;
	}

	public double GetCRNotePer() {
		return this.CRNotePer;
	}

	public double GetDiscountAmount() {
		return this.DiscountAmount;
	}

	public double GetDiscountPer() {
		return this.DiscountPer;
	}

	public double GetNetAmount() {
		return this.NetAmount;
	}

	public double GetPressTaxAmount() {
		return this.PressTaxAmount;
	}

	public double GetPressTaxPer() {
		return this.PressTaxPer;
	}

	public double GetSalesTaxAmount() {
		return this.SalesTaxAmount;
	}

	public double GetSalesTaxPer() {
		return this.SalesTaxPer;
	}

	public Integer InsertContractPublishDetails(
			ContractPublishDetails ObjContractPublishDetails , int PublicationID) {
		String SOAP_ACTION = "http://tempuri.org/Android_InsertContractPublishDetail";

		String OPERATION_NAME = "Android_InsertContractPublishDetail";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("subject");
		propertyInfo.setValue(ObjContractPublishDetails.Getsubject());
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Col");
		propertyInfo.setValue(ObjContractPublishDetails.GetCol());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ContractID");
		propertyInfo.setValue(ObjContractPublishDetails.GetContractID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("detailedPageTypeID");
		propertyInfo.setValue(ObjContractPublishDetails.GetdetailedPageTypeID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("IssueID");
		propertyInfo.setValue(ObjContractPublishDetails.GetIssueID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Page_Type");
		propertyInfo.setValue(ObjContractPublishDetails.GetPage_Type());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();

		propertyInfo.setName("PageNo");
		propertyInfo.setValue(ObjContractPublishDetails.GetPageNo());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("SCm");
		propertyInfo.setValue(ObjContractPublishDetails.GetSCm());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("SCol");
		propertyInfo.setValue(ObjContractPublishDetails.GetSCol());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("StatusID");
		propertyInfo.setValue(5);// ObjContractPublishDetails.GetStatusID());
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("PublicationID");
		propertyInfo.setValue(PublicationID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		// Integer ID Not Here
		// 13 double

		request.addProperty("Cm", ObjContractPublishDetails.GetCm());
		request.addProperty("CommisAmount",ObjContractPublishDetails.GetCommisAmount());
		request.addProperty("CommisPer",ObjContractPublishDetails.GetCommisPer());
		request.addProperty("CRNote", ObjContractPublishDetails.GetCRNote());
		request.addProperty("CRNotePer",ObjContractPublishDetails.GetCRNotePer());
		request.addProperty("DiscountAmount",ObjContractPublishDetails.GetDiscountAmount());
		request.addProperty("DiscountPer",ObjContractPublishDetails.GetDiscountPer());
		request.addProperty("GrossTotal",ObjContractPublishDetails.GetGrossTotal());
		request.addProperty("NetAmount",ObjContractPublishDetails.GetNetAmount());
		request.addProperty("PressTaxAmount",ObjContractPublishDetails.GetPressTaxAmount());
		request.addProperty("PressTaxPer",ObjContractPublishDetails.GetPressTaxPer());
		request.addProperty("SalesTaxAmount",ObjContractPublishDetails.GetSalesTaxAmount());
		request.addProperty("SalesTaxPer",ObjContractPublishDetails.GetSalesTaxPer());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		int result;
		try {
			new MarshalFloat().register(envelope);
			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
//			Log.d("Insert Contract publish  result ", "" + response + "");
		} catch (Exception e) {
			CatchMsg.WriteMSG("Insert  Contract Publish  ", e.getMessage());
//			Log.d("Error In Insert Save Contract  ", e.getMessage());
			result = 0;
			return result;
		}// End Try Catch
		if (Integer.valueOf(response.toString()) > 0) {
			result = Integer.valueOf(response.toString());
		} else {
			result = 0;
		}
		return result;
	}

	public List<ContractPublishDetails> GetContractPublishDetailsByContractID(
			int ContractID) {
		String SOAP_ACTION = "http://tempuri.org/Android_GetContractPublishDetail";

		String OPERATION_NAME = "Android_GetContractPublishDetail";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		PropertyInfo piContractID = new PropertyInfo();
		piContractID.setName("ContractID");
		piContractID.setValue(ContractID);
		piContractID.setType(int.class);
		request.addProperty(piContractID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		ContractPublishDetails objContractPublishDetails;
		List<ContractPublishDetails> list = null;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<ContractPublishDetails>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objContractPublishDetails = new ContractPublishDetails();
					obj3 = (SoapObject) obj2.getProperty(i);
					objContractPublishDetails.SetID(Integer.valueOf(obj3
							.getProperty("id").toString()));
					objContractPublishDetails.SetStatusID(Integer.parseInt(obj3
							.getProperty("StatusID").toString()));
					objContractPublishDetails.SetdetailedPageTypeID(Integer
							.valueOf(obj3.getProperty("PageID").toString()));
					objContractPublishDetails.SetIssueID(Integer.valueOf(obj3
							.getProperty("IssueID").toString()));
					objContractPublishDetails.Setsubject(obj3.getProperty(
							"Issuesubject").toString());
					objContractPublishDetails.SetCm(Double.valueOf(obj3
							.getProperty("Cm").toString()));
					objContractPublishDetails.SetCol(Integer.valueOf(obj3
							.getProperty("Col").toString()));
					objContractPublishDetails.SetSCm(Integer.valueOf(obj3
							.getProperty("SCm").toString()));

					objContractPublishDetails.SetSCol(Integer.valueOf(obj3
							.getProperty("SCol").toString()));
					objContractPublishDetails.SetPageNo(Integer.valueOf(obj3
							.getProperty("PageNo").toString()));

					objContractPublishDetails.SetNetAmount(Double.valueOf(obj3
							.getProperty("NetAmount").toString()));
					objContractPublishDetails.SetGrossTotal(Double.valueOf(obj3
							.getProperty("GrossTotal").toString()));
					objContractPublishDetails.SetPaid(obj3.getProperty("Paid")
							.toString());
					objContractPublishDetails.SetIssuePrice(Double.valueOf(obj3
							.getProperty("IssuePrice").toString()));
					objContractPublishDetails.SetStatusName(obj3.getProperty(
							"StatusName").toString());
					objContractPublishDetails.SetPageName(obj3.getProperty(
							"PageName").toString());
					objContractPublishDetails.SetPagePrice(Double.valueOf(obj3
							.getProperty("PagePrice").toString()));
					objContractPublishDetails.SetIssueDate(obj3.getProperty(
							"IssueDate").toString());
					list.add(objContractPublishDetails);
				}// End for
				return list;
			}// End try
		} catch (Exception e) {
//			Log.d("Error Contract Publish get Contract By ID ", e.getMessage());
		
			CatchMsg.WriteMSG("Contract Publish Get By ID", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	// public Boolean UpdateContractPublicsh(
	// ContractPublishDetails objContractPublishDetails, int UserID) {
	// String SOAP_ACTION =
	// "http://tempuri.org/AndroidUpdate_ContractPublishDetails";
	//
	// String OPERATION_NAME = "AndroidUpdate_ContractPublishDetails";
	//
	// SoapObject request = new SoapObject(Connection.NAMESPACE,
	// OPERATION_NAME);
	//
	// PropertyInfo piSubject = new PropertyInfo();
	// piSubject.setName("Subject");
	// piSubject.setValue(objContractPublishDetails.Getsubject());
	// piSubject.setType(String.class);
	// request.addProperty(piSubject);
	//
	// PropertyInfo piContractPublishID = new PropertyInfo();
	// piContractPublishID.setName("ID");
	// piContractPublishID.setValue(objContractPublishDetails.GetID());
	// piContractPublishID.setType(int.class);
	// request.addProperty(piContractPublishID);
	//
	// PropertyInfo piIssueID = new PropertyInfo();
	// piIssueID.setName("IssueID");
	// piIssueID.setValue(objContractPublishDetails.GetIssueID());
	// piIssueID.setType(int.class);
	// request.addProperty(piIssueID);
	//
	// PropertyInfo piPageNo = new PropertyInfo();
	// piPageNo.setName("PageNo");
	// piPageNo.setValue(objContractPublishDetails.GetPageNo());
	// piPageNo.setType(int.class);
	// request.addProperty(piPageNo);
	//
	// PropertyInfo PiModifyUser = new PropertyInfo();
	// PiModifyUser.setValue(UserID);
	// PiModifyUser.setName("ModifyUser");
	// PiModifyUser.setType(int.class);
	// request.addProperty(PiModifyUser);
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
	// } catch (Exception exception) {
	// response = exception.toString();
	// Log.d("Error In UpDate Contract Publish ", "" + response);
	// return false;
	// }// End try Catch
	// if (response.equals("True"))
	// return true;
	// else
	// return false;
	// }// end Update Contract Publish Details

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.toString();
	}
}// End Class Contract Publish Details
