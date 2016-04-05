package com.blogspot.onekeyucd.healthtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private TextView player1_healthText;
	private int player1_healthNum;

	private TextView player2_healthText;
	private int player2_healthNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		player1_healthText = (TextView)findViewById(R.id.player1_health);
		player1_healthNum = Integer.parseInt(player1_healthText.getText().toString());

		player2_healthText = (TextView)findViewById(R.id.player2_health);
		player2_healthNum = Integer.parseInt(player2_healthText.getText().toString());
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("player1_health", player1_healthNum);
		savedInstanceState.putInt("player2_health", player2_healthNum);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		player1_healthNum = savedInstanceState.getInt("player1_health");
		player1_healthText.setText(Integer.toString(player1_healthNum));

		player2_healthNum = savedInstanceState.getInt("player2_health");
		player2_healthText.setText(Integer.toString(player2_healthNum));
	}

	public void player1_minus(View view) {
		if(player1_healthNum > 0) player1_healthNum--;
		player1_healthText.setText(Integer.toString(player1_healthNum));
	}

	public void player1_plus(View view) {
		player1_healthNum++;
		player1_healthText.setText(Integer.toString(player1_healthNum));
	}

	public void player2_minus(View view) {
		if(player2_healthNum > 0) player2_healthNum--;
		player2_healthText.setText(Integer.toString(player2_healthNum));
	}

	public void player2_plus(View view) {
		player2_healthNum++;
		player2_healthText.setText(Integer.toString(player2_healthNum));
	}
}
