package com.emmanuel_yegon.cipherguard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.emmanuel_yegon.cipherguard.EncryptDecrypt;
import com.emmanuel_yegon.cipherguard.Model.Key;
import com.emmanuel_yegon.cipherguard.Model.Message;
import com.emmanuel_yegon.cipherguard.database.RoomDB;
import com.emmanuel_yegon.cipherguard.databinding.FragmentHomeBinding;
import com.emmanuel_yegon.cipherguard.utility.ETUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private MaterialButton btnEncrypt, btnDecrypt;
    private TextInputEditText txtEncrypt, txtDecrypt;

    RoomDB database;
    String key;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        database = RoomDB.getInstance(getActivity());
        txtEncrypt = binding.txtEncrypt;
        txtDecrypt = binding.txtDecrypt;


        btnEncrypt = binding.btnEncrypt;
        btnDecrypt = binding.btnDecrypt;

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = txtEncrypt.getText().toString();
                if (value != null && !value.isEmpty()) {
                    key = database.keyDao().getAllKey().get(0).getKey();
                    String encryptedText = EncryptDecrypt.encrypt(txtEncrypt.getText().toString(), key);
                    txtDecrypt.setText(encryptedText);

                    List<Key> keyList = database.keyDao().getAllKey();
                    Boolean saveMessage = keyList.get(0).getMessagebackup();
                    if (saveMessage)
                        saveEncryptedMessage(encryptedText, txtEncrypt.getText().toString());
                    ETUtility.setClipboard(getContext(), txtDecrypt.getText().toString());

                    txtEncrypt.getText().clear();
                    Toast.makeText(getContext(), "Encrypted text copied to clipboard", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String value = txtDecrypt.getText().toString();
                    if (value != null && !value.isEmpty()) {
                        key = database.keyDao().getAllKey().get(0).getKey();
                        String decryptedText = EncryptDecrypt.decrypt(value, key);
                        txtEncrypt.setText(decryptedText);
                        txtDecrypt.getText().clear();
                        ETUtility.setClipboard(getContext(),txtEncrypt.getText().toString());
                        Toast.makeText(getContext(),"Decrypted text copied to clipboard",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Field empty", Toast.LENGTH_SHORT).show();
                    }

                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "Key changed or invalid encrypted data", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "Invalid encrypted data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void saveEncryptedMessage(String encrypted_text, String plain_text) {

        Message message = new Message();
        message.setEncrypted_text(encrypted_text);
        message.setPlain_text(plain_text);
        message.setCreation_time(new Date());

        database.mainDao().saveItem(message);

    }
}