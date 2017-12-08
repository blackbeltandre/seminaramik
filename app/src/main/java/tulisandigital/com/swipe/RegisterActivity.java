package tulisandigital.com.swipe;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {
    private RequestQueue queue;
    AlertDialogManager alert = new AlertDialogManager();
    private EditText editTextNAMA_LENGKAP;
    private EditText editTextTEMPAT_LAHIR;
    private EditText  editTextTANGGAL_LAHIR;
    private EditText  editTextNO_HP;
    private EditText editTextEMAIL;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Button buttonClear;
    Button buttonInsert;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputbiodata);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Form Pendaftaran");
        toolbar.setSubtitle("Join us now !");
        setSupportActionBar(toolbar);

        editTextNAMA_LENGKAP = (EditText) findViewById(R.id.editTextNAMA_LENGKAP);
        editTextTEMPAT_LAHIR = (EditText) findViewById(R.id.editTextTEMPAT_LAHIR);
        editTextTANGGAL_LAHIR = (EditText) findViewById(R.id.editTextTANGGAL_LAHIR);
        editTextNO_HP = (EditText) findViewById(R.id.editTextNO_HP);
        editTextEMAIL = (EditText) findViewById(R.id.editTextEMAIL);
        FloatingActionButton add_user = (FloatingActionButton) findViewById(R.id.add_user);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This page using client & server side validation.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setEnabled(true);
        buttonClear  = (Button)findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==buttonClear) {
                    editTextNAMA_LENGKAP.setText("");
                    editTextTEMPAT_LAHIR.setText("");
                    editTextTANGGAL_LAHIR.setText("");
                    editTextNO_HP.setText("");
                    editTextEMAIL.setText("");
                }
            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                insert_guest();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    private boolean isValidnama_lengkap(String nama_lengkap) {
        if (nama_lengkap != null && nama_lengkap.length() > 1) {
            return true;
        }
        return false;
    }
    private boolean isValidtempat_lahir(String tempat_lahir) {
        if (tempat_lahir != null && tempat_lahir.length() > 1) {
            return true;
        }
        return false;
    }

    private void insert_guest() {
        final String nama_lengkap = editTextNAMA_LENGKAP.getText().toString().trim();
        if (!isValidnama_lengkap(nama_lengkap)) {
            /*Toast.makeText(this, "Form is valid", Toast.LENGTH_SHORT).show();*/
            editTextNAMA_LENGKAP.setError("Nama Lengkap Harus Di Isi.");
        }
        final String tempat_lahir = editTextTEMPAT_LAHIR.getText().toString().trim();
        if (!isValidtempat_lahir(tempat_lahir)) {
            editTextTEMPAT_LAHIR.setError("Tempat Lahir Harus Di Isi.");
        }
        final String tanggal_lahir = editTextTANGGAL_LAHIR.getText().toString().trim();
        final String no_hp = editTextNO_HP.getText().toString().trim();
        final String email = editTextEMAIL.getText().toString().trim();
        if(isValidnama_lengkap(nama_lengkap) && isValidtempat_lahir(tempat_lahir)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Sudah yakin akan menyimpannya ?");
            alertDialogBuilder.setPositiveButton("Sudah",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            class Insert_guest extends AsyncTask<Void, Void, String> {
                                ProgressDialog loading;
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                    loading = ProgressDialog.show(RegisterActivity.this, "Sedang memuat...", "Wait...", false, false);
                                }
                                @Override
                                protected void onPostExecute(String s) {
                                    super.onPostExecute(s);
                                    loading.dismiss();
                                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                                @Override
                                protected String doInBackground(Void... params) {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(AndroidTransaction.KEY_EMP_NAMA_LENGKAP, nama_lengkap);
                                    hashMap.put(AndroidTransaction.KEY_EMP_TEMPAT_LAHIR, tempat_lahir);
                                    hashMap.put(AndroidTransaction.KEY_EMP_TANGGAL_LAHIR, tanggal_lahir);
                                    hashMap.put(AndroidTransaction.KEY_EMP_NO_HP, no_hp);
                                    hashMap.put(AndroidTransaction.KEY_EMP_EMAIL, email);
                                    RequestHandler rh = new RequestHandler();
                                    String sign = rh.sendPostRequest(AndroidTransaction.URL_INSERT_BIODATA, hashMap);
                                    return sign;
                                }
                            }
                            Insert_guest Insert_guest_started = new Insert_guest();
                            Insert_guest_started.execute();
                        }
                    });
            alertDialogBuilder.setNegativeButton("Batal",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    private void findViewsById() {
        editTextTANGGAL_LAHIR = (EditText) findViewById(R.id.editTextTANGGAL_LAHIR);
        editTextTANGGAL_LAHIR.setInputType(InputType.TYPE_NULL);
        editTextTANGGAL_LAHIR.requestFocus();

    }
    private void setDateTimeField() {
        editTextTANGGAL_LAHIR.setOnClickListener(this);
        editTextTANGGAL_LAHIR.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextTANGGAL_LAHIR.setText(dateFormatter.format(newDate.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if(view == editTextTANGGAL_LAHIR) {
            fromDatePickerDialog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
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
                        alert.showAlertDialog(RegisterActivity.this, "Menutup Program....", "Silahkan Tunggu...", false);
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