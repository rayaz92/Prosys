package com.example.amir.core.ADR.activity.Classes;

import android.util.Log;

import com.example.amir.core.Collector.Classes.Connection;

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

public class DesignRequest {

    private Integer ID = 0 ;
    private Integer ClientID = 0;
    private String Note ="";
    private String Path = "";
    private Integer StatusID = 0;
    private String StatusName = "";
    private String RequestDate = "";
    private Double Cm = 0.0 ;
    private Double Col = 0.0 ;
    private Integer ColorType = 0;
    private Boolean IsAllowedToEdit = false ;
    private String DesignerNote = "";
    private Integer PublicationID = 0;
    private String PublicationFolderName ="";
    private String URL = "";
    private Integer AdsCategory = 0;
    private Boolean IsDefaultPath = false ;



    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getClientID() {
        return ClientID;
    }

    public void setClientID(Integer clientID) {
        ClientID = clientID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public Integer getStatus() {
        return StatusID;
    }

    public void setStatus(Integer statusid) {
        StatusID = statusid;
    }


    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }



    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ID.toString();
    }// End To String

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

    public Integer getColorType() {
        return ColorType;
    }

    public void setColorType(Integer colorType) {
        ColorType = colorType;
    }

    public Boolean getAllowedToEdit() {
        return IsAllowedToEdit;
    }

    public void setAllowedToEdit(Boolean allowedToEdit) {
        IsAllowedToEdit = allowedToEdit;
    }

    public List<DesignRequest> Android_SelectDesignRequest(Integer ClientID) {
        List<DesignRequest> list = null;
        String OPERATION_NAME = "Android_SelectDesignRequest";

        String SOAP_ACTION = "http://tempuri.org/Android_SelectDesignRequest";

        SoapObject request = new SoapObject(com.example.amir.core.ADR.activity.Classes.Connection.NAMESPACE,
                OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        androidHttpTransport.debug = true;

        try {
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ClientID");
            pi.setValue(ClientID);
            pi.setType(Integer.class);
            request.addProperty(pi);

//            PropertyInfo piIssueID = new PropertyInfo();
//            piIssueID.setName("Status");
//            piIssueID.setValue(Status);
//            piIssueID.setType(Boolean.class);
//            request.addProperty(piIssueID);
//
//            PropertyInfo piPartTypeID = new PropertyInfo();
//            piPartTypeID.setName("All");
//            piPartTypeID.setValue(All);
//            piPartTypeID.setType(Boolean.class);
//            request.addProperty(piPartTypeID);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.toString().equals("anyType{}") || response == null) {
                list = null;
            } else {
                list = new ArrayList<DesignRequest>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                Log.d("R", "" + resultsRequestSOAP + "");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    DesignRequest item = new DesignRequest();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.setID(Integer.parseInt(obj3.getProperty(
                            "ID").toString()));
                    item.setClientID(Integer.parseInt(obj3.getProperty(
                            "ClientID").toString()));
                    item.setNote(obj3.getProperty(
                            "Note").toString());
                    item.setPath(obj3.getProperty(
                            "Path").toString());

                    item.setStatus(Integer.valueOf(obj3.getProperty(
                            "Status").toString()));
                    item.setStatusName(obj3.getProperty(
                            "StatusName").toString());
                    item.setRequestDate(obj3.getProperty(
                            "EntryDate").toString().substring(0 , obj3.getProperty(
                            "EntryDate").toString().lastIndexOf("T")));

                    item.setCm(Double.valueOf(obj3.getProperty(
                            "Cm").toString()));
                    item.setCol(Double.valueOf(obj3.getProperty(
                            "Col").toString()));
                    item.setColorType(Integer.valueOf(obj3.getProperty(
                            "ColorTypeID").toString()));
                    item.setAllowedToEdit(Boolean.valueOf(obj3.getProperty(
                            "IsAllowedToEdit").toString()));

                    item.setDesignerNote(obj3.getProperty(
                            "DesignerNote").toString());

                    item.setPublicationID(Integer.valueOf(obj3.getProperty(
                            "PublicationID").toString()));
                    item.setPublicationFolderName(obj3.getProperty(
                            "FolderName").toString());
                    item.setURL(obj3.getProperty(
                            "URL").toString());

                    item.setAdsCategory(Integer.valueOf(obj3.getProperty(
                            "AdsSectorID").toString()));
                    item.setDefaultPath(Boolean.valueOf(obj3.getProperty(
                            "IsDefaultPath").toString()));

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

    public Integer Android_InsertDesignRequest(Integer ClientID, String Note, Double Cm, Double Col,int ColortypeID ,
                                               int EntryUserID , int SalesID , int PublicationID , int AdsCategory) {
        String SOAP_ACTION = "http://tempuri.org/Android_InsertDesignRequest";

        String OPERATION_NAME = "Android_InsertDesignRequest";

        SoapObject request = new SoapObject(com.example.amir.core.ADR.activity.Classes.Connection.NAMESPACE,
                OPERATION_NAME);
        PropertyInfo piAdsSectorID = new PropertyInfo();
        piAdsSectorID.setName("ClientID");
        piAdsSectorID.setValue(ClientID);
        piAdsSectorID.setType(int.class);
        request.addProperty(piAdsSectorID);//1

        PropertyInfo piAdsCm = new PropertyInfo();
        piAdsCm.setName("Cm");
        piAdsCm.setValue(Cm);
        piAdsCm.setType(Double.class);
        request.addProperty(piAdsCm);//2

        PropertyInfo piAdsCol = new PropertyInfo();
        piAdsCol.setName("Col");
        piAdsCol.setValue(Col);
        piAdsCol.setType(Double.class);
        request.addProperty(piAdsCol);//3

        PropertyInfo piNote = new PropertyInfo();//4
        piNote.setName("Note");
        piNote.setValue(Note);
        piNote.setType(String.class);
        request.addProperty(piNote);

        PropertyInfo piEntryUserID = new PropertyInfo();//5
        piEntryUserID.setName("EntryUserID");
        piEntryUserID.setValue(EntryUserID);
        piEntryUserID.setType(int.class);
        request.addProperty(piEntryUserID);


        PropertyInfo piColorTypeID = new PropertyInfo();//6
        piColorTypeID.setName("ColortypeID");//ColorTypeID
        piColorTypeID.setValue(ColortypeID);
        piColorTypeID.setType(int.class);
        request.addProperty(piColorTypeID);

        PropertyInfo piSalesID = new PropertyInfo();//7
        piSalesID.setName("SalesID");
        piSalesID.setValue(SalesID);
        piSalesID.setType(int.class);
        request.addProperty(piSalesID);

        PropertyInfo piPublicationID = new PropertyInfo();//8
        piPublicationID.setName("PublicationID");
        piPublicationID.setValue(PublicationID);
        piPublicationID.setType(int.class);
        request.addProperty(piPublicationID);

        PropertyInfo piAdsCategory = new PropertyInfo();//8
        piAdsCategory.setName("AdsCategory");
        piAdsCategory.setValue(AdsCategory);
        piAdsCategory.setType(int.class);
        request.addProperty(piAdsCategory);

//		request.addProperty("Image", ObjContract.GetImage());//42

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        Object response = null;

        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
                com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        int res;
        try {
            new MarshalFloat().register(envelope);
            new MarshalBase64().register(envelope);
            envelope.encodingStyle = SoapEnvelope.ENC;
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            Log.d("Insert Contract result ", "" + response + "");
        } catch (Exception e) {
//			Log.d("Error In Insert Save Contract  ", "" + response);
            new CatchMsg();
            CatchMsg.WriteMSG("Contract.Save Contract", e.getMessage());
            res = 0;
            return res;
        }// End Try Catch
        if (Integer.valueOf(response.toString()) > 0) {
            res = Integer.valueOf(response.toString());
        } else {
            res = 0;
        }
        return res;
    }

    public Boolean UpdateDesignRequest(Integer ID , Integer Status , Double Cm , Double Col , String Note,Integer ColorTypeID,
                                       int PublicationID, int AdsCategory) {
        List<Issue> list = null;
        String OPERATION_NAME = "UpdateDesignRequest";

        String SOAP_ACTION = "http://tempuri.org/UpdateDesignRequest";

        SoapObject request = new SoapObject(com.example.amir.core.ADR.activity.Classes.Connection.NAMESPACE,
                OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        androidHttpTransport.debug = true;

        try {
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piStatus = new PropertyInfo();
            piStatus.setName("Status");
            piStatus.setValue(Status);
            piStatus.setType(Integer.class);
            request.addProperty(piStatus);


            PropertyInfo piCm = new PropertyInfo();
            piCm.setName("Cm");
            piCm.setValue(Cm);
            piCm.setType(Double.class);
            request.addProperty(piCm);


            PropertyInfo piCol = new PropertyInfo();
            piCol.setName("Col");
            piCol.setValue(Col);
            piCol.setType(Double.class);
            request.addProperty(piCol);

            PropertyInfo piNote = new PropertyInfo();
            piNote.setName("Note");
            piNote.setValue(Note);
            piNote.setType(String.class);
            request.addProperty(piNote);

            PropertyInfo piColorTypeID = new PropertyInfo();
            piColorTypeID.setName("ColorTypeID");
            piColorTypeID.setValue(ColorTypeID);
            piColorTypeID.setType(Integer.class);
            request.addProperty(piColorTypeID);

            PropertyInfo piPublicationID = new PropertyInfo();
            piPublicationID.setName("PublicationID");
            piPublicationID.setValue(PublicationID);
            piPublicationID.setType(Integer.class);
            request.addProperty(piPublicationID);

//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapObject response = (SoapObject) envelope.getResponse();


            new MarshalFloat().register(envelope);
            new MarshalBase64().register(envelope);
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

    public Boolean UpdateDesignRequestApprove(Integer ID , Integer Status, int IssueID ,
                                              int PaymentTypeID , int SerialNo , int ClientID , int PublicationID) {
        List<Issue> list = null;
        String OPERATION_NAME = "UpdateDesignRequestApprove";

        String SOAP_ACTION = "http://tempuri.org/UpdateDesignRequestApprove";

        SoapObject request = new SoapObject(com.example.amir.core.ADR.activity.Classes.Connection.NAMESPACE,
                OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        androidHttpTransport.debug = true;

        try {
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piStatus = new PropertyInfo();
            piStatus.setName("Status");
            piStatus.setValue(Status);
            piStatus.setType(Integer.class);
            request.addProperty(piStatus);

            PropertyInfo piClientID = new PropertyInfo();
            piClientID.setName("ClientID");
            piClientID.setValue(ClientID);
            piClientID.setType(Integer.class);
            request.addProperty(piClientID);

            PropertyInfo piIssueID = new PropertyInfo();
            piIssueID.setName("IssueID");
            piIssueID.setValue(IssueID);
            piIssueID.setType(Integer.class);
            request.addProperty(piIssueID);

            PropertyInfo piPaymentTypeID = new PropertyInfo();
            piPaymentTypeID.setName("PaymentTypeID");
            piPaymentTypeID.setValue(PaymentTypeID);
            piPaymentTypeID.setType(Integer.class);
            request.addProperty(piPaymentTypeID);

            PropertyInfo piSerialNo = new PropertyInfo();
            piSerialNo.setName("SerialNo");
            piSerialNo.setValue(SerialNo);
            piSerialNo.setType(Integer.class);
            request.addProperty(piSerialNo);

            PropertyInfo piPublicationID = new PropertyInfo();
            piPublicationID.setName("PublicationID");
            piPublicationID.setValue(PublicationID);
            piPublicationID.setType(Integer.class);
            request.addProperty(piPublicationID);


            new MarshalFloat().register(envelope);
            new MarshalBase64().register(envelope);
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

    public Boolean UpdateDesignRequestCancel(Integer ID , Integer Status) {
        List<Issue> list = null;
        String OPERATION_NAME = "UpdateDesignRequestCancel";

        String SOAP_ACTION = "http://tempuri.org/UpdateDesignRequestCancel";

        SoapObject request = new SoapObject(com.example.amir.core.ADR.activity.Classes.Connection.NAMESPACE,
                OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                com.example.amir.core.ADR.activity.Classes.Connection.ADDRESS);
        androidHttpTransport.debug = true;

        try {
            PropertyInfo pi = new PropertyInfo();
            pi.setName("ID");
            pi.setValue(ID);
            pi.setType(Integer.class);
            request.addProperty(pi);

            PropertyInfo piStatus = new PropertyInfo();
            piStatus.setName("Status");
            piStatus.setValue(Status);
            piStatus.setType(Integer.class);
            request.addProperty(piStatus);

            new MarshalFloat().register(envelope);
            new MarshalBase64().register(envelope);
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

    public String getDesignerNote() {
        return DesignerNote;
    }

    public void setDesignerNote(String designerNote) {
        DesignerNote = designerNote;
    }

    public Integer getPublicationID() {
        return PublicationID;
    }

    public void setPublicationID(Integer publicationID) {
        PublicationID = publicationID;
    }

    public String getPublicationFolderName() {
        return PublicationFolderName;
    }

    public void setPublicationFolderName(String publicationFolderName) {
        PublicationFolderName = publicationFolderName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Integer getAdsCategory() {
        return AdsCategory;
    }

    public void setAdsCategory(Integer adsCategory) {
        AdsCategory = adsCategory;
    }

    public Boolean getDefaultPath() {
        return IsDefaultPath;
    }

    public void setDefaultPath(Boolean defaultPath) {
        IsDefaultPath = defaultPath;
    }
}// End Class
