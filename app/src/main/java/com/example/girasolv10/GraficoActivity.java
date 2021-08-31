package com.example.girasolv10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class GraficoActivity extends AppCompatActivity {

    BarChart graficoB;
    String array[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        array = new String[20];

        GestionBD gestionBD = new GestionBD(getApplicationContext());
        SQLiteDatabase db = gestionBD.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT idVH, E_ar FROM "+ GestionBD.DatosTabla.NOMBRE_TABLA2, null);
        while (c.moveToNext()){
            String numCadena = c.getString(0);
            int indice = Integer.parseInt(numCadena);

            array[indice]= c.getString(1);
        }
        c.close();

        graficoB = (BarChart) findViewById(R.id.chart);
        List<BarEntry> entradas = new ArrayList<>();
        /*for (int i=8; i <= 18; i=i+1){
            int nro = i - 8;
            entradas.add(new BarEntry(Float.parseFloat(array[i]), nro));
        }*/

        entradas.add(new BarEntry(0, Float.parseFloat(array[8])));
        entradas.add(new BarEntry(1, Float.parseFloat(array[9])));
        entradas.add(new BarEntry(2, Float.parseFloat(array[10])));
        entradas.add(new BarEntry(3, Float.parseFloat(array[11])));
        entradas.add(new BarEntry(4, Float.parseFloat(array[12])));
        entradas.add(new BarEntry(5, Float.parseFloat(array[13])));
        entradas.add(new BarEntry(6, Float.parseFloat(array[14])));
        entradas.add(new BarEntry(7, Float.parseFloat(array[15])));
        entradas.add(new BarEntry(8, Float.parseFloat(array[16])));
        entradas.add(new BarEntry(9, Float.parseFloat(array[17])));
        entradas.add(new BarEntry(10, Float.parseFloat(array[18])));

        //****saca valor de la linea
        String numLinea = "";
        String[] asl11 = {"1"};
        String[] proj = {GestionBD.DatosTabla.COLUMNA_E_T};

        try {
            Cursor c11 = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, proj, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", asl11, null, null, null);

            c11.moveToFirst();
            numLinea = c11.getString(0);

            c11.close();
        }catch (Exception e){

        }
        //****

        BarDataSet datos = new BarDataSet(entradas, "Grafico Energía Disponible");

        BarData data = new BarData(datos);

        datos.setColor(Color.CYAN);

        data.setBarWidth(0.9f);

        graficoB.setData(data);
        graficoB.setFitBars(true);
        graficoB.animateY(5000);

        LimitLine linea = new LimitLine(Float.parseFloat(numLinea));
        YAxis ejeY = graficoB.getAxisLeft();
        ejeY.addLimitLine(linea);
        linea.setLabel("Energia Requerida: "+numLinea);
        linea.setLineWidth(6);

        graficoB.invalidate();

    }
}
