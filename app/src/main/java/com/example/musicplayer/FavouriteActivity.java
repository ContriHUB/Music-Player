package com.example.musicplayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.databinding.ActivityFavouriteBinding;

public class FavouriteActivity extends AppCompatActivity {

    private ActivityFavouriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the theme for the activity
        setTheme(R.style.coolPink);

        // Initialize the binding object
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());

        // Set the content view using the binding root
        setContentView(binding.getRoot());
    }
}
