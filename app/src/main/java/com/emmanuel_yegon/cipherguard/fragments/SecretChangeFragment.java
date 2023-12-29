package com.emmanuel_yegon.cipherguard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.emmanuel_yegon.cipherguard.Model.Key;
import com.emmanuel_yegon.cipherguard.R;
import com.emmanuel_yegon.cipherguard.database.RoomDB;
import com.emmanuel_yegon.cipherguard.databinding.FragmentSecretChangeBinding;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SecretChangeFragment extends Fragment {

    FragmentSecretChangeBinding binding;
    private Button btnSaveKey, btnEnableDisableMessage, btnDeleteAllMessage, btnEnableLock;
    private EditText txtKey;

    private TextView txtWarning;
    RoomDB database;


    public SecretChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSecretChangeBinding.inflate(inflater, container, false);
        database = RoomDB.getInstance(getActivity());

        txtKey = binding.txtKey;
        txtWarning = binding.txtWarning;

        btnSaveKey = binding.btnSaveKey;
        btnEnableDisableMessage = binding.btnEnableDisableMessage;
        btnDeleteAllMessage = binding.btnDeleteAllMessage;
        btnEnableLock = binding.btnEnableLock;

        btnSaveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyText = txtKey.getText().toString();
                if (isValidPassword(keyText)) {
                    saveKey(keyText);
                    txtWarning.setVisibility(View.GONE);
                    txtKey.getText().clear();
                    Toast.makeText(getContext(), "Encryption key changed", Toast.LENGTH_SHORT).show();
                } else {
                    txtWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        btnEnableDisableMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                Boolean status;
                if (key.getMessagebackup()) {
                    status = false;
                    message = "Message backup disabled.";
                    dialogMessage = "Are you sure you want to disable ( Message history creation ) ? \nNo Message history " +
                            "will be made further.";
                    dialogTitle = "Disable ";
                } else {
                    status = true;
                    message = "Message backup enabled";
                    dialogMessage = "Are you sure you want to enable ( Message history creation ) ? \n By enabling you will" +
                            "have history of all messages you encrypt.";
                    dialogTitle = "Enable ";
                }
                new AlertDialog.Builder(inflater.getContext())
                        .setTitle(dialogTitle + "Confirmation ||")
                        .setMessage(dialogMessage)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.keyDao().enableDisable(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(status ? R.drawable.ic_enable : R.drawable.ic_disable)
                        .show();
            }
        });

        btnDeleteAllMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(inflater.getContext())
                        .setTitle("Delete !!")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.mainDao().deleteAllMessages();
                                Toast.makeText(getContext(), "All messages deleted.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(R.drawable.ic_delete).show();
            }
        });

        btnEnableLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                Boolean status;
                if (key.getSecurity()) {
                    status = false;
                    message = "Screen lock disabled.";
                    dialogMessage = "Are you sure you want to disable ( Screen lock ) ?";
                    dialogTitle = "Disable ";
                } else {
                    status = true;
                    message = "Screen lock enabled.";
                    dialogMessage = "Are you sure you want to enable ( Screen lock ) ?\n" +
                            "By enabling you have to use screen lock to open.";
                    dialogTitle = "Enable ";
                }

                new AlertDialog.Builder(inflater.getContext())
                        .setTitle(dialogTitle + "Lock !!")
                        .setMessage(dialogMessage)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.keyDao().enableDisableSecurity(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(status ? R.drawable.ic_enable : R.drawable.ic_disable).show();
            }
        });


        return binding.getRoot();
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    private void saveKey(String keyToSave) {
        List<Key> keyList = database.keyDao().getAllKey();
        Key myKey = keyList.get(0);
        database.keyDao().changeKey(myKey.getID(), keyToSave);
    }


}