package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.musicplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Creating Binding Object
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Requesting Runtime Permission
        requestRuntimePermission();

        // Set Theme
        setTheme(R.style.Theme_MusicPlayer);

        // Initializing Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlaySong.class);
                startActivity(intent);
            }
        });

        binding.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });

        binding.playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                startActivity(intent);
            }
        });
    }

    // Requesting file permission at runtime
    private void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
                // Requesting permission for Android 6 to Android 12 (API 23 to 32)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                } else {
                    // Permission already granted
                    Toast.makeText(this, "Permission to access music already granted!", Toast.LENGTH_SHORT).show();
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Requesting permission for Android 13 (API 33) and above to access audio files
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                            REQUEST_CODE_PERMISSION);
                } else {
                    // Permission already granted
                    Toast.makeText(this, "Permission to access music already granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Handle the result of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access music granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission to access music denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
