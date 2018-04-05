package com.example.simonas.tp4android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Simonas on 2018.04.04.
 */



public class TournamentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<Tournament> tournaments = Collections.emptyList();

    public static final String ENTRY_ID = "com.example.simonas.tp4android";

    //konstruktorius reikalingas susieti
    // esama langa ir perduoti sarasa pokemonui is DB
    public TournamentAdapter(Context context,List<Tournament> tournaments){
        this.context = context;
        this.tournaments = tournaments;
        inflater = LayoutInflater.from(context);
    }

    @Override
    //inflate the layout when viewholder is created
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_tournament,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    //bind data
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //get current position of item in
        // recyclerview to bind data and assign value from list
        MyHolder myHolder = (MyHolder) holder;
        Tournament currentTournament = tournaments.get(position);
        myHolder.tvGameId.setText("Game ID: "         + currentTournament.getGameid());
        myHolder.tvGame.setText(                        currentTournament.getGame());
        myHolder.tvFormat.setText("Type: "            + currentTournament.getFormat());
        myHolder.tvCurrency.setText("Currency: "      + currentTournament.getCurrency());
        myHolder.tvBuyIn.setText("Buyin: "            + currentTournament.getBuyin());
}

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvGameId,tvGame,tvFormat,tvCurrency,tvBuyIn;

        public MyHolder(View itemView){
            super(itemView);
            tvGameId         = (TextView)itemView.findViewById(R.id.id);
            tvGame           = (TextView)itemView.findViewById(R.id.etTournamentName);
            tvFormat         = (TextView)itemView.findViewById(R.id.type);
            tvCurrency       = (TextView)itemView.findViewById(R.id.currency);
            tvBuyIn          = (TextView)itemView.findViewById(R.id.buyin);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int itemPosition = getAdapterPosition();
            int TournamentsID = tournaments.get(itemPosition).getGameid();

            Tournament tournament = tournaments.get(itemPosition);

            Intent intent = new Intent(context, EntryActivity.class);

            intent.putExtra(ENTRY_ID, TournamentsID);
            context.startActivity(intent);
        }
    }
}
