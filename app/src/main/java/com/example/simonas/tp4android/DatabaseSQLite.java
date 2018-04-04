package com.example.simonas.tp4android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simonas on 2018.04.04.
 */

public class DatabaseSQLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";

    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "id";
    private static final String USER_LEVEL = "userlevel";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";

    //public Tournament (int gameid, String game, String user, String format, String currency, String buyin, double result) {
    private static final String TABLE_TOURNAMENTS = "tournament";
    private static final String TOURNAMENT_ID = "id";
    private static final String TOURNAMENT_GAME = "game";
    private static final String TOURNAMENT_USER = "user";
    private static final String TOURNAMENT_FORMAT = "format";
    private static final String TOURNAMENT_CURRENCY = "currency";
    private static final String TOURNAMENT_BUYIN = "buyin";
    private static final String TOURNAMENT_RESULT = "result";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USER_LEVEL + " TEXT,"
                + USER_NAME + " TEXT,"
                + USER_PASSWORD + " TEXT,"
                + USER_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_TOURNAMENTS_TABLE = "CREATE TABLE " + TABLE_TOURNAMENTS + "("
                + TOURNAMENT_ID + " INTEGER PRIMARY KEY,"
                + TOURNAMENT_GAME + " TEXT,"
                + TOURNAMENT_USER + " TEXT,"
                + TOURNAMENT_FORMAT + " TEXT,"
                + TOURNAMENT_CURRENCY + " TEXT,"
                + TOURNAMENT_BUYIN + " REAL,"
                + TOURNAMENT_RESULT + " REAL" + ")";
        db.execSQL(CREATE_TOURNAMENTS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENTS);

        //Create tables again
        onCreate(db);
    }

    void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_LEVEL, user.getUserlevel());
        values.put(USER_NAME, user.getUsernameForRegister());
        values.put(USER_PASSWORD, user.getPasswordForRegister());
        values.put(USER_EMAIL, user.getEmailForRegister());

        //Inserting row
        db.insert(TABLE_USERS, null, values);

        //Closing database connection;
        db.close();
    }

    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{
                        USER_ID,
                        USER_LEVEL,
                        USER_NAME,
                        USER_PASSWORD,
                        USER_EMAIL
                },
                USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return user;
    }

    public List<User> getAllusers(){
        List<User> users = new ArrayList<User>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                User user = new User();

                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserlevel(cursor.getString(1));
                user.setUsernameForRegister(cursor.getString(2));
                user.setPasswordForRegister(cursor.getString(3));
                user.setEmailForRegister(cursor.getString(4));

                //adding user to list
                users.add(user);
            } while (cursor.moveToNext());
        }

        //return users list
        return users;
    }

    public boolean isValidUser(String username, String password){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE "
                        + USER_NAME + "='" + username + "'AND " + USER_PASSWORD + "='" + password + "'", null);
        if (c.getCount() > 0)
            return true;
        return false;


    }

    public void addTournament (Tournament tournament) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_GAME, tournament.getGame());
        // values.put(TOURNAMENT_USER, tournament.getUser()); pasižiurėt kad netruktu
        values.put(TOURNAMENT_FORMAT, tournament.getFormat());
        values.put(TOURNAMENT_CURRENCY, tournament.getCurrency());
        values.put(TOURNAMENT_BUYIN, tournament.getBuyin());
        values.put(TOURNAMENT_RESULT, tournament.getResult());

        //Inserting row
        db.insert(TABLE_TOURNAMENTS, null, values);

        //Closing database connection;
        db.close();
    }

    public Tournament getTournament(int id) {
        Tournament tournament = new Tournament();

        List<Tournament> tournaments = new ArrayList<Tournament>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM pokemonai WHERE id = " + id + "", null);
        if (cursor.moveToFirst()) {
            do {
                tournament.setGameid(Integer.parseInt((cursor.getString(0))));
                tournament.setGame(cursor.getString(1));
                tournament.setFormat(cursor.getString(2));
                tournament.setCurrency(cursor.getString(3));
                tournament.setBuyin(cursor.getString(4));
                tournament.setResult(Double.parseDouble(cursor.getString(5)));

                //adding tournament to list

                tournaments.add(tournament);
            } while (cursor.moveToNext());

        }
        return tournaments.get(0);
    }

    public List<Tournament> getAllTournaments(){
        List<Tournament> tournaments = new ArrayList<Tournament>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TOURNAMENTS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Tournament tournament = new Tournament();

                tournament.setGameid(Integer.parseInt((cursor.getString(0))));
                tournament.setGame(cursor.getString(1));
                tournament.setFormat(cursor.getString(2));
                tournament.setCurrency(cursor.getString(3));
                tournament.setBuyin(cursor.getString(4));
                tournament.setResult(Double.parseDouble(cursor.getString(5)));
                //adding pokemon to list
                tournaments.add(tournament);
            } while (cursor.moveToNext());
        }

        //return users list
        return tournaments;
    }

    public void updateTournament(Tournament tournament){
        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_GAME, tournament.getGame());
        // values.put(TOURNAMENT_USER, tournament.getUser()); pasižiurėt kad netruktu
        values.put(TOURNAMENT_FORMAT, tournament.getFormat());
        values.put(TOURNAMENT_CURRENCY, tournament.getCurrency());
        values.put(TOURNAMENT_BUYIN, tournament.getBuyin());
        values.put(TOURNAMENT_RESULT, tournament.getResult());

        getReadableDatabase().update(TABLE_TOURNAMENTS, values, " id = "+tournament.getGameid(), null);
        getWritableDatabase().close();
    }

    public void deleteTournament(Tournament tournament){
        getWritableDatabase().delete(TABLE_TOURNAMENTS, " id = " +tournament.getGameid(), null);
        getWritableDatabase().close();
    }
}
