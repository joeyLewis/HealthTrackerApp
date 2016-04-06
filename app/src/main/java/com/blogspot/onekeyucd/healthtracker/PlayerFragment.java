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
	private static final String ARG_CUR_HP = "curHP";

	private static final int DEFAULT_HEALTH = 20;

	private String mName;
	private int mCurHP;

	private TextView mCurHPText;

	public static PlayerFragment newInstance(String name, int health) {
		PlayerFragment fragment = new PlayerFragment();
		Bundle args = new Bundle();
		args.putString(ARG_NAME, name);
		args.putInt(ARG_CUR_HP, health);
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
		mCurHP = DEFAULT_HEALTH;

		if(getArguments() != null) {
			mName = getArguments().getString(ARG_NAME);
			mCurHP = getArguments().getInt(ARG_CUR_HP);
		}

		if(savedInstanceState != null) {
			mName = savedInstanceState.getString(ARG_NAME);
			mCurHP = savedInstanceState.getInt(ARG_CUR_HP);
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
		outState.putInt(ARG_CUR_HP, mCurHP);
	}

	private void setupTextViews(View view) {
		TextView nameText = (TextView)view.findViewById(R.id.player_name);
		nameText.setText(mName);

		mCurHPText = (TextView)view.findViewById(R.id.player_health);
		mCurHPText.setText(Integer.toString(mCurHP));
	}

	private void setupMinusButton(View view) {
		Button minusButton = (Button)view.findViewById(R.id.health_minus);
		minusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCurHP > 0) mCurHPText.setText(Integer.toString(--mCurHP));
			}
		});

		minusButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(mCurHP - 5 < 0) mCurHPText.setText(Integer.toString(mCurHP = 0));
				else mCurHPText.setText(Integer.toString(mCurHP -= 5));
				return true;
			}
		});
	}

	private void setupPlusButton(View view) {
		Button plusButton = (Button)view.findViewById(R.id.health_plus);
		plusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurHPText.setText(Integer.toString(++mCurHP));
			}
		});

		plusButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// creates a rapidly incrementing look to the TextView, rather than instant change
				mCurHPText.setText(Integer.toString(mCurHP += 5));
				return true;
			}
		});
	}

	public void setCurHP(int hp) {
		mCurHP = hp;
		mCurHPText.setText(Integer.toString(mCurHP));
	}
}
