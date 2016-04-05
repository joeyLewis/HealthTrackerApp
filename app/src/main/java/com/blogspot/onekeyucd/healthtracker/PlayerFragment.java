package com.blogspot.onekeyucd.healthtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PlayerFragment extends Fragment {

	private static final String ARG_NAME = "name";
	private static final String ARG_HEALTH = "health";

	private static final int DEFAULT_HEALTH = 20;

	private String mName;
	private int mHealth;

	private TextView mHealthText;

	public static PlayerFragment newInstance(String name, int health) {
		PlayerFragment fragment = new PlayerFragment();
		Bundle args = new Bundle();
		args.putString(ARG_NAME, name);
		args.putInt(ARG_HEALTH, health);
		fragment.setArguments(args);
		return fragment;
	}

	public PlayerFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mName = getResources().getString(R.string.default_name);
		mHealth = DEFAULT_HEALTH;

		if(getArguments() != null) {
			mName = getArguments().getString(ARG_NAME);
			mHealth = getArguments().getInt(ARG_HEALTH);
		}

		if(savedInstanceState != null) {
			mName = savedInstanceState.getString(ARG_NAME);
			mHealth = savedInstanceState.getInt(ARG_HEALTH);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					                        Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_player, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupTextViews(view);
		setupMinusButton(view);
		setupPlusButton(view);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(ARG_NAME, mName);
		outState.putInt(ARG_HEALTH, mHealth);
	}

	private void setupTextViews(View view) {
		TextView nameText = (TextView)view.findViewById(R.id.player_name);
		nameText.setText(mName);

		mHealthText = (TextView)view.findViewById(R.id.player_health);
		mHealthText.setText(Integer.toString(mHealth));
	}

	private void setupMinusButton(View view) {
		Button minusButton = (Button)view.findViewById(R.id.health_minus);
		minusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mHealth > 0) mHealthText.setText(Integer.toString(--mHealth));
			}
		});

		minusButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(mHealth - 5 < 0) mHealthText.setText(Integer.toString(mHealth = 0));
				else mHealthText.setText(Integer.toString(mHealth -= 5));
				return true;
			}
		});
	}

	private void setupPlusButton(View view) {
		Button plusButton = (Button)view.findViewById(R.id.health_plus);
		plusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHealthText.setText(Integer.toString(++mHealth));
			}
		});

		plusButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// creates a rapidly incrementing look to the TextView, rather than instant change
				mHealthText.setText(Integer.toString(mHealth += 5));
				return true;
			}
		});
	}
}
