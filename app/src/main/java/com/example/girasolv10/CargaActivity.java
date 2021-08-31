package com.example.girasolv10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;

public class CargaActivity extends AppCompatActivity {
    Button btnGuardar, btnBuscar, btnBorrar, btnUpdate;
    EditText id, NombTarea, NombIcono, Pot, Inter, Tiempo, valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        id = (EditText)findViewById(R.id.etId);
        NombTarea = (EditText)findViewById(R.id.etNombTarea);
        NombIcono = (EditText)findViewById(R.id.etNombIcono);
        Pot = (EditText)findViewById(R.id.etPotencia);
        Inter = (EditText)findViewById(R.id.etInteraccion);
        Tiempo = (EditText)findViewById(R.id.etTiempo);
        valor = (EditText)findViewById(R.id.etValor);

        final GestionBD gestionBD = new GestionBD(getApplicationContext());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = gestionBD.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(GestionBD.DatosTabla.COLUMNA_ID, id.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, NombTarea.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, NombIcono.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, Pot.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, Tiempo.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, Inter.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_VALOR, valor.getText().toString());

                long newRowId = db.insert(GestionBD.DatosTabla.NOMBRE_TABLA, GestionBD.DatosTabla.COLUMNA_ID, valores);
                Toast.makeText(getApplicationContext(), "Se guardo el dato: "+newRowId, Toast.LENGTH_LONG).show();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = gestionBD.getReadableDatabase();
                String[] argsel = {id.getText().toString()};
                String[] projection = {GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, GestionBD.DatosTabla.COLUMNA_POTENCIA, GestionBD.DatosTabla.COLUMNA_TIEMPO,GestionBD.DatosTabla.COLUMNA_INTERACCION, GestionBD.DatosTabla.COLUMNA_VALOR};

                try {
                    Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA, projection, GestionBD.DatosTabla.COLUMNA_ID+"=?", argsel, null, null, null);

                    c.moveToFirst();
                    NombTarea.setText(c.getString(0));
                    NombIcono.setText(c.getString(1));
                    Pot.setText(c.getString(2));
                    Tiempo.setText(c.getString(3));
                    Inter.setText(c.getString(4));
                    valor.setText(c.getString(5));
                    c.close();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "El registro no existe", Toast.LENGTH_LONG).show();
                    limpiar();
                }

            }

            private void limpiar() {
                NombTarea.setText("");
                NombIcono.setText("");
                Pot.setText("");
                Tiempo.setText("");
                Inter.setText("");
                valor.setText("");
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = gestionBD.getWritableDatabase();
                String Selection = GestionBD.DatosTabla.COLUMNA_ID+"=?";
                String[] argsel = {id.getText().toString()};

                db.delete(GestionBD.DatosTabla.NOMBRE_TABLA, Selection, argsel);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = gestionBD.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_TAREA, NombTarea.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_NOMB_ICONO, NombIcono.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, Pot.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, Tiempo.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, Inter.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_VALOR, valor.getText().toString());
                String Selection = GestionBD.DatosTabla.COLUMNA_ID+"=?";
                String[] argsel = {id.getText().toString()};

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA, valores, Selection, argsel);
                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
            }
        });

    }
}
