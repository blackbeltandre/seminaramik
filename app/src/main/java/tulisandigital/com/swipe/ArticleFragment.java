package tulisandigital.com.swipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;


public class ArticleFragment extends Fragment{
    AlertDialogManager alert = new AlertDialogManager();
    View myView;
    private RecyclerView article_data;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;
    public ArticleFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.article,container,false);
        setHasOptionsMenu(true);
        article_data = (RecyclerView) myView.findViewById(R.id.article_data);
        LinearLayoutManager load_datanya = new LinearLayoutManager(getActivity());
        load_datanya.setOrientation(LinearLayoutManager.VERTICAL);
        article_data.setLayoutManager(load_datanya);
        requestQueue = Volley.newRequestQueue(getActivity());
        list_data = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, AndroidTransaction.URL_GET_ARTICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("article_result");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id"));
                        map.put("title", json.getString("title"));
                        map.put("description", json.getString("description"));
                        map.put("foto", json.getString("foto"));
                        map.put("author", json.getString("author"));
                        list_data.add(map);
                        AdapterList adapter = new AdapterList(getActivity(), list_data);
                        article_data.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                 }
              },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
          return myView;
    }
}