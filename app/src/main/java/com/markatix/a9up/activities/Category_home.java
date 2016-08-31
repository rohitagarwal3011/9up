package com.markatix.a9up.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
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

import static com.markatix.a9up.R.id.Phone;

public class Category_home extends AppCompatActivity {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor preEditor;
    private static final String TAG = Category_home.class.getSimpleName();
    private ImageView category_image;
    private TextView gift_button;
    private Button publish;

    public TextView score;
    public LinearLayout play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_home);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        preEditor = prefs.edit();
        score=(TextView)findViewById(R.id.score);
        category_image=(ImageView)findViewById(R.id.category_image);
        publish=(Button)findViewById(R.id.publish);
        play=(LinearLayout) findViewById(R.id.play);
        image_set();
        score.setText("Score : "+prefs.getString(TagsPreferences.SCORE,"0"));
        final ProgressDialog pd=new ProgressDialog(Category_home.this);
        gift_button=(TextView)findViewById(R.id.gift_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new Intent(Category_home.this, Questions.class));
//                finish();pd.setCancelable(false);
                pd.setMessage(" Please wait.. creating your quiz");
                pd.show();
                String tag_json_obj = "json_obj_req";
                String url= Constants.get_questions(prefs.getString(TagsPreferences.VENDOR_ID,"1"),prefs.getString(TagsPreferences.CATEGORY_ID,"1"));
                JsonObjectRequest registervendor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        Log.d(TAG,"Response :"+ response.toString());
                        prefs.edit().putString(TagsPreferences.QUESTIONS,response.toString()).apply();
//                        Gson gson = new Gson();
//                        Questions questions = gson.fromJson(response.toString(), Questions.class);
                        startActivity(new Intent(Category_home.this, Questions.class));
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //make loader invisible
                        pd.dismiss();
                        Toast.makeText(Category_home.this, "Unable to download questions.Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                AppController.getInstance().addToRequestQueue(registervendor,tag_json_obj);

            }
        });

        gift_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog =new Dialog(Category_home.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.gifts);
                ImageView close=(ImageView) dialog.findViewById(R.id.close);
                if(Integer.parseInt(prefs.getString(TagsPreferences.SCORE,"0"))>199)
                {
                    ImageView im=(ImageView)dialog.findViewById(R.id.s199);
                    im.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
                }
                if(Integer.parseInt(prefs.getString(TagsPreferences.SCORE,"0"))>499)
                {
                    ImageView im=(ImageView)dialog.findViewById(R.id.s499);
                    im.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
                }
                if(Integer.parseInt(prefs.getString(TagsPreferences.SCORE,"0"))>999)
                {
                    ImageView im=(ImageView)dialog.findViewById(R.id.s999);
                    im.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
                }

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 =new Dialog(Category_home.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog1.setContentView(R.layout.publish_score);
                final EditText name=(EditText)dialog1.findViewById(R.id.Name);
                final EditText email=(EditText)dialog1.findViewById(R.id.Email);
                final EditText phone=(EditText)dialog1.findViewById(R.id.Phone);
                TextView head_text=(TextView)dialog1.findViewById(R.id.head_text);


                if(!prefs.getString(TagsPreferences.VENDOR_EMAIL,"email").equalsIgnoreCase("email"))
                {
                    email.setText(prefs.getString(TagsPreferences.VENDOR_EMAIL,""));
                    email.setEnabled(false);
                }
                if(!prefs.getString(TagsPreferences.VENDOR_PHONE,"phone").equalsIgnoreCase("phone"))
                {
                    phone.setText(prefs.getString(TagsPreferences.VENDOR_PHONE,""));
                    phone.setEnabled(false);
                }
                if(!prefs.getString(TagsPreferences.VENDOR_NAME,"name").equalsIgnoreCase("name"))
                {
                    name.setText(prefs.getString(TagsPreferences.VENDOR_NAME,""));
                    name.setEnabled(false);
                }
                if(!(email.isEnabled()||name.isEnabled()||phone.isEnabled()))
                {
                    head_text.setVisibility(View.INVISIBLE);
                }

                TextView score=(TextView)dialog1.findViewById(R.id.score);
                Button publis=(Button)dialog1.findViewById(R.id.publish);

                score.setText("Score : "+prefs.getString(TagsPreferences.SCORE,"0"));
                dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


                dialog1.setCanceledOnTouchOutside(true);
                dialog1.setCancelable(true);
                dialog1.show();

                publis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pd.setMessage(" Please wait.. publishing Score");
                        pd.show();
                        String tag_json_obj = "json_obj_req";
                        preEditor.putString(TagsPreferences.VENDOR_EMAIL,email.getText().toString()).apply();
                        preEditor.putString(TagsPreferences.VENDOR_NAME,name.getText().toString()).apply();
                        preEditor.putString(TagsPreferences.VENDOR_PHONE,phone.getText().toString()).apply();
                        String url= Constants.publish(prefs.getString(TagsPreferences.VENDOR_ID,"1"),name.getText().toString(),email.getText().toString(),phone.getText().toString(),prefs.getString(TagsPreferences.SCORE,"0"));
                        JsonObjectRequest registervendor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pd.dismiss();
                                dialog1.dismiss();
                                Toast.makeText(Category_home.this, "Score Published", Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //make loader invisible
                                pd.dismiss();
                                Toast.makeText(Category_home.this, "Unable to publish Scores.Check Internet Connection", Toast.LENGTH_LONG).show();
                            }
                        });
                        AppController.getInstance().addToRequestQueue(registervendor,tag_json_obj);

                    }
                });

            }
        });

    }

    public void image_set()
    {
        int i=Integer.parseInt(prefs.getString(TagsPreferences.CATEGORY_ID,"9"));
        switch(i)
        {
            case 1:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_02));
                break;
            case 2:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_03));
                break;
            case 3:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_04));
                break;
            case 4:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_05));
                break;
            case 5:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_06));
                break;
            case 6:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_07));
                break;
            case 7:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_08));
                break;
            case 8:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_09));
                break;
            case 9:
                category_image.setImageDrawable(getResources().getDrawable(R.drawable.a_10));
                break;
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(Category_home.this, Categories_list.class));
        finish();
    }
}
