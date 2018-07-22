package com.example.amir.core.ADR.activity.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir on 9/18/2017.
 */

public class Issue {

    private Integer ID = 0;
    private String IssueDate = "" ;
    private Integer IssueNo = 0;
    private Boolean Published = false ;


    private Integer SerialNo = 0;
    private String ClientName = "";
    private Integer ClientID = 0;
    private String AgencyName = "";
    private Integer PageNo = 0;
    private String Size = "";
    private Double NetAmount = 0.0;
    private Integer StatusID = 0;
    private String StatusName = "";
    private Integer IssueID = 0;
    private String AdsText = "";
    private Integer SalesID = 0;
    private Integer ContractID = 0 ;
    private Integer ContractPublishedDetailID = 0;
    private Integer ContractTypeID = 0 ;
    private Integer DesignID = 0;
    private Integer DesignStatusID = 0;
    private String DesignStatusName = "";
    private Boolean DesignAlert = false ;
    private Boolean IsSalesApprove = false ;
    private Integer IssueCount = 0;
    private Integer IssuePublishedCount = 0;
    private Integer IssueNotPublishedCount = 0 ;
    private String PaymentTypeName = "";
    private Double Cm = 0.0;
    private Double Col = 0.0;
    private Integer PublicationID = 0;
    private String PublicationName = "";
    private String PublicationFoldername = "";
    private String PaymentFoldername = "";
    private String URL = "";
    private String Path = "";
    private String AlyaumPath = "";
    private Boolean IsDefaultPath = false   ;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public Integer getIssueNo() {
        return IssueNo;
    }

    public void setIssueNo(Integer issueNo) {
        IssueNo = issueNo;
    }

    public List<Issue> getIssueTest() {
        List<Issue> list = null;
        String OPERATION_NAME = "Android_GetIssueNew";
        String SOAP_ACTION = "http://tempuri.org/Android_GetIssueNew";
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
                list = new ArrayList<Issue>();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Issue item = new Issue();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.setID(Integer.parseInt(obj3.getProperty("ID")
                            .toString()));
                    item.setIssueDate(obj3.getProperty("Description")
                            .toString());
                    list.add(item);
                }// End for
            }// End try
        } catch (Exception e) {
            Log.d("Error In AdsType ", e.getMessage());

            CatchMsg.WriteMSG("AdsType", e.getMessage());
            list = null;
        }// End try Catch
        return list;
    }


    public List<Issue> getIssue(Integer PublicationID , Boolean IsAll) {
        List<Issue> list = null;
        String OPERATION_NAME = "Android_GetIssueNew";

        String SOAP_ACTION = "http://tempuri.org/Android_GetIssueNew";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("PublicationID");
            pi.setValue(PublicationID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piIsAll = new PropertyInfo();
            piIsAll.setName("IsAll");
            piIsAll.setValue(IsAll);
            piIsAll.setType(Boolean.class);
            request.addProperty(piIsAll);


            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.toString().equals("anyType{}") || response == null) {
                list = null;
            } else {
                list = new ArrayList<Issue>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                Log.d("R", "" + resultsRequestSOAP + "");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Issue item = new Issue();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.setID(Integer.parseInt(obj3.getProperty(
                            "ID").toString()));
                    item.setIssueDate(obj3.getProperty(
                            "IssueDate").toString().substring(0 , obj3.getProperty(
                            "IssueDate").toString().lastIndexOf("T")));

                    item.setIssueNo(Integer.parseInt(obj3.getProperty(
                            "IssueNo").toString()));

                    item.setPublished(Boolean.parseBoolean(obj3.getProperty(
                            "Published").toString()));

                    list.add(item);
                }// End for
            }// End try
        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());
            list = null;
            return list;
        }// End try Catch
        return list;
    }

    public List<Issue> GetIssueContract(Integer PublicationID , Integer IssueID ,
                                        Integer PartTypeID , String StatusID,Integer SalesID) {
        List<Issue> list = null;
        String OPERATION_NAME = "Android_SelectIssueContractByPublicationIDAndIssueIDAndPartTypeIDAndSalesID";

        String SOAP_ACTION = "http://tempuri.org/Android_SelectIssueContractByPublicationIDAndIssueIDAndPartTypeIDAndSalesID";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("PublicationID");
            pi.setValue(PublicationID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piIssueID = new PropertyInfo();
            piIssueID.setName("IssueID");
            piIssueID.setValue(IssueID);
            piIssueID.setType(Integer.class);
            request.addProperty(piIssueID);

            PropertyInfo piPartTypeID = new PropertyInfo();
            piPartTypeID.setName("PartTypeID");
            piPartTypeID.setValue(PartTypeID);
            piPartTypeID.setType(Integer.class);
            request.addProperty(piPartTypeID);

            PropertyInfo piStatusID = new PropertyInfo();
            piStatusID.setName("StatusID");
            piStatusID.setValue(StatusID);
            piStatusID.setType(String.class);
            request.addProperty(piStatusID);


            PropertyInfo piSalesID = new PropertyInfo();
            piSalesID.setName("SalesID");
            piSalesID.setValue(SalesID);
            piSalesID.setType(Integer.class);
            request.addProperty(piSalesID);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.toString().equals("anyType{}") || response == null) {
                list = null;
            } else {
                list = new ArrayList<Issue>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                Log.d("R", "" + resultsRequestSOAP + "");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    Issue item = new Issue();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.setSerialNo(Integer.parseInt(obj3.getProperty(
                            "SerialNo").toString()));
                    item.setClientID(Integer.parseInt(obj3.getProperty(
                            "ClientID").toString()));
                    item.setClientName(obj3.getProperty(
                            "ClientName").toString());
                    item.setAgencyName(obj3.getProperty(
                            "AgencyName").toString());
                    item.setPageNo(Integer.parseInt(obj3.getProperty(
                            "PageNo").toString()));
                    item.setSize(obj3.getProperty(
                            "Size").toString());
                    item.setNetAmount(Double.parseDouble(obj3.getProperty(
                            "NetAmount").toString()));
                    item.setStatusID(Integer.parseInt(obj3.getProperty(
                            "StatusID").toString()));
                    item.setStatusName(obj3.getProperty(
                            "StatusName").toString());
                    item.setIssueID(Integer.parseInt(obj3.getProperty(
                            "IssueID").toString()));
                    item.setIssueDate(obj3.getProperty(
                            "IssueDate").toString());
                    item.setAdsText(obj3.getProperty(
                            "AdsText").toString());
                    item.setSalesID(Integer.parseInt(obj3.getProperty(
                            "SalesID").toString()));

                    item.setContractID(Integer.parseInt(obj3.getProperty(
                            "ContractID").toString()));

                    item.setContractPublishedDetailID(Integer.parseInt(obj3.getProperty(
                            "ContractPublishedDetailID").toString()));

                    item.setContractTypeID(Integer.parseInt(obj3.getProperty(
                            "ContractTypeID").toString()));

                    item.setDesignID(Integer.parseInt(obj3.getProperty(
                            "DesignID").toString()));
                    item.setDesignStatusID(Integer.parseInt(obj3.getProperty(
                            "DesignStatusID").toString()));
                    item.setDesignStatusName(obj3.getProperty(
                            "DesignStatusName").toString());

                    item.setDesignAlert(Boolean.parseBoolean(obj3.getProperty(
                            "DesignAlert").toString()));

                    item.setSalesApprove(Boolean.parseBoolean(obj3.getProperty(
                            "IsSalesApprove").toString()));

                    item.setIssueCount(Integer.parseInt(obj3.getProperty(
                            "IssueCount").toString()));
                    item.setIssuePublishedCount(Integer.parseInt(obj3.getProperty(
                            "IssuePublishedCount").toString()));
                    item.setIssueNotPublishedCount(Integer.parseInt(obj3.getProperty(
                            "IssueNotPublishedCount").toString()));

                    item.setPaymentTypeName(obj3.getProperty(
                            "PaymentName").toString());

                    item.setCm(Double.parseDouble(obj3.getProperty(
                            "Cm").toString()));
                    item.setCol(Double.parseDouble(obj3.getProperty(
                            "Col").toString()));

                    item.setPublicationID(Integer.parseInt(obj3.getProperty(
                            "PublicationID").toString()));
                    item.setPublicationName(obj3.getProperty(
                            "PublicationName").toString());
                    item.setPublicationFoldername(obj3.getProperty(
                            "PublicationFoldername").toString());
                    item.setPaymentFoldername(obj3.getProperty(
                            "PaymentFoldername").toString());

                    item.setIssueNo(Integer.parseInt(obj3.getProperty(
                            "IssueNo").toString()));

                    item.setURL(obj3.getProperty(
                            "URL").toString());

                    item.setPath(obj3.getProperty(
                            "Path").toString());

                    item.setAlyaumPath(obj3.getProperty(
                            "AlyaumPath").toString());

                    item.setDefaultPath(Boolean.parseBoolean(obj3.getProperty(
                            "IsDefaultPath").toString()));
                    //amir
                    list.add(item);
                }// End for
            }// End try
        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());
            list = null;
            return list;
        }// End try Catch
        return list;
    }

    public Integer getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(Integer serialNo) {
        SerialNo = serialNo;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String agencyName) {
        AgencyName = agencyName;
    }

    public Integer getPageNo() {
        return PageNo;
    }

    public void setPageNo(Integer pageNo) {
        PageNo = pageNo;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public Double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(Double netAmount) {
        NetAmount = netAmount;
    }

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public Integer getIssueID() {
        return IssueID;
    }

    public void setIssueID(Integer issueID) {
        IssueID = issueID;
    }

    public String getAdsText() {
        return AdsText;
    }

    public void setAdsText(String adsText) {
        AdsText = adsText;
    }

    public Integer getSalesID() {
        return SalesID;
    }

    public void setSalesID(Integer salesID) {
        SalesID = salesID;
    }

    public Boolean UpdateSalesApprove(Integer ContractPublishedDetailID) {
        List<Issue> list = null;
        String OPERATION_NAME = "UpdateSalesApprove";

        String SOAP_ACTION = "http://tempuri.org/UpdateSalesApprove";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ContractPublishedDetailID);
            pi.setType(Integer.class);
            request.addProperty(pi);

//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapObject response = (SoapObject) envelope.getResponse();

            envelope.encodingStyle = SoapEnvelope.ENC;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object response = (Object) envelope.getResponse();
            if (response.toString().equals("true"))
                return true;
            else
                return false;

        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());

            return false;
        }// End try Catch
    }

    public Integer getContractID() {
        return ContractID;
    }

    public void setContractID(Integer contractID) {
        ContractID = contractID;
    }

    public Integer getContractPublishedDetailID() {
        return ContractPublishedDetailID;
    }

    public void setContractPublishedDetailID(Integer contractPublishedDetailID) {
        ContractPublishedDetailID = contractPublishedDetailID;
    }

    public Integer getContractTypeID() {
        return ContractTypeID;
    }

    public void setContractTypeID(Integer contractTypeID) {
        ContractTypeID = contractTypeID;
    }

    public Boolean UpdateContractPublishDetailStatus(Integer ContractPublishedDetailID ,Integer StatusID) {
        String OPERATION_NAME = "UpdateContractPublishDetailStatus";

        String SOAP_ACTION = "http://tempuri.org/UpdateContractPublishDetailStatus";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ContractPublishedDetailID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piStatusID = new PropertyInfo();
            piStatusID.setName("StatusID");
            piStatusID.setValue(StatusID);
            piStatusID.setType(Integer.class);
            request.addProperty(piStatusID);

//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapObject response = (SoapObject) envelope.getResponse();

            envelope.encodingStyle = SoapEnvelope.ENC;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object response = (Object) envelope.getResponse();
            if (response.toString().equals("true"))
                return true;
            else
                return false;

        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());

            return false;
        }// End try Catch
    }

    public Boolean getPublished() {
        return Published;
    }

    public void setPublished(Boolean published) {
        Published = published;
    }

    public Boolean Android_UpdateContractPublishedDetailAdsText(Integer ContractPublishedDetailID ,String AdsText) {
        String OPERATION_NAME = "Android_UpdateContractPublishedDetailAdsText";

        String SOAP_ACTION = "http://tempuri.org/Android_UpdateContractPublishedDetailAdsText";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ContractPublishedDetailID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piAdsText = new PropertyInfo();
            piAdsText.setName("AdsText");
            piAdsText.setValue(AdsText);
            piAdsText.setType(String.class);
            request.addProperty(piAdsText);

//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapObject response = (SoapObject) envelope.getResponse();

            envelope.encodingStyle = SoapEnvelope.ENC;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object response = (Object) envelope.getResponse();
            if (response.toString().equals("true"))
                return true;
            else
                return false;

        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());

            return false;
        }// End try Catch
    }

    public Integer getDesignID() {
        return DesignID;
    }

    public void setDesignID(Integer designID) {
        DesignID = designID;
    }

    public Integer getDesignStatusID() {
        return DesignStatusID;
    }

    public void setDesignStatusID(Integer designStatusID) {
        DesignStatusID = designStatusID;
    }

    public Boolean getSalesApprove() {
        return IsSalesApprove;
    }

    public void setSalesApprove(Boolean salesApprove) {
        IsSalesApprove = salesApprove;
    }

    public String getDesignStatusName() {
        return DesignStatusName;
    }

    public void setDesignStatusName(String designStatusName) {
        DesignStatusName = designStatusName;
    }

    public Boolean getDesignAlert() {
        return DesignAlert;
    }

    public void setDesignAlert(Boolean designAlert) {
        DesignAlert = designAlert;
    }

    public Integer getClientID() {
        return ClientID;
    }

    public void setClientID(Integer clientID) {
        ClientID = clientID;
    }

    public Boolean UpdateContractPublishDetailDesignID(Integer ContractPublishedDetailID, int DesignID) {
        List<Issue> list = null;
        String OPERATION_NAME = "UpdateContractPublishDetailDesignID";

        String SOAP_ACTION = "http://tempuri.org/UpdateContractPublishDetailDesignID";

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
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ContractPublishedDetailID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piDesignID = new PropertyInfo();
            piDesignID.setName("DesignID");
            piDesignID.setValue(DesignID);
            piDesignID.setType(Integer.class);
            request.addProperty(piDesignID);

//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapObject response = (SoapObject) envelope.getResponse();

            envelope.encodingStyle = SoapEnvelope.ENC;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object response = (Object) envelope.getResponse();
            if (response.toString().equals("true"))
                return true;
            else
                return false;

        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());

            return false;
        }// End try Catch
    }

    public Integer getIssuePublishedCount() {
        return IssuePublishedCount;
    }

    public void setIssuePublishedCount(Integer issuePublishedCount) {
        IssuePublishedCount = issuePublishedCount;
    }

    public Integer getIssueCount() {
        return IssueCount;
    }

    public void setIssueCount(Integer issueCount) {
        IssueCount = issueCount;
    }

    public Integer getIssueNotPublishedCount() {
        return IssueNotPublishedCount;
    }

    public void setIssueNotPublishedCount(Integer issueNotPublishedCount) {
        IssueNotPublishedCount = issueNotPublishedCount;
    }

    public String getPaymentTypeName() {
        return PaymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        PaymentTypeName = paymentTypeName;
    }

    public Double getCm() {
        return Cm;
    }

    public void setCm(Double cm) {
        Cm = cm;
    }

    public Double getCol() {
        return Col;
    }

    public void setCol(Double col) {
        Col = col;
    }

    public Integer getPublicationID() {
        return PublicationID;
    }

    public void setPublicationID(Integer publicationID) {
        PublicationID = publicationID;
    }

    public String getPublicationName() {
        return PublicationName;
    }

    public void setPublicationName(String publicationName) {
        PublicationName = publicationName;
    }

    public String getPublicationFoldername() {
        return PublicationFoldername;
    }

    public void setPublicationFoldername(String publicationFoldername) {
        PublicationFoldername = publicationFoldername;
    }

    public String getPaymentFoldername() {
        return PaymentFoldername;
    }

    public void setPaymentFoldername(String paymentFoldername) {
        PaymentFoldername = paymentFoldername;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return IssueDate;
    }// End To String

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getAlyaumPath() {
        return AlyaumPath;
    }

    public void setAlyaumPath(String alyaumPath) {
        AlyaumPath = alyaumPath;
    }

    public Boolean getDefaultPath() {
        return IsDefaultPath;
    }

    public void setDefaultPath(Boolean defaultPath) {
        IsDefaultPath = defaultPath;
    }
}
