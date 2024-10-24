package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.databinding.MusicViewBinding;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyHolder> {

    private Context context;
    private ArrayList<Music> musicList;

    public MusicAdapter(Context context, ArrayList<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView album;
        TextView duration;

        public MyHolder(@NonNull MusicViewBinding binding) {
            super(binding.getRoot());
            title = binding.songNameMV;
            album = binding.songAlbumMV;
            duration = binding.songDuration;
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MusicViewBinding binding = MusicViewBinding.inflate(inflater, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.title.setText(musicList.get(position).getTitle());
        holder.album.setText(musicList.get(position).getAlbum());
        holder.duration.setText(String.valueOf(musicList.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
