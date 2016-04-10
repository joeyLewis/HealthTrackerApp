package com.blogspot.onekeyucd.healthtracker;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.onekeyucd.healthtracker.Dialogs.*;

public class MainActivity extends AppCompatActivity implements DialogListener {

	private static final String ARG_NUM_PLAYERS = "num_players";

	private int numPlayers;

    private AddPlayerDialogFragment addPlayer;
    private RemovePlayerDialogFragment removePlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numPlayers = 0;
		if(savedInstanceState != null) numPlayers = savedInstanceState.getInt(ARG_NUM_PLAYERS);

		if(savedInstanceState == null) {
			for (int i = 0; i < 2; i++) {
				PlayerFragment playerToAdd = PlayerFragment.newInstance("Default Player " + Integer.toString(i + 1));
				addPlayer(playerToAdd);
			}
		}

        addPlayer = new AddPlayerDialogFragment();
        removePlayer = new RemovePlayerDialogFragment();
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
		for(Fragment player : getSupportFragmentManager().getFragments()) {
			if(player instanceof PlayerFragment) ((PlayerFragment)player).reset();
		}
		return true;
	}

	private boolean actionAddPlayer() {
		if(numPlayers < 4) {
			addPlayer.show(getSupportFragmentManager().beginTransaction(), "Add Player");
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
            removePlayer.show(getSupportFragmentManager().beginTransaction(), "Remove Player");
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
		for(Fragment player : getSupportFragmentManager().getFragments()) {
			if(player instanceof PlayerFragment) ((PlayerFragment)player).save();
		}
		return true;
	}

	private boolean actionLoadGame() {
		for(Fragment player : getSupportFragmentManager().getFragments()) {
			if(player instanceof PlayerFragment) removePlayer((PlayerFragment)player);
		}

		for(PlayerFragment player : FileSystem.getLastGame(getApplicationContext())) {
			addPlayer(player);
		}
		return true;
	}

	private void addPlayer(PlayerFragment playerToAdd) {
		if(playerToAdd != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.player_container, playerToAdd, playerToAdd.getArguments().getString(playerToAdd.ARG_NAME))
                    .addToBackStack(playerToAdd.getArguments().getString(playerToAdd.ARG_NAME))
                    .commit();
            numPlayers++;
        } else {
            Toast.makeText(getApplicationContext(), R.string.failed_add, Toast.LENGTH_SHORT)
                    .show();
        }

	}

	private void removePlayer(PlayerFragment playerToRemove) {
        if(playerToRemove != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(playerToRemove)
                    .commit();
            numPlayers--;
        } else {
            Toast.makeText(getApplicationContext(), R.string.failed_remove, Toast.LENGTH_SHORT)
                    .show();
        }
	}

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(dialog != null) {
            if(dialog.equals(addPlayer)) {
                PlayerFragment playerToAdd = PlayerFragment.newInstance(addPlayer.name, addPlayer.defaultHP, addPlayer.defaultHP);
                addPlayer(playerToAdd);
            } else if(dialog.equals(removePlayer)) {
				PlayerFragment playerToRemove = (PlayerFragment)getSupportFragmentManager().findFragmentByTag(removePlayer.name);
				removePlayer(playerToRemove);
            }
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        // do nothing instead :D
    }
}
