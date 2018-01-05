package nieuwdorp.luuk.eftelingstats;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL_WACHTTIJD = "https://eftelingapi.herokuapp.com/attractions";

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<EftelingItem> eftelingItems;
    private int wachttijdBaron;
    private int wachttijdBob = 0;
    private int wachttijdHollander = 0;
    private int wachttijdJoris = 0;
    private int wachttijdPanda = 0;
    private int wachttijdPython = 0;
    private int wachttijdVogel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.AttractionList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initList();
    }

    private void initList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wachttijden laden...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_WACHTTIJD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject all = new JSONObject(response);
                    JSONArray arrayAll = all.getJSONArray("AttractionInfo");
                    for (int i = 0; i < arrayAll.length(); i++) {
                        JSONObject o = arrayAll.getJSONObject(i);
                        switch (o.getString("Id")) {
                            case "baron1898":
                                wachttijdBaron = fetchAttraction(o);
                                break;
                            case "bobbaan":
                                wachttijdBob = fetchAttraction(o);
                                break;
                            case "devliegendehollander":
                                wachttijdHollander = fetchAttraction(o);
                                break;
                            case "jorisendedraak":
                                wachttijdJoris = fetchAttraction(o);
                                break;
                            case "pandadroom":
                                wachttijdPanda = fetchAttraction(o);
                                break;
                            case "python":
                                wachttijdPython = fetchAttraction(o);
                                break;
                            case "vogelrok":
                                wachttijdVogel = fetchAttraction(o);
                                break;
                        }

                    }
                    eftelingItems = new ArrayList<>();
                    eftelingItems.add(new EftelingItem("Baron 1898", MainActivity.this, wachttijdBaron));
                    eftelingItems.add(new EftelingItem("De Bobbaan", MainActivity.this, wachttijdBob));
                    eftelingItems.add(new EftelingItem("De Vliegende Hollander", MainActivity.this, wachttijdHollander));
                    eftelingItems.add(new EftelingItem("Joris en de Draak", MainActivity.this, wachttijdJoris));
                    eftelingItems.add(new EftelingItem("PandaDroom", MainActivity.this, wachttijdPanda));
                    eftelingItems.add(new EftelingItem("Python", MainActivity.this, wachttijdPython));
                    eftelingItems.add(new EftelingItem("Vogel Rok", MainActivity.this, wachttijdVogel));
                    adapter = new MyAdapter(eftelingItems, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "fak", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public int fetchAttraction (JSONObject o){
        try {
            if (!o.getString("State").equals("open")){
                return -1;
            }
            return o.getInt("WaitingTime");
        } catch (JSONException e) {
            e.printStackTrace();
            return -100;
        }
    }
}
