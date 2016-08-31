package com.markatix.a9up.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.markatix.a9up.R;
import com.markatix.a9up.utils.TagsPreferences;

public class Categories_list extends AppCompatActivity {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor preEditor;
    private static final String TAG = Categories_list.class.getSimpleName();
    private ImageView cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        preEditor = prefs.edit();

        cat1=(ImageView)findViewById(R.id.cat_1);
        cat2=(ImageView)findViewById(R.id.cat_2);
        cat3=(ImageView)findViewById(R.id.cat_3);
        cat4=(ImageView)findViewById(R.id.cat_4);
        cat5=(ImageView)findViewById(R.id.cat_5);
        cat6=(ImageView)findViewById(R.id.cat_6);
        cat7=(ImageView)findViewById(R.id.cat_7);
        cat8=(ImageView)findViewById(R.id.cat_8);
        cat9=(ImageView)findViewById(R.id.cat_9);
        clicks();
    }

    public void clicks()
    {
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Logo").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"1").apply();
                Log.d(TAG,"Category"+prefs.getString(TagsPreferences.CATEGORIES,""));
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Apps").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"2").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Music").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"3").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"History").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"4").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Television").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"5").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Bollywood").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"6").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Geography").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"7").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Cusine").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"8").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preEditor.putString(TagsPreferences.CATEGORIES,"Cricket").apply();
                preEditor.putString(TagsPreferences.CATEGORY_ID,"9").apply();
                startActivity(new Intent(Categories_list.this, Category_home.class));
                finish();
            }
        });
    }
}
