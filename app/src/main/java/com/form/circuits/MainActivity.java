package com.form.circuits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView circuits;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    String s1 [] = {"nomCircuit", "descriptionCourteCircuit", "descriptionLongueCircuit", "infoGeneraleCircuit", "datePremierJourCircuit", "prixRegulierCircuit", "detailVersement"};
    Integer s2[] = {R.id.nomCircuit, R.id.descriptionCourteCircuit, R.id.descriptionLongueCircuit, R.id.infoGeneraleCircuit, R.id.datePremierJourCircuit, R.id.prixRegulierCircuit, R.id.detailVersement};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circuits = (ListView) findViewById(R.id.lstView);

        recyclerView =findViewById(R.id.rec);


        MyAdapter myAdapter = new MyAdapter(this, s1, s2);

        lister();

    }

    public void lister(){
        final ArrayList<HashMap<String, Object>> tabCircuits = new ArrayList<HashMap<String, Object>>();
        String url = "http://10.0.2.2:80/testeAPI/PHP/circuitsControllerJSON.php";

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
//                                    map.put("img", String.valueOf(getResources().getIdentifier(nomImage, "drawable", getPackageName())));
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
                                        new String[] {"nomCircuit", "descriptionCourteCircuit", "descriptionLongueCircuit", "infoGeneraleCircuit", "datePremierJourCircuit", "prixRegulierCircuit", "detailVersement"},
                                        new int[] {R.id.nomCircuit, R.id.descriptionCourteCircuit, R.id.descriptionLongueCircuit, R.id.infoGeneraleCircuit, R.id.datePremierJourCircuit, R.id.prixRegulierCircuit, R.id.detailVersement});
                                circuits.setAdapter(monAdapter);
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

    @Override
    public void onRefresh() {
        lister();


        refreshLayout.setRefreshing(false);
    }

}