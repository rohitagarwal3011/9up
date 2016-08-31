package com.markatix.a9up.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.markatix.a9up.R;
import com.markatix.a9up.activities.Questions;
import com.markatix.a9up.utils.TagsPreferences;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Question_answer extends Fragment {

    public static final String TAG="Question_answer";
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor preEditor;
    private View rootview;
    private Button op1,op2,op3,op4;
    private Boolean option1=false,option2=false,option3=false,option4=false;
    public int score;
    private FrameLayout question_layout;
    private TextView quest;
    public static boolean exit;
    private ImageView banner;
    private Boolean loaded;
    public Question_answer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_question_answer, container, false);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        preEditor = prefs.edit();
        exit=false;
        loaded=false;
        banner=(ImageView)rootview.findViewById(R.id.banner);
        quest=(TextView)rootview.findViewById(R.id.question);
        op1=(Button)rootview.findViewById(R.id.op1);
        op2=(Button)rootview.findViewById(R.id.op2);
        op3=(Button)rootview.findViewById(R.id.op3);
        op4=(Button)rootview.findViewById(R.id.op4);
        score=Integer.parseInt(prefs.getString(TagsPreferences.SCORE,"0"));
        question_layout=(FrameLayout) rootview.findViewById(R.id.question_layout);
        question_layout.setClickable(true);
        set_q_and_ans();
        set_clicks();
        Questions.timer.setText("Timer\n00:09");


        //backbutton();
        return rootview;
    }

    public void set_timer()
    {
        new CountDownTimer(9000, 1000) {

            public void onTick(long millisUntilFinished) {

                Log.d(TAG,"Time : "+millisUntilFinished);
                if(!exit)
                Questions.timer.setText("Timer\n00:0"+millisUntilFinished / 1000);
                else {
                    cancel();
                }
            }

            public void onFinish() {
                if(!exit)
                {
                    Questions.question_number++;
                    if(Questions.question_number==5||Questions.question_number==10)
                    {
                        Questions.show="campaign";
                    }
                    else {
                        Questions.show = "next_question";
                    }
                    ((Questions)getActivity()).next_page();
                }

            }
        }   .start();
    }



    public void set_q_and_ans()
    {
        Gson gson = new Gson();
        com.markatix.a9up.models.Questions questions = gson.fromJson(prefs.getString(TagsPreferences.QUESTIONS,""), com.markatix.a9up.models.Questions.class);
        List<com.markatix.a9up.models.Questions.Data> data= questions.getData();


        com.markatix.a9up.models.Questions.Data.Figure figure=data.get(Questions.question_number-1).getFigure();

        if(figure.getExists())
        {
            Log.d(TAG,figure.getUrl());
            Picasso.with(getContext())
                    .load("http://"+figure.getUrl())
                    // optional
                    .placeholder(R.drawable.loading)
                        /*.error(R.drawable.error)      // optional
                        .resize(400,400)    */                    // optional
                    .into(banner,new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                if(!exit) {
                    loaded = true;
                    set_timer();
                }
            }

            @Override
            public void onError() {
                loaded=false;
            }
        });
        }
        else
        {
            loaded=true;
            set_timer();
        }
         String q=data.get(Questions.question_number-1).getQuestion();
        quest.setText(q);
        op1.setText(data.get(Questions.question_number-1).getAnswers().get(0).getContent());
        op2.setText(data.get(Questions.question_number-1).getAnswers().get(1).getContent());
        op3.setText(data.get(Questions.question_number-1).getAnswers().get(2).getContent());
        op4.setText(data.get(Questions.question_number-1).getAnswers().get(3).getContent());

        if(data.get(Questions.question_number-1).getAnswers().get(0).getCorrect()) {
            option1 = true;
            option2 = false;
            option3 = false;
            option4 = false;
        }
        else if(data.get(Questions.question_number-1).getAnswers().get(1).getCorrect()) {
            option2 = true;
            option1 = false;
            option3 = false;
            option4 = false;
        }
        else if(data.get(Questions.question_number-1).getAnswers().get(2).getCorrect()) {
            option3 = true;
            option2 = false;
            option1 = false;
            option4 = false;
        }
        else if(data.get(Questions.question_number-1).getAnswers().get(3).getCorrect()) {
            option4 = true;
            option2 = false;
            option3 = false;
            option1 = false;
        }


    }

    public void set_clicks()
    {
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit=true;
                op1.setEnabled(false);
                op2.setEnabled(false);
                op3.setEnabled(false);
                op4.setEnabled(false);




                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        if(option1)
                        {
                            op1.setBackgroundColor(Color.parseColor("#c1cf59"));
                            score+=9;
                            preEditor.putString(TagsPreferences.SCORE,String.valueOf(score)).commit();
                            question_layout.setClickable(false);


                        }
                        else
                        {
                            op1.setBackgroundColor(Color.parseColor("#ca0a07"));

                        }

                    }

                    public void onFinish() {


                        Questions.question_number++;
                        if(Questions.question_number==5||Questions.question_number==10)
                        {
                            Questions.show="campaign";
                        }
                        else {
                            Questions.show = "next_question";
                        }
                        ((Questions)getActivity()).next_page();
                    }
                }.start();


            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit=true;
                op1.setEnabled(false);
                op2.setEnabled(false);
                op3.setEnabled(false);
                op4.setEnabled(false);

                new CountDownTimer(2000, 1000) {


                    public void onTick(long millisUntilFinished) {

                        if(option2)
                        {
                            op2.setBackgroundColor(Color.parseColor("#c1cf59"));
                            score+=9;
                            preEditor.putString(TagsPreferences.SCORE,String.valueOf(score)).commit();

                        }
                        else
                        {
                            op2.setBackgroundColor(Color.parseColor("#ca0a07"));

                        }
                    }

                    public void onFinish() {
                        Questions.question_number++;
                        if(Questions.question_number==5||Questions.question_number==10)
                        {
                            Questions.show="campaign";
                        }
                        else {
                            Questions.show = "next_question";
                        }

                        ((Questions)getActivity()).next_page();
                    }
                }.start();

            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit=true;
                op1.setEnabled(false);
                op2.setEnabled(false);
                op3.setEnabled(false);
                op4.setEnabled(false);
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        if(option3)
                        {
                            op3.setBackgroundColor(Color.parseColor("#c1cf59"));
                            score+=9;
                            preEditor.putString(TagsPreferences.SCORE,String.valueOf(score)).commit();

                        }
                        else
                        {
                            op3.setBackgroundColor(Color.parseColor("#ca0a07"));

                        }
                    }

                    public void onFinish() {
                        Questions.question_number++;
                        if(Questions.question_number==5||Questions.question_number==10)
                        {
                            Questions.show="campaign";
                        }
                        else {
                            Questions.show = "next_question";
                        }

                        ((Questions)getActivity()).next_page();
                    }
                }.start();

            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit=true;
                op1.setEnabled(false);
                op2.setEnabled(false);
                op3.setEnabled(false);
                op4.setEnabled(false);

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        if(option4)
                        {
                            op4.setBackgroundColor(Color.parseColor("#c1cf59"));
                            score+=9;
                            preEditor.putString(TagsPreferences.SCORE,String.valueOf(score)).commit();

                        }
                        else
                        {
                            op4.setBackgroundColor(Color.parseColor("#ca0a07"));

                        }
                    }

                    public void onFinish() {
                        Questions.question_number++;
                        if(Questions.question_number==5||Questions.question_number==10)
                        {
                            Questions.show="campaign";
                        }
                        else {
                            Questions.show = "next_question";
                        }
                        ((Questions)getActivity()).next_page();
                    }
                }.start();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        set_q_and_ans();
//        set_clicks();
    }
}
