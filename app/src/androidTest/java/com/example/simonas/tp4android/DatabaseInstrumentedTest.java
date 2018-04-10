package com.example.simonas.tp4android;

/**
 * Created by Simonas on 2018.04.10.
 */


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.simonas.tp4android.model.DatabaseSQLite;
import com.example.simonas.tp4android.model.Tournament;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private Context instrumentationCtx;
    private DatabaseSQLite db;
    private List<Tournament> tournaments;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
        db = new DatabaseSQLite(instrumentationCtx);
        tournaments = Collections.emptyList();
    }

    @Test
    public void testAddPokemon() {
        // (String game, String format, String currency, String buyin, double result) {
        Tournament tournament = new Tournament("Test 11","REbuy", "USD","55$",100);
        db.addTournament(tournament);
        tournaments = db.getTournamentByName(tournament.getGame());
        assertTournamentEqual(tournament, tournaments.get(0));
    }

    private void assertTournamentEqual(Tournament expected, Tournament actual) {
        Assert.assertEquals(expected.getGame(),actual.getGame());
        Assert.assertEquals(expected.getFormat(),actual.getFormat());
        Assert.assertEquals(expected.getCurrency(),actual.getCurrency());
        Assert.assertEquals(expected.getBuyin(),actual.getBuyin());
        Assert.assertEquals(expected.getResult(),actual.getResult());
    }

}