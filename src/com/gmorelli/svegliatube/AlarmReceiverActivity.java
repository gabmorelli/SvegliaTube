package com.gmorelli.svegliatube;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    private VideoView mVideoView;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm);
 
        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                mMediaPlayer.stop();
                finish();
				return false;
			}
        });
 
        // instead of hardcoding the URI just make a YT API request and get the .3gp video uri
        playSound(this, Uri.parse("rtsp://rtsp2.youtube.com/ChoLENy73wIaEQnYRKJ3bPTBdBMYDSANFEgGDA==/0/0/0/video.3gp"));
    }
 
    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        /* try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
            
        } catch (IOException e) {
            System.out.println("OOPS");
        }*/
        
        try {
	        mVideoView = (VideoView) findViewById(R.id.receiver_vw_video);
	        String videourl = "rtsp://v4.cache8.c.youtube.com/CiILENy73wIaGQlgdj3OJzhDpxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
	        MediaController mc = new MediaController(this);
	        mc.setAnchorView(mVideoView);
	        mVideoView.setMediaController(mc);
	        mVideoView.requestFocus();
	        mVideoView.setVideoURI(Uri.parse(videourl));
	        //mc.show();
	        mVideoView.start();
        } catch (Exception e) {
            System.out.println("OOPS");
        }
        
    }
 
    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
}