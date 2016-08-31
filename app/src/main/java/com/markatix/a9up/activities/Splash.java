package com.markatix.a9up.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.markatix.a9up.R;
import com.markatix.a9up.app.AppController;
import com.markatix.a9up.utils.Constants;
import com.markatix.a9up.utils.TagsPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

    Animation slide_up;
    ImageView logo;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor preEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        preEditor = prefs.edit();
        setContentView(R.layout.activity_splash);
        slide_up= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        logo=(ImageView)findViewById(R.id.genie);

    }

    @Override
    protected void onResume() {
        super.onResume();
        logo.startAnimation(slide_up);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        if(prefs.getString(TagsPreferences.VENDOR_ID,"no_id").equalsIgnoreCase("no_id")) {

                            String tag_json_obj = "json_obj_req";
                            String url = Constants.first();
                            JsonObjectRequest registervendor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        preEditor.putString(TagsPreferences.VENDOR_ID, response.getString("user_id")).apply();

                                        String tag_json_obj = "json_obj_req";
                                        String url = Constants.youtube();
                                        JsonObjectRequest registervendor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {
                                                    String youtube_link=response.getString("url");
                                                    youtube_link=youtube_link.substring(youtube_link.indexOf('=')+1);
                                                    Log.d("LInk",youtube_link);
                                                    preEditor.putString(TagsPreferences.YOUTUBE_LINK, youtube_link).apply();

                                                    startActivity(new Intent(Splash.this, Categories_list.class));
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                //Toast.makeText(Splash.this, "Score Published", Toast.LENGTH_SHORT).show();

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //make loader invisible

                                                Toast.makeText(Splash.this, "Please connect to internet and Restart Game", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        AppController.getInstance().addToRequestQueue(registervendor, tag_json_obj);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Toast.makeText(Splash.this, "Score Published", Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //make loader invisible

                                    Toast.makeText(Splash.this, "Please connect to internet and Restart Game", Toast.LENGTH_LONG).show();
                                }
                            });
                            AppController.getInstance().addToRequestQueue(registervendor, tag_json_obj);
                        }
                        else {
                            String tag_json_obj = "json_obj_req";
                            String url = Constants.youtube();
                            JsonObjectRequest registervendor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        String youtube_link=response.getString("url");
                                        youtube_link=youtube_link.substring(youtube_link.indexOf('=')+1);
                                        Log.d("LInk",youtube_link);
                                        preEditor.putString(TagsPreferences.YOUTUBE_LINK, youtube_link).apply();

                                        startActivity(new Intent(Splash.this, Categories_list.class));
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Toast.makeText(Splash.this, "Score Published", Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //make loader invisible

                                    Toast.makeText(Splash.this, "Please connect to internet and Restart Game", Toast.LENGTH_LONG).show();
                                }
                            });
                            AppController.getInstance().addToRequestQueue(registervendor, tag_json_obj);

                        }

                    }
                },
                4000
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
