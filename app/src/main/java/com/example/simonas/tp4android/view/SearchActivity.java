package com.example.simonas.tp4android.view;


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

import com.example.simonas.tp4android.model.DatabaseSQLite;
import com.example.simonas.tp4android.R;
import com.example.simonas.tp4android.model.Tournament;
import com.example.simonas.tp4android.model.TournamentAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.simonas.tp4android.model.TournamentAdapter.ENTRY_ID;
/**
 * Created by Simonas on 2018.04.04.
 */


public class SearchActivity extends AppCompatActivity {

    SearchView searchView = null;

    RecyclerView rvTournaments;
    TournamentAdapter tournamentAdapter;

    List<Tournament> tournamentsSQLite = Collections.emptyList();

    List<Tournament> tournamentsSearch = new ArrayList<Tournament>();

    DatabaseSQLite dbtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.search_label);

        dbtp = new DatabaseSQLite(SearchActivity.this);

        tournamentsSQLite = dbtp.getAllTournaments();

        if (!tournamentsSQLite.isEmpty()) {
            setRecycleView(tournamentsSQLite);
        } else {
            Toast.makeText(this, "Duomenų bazėje nėra įrašų", Toast.LENGTH_SHORT).show();
        }

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
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

    private void setRecycleView(List<Tournament> tournamentsSQLite) {
        rvTournaments = (RecyclerView) findViewById(R.id.tournament_list);
        tournamentAdapter = new TournamentAdapter(SearchActivity.this, tournamentsSQLite);
        rvTournaments.setAdapter(tournamentAdapter);
        rvTournaments.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

    class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);
        String searchQuery;

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

            if (!tournamentsSearch.isEmpty()) {
                tournamentsSearch.clear();
            }

            //vartotojo paieska vykdoma sarase (ne db)
            for (int i = 0; i < tournamentsSQLite.size(); i++) {
                if (tournamentsSQLite.get(i).getGame().contains(searchQuery)) {
                    tournamentsSearch.add(tournamentsSQLite.get(i));
                }
            }

            if (tournamentsSearch.isEmpty()) {
                return "no rows";
            } else {
                return "rows";
            }
        }

            @Override
            protected void onPostExecute (String result){
                progressDialog.dismiss();

                setRecycleView(tournamentsSearch);
            }
    }
}

