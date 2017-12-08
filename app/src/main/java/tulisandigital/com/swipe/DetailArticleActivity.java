package tulisandigital.com.swipe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailArticleActivity extends AppCompatActivity {
    private TextView editTextId;
    AlertDialogManager alert = new AlertDialogManager();
    private TextView editTextTitle;
    private TextView editTextDescription;
    private TextView editTextDatePost;
    private TextView editTextAuthor;
    private String id;
    private Toolbar toolbar;
    ImageView thumb1View;
    PhotoViewAttacher photoViewAttacher ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_article);
        Intent intent = getIntent();
        id = intent.getStringExtra(AndroidTransaction.PARAM_ID);
        editTextId = (TextView) findViewById(R.id.editTextId);
        editTextTitle = (TextView) findViewById(R.id.editTextTitle);
        editTextDescription = (TextView) findViewById(R.id.editTextDescription);
        editTextDatePost = (TextView) findViewById(R.id.editTextDatePost);
        editTextAuthor = (TextView) findViewById(R.id.editTextAuthor);
        editTextId.setText(id);
        getData();
        thumb1View = (ImageView)findViewById(R.id.thumb_button_1);
        Drawable drawable = getResources().getDrawable(R.drawable.amiklogo);
        thumb1View.setImageDrawable(drawable);
        photoViewAttacher = new PhotoViewAttacher(thumb1View);
        photoViewAttacher.update();
        Toast.makeText(getApplicationContext(), "Ketuk 2x image untuk memperbesar/memperkecil ukuran.",
                Toast.LENGTH_LONG).show();
        FloatingActionButton zoom = (FloatingActionButton) findViewById(R.id.zoom);
        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ketuk 2x image untuk memperbesar/memperkecil ukuran.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Article");
        toolbar.setSubtitle("Fragment move to activity" +
                "");
        setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    private void getData(){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailArticleActivity.this,"Proses Data...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                ShowData(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(AndroidTransaction.URL_GET_ARTICLE_PARAM,id);
                return s;
            }
        }
        getData ge = new getData();
        ge.execute();
    }
    private void ShowData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(AndroidTransaction.TAG_JSON_ARRAY);
            JSONObject article_param = result.getJSONObject(0);
            String title = article_param.getString(AndroidTransaction.TAG_TITLE);
            String description = article_param.getString(AndroidTransaction.TAG_DESCRIPTION);
            String date_post = article_param.getString(AndroidTransaction.TAG_DATE_POST);
            String foto = article_param.getString(AndroidTransaction.TAG_FOTO);
            String author = article_param.getString(AndroidTransaction.TAG_AUTHOR);
            Glide.with(getApplicationContext())
                    .load(AndroidTransaction.URL_PATH_ASSETS+foto)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(thumb1View);
            editTextTitle.setPaintFlags(editTextTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            editTextTitle.setText(title);
            editTextDescription.setText(description);
            editTextDatePost.setText(date_post);
            editTextAuthor.setText(author);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                exit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void exit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda ingin menutup aplikasi?");
        alertDialogBuilder.setPositiveButton("Iya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alert.showAlertDialog(DetailArticleActivity.this, "Menutup Program....", "Silahkan Tunggu...", false);
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}