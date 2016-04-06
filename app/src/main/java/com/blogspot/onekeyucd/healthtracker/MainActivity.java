package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

	public void resetGame(View view) {
		mPlayer1.reset();
		mPlayer2.reset();
	}
}
