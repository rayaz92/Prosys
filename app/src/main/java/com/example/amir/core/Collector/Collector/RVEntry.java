package com.example.amir.core.Collector.Collector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amir.core.Collector.Adapter.AdapterListPayment;
import com.example.amir.core.Collector.Adapter.PaymentItem;
import com.example.amir.core.Collector.Classes.Bank;
import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.Collector.Classes.GnCheqe;
import com.example.amir.core.Collector.printing.DeviceList;
import com.example.amir.core.Collector.printing.DeviceListActivity;
import com.example.amir.core.Collector.printing.PrintInterMec;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;
import com.honeywell.mobility.print.LinePrinter;
import com.honeywell.mobility.print.LinePrinterException;
import com.honeywell.mobility.print.PrintProgressEvent;
import com.honeywell.mobility.print.PrintProgressListener;
import com.zj.btsdk.PrintPic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RVEntry extends AppCompatActivity {

    TextView tvRVEntryVoucherNo_RV , RVEntrytvTotalAmountPayment_ReceiptVoucher;
    EditText  RVEntryReceipt_Date ,RVEntryReceip_ClientName ,RVEntryReceipt_TotalAmount ,
            RVEntryReceipt_RefNo ,RVEntryReceipt_Note ,
    RVEntryReceipt_ChequeNo,RVEntryReceipt_DueDate , RVEntryReceipt_CheuqeAmount;

    Spinner SpinBank  ;

    ListView RVEntrylistPayment_ReceiptVoucher;

    Button RVEntryBtnAddPayment_ReceiptVoucher,RVEntryReceipt_BtnCancel, RVEntryReceipt_BtnSave ;

    GNPAY objGnpay;
    int NewSerNo ;
    String ColName, Accn_no, ClientName, AgeAcco_NO , AgentAccnName;

    Context context = this;
    ShowProgressDialog objShowProgressDialog;
    File mypathHeader;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    String MacAddress;

    List<Bank> listBanks;
    ArrayAdapter<Bank> AdapterBank;

    AdapterListPayment adapterListPayment;
    ArrayList<PaymentItem> ArraylistPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rventry);

        Init();
        InitPrinter();
        CopyAssetFiles();

    }

    private void Init(){
        try {
            tvRVEntryVoucherNo_RV =(TextView) findViewById(R.id.tvRVEntryVoucherNo_RV) ;
            RVEntrytvTotalAmountPayment_ReceiptVoucher = (TextView) findViewById(R.id.RVEntrytvTotalAmountPayment_ReceiptVoucher);

            RVEntryReceipt_Date =(EditText) findViewById(R.id.RVEntryReceipt_Date) ;
            RVEntryReceip_ClientName =(EditText) findViewById(R.id.RVEntryReceip_ClientName) ;
            RVEntryReceipt_TotalAmount =(EditText) findViewById(R.id.RVEntryReceipt_TotalAmount) ;
            RVEntryReceipt_RefNo=(EditText) findViewById(R.id.RVEntryReceipt_RefNo) ;
            RVEntryReceipt_Note =(EditText) findViewById(R.id.RVEntryReceipt_Note) ;
            RVEntryReceipt_ChequeNo=(EditText) findViewById(R.id.RVEntryReceipt_ChequeNo) ;
            RVEntryReceipt_DueDate =(EditText) findViewById(R.id.RVEntryReceipt_DueDate) ;
            RVEntryReceipt_CheuqeAmount=(EditText) findViewById(R.id.RVEntryReceipt_CheuqeAmount) ;

            SpinBank = (Spinner) findViewById(R.id.RVEntryReceipt_SpinBank);

            RVEntrylistPayment_ReceiptVoucher = (ListView) findViewById(R.id.RVEntrylistPayment_ReceiptVoucher);

            RVEntryBtnAddPayment_ReceiptVoucher = (Button) findViewById(R.id.RVEntryBtnAddPayment_ReceiptVoucher);
            RVEntryReceipt_BtnCancel = (Button) findViewById(R.id.RVEntryReceipt_BtnCancel);
            RVEntryReceipt_BtnSave = (Button) findViewById(R.id.RVEntryReceipt_BtnSave);


            RVEntryReceipt_DueDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog(RVEntryReceipt_DueDate);
                }
            });
            RVEntryBtnAddPayment_ReceiptVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddPayment();
                }
            });

            RVEntryReceipt_BtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IsValidSave()) {
                        SaveDialog();
                    }
                }
            });
            registerForContextMenu(RVEntrylistPayment_ReceiptVoucher);
            RVEntrylistPayment_ReceiptVoucher
                    .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent,
                                                       View view, int position, long id) {
                            RVEntryReceipt_CheuqeAmount.setText(""
                                    + adapterListPayment.getItem(position)
                                    .getCheuqeAmount());
                            RVEntryReceipt_DueDate.setText(adapterListPayment.getItem(position)
                                    .getDueDate());
                            RVEntryReceipt_ChequeNo.setText(""
                                    + adapterListPayment.getItem(position)
                                    .getChequeNo());

                            SpinBank.setSelection(GetPositionBankNo(adapterListPayment
                                    .getItem(position).getBankNo()));
                            return false;
                        }
                    });

            RVEntryReceipt_Date.setText(GetDateToday());
            RVEntryReceipt_CheuqeAmount.setText("" + 0.0);
            Intent i = getIntent();
            Accn_no = i.getStringExtra("accn_no");
            ColName = i.getStringExtra("ColName");
            AgeAcco_NO = i.getStringExtra("AgeAcco_NO");
            AgentAccnName = i .getStringExtra("AgentAccnName");
            RVEntryReceip_ClientName.setText(AgentAccnName);

            new GetSerNo().execute();
            new FillBank().execute();

            statusControl(true);
            ArraylistPayment = new ArrayList<PaymentItem>();
            adapterListPayment = new AdapterListPayment(context, 0,
                    ArraylistPayment);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void InitPrinter() {

       	Intent serverIntent = new Intent(context, DeviceList.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

    }

    @SuppressLint("SimpleDateFormat")
    private String GetDateToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.ENGLISH);//("dd/MM/yyyy");
        return sdf.format(cal.getTime());
        // return null;
    }// End Date Today

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        if (item.getTitle().equals(getString(R.string.Delete))) {
            PaymentItem paymentItem = adapterListPayment.getItem(info.position);
            RVEntrytvTotalAmountPayment_ReceiptVoucher
                    .setText(""
                            + ((Double.parseDouble(RVEntrytvTotalAmountPayment_ReceiptVoucher.getText()
                            .toString())) - paymentItem
                            .getCheuqeAmount()));
            adapterListPayment.remove(paymentItem);
            adapterListPayment.notifyDataSetChanged();
        }// Delete

        return super.onContextItemSelected(item);
    }

    private int GetPositionBankNo(int bankNo) {
        int i = 0;
        try {
            for (int x = 0; x < AdapterBank.getCount(); x++) {
                Bank bank = (Bank) SpinBank.getItemAtPosition(i);
                if (bank.getNo() == bankNo)
                    return i;
                else
                    i = i + 1;
            }
            return i;
        } catch (Exception e) {
            return 0;

        }

    }

    private void AddPayment() {
        try {
            int BankNo = AdapterBank
                    .getItem(SpinBank.getSelectedItemPosition()).getNo();

            String DueDate = RVEntryReceipt_DueDate.getText().toString();
            String ChequeNo = RVEntryReceipt_ChequeNo.getText().toString();
            String TransType = "";
            double ChequeAmount = Double.valueOf(RVEntryReceipt_CheuqeAmount.getText()
                    .toString());

            if (AdapterBank.getItemId(SpinBank.getSelectedItemPosition()) == 0) {
                TransType = "Cash";
            } else {
                TransType = "Cheque";
                if (!IsValidDate(RVEntryReceipt_DueDate.getText().toString(), RVEntryReceipt_Date
                        .getText().toString())) {
                    new GetToast(context, getString(R.string.InValidDate));
                    return;
                }
                if (DueDate.trim().length() == 0
                        || ChequeNo.trim().length() == 0) {
                    new GetToast(context,
                            getString(R.string.PleaseComChequeInfo));
                    return;
                }
            }
            if (ChequeAmount == 0.0) {
                new GetToast(context,
                        getString(R.string.ChequeAmountIsNotValid));
                return;
            }
            adapterListPayment.add(new PaymentItem(BankNo, DueDate, ChequeNo,
                    ChequeAmount, TransType));
            adapterListPayment = new AdapterListPayment(context, 0,
                    ArraylistPayment);
            RVEntrylistPayment_ReceiptVoucher.setAdapter(adapterListPayment);
            adapterListPayment.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(RVEntrylistPayment_ReceiptVoucher);
            RVEntrytvTotalAmountPayment_ReceiptVoucher
                    .setText(""
                            + (Double.valueOf(RVEntrytvTotalAmountPayment_ReceiptVoucher.getText()
                            .toString()) + Double
                            .valueOf(RVEntryReceipt_CheuqeAmount.getText()
                                    .toString())));

            RVEntryReceipt_CheuqeAmount.setText("");
            RVEntryReceipt_DueDate.setText("");
            RVEntryReceipt_ChequeNo.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    private void statusControl(Boolean isChecked) {
        RVEntryReceipt_ChequeNo.setEnabled(isChecked);
        RVEntryReceipt_DueDate.setEnabled(isChecked);
        RVEntryReceipt_CheuqeAmount.setEnabled(isChecked);
//        SpinBank.setEnabled(isChecked);
//        tvDueDate.setEnabled(isChecked);
//        tvChequqNo.setEnabled(isChecked);
//        tvChequqAmount.setEnabled(isChecked);
//        tvBank.setEnabled(isChecked);
        RVEntryBtnAddPayment_ReceiptVoucher.setEnabled(isChecked);
    }

    @SuppressLint("SimpleDateFormat")
    private boolean IsValidDate(String DateAfter, String Datetoday) {

        String strafter = DateAfter;
        String strtoday = Datetoday;
        boolean result = false;

        try {
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.ENGLISH);//("dd/MM/yyyy");
            Calendar C = Calendar.getInstance();
            C.setTime(dfDate.parse(strafter));

            Calendar C1 = Calendar.getInstance();
            C1.setTime(dfDate.parse(strtoday));

            Date dateafter = new Date(C.getTimeInMillis());
            Date datetoday = new Date(C1.getTimeInMillis());
            if (dateafter.compareTo(datetoday) >= 0) {
                result = true;
            }// End If
            else if (datetoday.after(dateafter))
                result = false;
        } catch (Exception e) {
            Log.d("Error in valide ", e.getMessage());
        }
        return result;
    }

    @SuppressLint("NewApi")
    private Boolean CopyAssetFiles() {
        // Copy the asset files we delivered with the application to a location
        // where the LinePrinter can access them.
        boolean result = false;
        try {
            AssetManager assetManager = getAssets();
            String[] files = { "printer_profiles.JSON" };
            for (String filename : files) {
                InputStream input = null;
                OutputStream output = null;
                input = assetManager.open(filename);
                File outputFile = new File(getExternalFilesDir(null), filename);
                output = new FileOutputStream(outputFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = input.read(buf)) > 0) {
                    output.write(buf, 0, len);
                }
                input.close();
                input = null;
                output.flush();
                output.close();
                output = null;
                result = true;
            }

            return result;
        } catch (Exception ex) {
            new GetToast(context, ex.getMessage());
            // tvMSG.setTextColor(Color.RED);
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    MacAddress = data.getExtras().getString(
                            DeviceList.EXTRA_DEVICE_ADDRESS);
                }

                break;

        }

    }

    private Boolean IsValidSave() {
        if (RVEntryReceipt_TotalAmount.getText().toString().trim().equals("")) {
            new GetToast(context, getString(R.string.PleaseInsertAmount));
            return false;
        }
        double x, y;
        x = Double.parseDouble(RVEntrytvTotalAmountPayment_ReceiptVoucher.getText().toString());
        y = Double.parseDouble(RVEntryReceipt_TotalAmount.getText().toString());
        if (x != y) {
            new GetToast(context,
                    getString(R.string.TotalAmountDoesntEqualRequiredValue));
            return false;
        }
        return true;
    }

    private void SaveDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(getString(R.string.AreYouSure));
        builder1.setCancelable(true);
        builder1.setPositiveButton(getString(R.string.Yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SaveRV().execute();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(getString(R.string.No),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }

    class GetSerNo extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(RVEntry.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait));
            objProgressDialog.ShowDialog();


        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                GNPAY objGnpay = new GNPAY();
                NewSerNo = objGnpay.GetSerNo();
                x = String.valueOf(NewSerNo);
            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();

            DateFormat df = new SimpleDateFormat("yy" , Locale.ENGLISH); // Just the year, with 2 digits
            String formattedDate = df.format(Calendar.getInstance().getTime());
            String Year = formattedDate;

            df = new SimpleDateFormat("MM", Locale.ENGLISH);
            formattedDate = df.format(Calendar.getInstance().getTime() );
            String Month = formattedDate;


            tvRVEntryVoucherNo_RV.setText(Year + Month + (String.valueOf(NewSerNo + 1)));
        }
    }

    class FillBank extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(RVEntry.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait));
            objProgressDialog.ShowDialog();


        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Bank objBank = new Bank();
                listBanks = objBank.getBankName();


            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if (listBanks != null) {
                AdapterBank = new ArrayAdapter<Bank>(RVEntry.this,
                        android.R.layout.simple_spinner_item, listBanks);

                AdapterBank
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinBank.setAdapter(AdapterBank);
            }
        }
    }

    private class SaveRV extends AsyncTask<Void, Void, Integer> {
        ShowProgressDialog objProgressDialog;
        // String Note = RVEntryReceipt_Note.getText().toString() + AgentAccnName.toString()ك

        @Override
        protected void onPreExecute() {
            objProgressDialog = new ShowProgressDialog(RVEntry.this,
                    getString(R.string.Loading));

            objProgressDialog.ShowDialog();

            objGnpay = new GNPAY();
            objGnpay.setCR(Double.valueOf(RVEntryReceipt_TotalAmount.getText().toString()));
            objGnpay.setNote(RVEntryReceipt_Note.getText().toString() + AgentAccnName.toString());
            objGnpay.setREF_NO((RVEntryReceipt_RefNo.getText().toString()));
            objGnpay.setser(Integer.valueOf(tvRVEntryVoucherNo_RV.getText().toString()));//NewSerNo

            objGnpay.setClientName(RVEntryReceip_ClientName.getText().toString().trim());
            objGnpay.setaccn_no(Accn_no.toString());
            objGnpay.setTY("RV");

//			objGnpay.setser(Integer.valueOf(tvVoucherNo.getText().toString()));
        }

        @Override
        protected Integer doInBackground(Void... params) {

            int Serial = new GNPAY().InsertGNPAY(objGnpay,
                    CoreLogInActivity.UserID, Accn_no, AgentAccnName ,
                    CoreLogInActivity.UserAccountNo,CoreLogInActivity.UserAccountName ,
                    Main.DeviceID);
//					LoginActivity.coll_id, accn_no, ClientName , Main.DeviceID);
            if (Serial > 0) {
                for (int i = 0; i < adapterListPayment.getCount(); i++) {
                    PaymentItem paymentItem = adapterListPayment.getItem(i);
                    if (new GnCheqe().InsertGnCheqe(objGnpay, Serial,
                            paymentItem, CoreLogInActivity.UserID,
                            CoreLogInActivity.UserID, CoreLogInActivity.UserAccountNo , 0) > 0) {
//							paymentItem, LoginActivity.coll_id,
//							LoginActivity.coll_id) > 0) {
                        continue;
                    } else
                        return 0;
                }
            } else
                return 0;
            return Serial;
        }
        @Override
        protected void onPostExecute(Integer result) {
            objProgressDialog.EndDialog();
            if (result > 0) {
                new GetToast(context, getString(R.string.DoneInsert));
                LoginActivity.PrinterName = "P3Inches";
                //ReceiptVoucher.this.finish();
                if(MacAddress != null){
                    if ( MacAddress.length() > 0) {
                        new PrintTask().execute(MacAddress);
                    }else{
                        new GetToast(context, getString(R.string.NoPrinterFound));
                        setResult(1);
                        RVEntry.this.finish();
                    }
                }else{
                    new GetToast(context, getString(R.string.NoPrinterFound));
                    setResult(1);
                    RVEntry.this.finish();
                }
            } else
                new GetToast(context, getString(R.string.ErrorInInsert));
        }
    }

    public class PrintTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

            objShowProgressDialog = new ShowProgressDialog(context,
                    getString(R.string.Please_Wait),
                    getString(R.string.Printing_receipt_voucher));
            objShowProgressDialog.ShowDialog();
//			ReceiptVoucher.this.setProgressBarIndeterminateVisibility(true);
            // tvMSG.setText("");
        }

        @Override
        protected String doInBackground(String... args) {
            String sResult = null;
            String sMacAddr = args[0];

            if (sMacAddr.contains(":") == false && sMacAddr.length() == 12) {
                // If the MAC address only contains hex digits without the
                // ":" delimiter, then add ":" to the MAC address string.
                char[] cAddr = new char[17];

                for (int i = 0, j = 0; i < 12; i += 2) {
                    sMacAddr.getChars(i, i + 2, cAddr, j);
                    j += 2;
                    if (j < 17) {
                        cAddr[j++] = ':';
                    }
                }
                sMacAddr = new String(cAddr);
            }
            String sPrinterURI = "bt://" + sMacAddr;
            LinePrinter.ExtraSettings exSettings = new LinePrinter.ExtraSettings();
            exSettings.setContext(context);



                try {
                    File profiles = new File(getExternalFilesDir(null),
                            "printer_profiles.JSON");
                    LinePrinter lp = new LinePrinter(profiles.getAbsolutePath(),
                            "PR3", sPrinterURI, exSettings);
                    // Registers to listen for the print progress events.
                    lp.addPrintProgressListener(new PrintProgressListener() {
                        public void receivedStatus(PrintProgressEvent aEvent) {
                            // Publishes updates on the UI thread.
                            publishProgress(aEvent.getMessageType());
                        }
                    });

                    int numtries = 0;
                    int maxretry = 2;

                    while (numtries < maxretry) {
                        try {
                            lp.connect(); // Connects to the printer
                            break;
                        } catch (LinePrinterException ex) {
                            numtries++;
                            Thread.sleep(1000);
                        }
                    }
                    if (numtries == maxretry)
                        lp.connect();// Final retry
                    // print data
                    try {
                        CreateLogo();
                    } catch (Exception e) {
                        if (!(e.getMessage() == null))
                            sResult = e.getMessage();
                        else
                            sResult = "error Do In Back Create Result";
                    }
                    if (mypathHeader.exists()) {
                        lp.writeGraphic(mypathHeader.getAbsolutePath(),
                                LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
                                800, 0);
                    }
                    // ContractInfo
                    try {
                        CreateAlmadina();
                    } catch (Exception e) {
                        if (!(e.getMessage() == null))
                            sResult = e.getMessage();
                        else
                            sResult = "error Do In Back Create Result";
                    }
                    if (mypathHeader.exists()) {
                        lp.writeGraphic(mypathHeader.getAbsolutePath(),
                                LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
                                800, 0);//1050
                    }

                    lp.disconnect(); // Disconnects from the printer
                    lp.close(); // Releases resources

                } catch (LinePrinterException ex) {
                    sResult = "LinePrinterException: " + ex.getMessage();
                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        sResult = "Unexpected exception: " + ex.getMessage();
                    } else {
                        sResult = "Unexpected exception.";
                    }
                }
                return sResult;

        }


        @Override
        protected void onPostExecute(String result) {
            objShowProgressDialog.EndDialog();
//			ReceiptVoucher.this.setProgressBarIndeterminateVisibility(false);
            if (!(mypathHeader == null))
                mypathHeader.delete();

            if (result != null) {
                new GetToast(context, result);
                // tvMSG.setText(result);
            }
            setResult(1);
            RVEntry.this.finish();
        }
    } // end of class PrintTask

    private void CreateLogo() throws FileNotFoundException {
        File directory = new File(Environment.getExternalStorageDirectory()
                + "/AndroidColleter");
        if (!directory.exists())
            directory.mkdir(); // directory is created;
        String current = "Data.bmp";
        mypathHeader = new File(directory, current);
        FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.okazlogotrans);
        new PrintInterMec().WriteAlmadinaLogo(mFileOutStream, bitmap);
    }//

    private void CreateAlmadina() throws FileNotFoundException {
        File directory = new File(Environment.getExternalStorageDirectory()
                + "/AndroidColleter");
        if (!directory.exists())
            directory.mkdir(); // directory is created;
        String current = "Data.bmp";
        mypathHeader = new File(directory, current);
        FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
//        if (adapterListPayment !=null){
//            if (adapterListPayment.getCount() > 0){
//                new PrintInterMec().WriteOnIMGOkazNew(mFileOutStream, objGnpay,
//                        adapterListPayment,GetDateToday(),String.valueOf(tvRVEntryVoucherNo_RV.getText()).toString(),
//                        String.valueOf(RVEntryReceipt_Note.getText().toString()));
//            }
//        }else {
            new PrintInterMec().WriteOnIMGAlmadina(mFileOutStream, objGnpay,
                    GetDateToday(), SpinBank.getSelectedItem().toString(),
                    RVEntryReceipt_ChequeNo.getText().toString(), String.valueOf(tvRVEntryVoucherNo_RV.getText()).toString(),
                    String.valueOf(RVEntryReceipt_Note.getText().toString()));
//        }
    }//

    protected void Dialog(final TextView _textDialog) {
        Calendar calendar = Calendar.getInstance();
        final int Year = calendar.get(Calendar.YEAR);
        final int Month = calendar.get(Calendar.MONTH);
        final int Day = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog.OnDateSetListener dateSetListener;
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int monthPlusone = (monthOfYear + 1);
//                String Date = "" + dayOfMonth + "-" + monthPlusone + "-" + year;
//                _textDialog.setText(Date);
                String Date = "" + year + "-" + monthPlusone + "-" + dayOfMonth;
                _textDialog.setText(Date);
            }
        };
        new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
    }



}
