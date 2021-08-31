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

public class Carga_IV_Activity extends AppCompatActivity {
    private EditText I0, I1, I2, I3, I4, I5, I6, I7, I8,I9, I10;
    private EditText V0, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10;
    private Button guardarIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga__iv_);

        I0 = (EditText) findViewById(R.id.ed_I0);
        I1 = (EditText) findViewById(R.id.ed_I1);
        I2 = (EditText) findViewById(R.id.ed_I2);
        I3 = (EditText) findViewById(R.id.ed_I3);
        I4 = (EditText) findViewById(R.id.ed_I4);
        I5 = (EditText) findViewById(R.id.ed_I5);
        I6 = (EditText) findViewById(R.id.ed_I6);
        I7 = (EditText) findViewById(R.id.ed_I7);
        I8 = (EditText) findViewById(R.id.ed_I8);
        I9 = (EditText) findViewById(R.id.ed_I9);
        I10 = (EditText) findViewById(R.id.ed_I10);

        V0 = (EditText) findViewById(R.id.ed_V0);
        V1 = (EditText) findViewById(R.id.ed_V1);
        V2 = (EditText) findViewById(R.id.ed_V2);
        V3 = (EditText) findViewById(R.id.ed_V3);
        V4 = (EditText) findViewById(R.id.ed_V4);
        V5 = (EditText) findViewById(R.id.ed_V5);
        V6 = (EditText) findViewById(R.id.ed_V6);
        V7 = (EditText) findViewById(R.id.ed_V7);
        V8 = (EditText) findViewById(R.id.ed_V8);
        V9 = (EditText) findViewById(R.id.ed_V9);
        V10 = (EditText) findViewById(R.id.ed_V10);

        mostrarPares();

        guardarIV = (Button) findViewById(R.id.btnGuardarIV);
        final GestionBD gestionBD = new GestionBD(getApplicationContext());

        guardarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sumP = 0;
                float pR0 =0;
                float pR1 =0;
                float pR2 =0;
                float pR3 =0;
                float pR4 =0;
                float pR5 =0;
                float pR6 =0;
                float pR7 =0;
                float pR8 =0;
                float pR9 =0;
                float pR10 =0;

                String numI0 = I0.getText().toString();
                float i0 = Float.parseFloat(numI0);
                String numV0 = V0.getText().toString();
                float v0 = Float.parseFloat(numV0);
                if (v0 > 3.7){
                    pR0 = (float) (i0 * 3.7);
                }else {
                    pR0 = v0*i0;
                }
                float p0 = v0*i0;
                //*****
                String numI1 = I1.getText().toString();
                float i1 = Float.parseFloat(numI1);
                String numV1 = V1.getText().toString();
                float v1 = Float.parseFloat(numV1);
                if (v1 > 3.7){
                    pR1 = (float) (i1 * 3.7);
                }else {
                    pR1 = v1*i1;
                }
                float p1 = v1*i1;
                //*****
                String numI2 = I2.getText().toString();
                float i2 = Float.parseFloat(numI2);
                String numV2 = V2.getText().toString();
                float v2 = Float.parseFloat(numV2);
                if (v2 > 3.7){
                    pR2 = (float) (i2 * 3.7);
                }else {
                    pR2 = v2*i2;
                }
                float p2 = v2*i2;
                //******
                String numI3 = I3.getText().toString();
                float i3 = Float.parseFloat(numI3);
                String numV3 = V3.getText().toString();
                float v3 = Float.parseFloat(numV3);
                if (v3 > 3.7){
                    pR3 = (float) (i3 * 3.7);
                }else {
                    pR3 = v3*i3;
                }
                float p3 = v3*i3;
                //******
                String numI4 = I4.getText().toString();
                float i4 = Float.parseFloat(numI4);
                String numV4 = V4.getText().toString();
                float v4 = Float.parseFloat(numV4);
                if (v4 > 3.7){
                    pR4 = (float) (i4 * 3.7);
                }else {
                    pR4 = v4*i4;
                }
                float p4 = v4*i4;
                //******
                String numI5 = I5.getText().toString();
                float i5 = Float.parseFloat(numI5);
                String numV5 = V5.getText().toString();
                float v5 = Float.parseFloat(numV5);
                if (v5 > 3.7){
                    pR5 = (float) (i5 * 3.7);
                }else {
                    pR5 = v5*i5;
                }
                float p5 = v5*i5;
                //******
                String numI6 = I6.getText().toString();
                float i6 = Float.parseFloat(numI6);
                String numV6 = V6.getText().toString();
                float v6 = Float.parseFloat(numV6);
                if (v6 > 3.7){
                    pR6 = (float) (i6 * 3.7);
                }else {
                    pR6 = v6*i6;
                }
                float p6 = v6*i6;
                //******
                String numI7 = I7.getText().toString();
                float i7 = Float.parseFloat(numI7);
                String numV7 = V7.getText().toString();
                float v7 = Float.parseFloat(numV7);
                if (v7 > 3.7){
                    pR7 = (float) (i7 * 3.7);
                }else {
                    pR7 = v7*i7;
                }
                float p7 = v7*i7;
                //******
                String numI8 = I8.getText().toString();
                float i8 = Float.parseFloat(numI8);
                String numV8 = V8.getText().toString();
                float v8 = Float.parseFloat(numV8);
                if (v8 > 3.7){
                    pR8 = (float) (i8 * 3.7);
                }else {
                    pR8 = v8*i8;
                }
                float p8 = v8*i8;
                //******
                String numI9 = I9.getText().toString();
                float i9 = Float.parseFloat(numI9);
                String numV9 = V9.getText().toString();
                float v9 = Float.parseFloat(numV9);
                if (v9 > 3.7){
                    pR9 = (float) (i9 * 3.7);
                }else {
                    pR9 = v9*i9;
                }
                float p9 = v9*i9;
                //******
                String numI10 = I10.getText().toString();
                float i10 = Float.parseFloat(numI10);
                String numV10 = V10.getText().toString();
                float v10 = Float.parseFloat(numV10);
                if (v10 > 3.7){
                    pR10 = (float) (i10 * 3.7);
                }else {
                    pR10 = v10*i10;
                }
                float p10 = v10*i10;
                //******
                sumP = p0+p1+p2+p3+p4+p5+p6+p7+p8+p9+p10;

                float q0 = pR0/sumP;
                float q1 = pR1/sumP;
                float q2 = pR2/sumP;
                float q3 = pR3/sumP;
                float q4 = pR4/sumP;
                float q5 = pR5/sumP;
                float q6 = pR6/sumP;
                float q7 = pR7/sumP;
                float q8 = pR8/sumP;
                float q9 = pR9/sumP;
                float q10 = pR10/sumP;

                SQLiteDatabase db = gestionBD.getWritableDatabase();
                String Selection = GestionBD.DatosTabla.COLUMNA_ID_VH+"=?";

                String[] as0 = {"8"};

                ContentValues val0 = new ContentValues();
                val0.put(GestionBD.DatosTabla.COLUMNA_I_ar, I0.getText().toString());
                val0.put(GestionBD.DatosTabla.COLUMNA_V_ar, V0.getText().toString());
                val0.put(GestionBD.DatosTabla.COLUMNA_P_ar, p0);
                val0.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q0);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val0, Selection, as0);
                //*****

                String[] as1 = {"9"};

                ContentValues val1 = new ContentValues();
                val1.put(GestionBD.DatosTabla.COLUMNA_I_ar, I1.getText().toString());
                val1.put(GestionBD.DatosTabla.COLUMNA_V_ar, V1.getText().toString());
                val1.put(GestionBD.DatosTabla.COLUMNA_P_ar, p1);
                val1.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q1);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val1, Selection, as1);
                //*****

                String[] as2 = {"10"};

                ContentValues val2 = new ContentValues();
                val2.put(GestionBD.DatosTabla.COLUMNA_I_ar, I2.getText().toString());
                val2.put(GestionBD.DatosTabla.COLUMNA_V_ar, V2.getText().toString());
                val2.put(GestionBD.DatosTabla.COLUMNA_P_ar, p2);
                val2.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q2);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val2, Selection, as2);
                //*****

                String[] as3 = {"11"};

                ContentValues val3 = new ContentValues();
                val3.put(GestionBD.DatosTabla.COLUMNA_I_ar, I3.getText().toString());
                val3.put(GestionBD.DatosTabla.COLUMNA_V_ar, V3.getText().toString());
                val3.put(GestionBD.DatosTabla.COLUMNA_P_ar, p3);
                val3.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q3);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val3, Selection, as3);
                //*****

                String[] as4 = {"12"};

                ContentValues val4 = new ContentValues();
                val4.put(GestionBD.DatosTabla.COLUMNA_I_ar, I4.getText().toString());
                val4.put(GestionBD.DatosTabla.COLUMNA_V_ar, V4.getText().toString());
                val4.put(GestionBD.DatosTabla.COLUMNA_P_ar, p4);
                val4.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q4);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val4, Selection, as4);
                //*****

                String[] as5 = {"13"};

                ContentValues val5 = new ContentValues();
                val5.put(GestionBD.DatosTabla.COLUMNA_I_ar, I5.getText().toString());
                val5.put(GestionBD.DatosTabla.COLUMNA_V_ar, V5.getText().toString());
                val5.put(GestionBD.DatosTabla.COLUMNA_P_ar, p5);
                val5.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q5);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val5, Selection, as5);
                //*****

                String[] as6 = {"14"};

                ContentValues val6 = new ContentValues();
                val6.put(GestionBD.DatosTabla.COLUMNA_I_ar, I6.getText().toString());
                val6.put(GestionBD.DatosTabla.COLUMNA_V_ar, V6.getText().toString());
                val6.put(GestionBD.DatosTabla.COLUMNA_P_ar, p6);
                val6.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q6);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val6, Selection, as6);
                //*****

                String[] as7 = {"15"};

                ContentValues val7 = new ContentValues();
                val7.put(GestionBD.DatosTabla.COLUMNA_I_ar, I7.getText().toString());
                val7.put(GestionBD.DatosTabla.COLUMNA_V_ar, V7.getText().toString());
                val7.put(GestionBD.DatosTabla.COLUMNA_P_ar, p7);
                val7.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q7);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val7, Selection, as7);
                //*****

                String[] as8 = {"16"};

                ContentValues val8 = new ContentValues();
                val8.put(GestionBD.DatosTabla.COLUMNA_I_ar, I8.getText().toString());
                val8.put(GestionBD.DatosTabla.COLUMNA_V_ar, V8.getText().toString());
                val8.put(GestionBD.DatosTabla.COLUMNA_P_ar, p8);
                val8.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q8);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val8, Selection, as8);
                //*****

                String[] as9 = {"17"};

                ContentValues val9 = new ContentValues();
                val9.put(GestionBD.DatosTabla.COLUMNA_I_ar, I9.getText().toString());
                val9.put(GestionBD.DatosTabla.COLUMNA_V_ar, V9.getText().toString());
                val9.put(GestionBD.DatosTabla.COLUMNA_P_ar, p9);
                val9.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q9);

                long newR = db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val9, Selection, as9);
                //*****

                String[] as10 = {"18"};

                ContentValues val10 = new ContentValues();
                val10.put(GestionBD.DatosTabla.COLUMNA_I_ar, I10.getText().toString());
                val10.put(GestionBD.DatosTabla.COLUMNA_V_ar, V10.getText().toString());
                val10.put(GestionBD.DatosTabla.COLUMNA_P_ar, p10);
                val10.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q10);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val10, Selection, as10);
                //*****

                Toast.makeText(getApplicationContext(), "Se guardaron los datos: "+newR, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void mostrarPares() {
        GestionBD gestionBD = new GestionBD(getApplicationContext());
        String array[], arr1[];
        array = new String[20];
        arr1 = new String[20];

        SQLiteDatabase db = gestionBD.getReadableDatabase();

        for (int i=8; i <= 18; i=i+1){
            String[] projection = {GestionBD.DatosTabla.COLUMNA_I_ar, GestionBD.DatosTabla.COLUMNA_V_ar};
            String cadena = String.valueOf(i);
            String[] argsel = {cadena};
            try {
                Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA2, projection, GestionBD.DatosTabla.COLUMNA_ID_VH+"=?", argsel, null, null, null);

                c.moveToFirst();
                array[i]=c.getString(0);
                arr1[i]=c.getString(1);

                c.close();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "No hay coeficientes guardados", Toast.LENGTH_LONG).show();
            }

        }

        I0.setText(array[8]);
        V0.setText(arr1[8]);
        I1.setText(array[9]);
        V1.setText(arr1[9]);
        I2.setText(array[10]);
        V2.setText(arr1[10]);
        I3.setText(array[11]);
        V3.setText(arr1[11]);
        I4.setText(array[12]);
        V4.setText(arr1[12]);
        I5.setText(array[13]);
        V5.setText(arr1[13]);
        I6.setText(array[14]);
        V6.setText(arr1[14]);
        I7.setText(array[15]);
        V7.setText(arr1[15]);
        I8.setText(array[16]);
        V8.setText(arr1[16]);
        I9.setText(array[17]);
        V9.setText(arr1[17]);
        I10.setText(array[18]);
        V10.setText(arr1[18]);
    }
}
