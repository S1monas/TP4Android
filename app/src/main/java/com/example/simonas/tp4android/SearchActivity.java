package com.example.simonas.tp4android;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.Collections;
import java.util.List;

import static com.example.simonas.tp4android.TournamentAdapter.ENTRY_ID;
/**
 * Created by Simonas on 2018.04.04.
 */


public class SearchActivity extends AppCompatActivity {

    SearchView searchView = null;

    RecyclerView rvTournaments;
    TournamentAdapter tournamentAdapter;

    List<Tournament> tournamentsSQLite = Collections.emptyList();

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

    private void setRecycleView(List<Tournament> tournaments) {
        rvTournaments = (RecyclerView) findViewById(R.id.tournament_list);
        tournamentAdapter = new TournamentAdapter(SearchActivity.this, tournaments);
        rvTournaments.setAdapter(tournamentAdapter);
        rvTournaments.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

}

