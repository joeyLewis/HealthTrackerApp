package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String ARG_NUM_PLAYERS = "num_players";

	private int numPlayers;

	private FragmentManager mFragmentManager = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numPlayers = 2;
		if(savedInstanceState != null) numPlayers = savedInstanceState.getInt(ARG_NUM_PLAYERS);

		if(savedInstanceState == null) {
			for (int i = 0; i < numPlayers; i++) {
				PlayerFragment playerToAdd = PlayerFragment.newInstance("Player " + Integer.toString(i + 1));
				mFragmentManager.beginTransaction()
						.add(R.id.player_container, playerToAdd, playerToAdd.getName())
						.commit();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_reset:
				return actionReset();
			case R.id.action_add_player:
				return actionAddPlayer();
			case R.id.action_remove_player:
				return actionRemovePlayer();
			case R.id.action_save_game:
				return actionSaveGame();
			case R.id.action_load_game:
				return actionLoadGame();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_NUM_PLAYERS, numPlayers);
	}

	private boolean actionReset() {
		for(Fragment player : mFragmentManager.getFragments().subList(0, numPlayers)) {
			((PlayerFragment)player).reset();
		}
		return true;
	}

	private boolean actionAddPlayer() {
		if(numPlayers < 4) {
			PlayerFragment playerToAdd =
					PlayerFragment.newInstance("Player " + Integer.toString(numPlayers + 1));
			addPlayer(playerToAdd);
		} else {
			Toast.makeText(getApplicationContext(),
			               getResources().getString(R.string.message_tooManyPlayers),
						   Toast.LENGTH_SHORT)
					.show();
		}
		return true;
	}

	private boolean actionRemovePlayer() {
		if(numPlayers > 1) {
			PlayerFragment playerToRemove =
					(PlayerFragment)mFragmentManager.getFragments().get(numPlayers - 1);
			removePlayer(playerToRemove);
		} else {
			Toast.makeText(getApplicationContext(),
			               getResources().getString(R.string.message_tooFewPlayers),
						   Toast.LENGTH_SHORT)
					.show();
		}
		return true;
	}

	private boolean actionSaveGame() {
		FileSystem.writeNewSave(getApplicationContext());
		for(Fragment player : mFragmentManager.getFragments().subList(0, numPlayers)) {
			((PlayerFragment)player).save();
		}
		return true;
	}

	private boolean actionLoadGame() {
		for(Fragment player : mFragmentManager.getFragments().subList(0, numPlayers)) {
			removePlayer((PlayerFragment)player);
		}

		for(PlayerFragment player : FileSystem.getLastGame(getApplicationContext())) {
			addPlayer(player);
		}
		return true;
	}

	private void addPlayer(PlayerFragment playerToAdd) {
		mFragmentManager.beginTransaction()
						.add(R.id.player_container, playerToAdd, playerToAdd.getName())
						.commit();
		numPlayers++;
	}

	private void removePlayer(PlayerFragment playerToRemove) {
		mFragmentManager.beginTransaction()
						.remove(playerToRemove)
						.commit();
		numPlayers--;
	}
}
