package com.example.lenovo.mobilehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Detail extends AppCompatActivity {

    ProgressDialog pDialog;
    VideoView videoview;
    private int position = 0;
    String videoUrl, imageUrl, cid;

    MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        mediaControls=new MediaController(this);

        videoUrl = "https://firebasestorage.googleapis.com/v0/b/moviehub-cb3ee.appspot.com/o/simple.mp4?alt=media&token=bf35f548-b2cd-42d2-8d96-c0818be6c196";

        /*if (mediaControls == null) {
            mediaControls = new MediaController(this);
        }*/

        videoview = (VideoView) findViewById(R.id.videoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(this);
        // Set progressbar title
        pDialog.setTitle("Streaming video, please wait.");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {


            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoUrl);
            videoview.setVideoURI(video);
            videoview.setMediaController(mediaControls);
            mediaControls.setAnchorView(videoview);
            /*mediaControls.setMediaPlayer(videoview);*/


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.requestFocus();
                videoview.seekTo(position);
                if (position == 0) {
                    videoview.start();
                } else {
                    videoview.pause();
                }
            }
        });

        videoview.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("Position", videoview.getCurrentPosition());
        videoview.pause();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        videoview.seekTo(position);
    }

}