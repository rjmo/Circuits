package com.form.circuits;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.form.circuits.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView circuits;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private android.content.Intent Intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        circuits = (ListView) findViewById(R.id.lstView);

        lister();

    }

    public void loginCircuit(View view) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);

    }

        public void lister(){
        final ArrayList<HashMap<String, Object>> tabCircuits = new ArrayList<HashMap<String, Object>>();
        String url = "http://10.0.2.2:80/API/PHP/circuitsControllerJSON.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i,j;

                            JSONArray jsonResponse = new JSONArray(response);


                            HashMap<String, Object> map;
                            String msg = jsonResponse.getString(0);

                            if(msg.equals("OK")){
                                JSONObject unCircuit;
                                for(i=1;i<jsonResponse.length();i++){
                                    unCircuit=jsonResponse.getJSONObject(i);
                                    map= new HashMap<String, Object>();
                                    j=(i%7);//m0.jpg, ...,m6.jpg round robin
                                    String nomImage = "m"+j;
                                   /* byte[] decodedString = Base64.decode(unCircuit.getString("image"), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    Drawable d = new BitmapDrawable(getResources(),decodedByte);
                                    map.put("Image", d);
                                    if (i==3)
                                        map.put("img", d);
                                    else*/


                                    String base64Image = unCircuit.getString("fichierPhoto");
                                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    map.put("fichierPhoto", decodedByte);
                                    map.put("nomCircuit", unCircuit.getString("nomCircuit"));
                                    map.put("descriptionCourteCircuit", unCircuit.getString("descriptionCourteCircuit"));
                                    map.put("descriptionLongueCircuit", unCircuit.getString("descriptionLongueCircuit"));
                                    map.put("infoGeneraleCircuit", unCircuit.getString("infoGeneraleCircuit"));
                                    map.put("datePremierJourCircuit", unCircuit.getString("datePremierJourCircuit"));
                                    map.put("prixRegulierCircuit", unCircuit.getString("prixRegulierCircuit"));
                                    map.put("detailVersement", unCircuit.getString("detailVersement"));

                                    tabCircuits.add(map);
                                }



                                SimpleAdapter monAdapter = new SimpleAdapter (MainActivity.this, tabCircuits, R.layout.lister_circuits_map,
                                        new String[] {"nomCircuit", "descriptionCourteCircuit", "descriptionLongueCircuit", "infoGeneraleCircuit", "datePremierJourCircuit", "prixRegulierCircuit", "detailVersement", "fichierPhoto"},
                                        new int[] {R.id.nomCircuit, R.id.descriptionCourteCircuit, R.id.descriptionLongueCircuit, R.id.infoGeneraleCircuit, R.id.datePremierJourCircuit, R.id.prixRegulierCircuit, R.id.detailVersement, R.id.imageCircuit});
//                                circuits.setAdapter(monAdapter);
//                                https://gist.github.com/oceantear/8331769
                                monAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                                    @Override
                                    public boolean setViewValue(View view, Object data, String textRepresentation) {

                                        if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                                            ImageView iv = (ImageView) view;
                                            Bitmap bm = (Bitmap) data;
                                            iv.setImageBitmap(bm);
                                            return true;
                                        }
                                        return false;
                                    }
                                });

                                ListView listView = new ListView(MainActivity.this);
                                listView.setAdapter(monAdapter);
                                setContentView(listView);

                            }
                            else{}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }


}