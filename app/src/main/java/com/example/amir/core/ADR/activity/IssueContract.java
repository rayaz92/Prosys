package com.example.amir.core.ADR.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractStatus;
import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.ADR.activity.Classes.IssueDate;
import com.example.amir.core.ADR.activity.Classes.PageType;
import com.example.amir.core.ADR.activity.Classes.Publication;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class IssueContract extends AppCompatActivity {

    Spinner spIssueContractPublication , spIssueContractIssue ,spIssueContractPartType;
    Button btnIssueContractSearch;
    TableLayout tblIssueContract;
    EditText txtIssueContractContractStatus ;
    CheckBox chbAll ;
    TextView txtCountOK, txtCountOnHold , txtCountNp ;

    List<PageType> listPageType;
    ArrayAdapter<PageType> arrayAdapterPageType;

    List<Issue> IssueList ;
    ArrayAdapter<Issue> IssueArrayAdapter;

    List<Publication> PublicationList ;
    ArrayAdapter<Publication> PublicationArrayAdapter;

    List<ContractStatus> ContractStatusList ;
    ArrayAdapter<ContractStatus> ContractStatusArrayAdapter;

    List<Issue> IssueContractList ;
    TableRow tblRow;

    ArrayList seletedItems ;
    String StatusName = "" ,StatusID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_contract);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Init();

    }

    private void Init(){
        try{

            spIssueContractPublication = (Spinner) findViewById(R.id.spIssueContractPublication);
            spIssueContractIssue = (Spinner) findViewById(R.id.spIssueContractIssue);
            spIssueContractPartType= (Spinner) findViewById(R.id.spIssueContractPartType);

            txtCountOK= (TextView) findViewById(R.id.txtCountOK);
            txtCountOnHold =  (TextView) findViewById(R.id.txtCountOnHold);
            txtCountNp = (TextView) findViewById(R.id.txtCountNP);

            chbAll = (CheckBox) findViewById(R.id.chbAll);

            txtIssueContractContractStatus = (EditText) findViewById(R.id.txtIssueContractContractStatus);

            btnIssueContractSearch = (Button) findViewById(R.id.btnIssueContractSearch);

            tblIssueContract = (TableLayout) findViewById(R.id.tblIssueContract);

            spIssueContractPublication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(PublicationList != null){
                        new getIssue().execute("",String.valueOf(chbAll.isChecked()));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
// ContractStatusDialog();
            txtIssueContractContractStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContractStatusDialog();
                }
            });

            btnIssueContractSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetIssueContract().execute();
                }
            });

            chbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        new getIssue().execute("",String.valueOf("true"));
                    }else{
                        new getIssue().execute("",String.valueOf("false"));
                    }
                }
            });
            new FillSpinner().execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.toString().equals("True")){
                    new GetIssueContract().execute();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    class FillSpinner extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(IssueContract.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Publication objPublication = new Publication();
                PublicationList = objPublication.getPublication();


                PageType objPageType = new PageType();
                listPageType = objPageType.GetPartType();

                ContractStatus objContractStatus = new ContractStatus();
                ContractStatusList = objContractStatus.getContractStatus();


            } catch (Exception e) {
//				Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(PublicationList != null){
                PublicationArrayAdapter = new ArrayAdapter<Publication>(IssueContract.this,
                        android.R.layout.simple_spinner_item, PublicationList);
                PublicationArrayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spIssueContractPublication.setAdapter(PublicationArrayAdapter);
            }

            if(listPageType != null){
                arrayAdapterPageType = new ArrayAdapter<PageType>(IssueContract.this,
                        android.R.layout.simple_spinner_item, listPageType);
                arrayAdapterPageType
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spIssueContractPartType.setAdapter(arrayAdapterPageType);
            }
        }
    }

    class getIssue extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        Integer PublicationID = PublicationList.get(spIssueContractPublication.getSelectedItemPosition()).getID() ;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(IssueContract.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Issue objIssue = new Issue();
                if(PublicationID > 0 ) {
                    IssueList = objIssue.getIssue(PublicationID , Boolean.valueOf(params[1]));
                }

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(IssueList != null){
                IssueArrayAdapter = new ArrayAdapter<Issue>(IssueContract.this,
                        android.R.layout.simple_spinner_item, IssueList);
                IssueArrayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spIssueContractIssue.setAdapter(IssueArrayAdapter);
                if(chbAll.isChecked()) {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int pos = 0;
                    for (int i = 0 ; i < IssueList.size() ; i++) {
                        String x = IssueList.get(i).getIssueDate().toString();

                    String yy = x.substring(0 ,x.indexOf("-"));
                        int y = Integer.valueOf(yy);

                        if(y == year){
                            pos = i;
                            break;
                        }
                    }
                    spIssueContractIssue.setSelection(pos);
                }
                //amir
            }
        }
    }

    class GetIssueContract extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        int PublicationID = PublicationList.get(spIssueContractPublication.getSelectedItemPosition()).getID() ;
        int IssueID = IssueList.get(spIssueContractIssue.getSelectedItemPosition()).getID();
        int PageTypeID = listPageType.get(spIssueContractPartType.getSelectedItemPosition()).GetPart_ID();

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(IssueContract.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Issue objIssue = new Issue();
                IssueContractList = new ArrayList<Issue>();
                IssueContractList = objIssue.GetIssueContract(PublicationID,IssueID,PageTypeID, StatusID,CoreLogInActivity.SalesID);
//                IssueContractList = objIssue.GetIssueContract(-1,-1,-1, -1);
            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(IssueContractList != null){
                FillIssueContractToTable();
            }else{
                tblIssueContract.removeAllViews();
            }
        }
    }

    private void FillIssueContractToTable(){

        try{
            tblRow = new TableRow(IssueContract.this);
            TextView tv = new TextView(IssueContract.this);
            tblIssueContract.removeAllViews();
            tblIssueContract.setBackgroundResource(R.drawable.linearlayoutborder);
//            tblIssueContract = new TableLayout(IssueContract.this);

//            Resources resource = IssueContract.this.getResources();
//            row.setBackgroundColor(resource.getColor(R.color.red)

//            TextView[] textArray = new TextView[IssueContractList.size()];
//            TableRow[] tr_head = new TableRow[IssueContractList.size()];

            for (int i = 0 ; i < IssueContractList.size() ; i++){

                tblRow = new TableRow(this);
//                tblRow.setId(i);
//                tblRow.setBackgroundColor(Color.GRAY);
//                tblRow.setBackgroundResource(R.drawable.linearlayoutborder);
                tblRow.setLayoutParams(new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                tblRow.setPadding(5, 5, 5, 5);
//                tblRow.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Toast.makeText(IssueContract.this,String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
//                    }
//                });

                if(tblIssueContract.getChildCount() == 0){

                    tv = new TextView(this);
                    tv.setText(getString(R.string.Ser));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.ClientName));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.Publication));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.PageNo));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.Status));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.Size));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.NetAmount));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.AdsText));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.DesignID));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.DesignStatus));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.SalesApprove));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.paymentType));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.IssueCount));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.IssuePublishedCount));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.IssueNotPublishedCount));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);


                    tv = new TextView(this);
                    tv.setText(getString(R.string.Design));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.FinalDesign));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tblIssueContract.addView(tblRow);

                    tblRow = new TableRow(this);
                    tblRow.setId(i);
//                    tblRow.setBackgroundColor(Color.GRAY);
//                    tblRow.setBackgroundResource(R.drawable.linearlayoutborder);
                    tblRow.setLayoutParams(new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.WRAP_CONTENT));
                    tblRow.setPadding(5, 5, 5, 5);


                }

                tblRow.setId(i);
                tblRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(IssueContract.this,String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
                    }
                });

                tblRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        if(!(GetIssueIsOpen(IssueContractList.get(v.getId()).getIssueID()))) {
                            if (IssueContractList.get(v.getId()).getStatusID() == 5 ) {
                                final int ContractPublishedDetailID = IssueContractList.get(v.getId()).getContractPublishedDetailID();

                                final CharSequence[] items = {" Cancel ", " Hold "};

                                new AlertDialog.Builder(IssueContract.this)
                                        .setTitle(getString(R.string.SelectToUpdate) + " : " +
                                                String.valueOf(IssueContractList.get(v.getId()).getSerialNo()))
                                        .setSingleChoiceItems(items, 0, null)
                                        .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.dismiss();
                                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                                // Do something useful withe the position of the selected radio button

                                                if (selectedPosition == 0) {
                                                    new UpdateContractPublishDetailStatus().execute(String.valueOf(ContractPublishedDetailID),
                                                            String.valueOf(2));
                                                } else {
                                                    new UpdateContractPublishDetailStatus().execute(String.valueOf(ContractPublishedDetailID),
                                                            String.valueOf(3));
                                                }
                                            }
                                        }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                        .show();
                            } else if(IssueContractList.get(v.getId()).getStatusID() == 3){

                            }
                        }else{
                            Toast.makeText(IssueContract.this , getString(R.string.ThisDateIsClosed), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getSerialNo()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getClientName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getPublicationName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getPageNo()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getStatusName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getSize()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getNetAmount()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);


                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getAdsText()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tv.setId(i);
                tblRow.addView(tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(IssueContract.this , AdsTextActivity.class);

                        i.putExtra("ContractID",IssueContractList.get(v.getId()).getContractID());
                        i.putExtra("ContractPublishedDetailID",IssueContractList.get(v.getId()).getContractPublishedDetailID());
                        i.putExtra("ContractTypeID",IssueContractList.get(v.getId()).getContractTypeID());
                        i.putExtra("ContractStatusID",IssueContractList.get(v.getId()).getStatusID());

                        startActivityForResult(i , 1);
                    }
                });

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getDesignID()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getDesignStatusName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getSalesApprove()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getPaymentTypeName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getIssueCount()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getIssuePublishedCount()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(IssueContractList.get(i).getIssueNotPublishedCount()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
                btn.setText(getString(R.string.Design));
                btn.setPadding(5, 5, 5, 5);
                btn.setId(i);
                tblRow.addView(btn);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

//                        String url =  getString(R.string.PaginationUrl);//(R.string.DruftClientUrl);
//                        http://87.101.143.70:1919/Advertisements/DammamMobawbeh/Druft/Client/
                            String url = String.valueOf(IssueContractList.get(v.getId()).getURL());

                            String PublicationFolderName = String.valueOf(IssueContractList.get(v.getId()).getPublicationFoldername());
                            String SClientID = String.valueOf(IssueContractList.get(v.getId()).getClientID());
                            String DesignID = String.valueOf(IssueContractList.get(v.getId()).getDesignID());

//                url = url + SClientID + "/" + DesignID + ".JPG";
                            // url = url + PublicationFolderName + "/" + "Druft" + "/" + "Client" + "/" + SClientID + "/" + DesignID + ".JPG";
                            url = url + PublicationFolderName + "/";

                            String CURL = String.valueOf(IssueContractList.get(v.getId()).getPath());
                            if(!(CURL.equals("anyType{}"))) {
                                CURL = CURL.substring(CURL.indexOf("Druft"));
                                url = url + CURL;
                            }//else{
                                
                           // }
                            Intent i = new Intent(IssueContract.this, ImageViewActivity.class);

                            i.putExtra("url", url);
                            i.putExtra("ClientID", IssueContractList.get(v.getId()).getClientID());
                            i.putExtra("DesignID", IssueContractList.get(v.getId()).getDesignID());
                            i.putExtra("ContractPublishedDetailID", IssueContractList.get(v.getId()).getContractPublishedDetailID());
                            i.putExtra("ContractTypeID", IssueContractList.get(v.getId()).getContractTypeID());
                            i.putExtra("IssueID", IssueContractList.get(v.getId()).getIssueID());
                            i.putExtra("SerialNo", IssueContractList.get(v.getId()).getSerialNo());
                            i.putExtra("ContractStatusID", IssueContractList.get(v.getId()).getStatusID());
                            i.putExtra("IsSalesApprove", IssueContractList.get(v.getId()).getSalesApprove());
                            i.putExtra("ClientName", IssueContractList.get(v.getId()).getClientName());
//                        i.putExtra("AdsCm", IssueContractList.get(v.getId()).getCm());
//                        i.putExtra("AdsCol", IssueContractList.get(v.getId()).getCol());
                            i.putExtra("PublicationID", IssueContractList.get(v.getId()).getPublicationID());
                            i.putExtra("PublicationFolderName", IssueContractList.get(v.getId()).getPublicationFoldername());
                            i.putExtra("PaymentFolderName", IssueContractList.get(v.getId()).getPaymentFoldername());
                            i.putExtra("IssueNo", IssueContractList.get(v.getId()).getIssueNo());

                            startActivityForResult(i, 1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                Button btnFinalDesign = new Button(this);
                btnFinalDesign.setLayoutParams(new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
                btnFinalDesign.setText(getString(R.string.FinalDesign));
                btnFinalDesign.setPadding(5, 5, 5, 5);
                btnFinalDesign.setId(i);
                tblRow.addView(btnFinalDesign);
                btnFinalDesign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String ContractType = "";
//                        String url =  getString(R.string.PaginationUrl);
//                        http://87.101.143.70:1919/Advertisements/DammamMobawbeh/720/cash/538648.JPG
                        try {
                            if (IssueContractList.get(v.getId()).getDefaultPath()) {
                                String url = String.valueOf(IssueContractList.get(v.getId()).getURL());

//                        url = " http://87.101.143.70:1919/Advertisements/DammamMobawbeh/720/cash/" + s + ".JPG";
                                String PublicationFolderName = String.valueOf(IssueContractList.get(v.getId()).getPublicationFoldername());
                                url = url + PublicationFolderName;
//                        url = url + "/" + String.valueOf(IssueContractList.get(v.getId()).getIssueID());
                                url = url + "/" + String.valueOf(IssueContractList.get(v.getId()).getIssueNo());


//                        if(IssueContractList.get(v.getId()).getContractTypeID() == 1){
//                            ContractType = "cash";
//                        }else if(IssueContractList.get(v.getId()).getContractTypeID() == 2){
//                            ContractType = "free";
//                        }else if(IssueContractList.get(v.getId()).getContractTypeID() == 8){
//                            ContractType = "credit";
//                        }

                                String PaymentFolderName = String.valueOf(IssueContractList.get(v.getId()).getPaymentFoldername());
                                url = url + "/" + PaymentFolderName;

//                        url = url + "/" + ContractType ;

//                        String s =String.valueOf(IssueContractList.get(v.getId()).getSerialNo());
                                String s = String.valueOf(IssueContractList.get(v.getId()).getDesignID());

                                url = url + "/" + s;

                                url = url + ".JPG";

//                        String url =  String.valueOf(IssueContractList.get(v.getId()).getURL());
////                        url = " http://87.101.143.70:1919/Advertisements/DammamMobawbeh/720/cash/" + s + ".JPG";
//                        String PublicationFolderName = String.valueOf(IssueContractList.get(v.getId()).getPublicationFoldername());
//                        url = url + PublicationFolderName  + "/";
//
//                        String CURL = String.valueOf(IssueContractList.get(v.getId()).getPath());
//                        CURL = CURL.substring(CURL.indexOf("Druft"));
//
//                        url = url + CURL ;


                                Intent i = new Intent(IssueContract.this, ImageViewActivity.class);
                                i.putExtra("url", url);
//                        i.putExtra("ContractID",IssueContractList.get(v.getId()).getContractID());
//                        i.putExtra("ContractPublishedDetailID",IssueContractList.get(v.getId()).getContractPublishedDetailID());
//                        i.putExtra("ContractTypeID",IssueContractList.get(v.getId()).getContractTypeID());
//                        i.putExtra("ContractStatusID",IssueContractList.get(v.getId()).getStatusID());

                                startActivityForResult(i, 1);
                            } else {

                                String url = String.valueOf(IssueContractList.get(v.getId()).getURL());
                                String PublicationFolderName = String.valueOf(IssueContractList.get(v.getId()).getPublicationFoldername());
                                url = url + PublicationFolderName;
                                url = url + "/" + String.valueOf(IssueContractList.get(v.getId()).getIssueNo());
                                url = url + String.valueOf(IssueContractList.get(v.getId()).getAlyaumPath());
                                String s = String.valueOf(IssueContractList.get(v.getId()).getDesignID());
                                url = url + "/" + s;
                                url = url + ".JPG";

                                Intent i = new Intent(IssueContract.this, ImageViewActivity.class);
                                i.putExtra("url", url);
                                startActivityForResult(i, 1);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });


                tblIssueContract.addView(tblRow);
                CalculateSums();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ContractStatusDialog(){
        try{

//            final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};

            seletedItems = new ArrayList();
            List<String> listItems = new ArrayList<String>();

            for (int i = 0 ; i <ContractStatusList.size() ;i++){
                listItems.add(ContractStatusList.get(i).getName().toString());
            }

            final CharSequence[] dialogList =  listItems.toArray(new CharSequence[listItems.size()]);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Select Status")
                    .setMultiChoiceItems(dialogList, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                seletedItems.add(indexSelected);

                            } else if (seletedItems.contains(indexSelected)) {
                                // Else, if the item is already in the array, remove it
                                seletedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on OK
                            //  You can write the code  to save the selected item here

                            StatusName = "";
                            StatusID = "";
                            for(int i = 0 ; i <seletedItems.size() ; i++){
//                                StatusName = StatusName + "||" + seletedItems.get(i).toString();
//                                StatusID = StatusID + "," + seletedItems.get(i).toString();
                                for(int j = 0 ; j < ContractStatusList.size() ; j++){
                                    StatusName = StatusName + "||" + ContractStatusList.get(Integer.valueOf(seletedItems.get(i).toString())).getName();
                                    StatusID = StatusID + "," + ContractStatusList.get(Integer.valueOf(seletedItems.get(i).toString())).getID();
                                    break;
                                }
                            }
                            txtIssueContractContractStatus.setText(StatusName);
                            StatusID = StatusID + ",";
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on Cancel
                        }
                    }).create();
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class UpdateContractPublishDetailStatus extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(IssueContract.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                Issue objIssue = new Issue();
                Boolean Done =objIssue.UpdateContractPublishDetailStatus(Integer.valueOf(params[0]) ,
                        Integer.valueOf(params[1]));

                if(Done)
                    x = "True";
            } catch (Exception e) {
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(result.toString().equals("True")) {
                Toast.makeText(IssueContract.this, getString(R.string.Save_Done), Toast.LENGTH_SHORT).show();
                new GetIssueContract().execute();
            }
        }
    }

    private  Boolean GetIssueIsOpen(Integer IssueID){
        try{
            Boolean Published = false ;
            for (int i = 0 ; i < IssueList.size() ; i++){
                int o = IssueList.get(i).getID();
                if(IssueID == o ){
                    Published = IssueList.get(i).getPublished();
                    break;
                }
            }
            return Published;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    private void CalculateSums(){
        try{
            if(IssueContractList != null){
             int CountOk = 0 , CountOnHold = 0 , CountNp = 0;
                for(int i = 0 ;i < IssueContractList.size() ; i++){

                    if(IssueContractList.get(i).getStatusID() == 1){
                        CountOk =   CountOk + 1;
                    }else if(IssueContractList.get(i).getStatusID() == 3){
                        CountOnHold = CountOnHold + 1;
                    }else if(IssueContractList.get(i).getStatusID() == 5){
                        CountNp = CountNp + 1 ;
                    }
                }
                txtCountOK.setText(String.valueOf(CountOk));
                txtCountOnHold.setText(String.valueOf(CountOnHold));
                txtCountNp.setText(String.valueOf(CountNp));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
