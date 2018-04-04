package com.example.simonas.tp4android;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.simonas.tp4android.TournamentAdapter.ENTRY_ID;
/**
 * Created by Simonas on 2018.04.04.
 */


public class SearchActivity extends AppCompatActivity {

    // Cloud kintamieji
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    // TODO: pasikeisti url ir cookies
    public static final String CLOUD_DB_URL = "http://java17.byethost4.com/mobile/database.php";
    public static final String COOKIES_CONTENT = "7a4d917e220fbf9a55cab3046bd1a3b7";


    SearchView searchView = null;

    RecyclerView rvTournaments;
    TournamentAdapter tournamentAdapter;

    List<Tournament> tournamentsSQLite = Collections.emptyList();

    List<Tournament> tournamentsPaieskai = new ArrayList<Tournament>();

    DatabaseSQLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.search_label);

        db = new DatabaseSQLite(SearchActivity.this);

        // Taupydami duomenų bazės resursus, darome 1 call'ą (getAllPokemonai) užkrovus paieškos langą,
        // vėliau (not implemented) pokemonų ieškome iš užpildyto sąrašo
        tournamentsSQLite = db.getAllTournaments();

        if (!tournamentsSQLite.isEmpty()) {
            setRecycleView(tournamentsSQLite);
        } else {
            Toast.makeText(this, "Duomenų bazėje nėra įrašų", Toast.LENGTH_SHORT).show();
        }

        Button btnPrideti = (Button) findViewById(R.id.btnPrideti);
        btnPrideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, EntryActivity.class);
                intent.putExtra(ENTRY_ID, -1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //adds item to actionbar
        getMenuInflater().inflate(R.menu.search, menu);
        //get search item from actionbar and get search service
        MenuItem searchitem = menu.findItem(R.id.actionSearch);
        SearchManager searchManger = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchitem != null) {
            searchView = (SearchView) searchitem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManger.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

    }

    @Override
    //every time when you press search button an actvity is recreated which in turn
    //calls this function
    protected void onNewIntent(Intent intent) {
        //get search query and create object of class AsyncFetch
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
        }
    }

    /*
        setup and hand over list pokemonaiSQLite to recyclerview
        @params list of pokemonai from db
     */
    private void setRecycleView(List<Tournament> tournaments) {
        rvTournaments = (RecyclerView) findViewById(R.id.tournament_list);
        tournamentAdapter = new TournamentAdapter(SearchActivity.this, tournaments);
        rvTournaments.setAdapter(tournamentAdapter);
        rvTournaments.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

    class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);
        String searchQuery;
        HttpURLConnection conn;
        URL url = null;

        public AsyncFetch(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Prašome palaukti");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (!tournamentsPaieskai.isEmpty()) {
                tournamentsPaieskai.clear();
            }

            // vartotojo paieska vykdoma sarase (ne db)
            for (int i = 0 ; i <tournamentsSQLite.size();i++) {
                if (tournamentsSQLite.get(i).getGame().contains(searchQuery)) {
                    tournamentsPaieskai.add(tournamentsSQLite.get(i));
                }
            }

            if (tournamentsPaieskai.isEmpty()) {
                return "no rows";
            } else {
                return "rows";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            progressDialog.dismiss();

            setRecycleView(tournamentsPaieskai);
        }

    }
}

