package com.example.simonas.tp4android.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


/**
 * Created by Simonas on 2018.04.04.
 */
import com.example.simonas.tp4android.model.DatabaseSQLite;
import com.example.simonas.tp4android.R;
import com.example.simonas.tp4android.model.Tournament;

import static com.example.simonas.tp4android.model.TournamentAdapter.ENTRY_ID;

public class EntryActivity extends AppCompatActivity {

    Button btnSubmit, btnUpdate, btnDelete;

    EditText etResult, etTournament;
    RadioGroup rbGroup;
    RadioButton rb22, rb55, rb109;
    CheckBox cbKnockOut, cbRebuy, cbFreezeOut;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    Tournament pradinisTournament;
    Tournament galutinisTournament;

    DatabaseSQLite dbtp;

    String items[] = {"USD", "Euro", "GBP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        dbtp = new DatabaseSQLite(EntryActivity.this);

        int entryID = -1;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (!extras.isEmpty()) {
                entryID = extras.getInt(ENTRY_ID);
            }
        } else { // jeigu yra naujas irasas, id = -1, jeigu egzistuojantis, bus teigiamas
            entryID = (Integer) savedInstanceState.getSerializable(ENTRY_ID);
        }

        if (entryID == -1) {
            setTitle(R.string.new_entry_label2);
        } else {
            setTitle(R.string.entry_update_label2);
        }

        pradinisTournament = new Tournament();
        if (entryID == -1) { //naujas irasas
            pradinisTournament.setGameid(-1);
            pradinisTournament.setGame("");
            pradinisTournament.setFormat("Freeze Out");
            pradinisTournament.setCurrency("USD");
            pradinisTournament.setBuyin("55$");
            pradinisTournament.setResult(0);

        } else { // egzistuojantis irasas
            pradinisTournament = dbtp.getTournament(entryID);
        }
        galutinisTournament = new Tournament();

        //setTitle(R.string.new_entry_label);

        btnSubmit = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        if (entryID == -1){ //naujas irasas - disable update button
            btnUpdate.setEnabled(false);
            btnSubmit.setEnabled(true);
        }else { // egzistuojantis irasas - disable submit
            btnUpdate.setEnabled(true);
            btnSubmit.setEnabled(false);
        }

        etResult = (EditText) findViewById(R.id.etResult);
        etTournament= (EditText) findViewById(R.id.etTournamentName);

        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rb22 = (RadioButton) findViewById(R.id.rb22);
        rb55 = (RadioButton) findViewById(R.id.rb55);
        rb109 = (RadioButton) findViewById(R.id.rb109);

        cbKnockOut = (CheckBox) findViewById(R.id.cbKnockOut);
        cbRebuy = (CheckBox) findViewById(R.id.cbRebuy);
        cbFreezeOut = (CheckBox) findViewById(R.id.cbFreezeOut);

        spinner = (Spinner) findViewById(R.id.spinner);

        adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        fillFields(pradinisTournament);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getFields();
                dbtp.addTournament(galutinisTournament);
                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();
                dbtp.updateTournament(galutinisTournament);
                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();
                dbtp.deleteTournament(galutinisTournament);
                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });
    }

    private void getFields(){

        String name = etTournament.getText().toString();
        double result = Double.parseDouble(etResult.getText().toString());
        String rb = "";
        String spinnerText = "";

        if (rb22.isChecked()) {
            rb = rb22.getText().toString();
        } else if (rb55.isChecked()) {
            rb = rb55.getText().toString();
        } else {
            rb = rb109.getText().toString();
        }

        String checkboxText = "";

        if (cbRebuy.isChecked()) {
            checkboxText = checkboxText + "Rebuy,";
        }
        if (cbKnockOut.isChecked()) {
            checkboxText = checkboxText + "KnockOut,";
        }
        if (cbFreezeOut.isChecked()) {
            checkboxText = checkboxText + "FreezeOut";
        }

        spinnerText = spinner.getSelectedItem().toString();

        galutinisTournament.setGameid(pradinisTournament.getGameid());
        galutinisTournament.setGame(name);
        galutinisTournament.setFormat(checkboxText);
        galutinisTournament.setCurrency(spinnerText);
        galutinisTournament.setBuyin(rb);
        galutinisTournament.setResult(result);
    }

    private void fillFields (Tournament tournament){
        etTournament.setText(tournament.getGame());
        etResult.setText(String.valueOf(tournament.getResult()));

        //rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rb22.setChecked(tournament.getBuyin().equals("22$"));
        rb55.setChecked(tournament.getBuyin().equals("55$"));
        rb109.setChecked(tournament.getBuyin().equals("109$"));

        cbKnockOut.setChecked(tournament.getFormat().equals("KnockOut"));
        cbRebuy.setChecked(tournament.getFormat().equals("Rebuy"));
        cbFreezeOut.setChecked(tournament.getFormat().equals("FreezeOut"));

        spinner.setSelection(adapter.getPosition(tournament.getCurrency()));
    }

   /* @Override //Uzkomentinta mygtuko override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFields();
                if (pradinisTournament.equals(galutinisTournament)) { //Nebuvo pakeistas
                    finish();
                } else {  //Buvo pakeistas
                    showDialog();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                EntryActivity.this);

        // set title
        alertDialogBuilder.setTitle("Įspėjimas");

        // set dialog message
        alertDialogBuilder
                .setMessage("Išsaugoti pakeitimus?")
                .setCancelable(false)
                .setPositiveButton("Taip",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                })
                .setNegativeButton("Ne",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        EntryActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
