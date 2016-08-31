package com.markatix.a9up.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.markatix.a9up.R;
import com.markatix.a9up.activities.Questions;

/**
 * A simple {@link Fragment} subclass.
 */
public class Next_question extends Fragment {

    public static final String TAG="Next_question";
    public TextView q_number;
    public Button next_q;
    private View rootview;

    public Next_question() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootview=inflater.inflate(R.layout.fragment_next_question, container, false);

        q_number=(TextView)rootview.findViewById(R.id.q_num);
        next_q=(Button)rootview.findViewById(R.id.next_q);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        q_number.setText(String.valueOf(Questions.question_number));
        Questions.show="Question_answer";
        next_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Questions)getActivity()).next_page();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
