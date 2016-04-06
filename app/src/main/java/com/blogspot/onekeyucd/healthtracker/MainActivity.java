package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	private static final int DEFAULT_PLAYERS = 2;

	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFragmentManager = getSupportFragmentManager();

		if(mFragmentManager.getFragments() == null) {
			for(int i = 0; i < DEFAULT_PLAYERS; i++) {
				PlayerFragment player = PlayerFragment.newInstance("Player " + Integer.toString(i + 1));
				FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.player_container, player, player.getName()).commit();
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
				for(Fragment player : mFragmentManager.getFragments()) ((PlayerFragment)player).reset();
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}
}
