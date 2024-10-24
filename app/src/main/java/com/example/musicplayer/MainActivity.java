package com.example.musicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musicplayer.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Creating Binding Object
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;  // for nav drawer
    private MusicAdapter musicAdapter;
    public static ArrayList<Music> MusicListMA;

    private static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeLayout();

        // Setting up button click listeners
        binding.shuffleBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PlaySong.class);
            startActivity(intent);
        });

        binding.favouriteBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
            startActivity(intent);
        });

        binding.playlistBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
            startActivity(intent);
        });

        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navFeedback:
                    Toast.makeText(getBaseContext(), "Feedback", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navSettings:
                    Toast.makeText(getBaseContext(), "Settings", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navAbout:
                    Toast.makeText(getBaseContext(), "About", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navExit:
                    // Exit the app
                    System.exit(1);
                    break;
            }
            return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeLayout() {
        // Requesting Runtime Permission
        requestRuntimePermission();

        // Set Theme
        setTheme(R.style.coolPinkNav);

        // Initializing Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing Nav Drawer Toggle
        toggle = new ActionBarDrawerToggle(this, binding.getRoot(), R.string.open, R.string.close);
        binding.getRoot().addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Creating MusicList
        MusicListMA = getAllAudio();

        // Setting up RecyclerView
        binding.musicRV.setHasFixedSize(true);
        binding.musicRV.setItemViewCacheSize(13);
        binding.musicRV.setLayoutManager(new LinearLayoutManager(this));

        // Initializing and setting adapter
        musicAdapter = new MusicAdapter(this, MusicListMA);
        binding.musicRV.setAdapter(musicAdapter);
        binding.totalSongs.setText("Total Songs: " + musicAdapter.getItemCount());
    }

    @SuppressLint("Recycle")
    private ArrayList<Music> getAllAudio() {
        ArrayList<Music> tempList = new ArrayList<>();

        // Filter only music or audio files
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATE_ADDED,
                MediaStore.Audio.Media.DATA,
        };

        Cursor cursor = this.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.DATE_ADDED
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    @SuppressLint("Range") String titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    @SuppressLint("Range") String albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    @SuppressLint("Range") String artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    @SuppressLint("Range") String pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    @SuppressLint("Range") long durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));


                    if (durationC > 0) {
                        Music music = new Music(
                                idC != null ? idC : "Unknown",
                                titleC != null ? titleC : "Unknown",
                                albumC != null ? albumC : "Unknown",
                                artistC != null ? artistC : "Unknown",
                                pathC,
                                durationC
                        );

                        File file = new File(pathC);
                        if (file.exists()) {
                            tempList.add(music);
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return tempList;
    }
}
