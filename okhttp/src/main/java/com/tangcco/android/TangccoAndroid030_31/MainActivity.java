package com.tangcco.android.TangccoAndroid030_31;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tangcco.android.TangccoAndroid030_31.volley_util.BitmapCache;
import com.tangcco.android.TangccoAndroid030_31.volley_util.MyJsonObjectRequest;
import com.tangcco.android.TangccoAndroid030_31.volley_util.MyStringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    RequestQueue mQueue;
    String host = "192.168.1.165";
    ImageView iv_showimage;
    NetworkImageView iv_networkimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        mQueue.start();
//      iv_showimage = (ImageView) findViewById(R.id.imageView);
        iv_networkimage = (NetworkImageView) findViewById(R.id.network_image_view);
    }


    public void stringRequest(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "admin");
        params.put("password", "admin");

        // StringRequest request = new StringRequest(Request.Method.GET, "http://" + host + ":8080/TangccoAndroidService030/login.jsp", new Response.Listener<String>() {
        StringRequest request = new MyStringRequest("http://" + host + ":8080/TangccoAndroidService030/login.jsp", new Response.Listener<String>() {
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_LONG).show();
//                JSONObject jsonObject = new JSONObject(s);
//                String name = jsonObject.getString("name");
//                Log.i("name",name);
//                Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, params);

        mQueue.add(request);
    }

    public void jsonRequest(View view) {
//        JSONObject jsonObject = new JSONObject();// {"name":"admin,"password":"admin"}
//        try {
//            jsonObject.put("name","admin");
//            jsonObject.put("password","admin");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "admin");
        params.put("password", "admin");

        // JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,"http://" + host + ":8080/TangccoAndroidService030/login.jsp",jsonObject,new Response.Listener<JSONObject>() {
        MyJsonObjectRequest request = new MyJsonObjectRequest("http://" + host + ":8080/TangccoAndroidService030/login.jsp", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, params);

        mQueue.add(request);
    }


    public void imageRequest(View view) {
//        ImageRequest request = new ImageRequest("http://" + host + ":8080/TangccoAndroidService030/image/a.jpg", new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                if (bitmap != null)
//                    iv_showimage.setImageBitmap(bitmap);
//            }
//        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//        mQueue.add(request);

//        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_showimage, R.drawable.ic_launcher, R.drawable.ic_launcher);
//        imageLoader.get("http://" + host + ":8080/TangccoAndroidService030/image/a.jpg", listener);

        iv_networkimage.setDefaultImageResId(android.R.drawable.ic_menu_rotate);
        iv_networkimage.setErrorImageResId(android.R.drawable.ic_delete);
        iv_networkimage.setImageUrl("http://" + host + ":8080/TangccoAndroidService030/image/a.jpg", new ImageLoader(mQueue, new BitmapCache()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
