package com.example.musicplayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.databinding.ActivityPlaylistBinding;

public class PlaylistActivity extends AppCompatActivity {

    private ActivityPlaylistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the theme for the activity
        setTheme(R.style.coolPink);

        // Initialize the binding object
        binding = ActivityPlaylistBinding.inflate(getLayoutInflater());

        // Set the content view using the binding root
        setContentView(binding.getRoot());
    }
}
