package com.mh.treasurehuntmh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ClueActivity extends AppCompatActivity {

    private static final String TAG = "ClueActivity";
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private String mToken;
    private Long mLevel;
    private String mClueType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_image);

        Intent launchedIntent = getIntent();
        mToken = launchedIntent.getStringExtra("token");

        getClue();
    }

    //Store the current level of user
    private void getClue() {

        Query query = reference.child(mToken);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLevel = dataSnapshot.child("current_level").getValue(Long.class);
                mClueType = dataSnapshot.child("clue_type").child(String.valueOf(mLevel)).getValue(String.class);
                Log.d(TAG, "onDataChange: " + mClueType + " type(clue) ");

                if (mClueType.equals("i")) {

                    setImageView();
                    Log.d(TAG, "onDataChange: imageview");

                } else {

                    setVideoView();
                    Log.d(TAG, "onDataChange: videoview");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setImageView() {

        setContentView(R.layout.activity_clue_image);
        ImageView clueImageView = (ImageView) findViewById(R.id.HintImgView);
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child(mToken).child(String.valueOf(mLevel) + ".jpg");
        Glide.with(this)
                .load(reference)
                .into(clueImageView);
    }

    private void setVideoView() {

        Uri videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/varavelpp.appspot.com/o/token1%2F1.mp4?alt=media&token=766beebc-0e90-440a-bf2b-490a674e296d");

        videoPlayer(videoUri);

    }

    private void videoPlayer(Uri videoUri) {

        setContentView(R.layout.activity_clue_video);
        VideoView clueVideoView = (VideoView) findViewById(R.id.HintvideoView);
        final ProgressBar bufferProgress = (ProgressBar) findViewById(R.id.bufferProgress);


        clueVideoView.setVideoURI(videoUri);
        clueVideoView.requestFocus();

        //Show/Hide Buffering Status
        clueVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    bufferProgress.setVisibility(View.VISIBLE);

                }

                else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {

                    bufferProgress.setVisibility(View.INVISIBLE);

                }

                return false;
            }
        });

        clueVideoView.start();

        //Show replay button when video finishes

        clueVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                Toast.makeText(ClueActivity.this, "Video finished", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

