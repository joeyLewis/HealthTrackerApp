package com.blogspot.onekeyucd.healthtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.blogspot.onekeyucd.healthtracker.Database.SaveGameContract;
import com.blogspot.onekeyucd.healthtracker.Database.SaveGameDbHelper;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {

    private static final String[] projection = {
            SaveGameContract.SaveGameEntry._ID,
            SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_NAME,
            SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_HP
    };

    private static final String sortOrder =
            SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_NAME + " ASC";

    public static List<PlayerFragment> getLastGame(Context context) {

        List<PlayerFragment> players = new ArrayList<>();

        try {
            SaveGameDbHelper dbHelper = new SaveGameDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor c = db.query(SaveGameContract.SaveGameEntry.TABLE_NAME,
                    projection, null, null, null, null, sortOrder);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                String playerName = c.getString(c.getColumnIndex(SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_NAME));
                int playerHP = c.getInt(c.getColumnIndex(SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_HP));
                PlayerFragment player = PlayerFragment.newInstance(playerName, playerHP);
                players.add(player);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Toast.makeText(context, R.string.failed_load, Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

        return players;
    }

    public static void writeNewSave(Context context) {
        SaveGameDbHelper dbHelper = new SaveGameDbHelper(context);
        dbHelper.onUpgrade(dbHelper.getReadableDatabase(), 1, 1);
    }

    public static void savePlayer(PlayerFragment player, Context context) {
        try {
            SaveGameDbHelper dbHelper = new SaveGameDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_NAME, player.getName());
            values.put(SaveGameContract.SaveGameEntry.COLUMN_NAME_PLAYER_HP, player.getHP());
            db.insert(SaveGameContract.SaveGameEntry.TABLE_NAME, "null", values);
        } catch (Exception e) {
            Toast.makeText(context, R.string.failed_save + player.getName(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
}
