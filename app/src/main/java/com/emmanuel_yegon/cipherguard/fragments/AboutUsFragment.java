package com.emmanuel_yegon.cipherguard.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.emmanuel_yegon.cipherguard.databinding.FragmentAboutUsBinding;


public class AboutUsFragment extends Fragment {

    ImageView imgYoutube, imgInstagram,imgTwitter;
    TextView txtEmail,txtWebsiteUrl;
    FragmentAboutUsBinding binding;




    public AboutUsFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAboutUsBinding.inflate(inflater,container,false);

        imgYoutube = binding.imgYoutube;
        txtEmail = binding.txtEmail;
        txtWebsiteUrl = binding.txtWebsiteUrl;
        imgInstagram = binding.imgInstagram;
        imgTwitter = binding.imgTwitter;

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/emmanuel-yegon-4b382324b/"));
                startActivity(intent);
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"kptooman@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT,"From Cipher");
                    startActivity(intent);
                }catch (ActivityNotFoundException ex){

                }
            }
        });

        txtWebsiteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/emmanuel-yegon"));
                startActivity(intent);
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/manuyegon254/1234"));
                startActivity(intent);
            }
        });

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/emmanuel-yegon-4b382324b/"));
                startActivity(intent);
            }
        });

        return binding.getRoot();

    }
}