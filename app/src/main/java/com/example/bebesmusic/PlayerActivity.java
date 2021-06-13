package com.example.bebesmusic;

import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.bebesmusic.MainActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity {
    TextView song_name, artist_name, duration_played, duration_total;
    ImageView conver_art, nextBtn, preBtn, backBtn, shuffleBtn, repeatBtn;
    FloatingActionButton playPasuebtn;
    SeekBar seekBar;
    int position= -1;
    static ArrayList<MusicFiles> listsong = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Khai báo
        initView();

        gtIntentMethod();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String  formattedTime(int mCurrentPosition) {
        String totalout= "";
        String totalnew= "";
        String seconds= String.valueOf(mCurrentPosition % 60);
        String minutes= String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalnew = minutes  + ":" + "0" + seconds;
        if (seconds.length() == 1){
            return totalnew;
        }
        else {
            return totalout;
        }
    }

    private void gtIntentMethod() {
        position= getIntent().getIntExtra("position", -1);
        listsong = musicFiles;
        if (listsong != null){
            playPasuebtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listsong.get(position).getPath());
        }
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
    }

    private void initView() {
        song_name= findViewById(R.id.song_name);
        artist_name= findViewById(R.id.song_artist);
        duration_played= findViewById(R.id.durationPlayed);
        duration_total= findViewById(R.id.durationTotal);
        conver_art= findViewById(R.id.cover_art);
        nextBtn= findViewById(R.id.id_next);
        preBtn= findViewById(R.id.id_prev);
        backBtn= findViewById(R.id.back_btn);
        shuffleBtn= findViewById(R.id.id_shuffle);
        repeatBtn= findViewById(R.id.id_repeat);
        playPasuebtn= findViewById(R.id.play_pause);
        seekBar= findViewById(R.id.seekBar);
    }
}