package com.example.amir.core.ADR.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RemoteController;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Ads_Category;
import com.example.amir.core.ADR.activity.Classes.Client;
import com.example.amir.core.ADR.activity.Classes.ColorType;
import com.example.amir.core.ADR.activity.Classes.DesignRequest;
import com.example.amir.core.ADR.activity.Classes.EnglishNameWithAgency;
import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.ADR.activity.Classes.Publication;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

import java.util.List;

public class DesignRequestActivity extends AppCompatActivity {

    EditText txtDesignRequestClientName , txtDesignRequestCm , txtDesignRequestCol , txtDesignRequestNote ;
    Button btnDesignRequestSearch , btnDesignRequestAdd , btnDesignRequestUpdate;
    TableLayout tblDesignRquest;
    TableRow tblRow;
    Spinner DesignRequestspinColorType , DesignRequestspinPublication ,DesignRequestspinAdsCategory ;
    ImageView DesignRequestimgSearchClient;

    List<EnglishNameWithAgency> listArrayClient ;//=  new List<EnglishNameWithAgency>();
    List<EnglishNameWithAgency> listEnglishNameWithAgency;
    ArrayAdapter<EnglishNameWithAgency> ArrayClient;
    AlertDialog alertDialog1;

    List<ColorType> ColorTypeList ;
    ArrayAdapter<ColorType> ColorTypeArrayAdapter;

    List<Publication> PublicationList ;
    ArrayAdapter<Publication> PublicationArrayAdapter;

    List<Ads_Category> Ads_CategoryList;
    ArrayAdapter<Ads_Category> Ads_CategoryArrayAdapter;

    List<DesignRequest> DesignRequestlist;

    int ClientID = 0 , DesignID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adr_activity_design_request);
//        getActionBar().hide();
        Init();

        new GetColorType().execute();
        new GetPublication().execute();
        new GetAds_Category().execute();
    }

    private void Init(){
        try{


            txtDesignRequestClientName = (EditText) findViewById(R.id.txtDesignRequestClientName);
            txtDesignRequestCm = (EditText) findViewById(R.id.txtDesignRequestCm);
            txtDesignRequestCol= (EditText) findViewById(R.id.txtDesignRequestCol);
            txtDesignRequestNote = (EditText)findViewById(R.id.txtDesignRequestNote);

            btnDesignRequestSearch = (Button) findViewById(R.id.btnDesignRequestSearch);
            btnDesignRequestAdd = (Button) findViewById(R.id.btnDesignRequestAdd);
            btnDesignRequestUpdate = (Button) findViewById(R.id.btnDesignRequestUpdate);


            DesignRequestspinColorType = (Spinner) findViewById(R.id.DesignRequestspinColorType);
            DesignRequestspinPublication = (Spinner) findViewById(R.id.DesignRequestspinPublication);
            DesignRequestspinAdsCategory = (Spinner) findViewById(R.id.DesignRequestspinAdsCategory);

            DesignRequestimgSearchClient = (ImageView) findViewById(R.id.DesignRequestimgSearchClient);

            DesignRequestimgSearchClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDesignRequestUpdate.setEnabled(false);
                    if (txtDesignRequestClientName.getText().toString().trim().length() > 2) {
                        new GetEnglishNameWithAgency().execute();
                    }
                }
            });
            btnDesignRequestSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //new GetEnglishNameWithAgency().execute();
                    btnDesignRequestUpdate.setEnabled(false);
                        if (ClientID > 0) {
                            new Android_SelectDesignRequest().execute();

                    }else{
                        Toast.makeText(DesignRequestActivity.this,getString(R.string.Please_Select_Client),Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnDesignRequestAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ClientID > 0) {
                            if(IsValid()) {
                                new Android_InsertDesignRequest().execute();
                            }else{
                                Toast.makeText(DesignRequestActivity.this,getString(R.string.Check),Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(DesignRequestActivity.this, getString(R.string.Please_Select_Client), Toast.LENGTH_SHORT).show();
                        }
                }
            });

            btnDesignRequestUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DesignID > 0) {
                        new UpdateDesignRequest().execute(String.valueOf(DesignID));
                    }
                }
            });


            tblDesignRquest = (TableLayout) findViewById(R.id.tblDesignRquest);

            Intent i = getIntent();
            String ClientName = "";
            ClientName = i.getStringExtra("ClientName");
            if(ClientName.toString().trim().length() > 0){

                txtDesignRequestClientName.setText(ClientName.toString());
                btnDesignRequestUpdate.setEnabled(false);
                if (txtDesignRequestClientName.getText().toString().trim().length() > 2) {
                    new GetEnglishNameWithAgency().execute();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class GetEnglishNameWithAgency extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;
        String txt = txtDesignRequestClientName.getText()
                .toString();


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                listArrayClient = new EnglishNameWithAgency().GetEnglishNameWithAgency(txt);

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            AutgetView();

        }
    }

    private void AutgetView(){
        try{

            ArrayClient = new ArrayAdapter<EnglishNameWithAgency>(DesignRequestActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    listArrayClient);

            onCreateDialogSingleChoice(ArrayClient);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class GetColorType extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                ColorType objColorType = new ColorType();
                ColorTypeList = objColorType.GetColorType();

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(ColorTypeList != null){
                ColorTypeArrayAdapter = new ArrayAdapter<ColorType>(DesignRequestActivity.this,
                        android.R.layout.simple_spinner_item, ColorTypeList);
                ColorTypeArrayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                DesignRequestspinColorType.setAdapter(ColorTypeArrayAdapter);
            }
        }
    }

    class GetPublication extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Publication objPublication = new Publication();
                PublicationList = objPublication.getPublication();

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(PublicationList != null){
                PublicationArrayAdapter = new ArrayAdapter<Publication>(DesignRequestActivity.this,
                        android.R.layout.simple_spinner_item, PublicationList);
                PublicationArrayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                DesignRequestspinPublication.setAdapter(PublicationArrayAdapter);

                for (int i = 0 ; i < PublicationList.size() ; i++){
                    int PubID = PublicationList.get(i).getID();
                    if(PubID == CoreLogInActivity.DefaultPublication){
                        DesignRequestspinPublication.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    class GetAds_Category extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Ads_Category objAds_Category1 = new Ads_Category();
                Ads_CategoryList = objAds_Category1.GetAds_Category();

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(Ads_CategoryList != null){
                Ads_CategoryArrayAdapter = new ArrayAdapter<Ads_Category>(DesignRequestActivity.this,
                        android.R.layout.simple_spinner_item, Ads_CategoryList);
                Ads_CategoryArrayAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                DesignRequestspinAdsCategory.setAdapter(Ads_CategoryArrayAdapter);

            }
        }
    }

    public void onCreateDialogSingleChoice(ArrayAdapter adapterArea){

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO Auto-generated method stub
                    ListView lw = ((AlertDialog) dialog).getListView();
                    Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                    EnglishNameWithAgency SelectedArea = (EnglishNameWithAgency) checkedItem;
                    Toast.makeText(getApplicationContext(),
                            checkedItem.toString(), Toast.LENGTH_SHORT).show();
                    txtDesignRequestClientName.setText(SelectedArea.GetNameEnglish());
                    ClientID = SelectedArea.GetID();
                    alertDialog1.dismiss();

                    if(ClientID > 0){
//                        new Android_SelectDesignRequest().execute();
                    }
                }
            });
            alertDialog1 = builder.create();
            alertDialog1.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class Android_SelectDesignRequest extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;
        String txt = txtDesignRequestClientName.getText()
                .toString();


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                DesignRequest objDesignRequest = new DesignRequest();
                DesignRequestlist = objDesignRequest.Android_SelectDesignRequest(ClientID);

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(DesignRequestlist != null){

                FillDesignRquestToTable();
            }else{
                tblDesignRquest.removeAllViews();
            }
        }
    }

    private void FillDesignRquestToTable(){

        try{
            tblRow = new TableRow(DesignRequestActivity.this);
            TextView tv = new TextView(DesignRequestActivity.this);
            tblDesignRquest.removeAllViews();
            tblDesignRquest.setBackgroundResource(R.drawable.linearlayoutborder);

            for (int i = 0 ; i < DesignRequestlist.size() ; i++){


                if(tblDesignRquest.getChildCount() == 0){


                    tblRow = new TableRow(this);
//                tblRow.setId(i);
//                tblRow.setBackgroundColor(Color.GRAY);
//                tblRow.setBackgroundResource(R.drawable.linearlayoutborder);
                    tblRow.setLayoutParams(new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT));
                    tblRow.setPadding(5, 5, 5, 5);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.ID));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tv = new TextView(this);
                    tv.setText(getString(R.string.ClientID));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
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
                    tv.setText(getString(R.string.Note));
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
                    tv.setText(getString(R.string.RequestDate));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);


                    tv = new TextView(this);
                    tv.setText(getString(R.string.DesignerNote));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);


                    tv = new TextView(this);
                    tv.setText(getString(R.string.View));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.linearlayoutborder);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    tblRow.addView(tv);

                    tblDesignRquest.addView(tblRow);

                }


                tblRow = new TableRow(this);
                tblRow.setId(i);
                tblRow.setLayoutParams(new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                tblRow.setPadding(5, 5, 5, 5);

                tblRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        return true;
                    }
                });

                tblRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        new UpdateDesignRequest().equals(DesignRequestlist.get(v.getId()).getID());

                        btnDesignRequestUpdate.setEnabled(false);
                        txtDesignRequestCm.setText(String.valueOf(DesignRequestlist.get(v.getId()).getCm()));
                        txtDesignRequestCol.setText(String.valueOf(DesignRequestlist.get(v.getId()).getCol()));
                        txtDesignRequestNote.setText(String.valueOf(DesignRequestlist.get(v.getId()).getNote()));
                        int CColorTypeID = DesignRequestlist.get(v.getId()).getColorType();
                        int CPublicationID = DesignRequestlist.get(v.getId()).getPublicationID();
                        int CAdsCategory = DesignRequestlist.get(v.getId()).getAdsCategory();


                        for (int i = 0 ; i < ColorTypeList.size() ; i++){
                            int ColorTypeID = ColorTypeList.get(i).getID();
                            if(ColorTypeID == CColorTypeID){
                                DesignRequestspinColorType.setSelection(i);
                                break;
                            }
                        }
                        for (int i = 0 ; i < PublicationList.size() ; i++){
                            int PublicationID = PublicationList.get(i).getID();
                            if(PublicationID == CPublicationID){
                                DesignRequestspinPublication.setSelection(i);
                                break;
                            }
                        }
                        for (int i = 0 ; i < Ads_CategoryList.size() ; i++){
                            int AdsCategoryID = Ads_CategoryList.get(i).GetID();
                            if(AdsCategoryID == CAdsCategory){
                                DesignRequestspinAdsCategory.setSelection(i);
                                break;
                            }
                        }

                        DesignID = DesignRequestlist.get(v.getId()).getID();
                        if (DesignID > 0) {
                            if(DesignRequestlist.get(v.getId()).getAllowedToEdit()) {
                                btnDesignRequestUpdate.setEnabled(true);
                                txtDesignRequestCm.setEnabled(false);
                                txtDesignRequestCol.setEnabled(false);
                                DesignRequestspinColorType.setEnabled(false);
                                DesignRequestspinAdsCategory.setEnabled(false);
                            }else{
                                btnDesignRequestUpdate.setEnabled(false);
                            }
                        } else {
                            btnDesignRequestUpdate.setEnabled(false);
                        }
                    }
                });

                tblRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final int Index = v.getId();
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(
                                DesignRequestActivity.this);
                        builder1.setMessage(getString(R.string.CancelDesign) + String.valueOf(DesignRequestlist.get(v.getId()).getID()));
                        builder1.setTitle(getString(R.string.AreYouSure));
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(getString(R.string.Yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        new CancelDesign().execute(String.valueOf(DesignRequestlist.get(Index).getID()));
                                    }
                                });
                        builder1.setNegativeButton(getString(R.string.No),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        android.app.AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return false;
                    }
                });

                tv = new TextView(this);
                tv.setText(String.valueOf(DesignRequestlist.get(i).getID()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(DesignRequestlist.get(i).getClientID()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(txtDesignRequestClientName.getText().toString()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(DesignRequestlist.get(i).getNote()));
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
                        String s = String.valueOf(DesignRequestlist.get(v.getId()).getNote());
                        Intent i = new Intent(DesignRequestActivity.this , EditTextActivity.class);
                        i.putExtra("DesignRequestNoteHeader" , getString(R.string.Note));
                        i.putExtra("DesignRequestNote" , s);
                        startActivity(i);
                    }
                });

                tv = new TextView(this);
                tv.setText(String.valueOf(DesignRequestlist.get(i).getStatusName()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                tv = new TextView(this);
                tv.setText(String.valueOf(DesignRequestlist.get(i).getRequestDate()));
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.border);
                tv.setPadding(5, 5, 5, 5);
                tv.setTextSize(20);
                tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                tblRow.addView(tv);

                Button DesignerNote = new Button(this);
                DesignerNote.setLayoutParams(new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
                DesignerNote.setText(getString(R.string.View));
                DesignerNote.setPadding(5, 5, 5, 5);
                DesignerNote.setId(i);
                tblRow.addView(DesignerNote);

                DesignerNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = String.valueOf(DesignRequestlist.get(v.getId()).getDesignerNote());
                        Intent i = new Intent(DesignRequestActivity.this , EditTextActivity.class);
                        i.putExtra("DesignRequestNoteHeader" , getString(R.string.DesignerNote));
                        i.putExtra("DesignRequestNote" ,s );
                        startActivity(i);
                    }
                });

                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
                btn.setText(getString(R.string.View));
                btn.setPadding(5, 5, 5, 5);
                btn.setId(i);
                tblRow.addView(btn);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//http://87.101.143.70:1919/Advertisements/DammamMobawbeh/Druft/Client/
//                        String url =  String.valueOf(DesignRequestlist.get(v.getId()).getURL());
//                        url = url +  String.valueOf(DesignRequestlist.get(v.getId()).getPublicationFolderName());
//                        url = url + "/Druft/Client/";
//                        String SClientID = String.valueOf(ClientID);
//                        String DesignID = String.valueOf(DesignRequestlist.get(v.getId()).getID());
//                        url = url + SClientID + "/" + DesignID + ".JPG";
                try {


                        String url =  String.valueOf(DesignRequestlist.get(v.getId()).getURL());
                        url = url +  String.valueOf(DesignRequestlist.get(v.getId()).getPublicationFolderName() + "/");

                        String CURL = String.valueOf(DesignRequestlist.get(v.getId()).getPath());
                    if(!(CURL.equals("anyType{}"))) {
                        CURL = CURL.substring(CURL.indexOf("Druft"));
                    }
                        url = url + CURL ;
                            Intent i = new Intent(DesignRequestActivity.this, ImageViewActivity.class);
                            i.putExtra("url", url);
                            startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                    }
                });


                tblDesignRquest.addView(tblRow);

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class Android_InsertDesignRequest extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;
        String Note = txtDesignRequestNote.getText().toString();
        Double Cm = Double.valueOf(txtDesignRequestCm.getText().toString());
        Double Col =  Double.valueOf(txtDesignRequestCol.getText().toString());
        int ColorTypeID = ColorTypeList.get(DesignRequestspinColorType.getSelectedItemPosition()).getID();
        int PublicationID = PublicationList.get(DesignRequestspinPublication.getSelectedItemPosition()).getID();
        int AdsCategoryID = Ads_CategoryList.get(DesignRequestspinAdsCategory.getSelectedItemPosition()).GetID();


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                DesignRequest objDesignRequest = new DesignRequest();
               int NewID = objDesignRequest.Android_InsertDesignRequest(ClientID,Note, Cm,Col,ColorTypeID ,CoreLogInActivity.UserID,
                       CoreLogInActivity.SalesID , PublicationID,AdsCategoryID);

                if(NewID > 0){
                    x = "Done";
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
            if(result.toString().equals("Done")){
                txtDesignRequestCm.setText("0");
                txtDesignRequestCol.setText("0");
                txtDesignRequestNote.setText("");

                Toast.makeText(DesignRequestActivity.this,getString(R.string.Save_Done),Toast.LENGTH_SHORT).show();
                new Android_SelectDesignRequest().execute();
            }
        }
    }

    class UpdateDesignRequest extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;
        String Note = txtDesignRequestNote.getText().toString();
        Double Cm = Double.valueOf(txtDesignRequestCm.getText().toString());
        Double Col =  Double.valueOf(txtDesignRequestCol.getText().toString());
        int ColorTypeID = ColorTypeList.get(DesignRequestspinColorType.getSelectedItemPosition()).getID();
        int PublicationID = PublicationArrayAdapter.getItem(DesignRequestspinPublication.getSelectedItemPosition()).getID();
        int AdsCategoryID = Ads_CategoryList.get(DesignRequestspinAdsCategory.getSelectedItemPosition()).GetID();

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                DesignRequest objDesignRequest = new DesignRequest();
                Boolean Updated = objDesignRequest.UpdateDesignRequest(Integer.valueOf(params[0]),3 , Cm , Col , Note , ColorTypeID
                        ,PublicationID,AdsCategoryID);

                if(Updated){
                    x = "Done";
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
            if(result.toString().equals("Done")){
                txtDesignRequestCm.setText("0");
                txtDesignRequestCol.setText("0");
                txtDesignRequestNote.setText("");

                Toast.makeText(DesignRequestActivity.this,getString(R.string.Save_Done),Toast.LENGTH_SHORT).show();
                new Android_SelectDesignRequest().execute();
            }
        }
    }

    boolean IsValid(){
        try{
            Boolean Valid = true;
            Integer Errors = 0 ;

            if(txtDesignRequestCm.getText().toString().trim().length() <  1 ||
                    Double.valueOf(txtDesignRequestCm.getText().toString()) < .1){
                txtDesignRequestCm.setError(getString(R.string.Error));
                Errors = Errors + 1;
            }else{
                txtDesignRequestCm.setError(null);
            }

            if(txtDesignRequestCol.getText().toString().trim().length() <  1 ||
                    Double.valueOf(txtDesignRequestCol.getText().toString()) < .1){
                txtDesignRequestCol.setError(getString(R.string.Error));
                Errors = Errors + 1;
            }else{
                txtDesignRequestCol.setError(null);
            }

            if(txtDesignRequestNote.getText().toString().trim().length() <  1 ){
                txtDesignRequestNote.setError(getString(R.string.Error));
                Errors = Errors + 1;
            }else{
                txtDesignRequestNote.setError(null);
            }

            if(Errors > 0){
                Valid = false;
            }else{
                Valid = true;
            }

            return Valid ;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    class CancelDesign extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DesignRequestActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                DesignRequest objDesignRequest = new DesignRequest();
                Boolean UpdateDesignCancel = objDesignRequest.UpdateDesignRequestCancel(Integer.valueOf(params[0]) , 7);

                if(UpdateDesignCancel)
                    x = "True";
            } catch (Exception e) {
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(result.toString().equals("True"))
                Toast.makeText(DesignRequestActivity.this,getString(R.string.Save_Done),Toast.LENGTH_SHORT).show();
//            ImageViewActivity.this.finish();
            new Android_SelectDesignRequest().execute();
        }
    }
}
