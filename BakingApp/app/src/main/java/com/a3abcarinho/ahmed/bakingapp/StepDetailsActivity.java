package com.a3abcarinho.ahmed.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class StepDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_activity);
    }



    public static class StepDetailsFragment extends Fragment {
        private List<Step> stepList;
        private Step step;
        private ExoPlayer exoPlayer;
        private SimpleExoPlayer player;
        private SimpleExoPlayerView exoPlayerView;
        private TextView descriptionTV;
        private ImageView videoImageView;
        private Button previous;
        private Button next;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;
        private int position;
        private static final String Position = "position";
        private static final String VideoStarted = "videoStarted";
        private static final String CurrentPosition = "currentPosition";
        long playbackPosition;
        boolean playWhenReady;
        private TrackSelector trackSelector;
        private String vidUrl;


        public StepDetailsFragment() {
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.step_detail_fragment, container, false);
            boolean tablet = getActivity().getResources().getBoolean(R.bool.Tablet);
            exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.player);
            descriptionTV = (TextView) view.findViewById(R.id.descTV);
            videoImageView = (ImageView) view.findViewById(R.id.ImageView);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.stepContent);
            if (!tablet) {
                previous = (Button) view.findViewById(R.id.previousButton);
                next = (Button) view.findViewById(R.id.nextButton);
                linearLayout = (LinearLayout) view.findViewById(R.id.buttonsContainer);
                Intent intent = getActivity().getIntent();
                stepList = intent.getParcelableArrayListExtra(Step.KEY);
                position = intent.getIntExtra("position", 0);
                step = stepList.get(position);
            }
            Bundle arguments = getArguments();
            if (arguments != null || !tablet) {
                if (arguments != null) {
                    stepList = arguments.getParcelableArrayList(Step.KEY);
                    position = arguments.getInt("position", 0);
                    step = stepList.get(position);
                }
                boolean landscape = getActivity().getResources().getBoolean(R.bool.land);
                if (!tablet) {
                    if (landscape) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        if (position == 0) {
                            previous.setVisibility(View.GONE);
                        } else if (position == stepList.size() - 1) {
                            next.setVisibility(View.GONE);
                        }
                        descriptionTV.setText(step.getDescription());
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position++;
                                openActivity();
                            }
                        });
                        previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position--;
                                openActivity();
                            }
                        });
                    } else {
                        if (position == 0) {
                            previous.setVisibility(View.GONE);
                        } else if (position == stepList.size() - 1) {
                            next.setVisibility(View.GONE);
                        }
                        descriptionTV.setText(step.getDescription());
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position++;
                                openActivity();
                            }
                        });
                        previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position--;
                                openActivity();
                            }
                        });
                    }
                } else {
                    descriptionTV.setText(step.getDescription());
                }
                if (!step.getVideoUrl().isEmpty()) {
                    vidUrl = step.getVideoUrl();
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector = new DefaultTrackSelector(trackSelection);
                    player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

                } else {
                    videoImageView.setVisibility(View.VISIBLE);
                    exoPlayerView.setVisibility(View.GONE);
                }
            } else {
                relativeLayout.setVisibility(View.GONE);
                videoImageView.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.GONE);
            }
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if(savedInstanceState != null){
                playbackPosition = savedInstanceState.getLong(CurrentPosition);
                playWhenReady = savedInstanceState.getBoolean(VideoStarted);
                if(player !=null){
                    player.setPlayWhenReady(playWhenReady);
                    player.seekTo(playbackPosition);
                }

            }
        }


        public void  initPlayer(String url){
            exoPlayerView.setVisibility(View.VISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),new DefaultTrackSelector(),new DefaultLoadControl());
            exoPlayerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playbackPosition);
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource,true,false);



        }

        @Override
        public void onStart() {
            super.onStart();
                if(player != null){
                    initPlayer(vidUrl);
                }
         }



        private MediaSource buildMediaSource(Uri uri) {
            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            return new ExtractorMediaSource(uri,new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), getString(R.string.app_name)), defaultBandwidthMeter),new DefaultExtractorsFactory(),null,null);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            if(player != null){
                outState.putLong(CurrentPosition,player.getCurrentPosition());
                outState.putBoolean(VideoStarted,player.getPlayWhenReady());
            }
            outState.putInt(Position,position);
        }

        @Override
        public void onPause() {
            super.onPause();
            if(player != null){
                playbackPosition = player.getCurrentPosition();
                player.release();

            }
        }

        @Override
        public void onStop() {
            super.onStop();
            if (player != null) {
                playbackPosition = player.getCurrentPosition();
                player.release();
            }
        }


        @Override
        public void onDestroyView() {
            super.onDestroyView();
            if(player!=null){
                player.release();
            }
        }
        private void openActivity() {
            ArrayList<Step> stepArrayList = new ArrayList<>();
            stepArrayList.addAll(stepList);
            Intent intent = new Intent(getActivity(),StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(Step.KEY,stepArrayList);
            intent.putExtra("position",position);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
