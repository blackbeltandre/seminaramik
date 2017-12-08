package tulisandigital.com.swipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataAdapterFragment extends Fragment {
    AlertDialogManager alert = new AlertDialogManager();
    View myView;
    private SearchView searchView;
    private String JSON_STRING;
    private ListView listView;
    public DataAdapterFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listbiodata,container,false);
        getJSON();
        setHasOptionsMenu(true);
        listView = (ListView) myView.findViewById(R.id.listView);
        return myView;
    }
    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.complex_toolbar, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.nama_lengkap);
        Toast.makeText(getContext(), "Pencarian boleh berdasarkan kriteria nama lengkap.",
                Toast.LENGTH_LONG).show();
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nama_lengkap) {
                Toast.makeText(getContext(), "Anda mencari "+ nama_lengkap,
                        Toast.LENGTH_LONG).show();
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                    Pencarian(nama_lengkap);
                }
                MenuItem myActionMenuItem = menu.findItem( R.id.nama_lengkap);
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                add();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
    private void Show_all_data(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(AndroidTransaction.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject dataJson = result.getJSONObject(i);
                String nama_lengkap = dataJson.getString(AndroidTransaction.TAG_NAMA_LENGKAP);
                String tempat_lahir = dataJson.getString(AndroidTransaction.TAG_TEMPAT_LAHIR);
                String tanggal_lahir = dataJson.getString(AndroidTransaction.TAG_TANGGAL_LAHIR);
                String no_hp = dataJson.getString(AndroidTransaction.TAG_NO_HP);
                String email = dataJson.getString(AndroidTransaction.TAG_EMAIL);
                String id = dataJson.getString(AndroidTransaction.TAG_ID);
                HashMap<String,String> dataAdapter = new HashMap<>();
                dataAdapter.put(AndroidTransaction.TAG_NAMA_LENGKAP,nama_lengkap);
                dataAdapter.put(AndroidTransaction.TAG_NO_HP,no_hp);
                dataAdapter.put(AndroidTransaction.TAG_EMAIL,email);
                list.add(dataAdapter);
            }
        } catch (JSONException error) {
            error.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.datainterface,
                new String[]{AndroidTransaction.TAG_NAMA_LENGKAP, AndroidTransaction.TAG_NO_HP, AndroidTransaction.TAG_EMAIL},
                new int[]{R.id.nama_lengkap,R.id.no_hp,R.id.email});
        listView.setAdapter(adapter);
    }
    public void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Sedang Memuat","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                Show_all_data();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(AndroidTransaction.URL_GET_ALL_BIODATA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void Pencarian(final String nama_lengkap){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Proses Data...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;// Method Show Data
                Show_all_data();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(AndroidTransaction.URL_GET_BIODATA_BY_PARAM,nama_lengkap);
                return s;
            }
        }
        getData ge = new getData();
        ge.execute();
    }
    private void add(){
        Intent intent_tambah = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent_tambah);
    }
}