package com.example.girasolv10;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.girasolv10.ui.main.SectionsPagerAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity implements InformacFragment.OnFragmentInteractionListener, TareFragment.OnFragmentInteractionListener,
                            ParametrosFragment.OnFragmentInteractionListener, EnergiaFragment.OnFragmentInteractionListener, ListaTareasFragment.OnFragmentInteractionListener,
                            EditTareaFragment.OnFragmentInteractionListener{

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private String NameOfFolder = "/CarpetaGirasol";
    private String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        llenarBD();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("¡Atención!")
                        .setMessage("Debes otorgar el permiso de Escritura para poder continuar")
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

            }
        }else {
            //tiene permiso
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
            final File dir = new File(file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    private void llenarBD() {
        boolean ban=false;
        final GestionBD gestionBD = new GestionBD(getApplicationContext());

        SQLiteDatabase db = gestionBD.getWritableDatabase();
        String[] argsel = {"1"};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_NOMB_TAREA};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA, projection, GestionBD.DatosTabla.COLUMNA_ID+"=?", argsel, null, null, null);

            c.moveToFirst();
            //Toast.makeText(getApplicationContext(), "Tabla llenada", Toast.LENGTH_LONG).show();
            cadena = c.getString(0);
            c.close();
            ban=true;
        }catch (Exception e) {
           // Toast.makeText(getApplicationContext(), "Tabla no llenada", Toast.LENGTH_LONG).show();

        }
        if (ban) {
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Datos base cargados", Toast.LENGTH_LONG).show();
            ContentValues valores = new ContentValues();
            valores.put(GestionBD.DatosTabla.COLUMNA_ID, "1");
            valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Chrome");
            valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "crome");
            valores.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores);

            ContentValues valoresM = new ContentValues();
            valoresM.put(GestionBD.DatosTabla.COLUMNA_ID, "2");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Mint");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "mint");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valoresM.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valoresM);

            ContentValues valores1 = new ContentValues();
            valores1.put(GestionBD.DatosTabla.COLUMNA_ID, "3");
            valores1.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Google Drive");
            valores1.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "drive");
            valores1.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores1.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores1.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores1.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores1.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores1.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores1);

            ContentValues valores2 = new ContentValues();
            valores2.put(GestionBD.DatosTabla.COLUMNA_ID, "4");
            valores2.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Gmail");
            valores2.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "gmail");
            valores2.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores2.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores2.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores2.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores2.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores2.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores2);

            ContentValues valores3 = new ContentValues();
            valores3.put(GestionBD.DatosTabla.COLUMNA_ID, "5");
            valores3.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Google Search");
            valores3.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "search");
            valores3.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores3.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores3.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores3.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores3.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores3.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores3);

            ContentValues valores4 = new ContentValues();
            valores4.put(GestionBD.DatosTabla.COLUMNA_ID, "6");
            valores4.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "YouTube");
            valores4.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "youtube");
            valores4.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores4.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores4.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores4.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores4.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores4.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores4);

            ContentValues valores5 = new ContentValues();
            valores5.put(GestionBD.DatosTabla.COLUMNA_ID, "7");
            valores5.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Google Maps");
            valores5.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "google");
            valores5.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores5.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores5.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores5.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores5.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores5.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores5);

            ContentValues valores6 = new ContentValues();
            valores6.put(GestionBD.DatosTabla.COLUMNA_ID, "8");
            valores6.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Translate");
            valores6.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "translate");
            valores6.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores6.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores6.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores6.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores6.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores6.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores6);

            ContentValues valores7 = new ContentValues();
            valores7.put(GestionBD.DatosTabla.COLUMNA_ID, "9");
            valores7.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Google Fotos");
            valores7.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "photos");
            valores7.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.54");
            valores7.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores7.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "2");
            valores7.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores7.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores7.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores7);

            ContentValues valores8 = new ContentValues();
            valores8.put(GestionBD.DatosTabla.COLUMNA_ID, "10");
            valores8.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "WhatsApp");
            valores8.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "whatsapp");
            valores8.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.70");
            valores8.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores8.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores8.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores8.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores8.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores8);

            ContentValues valores9 = new ContentValues();
            valores9.put(GestionBD.DatosTabla.COLUMNA_ID, "11");
            valores9.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Facebook");
            valores9.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "facebook");
            valores9.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "2.70");
            valores9.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores9.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores9.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores9.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores9.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores9);

            ContentValues valores10 = new ContentValues();
            valores10.put(GestionBD.DatosTabla.COLUMNA_ID, "12");
            valores10.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Instagram");
            valores10.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "instagram");
            valores10.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "2.70");
            valores10.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores10.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores10.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores10.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores10.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores10);

            ContentValues valores11 = new ContentValues();
            valores11.put(GestionBD.DatosTabla.COLUMNA_ID, "13");
            valores11.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Telegram");
            valores11.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "telegram");
            valores11.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.70");
            valores11.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores11.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores11.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores11.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores11.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores11);

            ContentValues valores12 = new ContentValues();
            valores12.put(GestionBD.DatosTabla.COLUMNA_ID, "14");
            valores12.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Twitter");
            valores12.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "twitter");
            valores12.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.70");
            valores12.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores12.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores12.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores12.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores12.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores12);

            ContentValues valores13 = new ContentValues();
            valores13.put(GestionBD.DatosTabla.COLUMNA_ID, "15");
            valores13.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, "Pinterest");
            valores13.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, "pinterest");
            valores13.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, "1.70");
            valores13.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, "1");
            valores13.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, "3");
            valores13.put(GestionBD.DatosTabla.COLUMNA_REPETICION, "1");
            valores13.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            valores13.put(GestionBD.DatosTabla.COLUMNA_ET_i, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores13);

           // el primer Q0
            ContentValues valSZ = new ContentValues();
            valSZ.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "1");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            valSZ.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, valSZ);

            ContentValues val0 = new ContentValues();
            val0.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "8");
            val0.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val0.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val0);

            ContentValues val1 = new ContentValues();
            val1.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "9");
            val1.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val1.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val1);

            ContentValues val2 = new ContentValues();
            val2.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "10");
            val2.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val2.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val2);

            ContentValues val3 = new ContentValues();
            val3.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "11");
            val3.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val3.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val3);

            ContentValues val4 = new ContentValues();
            val4.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "12");
            val4.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val4.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val4);

            ContentValues val5 = new ContentValues();
            val5.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "13");
            val5.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val5.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val5);

            ContentValues val6 = new ContentValues();
            val6.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "14");
            val6.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val6.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val6);

            ContentValues val7 = new ContentValues();
            val7.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "15");
            val7.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val7.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val7);

            ContentValues val8 = new ContentValues();
            val8.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "16");
            val8.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val8.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val8);

            ContentValues val9 = new ContentValues();
            val9.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "17");
            val9.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val9.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val9);

            ContentValues val90 = new ContentValues();
            val90.put(GestionBD.DatosTabla.COLUMNA_ID_VH, "18");
            val90.put(GestionBD.DatosTabla.COLUMNA_Q_i, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_E_i, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_I_ar, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_V_ar, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_P_ar, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");
            val90.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA2, GestionBD.DatosTabla.COLUMNA_ID_VH, val90);

            //COLUMNA_ID_DP
            ContentValues val10 = new ContentValues();
            val10.put(GestionBD.DatosTabla.COLUMNA_ID_DP, "1");
            val10.put(GestionBD.DatosTabla.COLUMNA_NOMB_ZONA, "Nombre Zona");
            val10.put(GestionBD.DatosTabla.COLUMNA_MES, "Cualquiera");
            val10.put(GestionBD.DatosTabla.COLUMNA_POT_SB, "0.241");
            val10.put(GestionBD.DatosTabla.COLUMNA_TIEMPO_SB, "120");
            val10.put(GestionBD.DatosTabla.COLUMNA_VC, "3.7");
            val10.put(GestionBD.DatosTabla.COLUMNA_CAP_USR, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_GAMA, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_NIVEL_BRILLO, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_HSP, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_P_MAX, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_OPCS_Q, "0");
            val10.put(GestionBD.DatosTabla.COLUMNA_E_T, "0");

            db.insert(GestionBD.DatosTabla.NOMBRE_TABLA3, GestionBD.DatosTabla.COLUMNA_ID_DP, val10);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
                    final File dir = new File(file_path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                } else {
                    Toast.makeText(this, "No se otorgaron los permisos", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}