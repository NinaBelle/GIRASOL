package com.example.girasolv10;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnergiaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnergiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnergiaFragment extends Fragment {
    private TextView er0,er1, er2, er3, er4, er5, er6, er7, er8, er9, er10;
    private TextView eD0,eD1, eD2, eD3, eD4, eD5, eD6, eD7, eD8, eD9, eD10;
    private TextView R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10;
    private TextView Hra0, Hra1, Hra2, Hra3, Hra4, Hra5, Hra6, Hra7, Hra8, Hra9, Hra10;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView titulo;
    private double resuFinal;
    private String mParam1;
    private String mParam2;
    private String array1[]= new String[20];
    private Button grafico;

    private OnFragmentInteractionListener mListener;

    public EnergiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnergiaFragment.   Rename and change types and number of parameters
     */

    public static EnergiaFragment newInstance(String param1, String param2) {
        EnergiaFragment fragment = new EnergiaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_energia, container, false);
        titulo = (TextView) view.findViewById(R.id.tag4);
        grafico = (Button) view.findViewById(R.id.btnGrafico);

        Hra0 = (TextView)view.findViewById(R.id.hora1);
        Hra1 = (TextView)view.findViewById(R.id.hora2);
        Hra2 = (TextView)view.findViewById(R.id.hora3);
        Hra3 = (TextView)view.findViewById(R.id.hora4);
        Hra4 = (TextView)view.findViewById(R.id.hora5);
        Hra5 = (TextView)view.findViewById(R.id.hora6);
        Hra6 = (TextView)view.findViewById(R.id.hora7);
        Hra7 = (TextView)view.findViewById(R.id.hora8);
        Hra8 = (TextView)view.findViewById(R.id.hora9);
        Hra9 = (TextView)view.findViewById(R.id.hora10);
        Hra10 = (TextView)view.findViewById(R.id.hora11);

        er0 = (TextView)view.findViewById(R.id.er0);
        er1 = (TextView)view.findViewById(R.id.er1);
        er2 = (TextView)view.findViewById(R.id.er2);
        er3 = (TextView)view.findViewById(R.id.er3);
        er4 = (TextView)view.findViewById(R.id.er4);
        er5 = (TextView)view.findViewById(R.id.er5);
        er6 = (TextView)view.findViewById(R.id.er6);
        er7 = (TextView)view.findViewById(R.id.er7);
        er8 = (TextView)view.findViewById(R.id.er8);
        er9 = (TextView)view.findViewById(R.id.er9);
        er10 = (TextView)view.findViewById(R.id.er10);

        eD0 = (TextView)view.findViewById(R.id.eDisp0);
        eD1 = (TextView)view.findViewById(R.id.eDisp1);
        eD2 = (TextView)view.findViewById(R.id.eDisp2);
        eD3 = (TextView)view.findViewById(R.id.eDisp3);
        eD4 = (TextView)view.findViewById(R.id.eDisp4);
        eD5 = (TextView)view.findViewById(R.id.eDisp5);
        eD6 = (TextView)view.findViewById(R.id.eDisp6);
        eD7 = (TextView)view.findViewById(R.id.eDisp7);
        eD8 = (TextView)view.findViewById(R.id.eDisp8);
        eD9 = (TextView)view.findViewById(R.id.eDisp9);
        eD10 = (TextView)view.findViewById(R.id.eDisp10);

        R0 = (TextView)view.findViewById(R.id.resto0);
        R1 = (TextView)view.findViewById(R.id.resto1);
        R2 = (TextView)view.findViewById(R.id.resto2);
        R3 = (TextView)view.findViewById(R.id.resto3);
        R4 = (TextView)view.findViewById(R.id.resto4);
        R5 = (TextView)view.findViewById(R.id.resto5);
        R6 = (TextView)view.findViewById(R.id.resto6);
        R7 = (TextView)view.findViewById(R.id.resto7);
        R8 = (TextView)view.findViewById(R.id.resto8);
        R9 = (TextView)view.findViewById(R.id.resto9);
        R10 = (TextView)view.findViewById(R.id.resto10);
        //titulo.setTextColor(Color.parseColor("#3F51B5"));
        calcularERequerida();
        calcularEDisponible();
        calcularRemanente();
        grafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), GraficoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void calcularERequerida() {
        float lambda, alfa;
        double resu = 0;
        float potencia=0;
        float tiempo= 0;
        int interaccion= 0, repeticiones=0;
        int capUsr = 0;
        int gama = 0;
        int nivBri = 0;
        double l1;
        int l2;
        String potSB="", tiempoSB="";

        GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
        SQLiteDatabase db = gestionBD.getWritableDatabase();

        String[] asl11 = {"1"};
        String[] proj = {GestionBD.DatosTabla.COLUMNA_POT_SB, GestionBD.DatosTabla.COLUMNA_TIEMPO_SB};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, proj, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", asl11, null, null, null);

            c.moveToFirst();
            //eStandBy = c.getString(0);
            potSB = c.getString(0);
            tiempoSB =c.getString(1);

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "no encontro datos", Toast.LENGTH_LONG).show();
        }
        int timeSB = Integer.parseInt(tiempoSB);
        float wattsSB = Float.parseFloat(potSB);
        float hs = (float) timeSB/60;
        float energiaSB = wattsSB * hs;
        //String eneSB = String.valueOf(energiaSB);
        //********
        String[] argsel = {"1"};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_CAP_USR, GestionBD.DatosTabla.COLUMNA_GAMA, GestionBD.DatosTabla.COLUMNA_NIVEL_BRILLO};
        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, projection, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", argsel, null, null, null);
            c.moveToFirst();
            String num= c.getString(0);
            capUsr = Integer.parseInt(num);
            String num1= c.getString(1);
            gama = Integer.parseInt(num1);
            String num2= c.getString(2);
            nivBri = Integer.parseInt(num2);

        }catch (Exception e){
            Log.i("Error", e.getMessage());
            e.printStackTrace();
        }
        l1 = Math.pow(gama, 2) - gama;
        l2 = 2*nivBri -2 ;
        float ss1= (float)l1/10;
        float ss2= (float)l2/10;
        lambda = (float) ( ss1 + ss2);

        float a1= (float) 0.8 *(capUsr - 1);
        Log.i("Elxxa1:", "C:"+capUsr+" A1: "+a1);
        String[] selectionArgs = {"1"};
        Cursor c = db.rawQuery("SELECT id, potencia, tiempo, interaccion, repeticion FROM ListaTareas WHERE valor = ?", selectionArgs);
        while (c.moveToNext()){

            String numCadena = c.getString(0);
            int indice = Integer.parseInt(numCadena);

            String numCadena1 = c.getString(1);
            potencia = Float.parseFloat(numCadena1);
            //float hsp1 = Float.parseFloat(HSP);
            Log.i("potencia", ""+potencia);

            String numCadena2 = c.getString(2);
            tiempo = Float.parseFloat(numCadena2);
            double tiempoHS= (double) tiempo / 60;
            Log.i("tiempoDTabla"," "+tiempo);
            Log.i("tiempoHS", " "+tiempoHS);

            String numCadena3 = c.getString(3);
            interaccion = Integer.parseInt(numCadena3);
            Log.i("interaccion", " "+interaccion);

            String numCadena4 = c.getString(4);
            repeticiones = Integer.parseInt(numCadena4);
            Log.i("repeticiones", " "+repeticiones);

            int inter= interaccion -1;

            int a2= (int) Math.pow(2, inter);
            alfa = (a2 -1)+ a1;///***********************a1=1.6  a2=2  alfa=2.6
            Log.i("alfa:", " "+alfa);

            double e1 = alfa * tiempoHS;
            double e2 = lambda * potencia;
            double energia = (tiempoHS + e1) * (potencia + e2);
            Log.i("energia ", "e1 "+e1+"   e2"+e2+"  energia"+energia);
            double repe=0;
            if (repeticiones>0) {
                repe = energia * repeticiones;
            }else {
                repe = energia;
            }
            resu = resu + repe;
            Log.i("acumulado", " "+resu);
        }
        Toast.makeText(getActivity().getApplicationContext(), "Resultados Calculdos", Toast.LENGTH_LONG).show();//eSB:"+energiaSB+" repet:"+repeticiones
        c.close();
        resu = resu+energiaSB;
        resuFinal = Math.round(resu * 100) / 100d;
        String cadena = String.valueOf(resuFinal);
        //****Guardar el total energia requerida para la grafica
        ContentValues valGraf = new ContentValues();
        valGraf.put(GestionBD.DatosTabla.COLUMNA_E_T, cadena);

        String Selec = GestionBD.DatosTabla.COLUMNA_ID_DP+"=?";
        String[] asl = {"1"};

        db.update(GestionBD.DatosTabla.NOMBRE_TABLA3, valGraf, Selec, asl);
        //****
        er0.setText(cadena+" wh");
        er1.setText(cadena+" wh");
        er2.setText(cadena+" wh");
        er3.setText(cadena+" wh");
        er4.setText(cadena+" wh");
        er5.setText(cadena+" wh");
        er6.setText(cadena+" wh");
        er7.setText(cadena+" wh");
        er8.setText(cadena+" wh");
        er9.setText(cadena+" wh");
        er10.setText(cadena+" wh");
    }

    private void calcularEDisponible() {
        String array[];
        array = new String[20];

        GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
        SQLiteDatabase db = gestionBD.getWritableDatabase();
        //String eStandBy = "";
        String numOpc = "";
        String HSP = "";
        String PMAX = "";
        String[] asl11 = {"1"};
        String[] proj = {GestionBD.DatosTabla.COLUMNA_HSP, GestionBD.DatosTabla.COLUMNA_P_MAX,GestionBD.DatosTabla.COLUMNA_OPCS_Q};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, proj, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", asl11, null, null, null);

            c.moveToFirst();
            //eStandBy = c.getString(0);
            HSP = c.getString(0);
            PMAX =c.getString(1);
            numOpc = c.getString(2);

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "no encontro datos", Toast.LENGTH_LONG).show();
        }
        float hsp1 = Float.parseFloat(HSP);
        float pmax1 = Float.parseFloat(PMAX);

        if (numOpc.contains("0")) {
            Cursor c = db.rawQuery("SELECT idVH, Q_i FROM "+ GestionBD.DatosTabla.NOMBRE_TABLA2, null);
            while (c.moveToNext()){
                String numCadena = c.getString(0);
                int indice = Integer.parseInt(numCadena);

                array[indice]= c.getString(1);
            }
            c.close();

            for (int i=8; i <= 18; i=i+1){
                String variable = array[i];
                float var1 = Float.parseFloat(variable);
                float total = var1 * hsp1 * pmax1;
                double totalfinal = Math.round(total * 100) / 100d;
                String cadena = String.valueOf(totalfinal);
                array1[i]= cadena;
            }
        }
        if (numOpc.contains("1")) {
            Cursor c = db.rawQuery("SELECT idVH, Q_ar FROM "+ GestionBD.DatosTabla.NOMBRE_TABLA2, null);
            while (c.moveToNext()){
                String numCadena = c.getString(0);
                int indice = Integer.parseInt(numCadena);

                array[indice]= c.getString(1);
            }
            c.close();
            //array1=array;
            for (int i=8; i <= 18; i=i+1){
                String variable = array[i];
                float var1 = Float.parseFloat(variable);
                float total = var1 * hsp1 * pmax1;
                double totalfinal = Math.round(total * 100) / 100d;
                String cadena = String.valueOf(totalfinal);
                array1[i]= cadena;
            }
        }

        for (int s=8; s <= 18; s=s+1){
            String Selection = GestionBD.DatosTabla.COLUMNA_ID_VH+"=?";
            String indice = String.valueOf(s);
            String[] as0 = {indice};

            ContentValues val0 = new ContentValues();
            val0.put(GestionBD.DatosTabla.COLUMNA_E_i_ar, array1[s]);

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val0, Selection, as0);
        }
        eD0.setText(array1[8]+" wh");
        eD1.setText(array1[9]+" wh");
        eD2.setText(array1[10]+" wh");
        eD3.setText(array1[11]+" wh");
        eD4.setText(array1[12]+" wh");
        eD5.setText(array1[13]+" wh");
        eD6.setText(array1[14]+" wh");
        eD7.setText(array1[15]+" wh");
        eD8.setText(array1[16]+" wh");
        eD9.setText(array1[17]+" wh");
        eD10.setText(array1[18]+" wh");
    }

    private void calcularRemanente() {
        String arrRema[];
        arrRema = new String[20];
        for (int i=8; i <= 18; i=i+1){
            String variable = array1[i];
            float eneDisp = Float.parseFloat(variable);
            double total = eneDisp-resuFinal;
            double totalfinal = Math.round(total * 100) / 100d;
            String cadena = String.valueOf(totalfinal);
            arrRema[i]= cadena;

        }
        R0.setText(arrRema[8]+" wh");
        R1.setText(arrRema[9]+" wh");
        R2.setText(arrRema[10]+" wh");
        R3.setText(arrRema[11]+" wh");
        R4.setText(arrRema[12]+" wh");
        R5.setText(arrRema[13]+" wh");
        R6.setText(arrRema[14]+" wh");
        R7.setText(arrRema[15]+" wh");
        R8.setText(arrRema[16]+" wh");
        R9.setText(arrRema[17]+" wh");
        R10.setText(arrRema[18]+" wh");

        float pnum1 = Float.parseFloat(arrRema[8]);
        if (pnum1 > 0){
            Hra0.setTextColor(Color.RED);
            R0.setTextColor(Color.RED);
            eD0.setTextColor(Color.RED);
            er0.setTextColor(Color.RED);
        }else {
            Hra0.setTextColor(Color.BLUE);
            R0.setTextColor(Color.BLUE);
            eD0.setTextColor(Color.BLUE);
            er0.setTextColor(Color.BLUE);
        }

        float pnum2 = Float.parseFloat(arrRema[9]);
        if (pnum2 > 0){
            Hra1.setTextColor(Color.RED);
            R1.setTextColor(Color.RED);
            eD1.setTextColor(Color.RED);
            er1.setTextColor(Color.RED);
        }else {
            Hra1.setTextColor(Color.BLUE);
            R1.setTextColor(Color.BLUE);
            eD1.setTextColor(Color.BLUE);
            er1.setTextColor(Color.BLUE);
        }

        float pnum3 = Float.parseFloat(arrRema[10]);
        if (pnum3 > 0){
            Hra2.setTextColor(Color.RED);
            R2.setTextColor(Color.RED);
            eD2.setTextColor(Color.RED);
            er2.setTextColor(Color.RED);
        }else {
            Hra2.setTextColor(Color.BLUE);
            R2.setTextColor(Color.BLUE);
            eD2.setTextColor(Color.BLUE);
            er2.setTextColor(Color.BLUE);
        }

        float pnum4 = Float.parseFloat(arrRema[11]);
        if (pnum4 > 0){
            Hra3.setTextColor(Color.RED);
            R3.setTextColor(Color.RED);
            eD3.setTextColor(Color.RED);
            er3.setTextColor(Color.RED);
        }else {
            Hra3.setTextColor(Color.BLUE);
            R3.setTextColor(Color.BLUE);
            eD3.setTextColor(Color.BLUE);
            er3.setTextColor(Color.BLUE);
        }

        float pnum5 = Float.parseFloat(arrRema[12]);
        if (pnum5 > 0){
            Hra4.setTextColor(Color.RED);
            R4.setTextColor(Color.RED);
            eD4.setTextColor(Color.RED);
            er4.setTextColor(Color.RED);
        }else {
            Hra4.setTextColor(Color.BLUE);
            R4.setTextColor(Color.BLUE);
            eD4.setTextColor(Color.BLUE);
            er4.setTextColor(Color.BLUE);
        }

        float pnum6 = Float.parseFloat(arrRema[13]);
        if (pnum6 > 0){
            Hra5.setTextColor(Color.RED);
            R5.setTextColor(Color.RED);
            eD5.setTextColor(Color.RED);
            er5.setTextColor(Color.RED);
        }else {
            Hra5.setTextColor(Color.BLUE);
            R5.setTextColor(Color.BLUE);
            eD5.setTextColor(Color.BLUE);
            er5.setTextColor(Color.BLUE);
        }

        float pnum7 = Float.parseFloat(arrRema[14]);
        if (pnum7 > 0){
            Hra6.setTextColor(Color.RED);
            R6.setTextColor(Color.RED);
            eD6.setTextColor(Color.RED);
            er6.setTextColor(Color.RED);
        }else {
            Hra6.setTextColor(Color.BLUE);
            R6.setTextColor(Color.BLUE);
            eD6.setTextColor(Color.BLUE);
            er6.setTextColor(Color.BLUE);
        }

        float pnum8 = Float.parseFloat(arrRema[15]);
        if (pnum8 > 0){
            Hra7.setTextColor(Color.RED);
            R7.setTextColor(Color.RED);
            eD7.setTextColor(Color.RED);
            er7.setTextColor(Color.RED);
        }else {
            Hra7.setTextColor(Color.BLUE);
            R7.setTextColor(Color.BLUE);
            eD7.setTextColor(Color.BLUE);
            er7.setTextColor(Color.BLUE);
        }

        float pnum9 = Float.parseFloat(arrRema[16]);
        if (pnum9 > 0){
            Hra8.setTextColor(Color.RED);
            R8.setTextColor(Color.RED);
            eD8.setTextColor(Color.RED);
            er8.setTextColor(Color.RED);
        }else {
            Hra8.setTextColor(Color.BLUE);
            R8.setTextColor(Color.BLUE);
            eD8.setTextColor(Color.BLUE);
            er8.setTextColor(Color.BLUE);
        }

        float pnum10 = Float.parseFloat(arrRema[17]);
        if (pnum10 > 0){
            Hra9.setTextColor(Color.RED);
            R9.setTextColor(Color.RED);
            eD9.setTextColor(Color.RED);
            er9.setTextColor(Color.RED);
        }else {
            Hra9.setTextColor(Color.BLUE);
            R9.setTextColor(Color.BLUE);
            eD9.setTextColor(Color.BLUE);
            er9.setTextColor(Color.BLUE);
        }

        float pnum11 = Float.parseFloat(arrRema[18]);
        if (pnum11 > 0){
            Hra10.setTextColor(Color.RED);
            R10.setTextColor(Color.RED);
            eD10.setTextColor(Color.RED);
            er10.setTextColor(Color.RED);
        }else {
            Hra10.setTextColor(Color.BLUE);
            R10.setTextColor(Color.BLUE);
            eD10.setTextColor(Color.BLUE);
            er10.setTextColor(Color.BLUE);
        }

    }
    // Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        //Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
