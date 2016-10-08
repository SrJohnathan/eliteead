package ead.elite.app.br.com.appelite.ead.net.volley;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ead.elite.app.br.com.appelite.ead.interfaces.DadosVolley;

/**
 * Created by PC on 16/04/2016.
 */
public class Conexao {



    public static void Conexao(Context context, String url ,HashMap<String,String> list, final DadosVolley volley) {

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        CustomRequest  customRequest = new CustomRequest(Request.Method.POST, url, list, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volley.geJsonObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volley.ErrorVolley(error.getMessage());
            }
        });
        customRequest.setRetryPolicy(new DefaultRetryPolicy(2000,3,2));
        mRequestQueue.add(customRequest);


    }





}
