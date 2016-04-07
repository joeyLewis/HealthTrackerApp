package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	private static final String ARG_NUM_PLAYERS = "num_players";
	private static final int DEFAULT_PLAYERS = 2;

	private int numPlayers;

	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numPlayers = DEFAULT_PLAYERS;
		if(savedInstanceState != null) numPlayers = savedInstanceState.getInt(ARG_NUM_PLAYERS);

		mFragmentManager = getSupportFragmentManager();

		if(mFragmentManager.getFragments() == null) {
			for(int i = 0; i < numPlayers; i++) {
				PlayerFragment player = PlayerFragment.newInstance("Player " + Integer.toString(i + 1));
				mFragmentManager.beginTransaction()
												.add(R.id.player_container, player, player.getName()).commit();
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
				for(Fragment player : mFragmentManager.getFragments().subList(0, numPlayers)) {
					((PlayerFragment)player).reset();
				}
				return true;
			case R.id.action_add_player:
				addPlayer();
				return true;
			case R.id.action_remove_player:
				removePlayer();
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_NUM_PLAYERS, numPlayers);
	}

	private void addPlayer() {
		if(numPlayers < 4) {
			PlayerFragment playerToAdd =
							PlayerFragment.newInstance("Player " + Integer.toString(++numPlayers));
			mFragmentManager.beginTransaction()
											.add(R.id.player_container, playerToAdd, playerToAdd.getName()).commit();
		}
	}

	private void removePlayer() {
		if(numPlayers > 1) {
			PlayerFragment playerToRemove =
							(PlayerFragment)mFragmentManager.getFragments().get(--numPlayers);
			mFragmentManager.beginTransaction()
											.remove(playerToRemove).commit();
		}
	}
}
