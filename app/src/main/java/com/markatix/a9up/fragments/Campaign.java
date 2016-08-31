package com.markatix.a9up.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.markatix.a9up.R;
import com.markatix.a9up.activities.Categories_list;
import com.markatix.a9up.activities.Category_home;
import com.markatix.a9up.activities.Questions;
import com.markatix.a9up.utils.TagsPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class Campaign extends Fragment  {


    private static SharedPreferences prefs;
    public static final String TAG="Campaign";
    public int q_number;
    public Button proceed;
    private View rootview;
    private FrameLayout campaign_layout;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyDbKXB-k4MlFzhYXAVkO1aFZu03CYUY2mo";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public Campaign() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_campaign, container, false);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        proceed=(Button)rootview.findViewById(R.id.proceed);
        proceed.setEnabled(false);
        q_number= Questions.question_number;
        campaign_layout=(FrameLayout)rootview.findViewById(R.id.campaign_layout);

        final YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("AIzaSyDbKXB-k4MlFzhYXAVkO1aFZu03CYUY2mo", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    PlayerStyle style = PlayerStyle.MINIMAL;
                    YPlayer.setPlayerStyle(style);
                    YPlayer.loadVideo(prefs.getString(TagsPreferences.YOUTUBE_LINK,"r6JbMCVbhWw"));
                    YPlayer.play();

                    new CountDownTimer(20000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            Log.d(TAG,"Time : "+millisUntilFinished);
                            if(millisUntilFinished>10000)
                                proceed.setText("PROCEED in 00:"+millisUntilFinished / 1000);
                            else
                                proceed.setText("PROCEED in 00:0"+millisUntilFinished / 1000);

                        }

                        public void onFinish() {
                            {
                                if(q_number==10)
                                {
                                    proceed.setText("Try new Category");
                                    campaign_layout.setBackground(getResources().getDrawable(R.drawable.congrats));

                                }
                                else
                                    proceed.setText("PROCEED");

                                proceed.setEnabled(true);
                            }

                        }
                    }   .start();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                proceed.setEnabled(true);

            }
        });




        if(q_number==10)
        {
            proceed.setText("Try new Category");
            campaign_layout.setBackground(getResources().getDrawable(R.drawable.congrats));

        }
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(q_number==10)
                {
                    startActivity(new Intent(getContext(), Categories_list.class));
                    getActivity().finish();
                }
                else {
                    Questions.show = "next_question";
                    ((Questions)getActivity()).next_page();
                }
            }
        });



        return rootview;
    }

}
