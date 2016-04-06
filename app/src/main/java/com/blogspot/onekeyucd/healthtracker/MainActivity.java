package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fragmentManager = getSupportFragmentManager();
		PlayerFragment player1 = (PlayerFragment)fragmentManager.findFragmentById(R.id.player1_fragment);
		if(player1.getView() != null) player1.getView().setBackgroundColor(getResources().getColor(R.color.IndianRed));
		PlayerFragment player2 = (PlayerFragment)fragmentManager.findFragmentById(R.id.player2_fragment);
		if(player1.getView() != null) player2.getView().setBackgroundColor(getResources().getColor(R.color.SkyBlue));
	}

	public void resetGame(View view) {
		
	}
}
