package com.markatix.a9up.utils;

import android.util.Log;



import java.net.URI;
import java.net.URL;

/**
 * Created by rohit on 12/8/16.
 */
public class Constants {

    public static String BASE_URL="http://www.socialestore.com:8000/customapi/";
    private static String TAG ="Constants";

    public static String vendor_create= BASE_URL+"vendor/create-vendor/";
    public static String verify_domain= BASE_URL+"vendor/subdomain-check/";
    public static String verify_phone= BASE_URL+"vendor/vendor-mobile-verification/";
    public static String verify_otp= BASE_URL+"vendor/vendor-otp-verification/";

    public static URL convertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get_questions(String user_id,String category_id){
        String url_create_vendor="http://stoneart.asia:8000/nine_app/get_questions/"+user_id+"/?category="+category_id;
        url_create_vendor.trim();
        URL url;
        url=convertToUrl(url_create_vendor);
        Log.d(TAG, "Url get questions url: " + url.toString());
        return url.toString();
    }


    public static String publish(String user_id,String name,String email,String mobile,String score){
        String url_create_vendor="http://stoneart.asia:8000/nine_app/score_submission/"+user_id+"/?email="+email+"&mobile="+mobile+"&score="+score+"&name="+name;
        url_create_vendor.trim();
        URL url;
        url=convertToUrl(url_create_vendor);
        Log.d(TAG, "Url get questions url: " + url.toString());
        return url.toString();
    }

    public static String first(){
        String url_create_vendor="http://stoneart.asia:8000/nine_app/first_time";
        url_create_vendor.trim();
        URL url;
        url=convertToUrl(url_create_vendor);
        Log.d(TAG, "Url get questions url: " + url.toString());
        return url.toString();
    }
    public static String youtube(){
        String url_create_vendor="http://stoneart.asia:8000/nine_app/get_ad_url/1";
        url_create_vendor.trim();
        URL url;
        url=convertToUrl(url_create_vendor);
        Log.d(TAG, "Url get questions url: " + url.toString());
        return url.toString();
    }


    public static String verify_domain(String subdomain){
        String url_verify_domain=verify_domain+"?subdomain="+subdomain;
        url_verify_domain.trim();
        URL url;
        url=convertToUrl(url_verify_domain);
        Log.d(TAG, "Url Verify_domain url: " + url.toString());
        return url.toString();
    }
    public static String verify_phone(String vendor_id, String mobile_no)
    {
        String url_verify_phone=verify_phone+"?vendor_id="+vendor_id+"&mobile_no="+mobile_no;
        url_verify_phone.trim();
        URL url;
        url=convertToUrl(url_verify_phone);
        Log.d(TAG, "URL Verify_phone : "+ url.toString());
        return url.toString();
    }
    public static String verify_otp(String vendor_id, String otp)
    {
        String url_verify_otp=verify_otp+"?vendor_id="+vendor_id+"&otp="+otp;
        url_verify_otp.trim();
        URL url;
        url=convertToUrl(url_verify_otp);
        Log.d(TAG, "URL Verify OTP : "+ url.toString());
        return url.toString();
    }

}
