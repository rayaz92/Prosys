package com.example.amir.core.Collector.Classes;

import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyWorkClass implements Serializable {

    private int ser = 0;
    private double CR = 0.0;
    private String Note = "";
    private String ClientName = "";
    private String accn_no = "";

    public List<DailyWorkClass> DaliyWork(Date Date, int UserID) {
        List<DailyWorkClass> list = null;
        DailyWorkClass ObjDailyWork;
        String SOAP_ACTION = "http://tempuri.org/Android_DaliyWorkCollector";

        String OPERATION_NAME = "Android_DaliyWorkCollector";

        SoapObject request = new SoapObject(com.example.amir.core.Collector.Classes.Connection.NAMESPACE,
                OPERATION_NAME);

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("DateFrom");
        propertyInfo.setValue(Date);
        propertyInfo.setType(Date.class);
        request.addProperty(propertyInfo);

        propertyInfo = new PropertyInfo();
        propertyInfo.setName("UserID");
        propertyInfo.setValue(UserID);
        propertyInfo.setType(int.class);
        request.addProperty(propertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
                com.example.amir.core.Collector.Classes.Connection.ADDRESS);
        try {
            new MarshalDate().register(envelope);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response1 = (SoapObject) envelope.getResponse();

            if (response1.toString().equals("anyType{}") || response1 == null) {
                list = null;
            } else {
                list = new ArrayList<DailyWorkClass>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
//				Log.d("Result DaliyWork Collector ", "" + resultsRequestSOAP
//						+ "");
                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    ObjDailyWork = new DailyWorkClass();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    ObjDailyWork.setSer((Integer.parseInt(obj3.getProperty("ser")
                            .toString())));
                    ObjDailyWork.setAccn_no((obj3.getProperty("accn_no").toString()));
                    ObjDailyWork.setClientName(obj3.getProperty("name").toString());
                    ObjDailyWork.setCR(Double.valueOf(obj3.getProperty("CR")
                            .toString()));
                    ObjDailyWork.setNote((obj3.getProperty("note").toString()));
                    list.add(ObjDailyWork);
                }// End for
            }// End try
        } catch (Exception e) {
//			Log.d("Error Daliy Work Collector ..", e.getMessage());
            list = null;
        }// End try Catch
        return list;
    }// end DaliyWork

    public int getSer() {
        return ser;
    }

    public void setSer(int ser) {
        this.ser = ser;
    }

    public double getCR() {
        return CR;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getAccn_no() {
        return accn_no;
    }

    public void setAccn_no(String accn_no) {
        this.accn_no = accn_no;
    }
}// End Class
