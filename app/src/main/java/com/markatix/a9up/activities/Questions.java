package com.markatix.a9up.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.markatix.a9up.R;
import com.markatix.a9up.app.AppController;
import com.markatix.a9up.fragments.Campaign;
import com.markatix.a9up.fragments.Next_question;
import com.markatix.a9up.fragments.Question_answer;
import com.markatix.a9up.utils.ChangeFragment;
import com.markatix.a9up.utils.TagsPreferences;

public class Questions extends AppCompatActivity {


    public static int question_number;
    public static String show;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor preEditor;
    public static TextView score, timer;
    public static Boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_questions);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        preEditor = prefs.edit();
        AppController.activityVisible=true;
        question_number = 1;
        show = "next_question";
        exit = false;
        score = (TextView) findViewById(R.id.score);
        timer = (TextView) findViewById(R.id.timer);
        Log.d("Questions :", "View Created");
        next_page();

    }

    public void next_page() {

        if(AppController.isActivityVisible()) {
            score.setText("Score\n" + prefs.getString(TagsPreferences.SCORE, "0"));
            if (!exit) {
                if (show.equalsIgnoreCase("campaign")) {
                    Question_answer.exit = false;
                    timer.setVisibility(View.INVISIBLE);
                    Log.d("Questions :", "Campaign");
                    final Campaign info = (Campaign) getSupportFragmentManager().findFragmentByTag(Campaign.TAG);
                    ChangeFragment.replaceFragment(getSupportFragmentManager(), R.id.frame_main, new Campaign(), Campaign.TAG);
                } else if (show.equalsIgnoreCase("next_question")) {
                    Question_answer.exit = true;
                    timer.setVisibility(View.INVISIBLE);
                    Log.d("Questions :", "Next Question");
                    final Next_question info = (Next_question) getSupportFragmentManager().findFragmentByTag(Next_question.TAG);
                    ChangeFragment.replaceFragment(getSupportFragmentManager(), R.id.frame_main, new Next_question(), Next_question.TAG);
                } else {
                    timer.setVisibility(View.VISIBLE);
                    Log.d("Questions :", "Question and Answers");
                    final Question_answer info = (Question_answer) getSupportFragmentManager().findFragmentByTag(Question_answer.TAG);
                    ChangeFragment.replaceFragment(getSupportFragmentManager(), R.id.frame_main, new Question_answer(), Question_answer.TAG);
                }
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if (!Question_answer.exit) {

        } else {
            exit = true;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Questions.this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirm Exit");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want exit this Category?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.new_logo);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    //Toast.makeText(getContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Questions.this, Category_home.class));
                    finish();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    // Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    exit = false;
                    dialog.cancel();
                    onResume();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppController.activityPaused();
    }

}
