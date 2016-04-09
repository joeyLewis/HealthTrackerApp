package com.blogspot.onekeyucd.healthtracker.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.blogspot.onekeyucd.healthtracker.R;

public class AddPlayerDialogFragment extends DialogFragment {

    private EditText playerName;
    private EditText playerDefaultHP;

    public String name;
    public int defaultHP;

    public DialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.fragment_add_player_dialog, null);

        assignViews(root);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_add_player);
        builder.setView(root);
        builder.setPositiveButton(R.string.button_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            name = playerName.getText().toString();
                            defaultHP = Integer.parseInt(playerDefaultHP.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            name = null;
                            defaultHP = 0;
                        }

                        mListener.onDialogPositiveClick(AddPlayerDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.button_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        name = null;
                        defaultHP = 0;
                        mListener.onDialogNegativeClick(AddPlayerDialogFragment.this);
                    }
                });
        return builder.create();
    }

    private void assignViews(View view) {
        playerName = (EditText)view.findViewById(R.id.add_player_name);
        playerDefaultHP = (EditText)view.findViewById(R.id.add_player_health);
    }

}
