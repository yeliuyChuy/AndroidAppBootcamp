package com.example.android.a03musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button playButton;
    SeekBar positionBar;
    SeekBar volBar;
    TextView elapsedTimeLabel;
    TextView remainTimeLabel;
    MediaPlayer mp;

    int duration;


    public void initPlayer(){
        //Media Player
        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(false);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        duration = mp.getDuration();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        playButton = (Button)findViewById(R.id.playButton);
        elapsedTimeLabel = (TextView)findViewById(R.id.elapsedTimeLabel);
        remainTimeLabel = (TextView)findViewById(R.id.remainTimeLabel);

        initPlayer();

        //position bar
        positionBar = (SeekBar)findViewById(R.id.positionBar);
        positionBar.setMax(duration);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        //Volume Bar
        volBar = (SeekBar)findViewById(R.id.volBar);
        volBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volNum = progress / 100f;
                        mp.setVolume(volNum,volNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        //Thread for updating time label
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null){
                    try{
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(100);
                    } catch (InterruptedException e){}
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int currentPosition = msg.what;
            //upload positionbar
            positionBar.setProgress(currentPosition);

            //upload label
            String elapsedTime = createTimeLabel(currentPosition);
            String remainTime = createTimeLabel(duration - currentPosition);
            elapsedTimeLabel.setText(elapsedTime);
            remainTimeLabel.setText(remainTime);
        }
    };

    public String createTimeLabel(int time){
        String timeLabel = "";
        int min = time/1000/60;
        int sec = time/1000%60;

        timeLabel = min + ":";
        if(sec<10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public void playButtonClick(View view){
        if(!mp.isPlaying()){
            mp.start();
            playButton.setBackgroundResource(R.drawable.pause);
        } else {
            mp.pause();
            playButton.setBackgroundResource(R.drawable.play);
        }
    }


}