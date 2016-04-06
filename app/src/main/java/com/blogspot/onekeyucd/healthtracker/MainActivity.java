package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	FragmentManager mFragmentManager;
	PlayerFragment mPlayer1;
	PlayerFragment mPlayer2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		mPlayer1 = (PlayerFragment)mFragmentManager.findFragmentById(R.id.player1_fragment);
		if(mPlayer1.getView() != null) {
			mPlayer1.getView().setBackgroundColor(getResources().getColor(R.color.IndianRed));
		}
		mPlayer2 = (PlayerFragment)mFragmentManager.findFragmentById(R.id.player2_fragment);
		if(mPlayer2.getView() != null) {
			mPlayer2.getView().setBackgroundColor(getResources().getColor(R.color.SkyBlue));
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
				mPlayer1.reset();
				mPlayer2.reset();
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}
}
