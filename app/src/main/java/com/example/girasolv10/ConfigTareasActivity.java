package com.example.girasolv10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfigTareasActivity extends AppCompatActivity {

    private Button guardar;
    ListView LVlistaTareas;
    ArrayList<DatosTareas> listaT;
    ArrayList<String> listainformacion;
    String array[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_tareas);

        guardar = (Button) findViewById(R.id.btnGuardarSelecTareas);
        LVlistaTareas = (ListView) findViewById(R.id.selecTareas_LV);
        listaT = new ArrayList<DatosTareas>();


        array = new String[50];

        final GestionBD gestionBD = new GestionBD(getApplicationContext());
        final SQLiteDatabase db = gestionBD.getWritableDatabase();
        DatosTareas tareas = null;

        listaT.add(new DatosTareas(0, R.drawable.icon1, "ID", "Nomb Tarea", "Potencia", "Tiempo","Interaccion" ));
        try {
            String resu= "";
            Cursor c = db.rawQuery("SELECT * FROM "+ GestionBD.DatosTabla.NOMBRE_TABLA, null);
            while (c.moveToNext()){
                tareas = new DatosTareas();

                String numCadena = c.getString(0);
                int numEntero = Integer.parseInt(numCadena);
                tareas.setId(numEntero);

                String rutaI= c.getString(2);
                String uri = "@drawable/"+rutaI;
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                tareas.setImagen(imageResource);

                tareas.setNroT(numCadena);
                tareas.setNombTarea(c.getString(1));

                tareas.setPotencia(c.getString(3));
                tareas.setTiempo(c.getString(4));
                tareas.setInteraccion(c.getString(5));

                listaT.add(tareas);
                resu = numCadena;
            }
            Toast.makeText(getApplicationContext(), "Resultado : "+resu, Toast.LENGTH_LONG).show();
            c.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No hay datos en tabla", Toast.LENGTH_LONG).show();
        }

        AdaptadorDT miadaptador = new AdaptadorDT(this, listaT);
        LVlistaTareas.setAdapter(miadaptador);

        LVlistaTareas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        LVlistaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(LVlistaTareas.isItemChecked(position)){
                    //Toast.makeText(getApplicationContext(), "Posicion: "+position, Toast.LENGTH_LONG).show();
                    view.setBackgroundColor(Color.parseColor("#BFE0EE"));
                    array[position]= "1";
                }else{
                    //Toast.makeText(getApplicationContext(), "Se desmarco: "+position, Toast.LENGTH_LONG).show();
                    view.setBackgroundColor(Color.TRANSPARENT);
                    array[position]= "0";
                }
                //LVlistaTareas.setItemChecked(position, true);
                //conversationAdapter.notifyDataSetChanged();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerACeros();
                SQLiteDatabase db = gestionBD.getReadableDatabase();
                for (int i = 0; i < 20; i++) {
                    if (array[i]=="1"){
                        ContentValues valores = new ContentValues();
                        valores.put(GestionBD.DatosTabla.COLUMNA_VALOR, "1");

                        String Selec = GestionBD.DatosTabla.COLUMNA_ID+"=?";
                        String cadena = String.valueOf(i);
                        String[] asl = {cadena};

                        db.update(GestionBD.DatosTabla.NOMBRE_TABLA, valores, Selec, asl);
                    }
                }
                Toast.makeText(getApplicationContext(), "Se guardo seleccion", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void ponerACeros() {
        GestionBD gestionBD = new GestionBD(getApplicationContext());
        SQLiteDatabase db = gestionBD.getWritableDatabase();
        for (int s = 1; s <= 14; s++){
            ContentValues valores = new ContentValues();
            valores.put(GestionBD.DatosTabla.COLUMNA_VALOR, "0");
            String Selec = GestionBD.DatosTabla.COLUMNA_ID+"=?";
            String cadena = String.valueOf(s);
            String[] asl = {cadena};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA, valores, Selec, asl);
        }
        db.close();
    }


}
