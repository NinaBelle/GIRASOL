package com.example.girasolv10;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParametrosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParametrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParametrosFragment extends Fragment {

    private Spinner opcDia, opcMeses, opcCarga;
    private Button btnLeerArdu, btnGuardarParam, btnExtra;
    private GestionBD gestionBD=null;
    private EditText etQ8, etQ9, etQ10, etQ11, etQ12, etQ13, etQ14, etQ15, etQ16, etQ17, etQ18, nombZona, potMax, hsp, potSB, tempoSB, volCarga;
    private TextView instruc, etiqDG;
    private RadioGroup CUrg, GCrg, NBrg;
    private RadioButton rbCU1, rbCU2, rbCU3, rbGC1, rbGC2, rbGC3, rbNB1, rbNB2, rbNB3;
    private String meselegido, diaelegido;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String NameOfFolder = "/CarpetaGirasol";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ParametrosFragment() {
        // Required empty public constructor
    }


    public static ParametrosFragment newInstance(String param1, String param2) {
        ParametrosFragment fragment = new ParametrosFragment();
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
        View vista = inflater.inflate(R.layout.fragment_parametros, container, false);

        gestionBD = new GestionBD(getActivity().getApplicationContext());

        instruc = (TextView) vista.findViewById(R.id.relleno);
        etiqDG = (TextView) vista.findViewById(R.id.etiqDGuardado) ;
        opcMeses = (Spinner) vista.findViewById(R.id.txtMes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        opcMeses.setAdapter(adapter);
        opcMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                meselegido = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        opcDia = (Spinner) vista.findViewById(R.id.spdia);
        ArrayAdapter<CharSequence> adapterD = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.dia, android.R.layout.simple_spinner_item);
        opcDia.setAdapter(adapterD);
        opcDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diaelegido = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLeerArdu = (Button)vista.findViewById(R.id.btnLeerArdu);

        opcCarga = (Spinner)vista.findViewById(R.id.opcCarga);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opcionesCarga, android.R.layout.simple_spinner_item);
        opcCarga.setAdapter(adapter1);
        opcCarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        btnLeerArdu.setVisibility(View.VISIBLE);
                        instruc.setVisibility(View.INVISIBLE);
                    break;
                    case 2:
                        instruc.setText("Proceda a completar manualmente");
                        instruc.setVisibility(View.VISIBLE);
                        btnLeerArdu.setVisibility(View.INVISIBLE);
                        limpiar();
                        ponerCero();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CUrg = (RadioGroup)vista.findViewById(R.id.rg_CU);
        GCrg = (RadioGroup)vista.findViewById(R.id.rg_GC);
        NBrg = (RadioGroup)vista.findViewById(R.id.rg_NB);

        rbCU1 = (RadioButton)vista.findViewById(R.id.rbCapU1);
        rbCU2 = (RadioButton)vista.findViewById(R.id.rbCapU2);
        rbCU3 = (RadioButton)vista.findViewById(R.id.rbCapU3);

        rbGC1 = (RadioButton)vista.findViewById(R.id.rbGC1);
        rbGC2 = (RadioButton)vista.findViewById(R.id.rbGC2);
        rbGC3 = (RadioButton)vista.findViewById(R.id.rbGC3);

        rbNB1 = (RadioButton)vista.findViewById(R.id.rbNB1);
        rbNB2 = (RadioButton)vista.findViewById(R.id.rbNB2);
        rbNB3 = (RadioButton)vista.findViewById(R.id.rbNB3);

        nombZona = (EditText)vista.findViewById(R.id.txtZona);
        potMax = (EditText)vista.findViewById(R.id.txtPotenciaMax);
        hsp = (EditText)vista.findViewById(R.id.txtHSP);
        volCarga = (EditText)vista.findViewById(R.id.txtVC);

        potSB = (EditText)vista.findViewById(R.id.txtConsumSB);
        tempoSB = (EditText)vista.findViewById(R.id.edtiempoSB);

        etQ8 = (EditText)vista.findViewById(R.id.etQ8);
        etQ9 = (EditText)vista.findViewById(R.id.etQ9);
        etQ10 = (EditText)vista.findViewById(R.id.etQ10);
        etQ11 = (EditText)vista.findViewById(R.id.etQ11);
        etQ12 = (EditText)vista.findViewById(R.id.etQ12);
        etQ13 = (EditText)vista.findViewById(R.id.etQ13);
        etQ14 = (EditText)vista.findViewById(R.id.etQ14);
        etQ15 = (EditText)vista.findViewById(R.id.etQ15);
        etQ16 = (EditText)vista.findViewById(R.id.etQ16);
        etQ17 = (EditText)vista.findViewById(R.id.etQ17);
        etQ18 = (EditText)vista.findViewById(R.id.etQ18);
        

        btnGuardarParam = (Button)vista.findViewById(R.id.btnGuardarParam);
        btnExtra = (Button)vista.findViewById(R.id.btnExtra);
        btnGuardarParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarTodo();
            }
        });
        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Carga_IV_Activity.class);
                startActivity(intent);
            }
        });
        cargarRadios();
        cargarQs();
        cargarOtrosDatos();

        btnLeerArdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerArduino();
            }

        });

        return vista;
    }

    private void ponerCero() {
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        int elcero= 0;
        ContentValues valores = new ContentValues();
        valores.put(GestionBD.DatosTabla.COLUMNA_OPCS_Q, elcero);

        String Selec = GestionBD.DatosTabla.COLUMNA_ID_DP+"=?";
        String[] asl = {"1"};

        db.update(GestionBD.DatosTabla.NOMBRE_TABLA3, valores, Selec, asl);
        Toast.makeText(getActivity().getApplicationContext(), "Carga Manual", Toast.LENGTH_LONG).show();
    }

    private void cargarRadios() {
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String[] argsel = {"1"};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_CAP_USR, GestionBD.DatosTabla.COLUMNA_GAMA, GestionBD.DatosTabla.COLUMNA_NIVEL_BRILLO};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, projection, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", argsel, null, null, null);

            c.moveToFirst();
            String capU = c.getString(0);
            switch (capU){
                case "0":
                    CUrg.check(R.id.rbCapU1); break;
                case "3": CUrg.check(R.id.rbCapU1); break;
                case "2": CUrg.check(R.id.rbCapU2); break;
                case "1": CUrg.check(R.id.rbCapU3); break;
            }

            String gamaC = c.getString(1);
            switch (gamaC){
                case "0": GCrg.check(R.id.rbGC1); break;
                case "3": GCrg.check(R.id.rbGC3); break;
                case "2": GCrg.check(R.id.rbGC2); break;
                case "1": GCrg.check(R.id.rbGC1); break;
            }

            String nivelB = c.getString(2);
            switch (nivelB){
                case "0": NBrg.check(R.id.rbNB1); break;
                case "3": NBrg.check(R.id.rbNB3); break;
                case "2": NBrg.check(R.id.rbNB2); break;
                case "1": NBrg.check(R.id.rbNB1); break;
            }

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "No se encontro registros de Parámetros", Toast.LENGTH_LONG).show();

        }
    }


    private void cargarOtrosDatos() {
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String[] argsel = {"1"};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_NOMB_ZONA, GestionBD.DatosTabla.COLUMNA_MES, GestionBD.DatosTabla.COLUMNA_POT_SB, GestionBD.DatosTabla.COLUMNA_TIEMPO_SB, GestionBD.DatosTabla.COLUMNA_HSP, GestionBD.DatosTabla.COLUMNA_P_MAX, GestionBD.DatosTabla.COLUMNA_OPCS_Q, GestionBD.DatosTabla.COLUMNA_VC};
        //String[] otraproj = {GestionBD.DatosTabla.COLUMNA_E_i};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, projection, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", argsel, null, null, null);

            c.moveToFirst();
            nombZona.setText(c.getString(0));
            String mestraido = c.getString(1);

            potSB.setText(c.getString(2));
            tempoSB.setText(c.getString(3));

            hsp.setText(c.getString(4));
            potMax.setText(c.getString(5));
            switch (mestraido){
                case "Enero": opcMeses.setSelection(1); break;
                case "Febrero": opcMeses.setSelection(2); break;
                case "Marzo": opcMeses.setSelection(3); break;
                case "Abril": opcMeses.setSelection(4); break;
                case "Mayo": opcMeses.setSelection(5); break;
                case "Junio": opcMeses.setSelection(6); break;
                case "Julio": opcMeses.setSelection(7); break;
                case "Agosto": opcMeses.setSelection(8); break;
                case "Septiembre": opcMeses.setSelection(9); break;
                case "Octubre": opcMeses.setSelection(10); break;
                case "Noviembre": opcMeses.setSelection(11); break;
                case "Diciembre": opcMeses.setSelection(12); break;
            }
            if (c.getString(6).contains("0")){etiqDG.setText("Guardado manualmente");}
            if (c.getString(6).contains("1")){etiqDG.setText("Guardado de Arduino");}
            volCarga.setText(c.getString(7));
            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "No se encontro registros", Toast.LENGTH_LONG).show();

        }
        /*try {
            Cursor c1 = db.query(GestionBD.DatosTabla.NOMBRE_TABLA2, otraproj, GestionBD.DatosTabla.COLUMNA_ID_VH+"=?", argsel, null,null, null);
            c1.moveToFirst();
            etE0.setText(c1.getString(0));
            c1.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "No se encontro primer E", Toast.LENGTH_LONG).show();
        }*/
    }

    private void cargarQs() {
        String array[];
        array = new String[30];

        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String[] projection = new String[0];

        //**********
        String numOpc = "";
        String[] asl11 = {"1"};
        String[] proj = {GestionBD.DatosTabla.COLUMNA_OPCS_Q};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, proj, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", asl11, null, null, null);

            c.moveToFirst();
            numOpc = c.getString(0);

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "no encontro datos", Toast.LENGTH_LONG).show();
        }

        if (numOpc.contains("0")) {
            projection = new String[]{GestionBD.DatosTabla.COLUMNA_Q_i};
        }
        if (numOpc.contains("1")) {
            projection = new String[]{GestionBD.DatosTabla.COLUMNA_Q_ar};
        }
        //*****
        for (int i=8; i <= 18; i=i+1){

            String cadena = String.valueOf(i);
            String[] argsel = {cadena};
            try {

                Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA2, projection, GestionBD.DatosTabla.COLUMNA_ID_VH+"=?", argsel, null, null, null);

                c.moveToFirst();
                array[i]=c.getString(0);

                c.close();
            }catch (Exception e){
                Toast.makeText(getActivity().getApplicationContext(), "No hay coeficientes guardados", Toast.LENGTH_LONG).show();
                limpiar();
            }

        }
        etQ8.setText(array[8]);
        etQ9.setText(array[9]);
        etQ10.setText(array[10]);
        etQ11.setText(array[11]);
        etQ12.setText(array[12]);
        etQ13.setText(array[13]);
        etQ14.setText(array[14]);
        etQ15.setText(array[15]);
        etQ16.setText(array[16]);
        etQ17.setText(array[17]);
        etQ18.setText(array[18]);
    }

    private void limpiar() {
        etQ8.setText("");
        etQ9.setText("");
        etQ10.setText("");
        etQ11.setText("");
        etQ12.setText("");
        etQ13.setText("");
        etQ14.setText("");
        etQ15.setText("");
        etQ16.setText("");
        etQ17.setText("");
        etQ18.setText("");
    }


    private void leerArduino() {
        try {
            leerArchivo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String arr[];
        arr = new String[30];

        SQLiteDatabase db = gestionBD.getReadableDatabase();
        for (int i=8; i <= 18; i=i+1){
            String[] projection = {GestionBD.DatosTabla.COLUMNA_Q_ar};
            String cadena = String.valueOf(i);
            String[] argsel = {cadena};
            try {
                Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA2, projection, GestionBD.DatosTabla.COLUMNA_ID_VH+"=?", argsel, null, null, null);

                c.moveToFirst();
                arr[i]=c.getString(0);

                c.close();
            }catch (Exception e){
                Toast.makeText(getActivity().getApplicationContext(), "No hay coeficientes guardados", Toast.LENGTH_LONG).show();
                limpiar();
            }

        }
        etQ8.setText(arr[8]);
        etQ9.setText(arr[9]);
        etQ10.setText(arr[10]);
        etQ11.setText(arr[11]);
        etQ12.setText(arr[12]);
        etQ13.setText(arr[13]);
        etQ14.setText(arr[14]);
        etQ15.setText(arr[15]);
        etQ16.setText(arr[16]);
        etQ17.setText(arr[17]);
        etQ18.setText(arr[18]);

        ContentValues valores = new ContentValues();
        valores.put(GestionBD.DatosTabla.COLUMNA_OPCS_Q, "1");// se pone en 1 la opcion Q se refiere a que cargo del archivo txt la ultima lectura guardada

        String Selec = GestionBD.DatosTabla.COLUMNA_ID_DP+"=?";
        String[] asl = {"1"};

        db.update(GestionBD.DatosTabla.NOMBRE_TABLA3, valores, Selec, asl);

    }


    private void leerArchivo() throws IOException {
        boolean ban=false;
        BufferedReader reader = null;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        final File dir = new File(file_path);
        File archivo = new File(dir,"datosIV.txt");
        try {

            reader = new BufferedReader(new FileReader(archivo));
            ban=true;
            Toast.makeText(getActivity().getApplicationContext(), "Archivo Encontrado", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "No se encontro Archivo", Toast.LENGTH_LONG).show();
        }
        if (ban){
            boolean ban1=false;
            boolean ban2=false;
            SQLiteDatabase db = gestionBD.getWritableDatabase();

            String parI[], parV[], hora[];
            hora = new String[140];
            parI = new String[140];
            parV = new String[140];
            String mes, dia, vblemes = null;
            switch (meselegido){
                //case "Elegir un mes": vblemes="0"; break;
                case "Enero": vblemes="1"; break;
                case "Febrero": vblemes="2"; break;
                case "Marzo": vblemes="3"; break;
                case "Abril": vblemes="4"; break;
                case "Mayo": vblemes="5"; break;
                case "Junio": vblemes="6"; break;
                case "Julio": vblemes="7"; break;
                case "Agosto": vblemes="8"; break;
                case "Septiembre": vblemes="9"; break;
                case "Octubre": vblemes="10"; break;
                case "Noviembre": vblemes="11"; break;
                case "Diciembre": vblemes="12"; break;
            }
            int i=0, ulti=0;
            float promI[], promV[];
            promI = new float[20];
            promV = new float[20];

            if (meselegido.contains("Elegir un mes")){
                Toast.makeText(getActivity().getApplicationContext(), "Debe elegir un Mes", Toast.LENGTH_LONG).show();
            }else {
                if (diaelegido.contains("Elegir día")){
                    Toast.makeText(getActivity().getApplicationContext(), "Debe elegir un Día", Toast.LENGTH_LONG).show();
                }else {
                    ban2=true;
                }
            }
            if (ban2){
                String linea;
                while ((linea = reader.readLine()) != null){
                    String[] parts = linea.split(";");
                    mes = parts[0];
                    dia = parts[1];
                    if (mes.contains(vblemes)){ //se filtra por fecha
                        if (dia.contains(diaelegido)){
                            ban1=true;
                            hora[i] = parts[2];
                            parI[i] = parts[3];
                            parV[i] = parts[4];
                            i=i+1;
                        }
                    }
                }
            }

            if (ban1){//se realiza los calculos
                int s=0;
                float cont=0, sumaI=0, sumaV=0;
                i = i-2;
                Log.i("marcaI: ", "..:"+i);
                for (int j=0; j <= i; j=j+1){
                    if (hora[j].contains(hora[j + 1])){
                        cont = cont +1;
                        String cadena1 = parI[j];
                        String cadena2 = parV[j];
                        String cad1 = cadena1.replace(",", ".");
                        String cad2 = cadena2.replace(",", ".");
                        float pI = Float.parseFloat(cad1);
                        float pV = Float.parseFloat(cad2);
                        sumaI = sumaI + pI;
                        sumaV = sumaV + pV;
                /*Log.i("ParI ", ": "+parI[j]);
                Log.i("elcontador: ", " "+cont);
                Log.i("cadena1: ", " "+cadena1);
                Log.i("Cad1:", " "+cad1);*/
                    }else{
                        String cadi1 = parI[j].replace(",", ".");
                        String cadi2 = parV[j].replace(",", ".");
                        float piI = Float.parseFloat(cadi1);
                        float piV = Float.parseFloat(cadi2);
                        sumaI = sumaI + piI;
                        sumaV = sumaV + piV;
                        cont = cont + 1;
                        promI[s] = sumaI/cont;
                        promV[s] = sumaV/cont;
                        Log.i("els: ", " "+s);
                        Log.i("ElpromI: ", " "+promI[s]);
                        Log.i("ElPromV", " "+promV[s]);
                        s=s+1;
                        sumaI=0;
                        sumaV=0;
                        cont=0;
                    }
                }
                ulti=i+1;
                String cai1 = parI[ulti].replace(",", ".");
                String cai2 = parV[ulti].replace(",", ".");
                float piuI = Float.parseFloat(cai1);
                float piuV = Float.parseFloat(cai2);
                sumaI = sumaI + piuI;
                sumaV = sumaV + piuV;
                cont = cont + 1;
                promI[s] = sumaI/cont;
                promV[s] = sumaV/cont;
                Log.i("els: ", " "+s);
                Log.i("ElpromI: ", " "+promI[s]);
                Log.i("ElPromV", " "+promV[s]);
                //Toast.makeText(getActivity().getApplicationContext(), "PromI: "+promI[0]+" PromV: "+promV[0], Toast.LENGTH_LONG).show();
                float sumP;

                Toast.makeText(getActivity().getApplicationContext(), "Voltaje de Carga de Batería: "+volCarga.getText().toString(), Toast.LENGTH_LONG).show();
                String Volc = volCarga.getText().toString();
                float vc = Float.parseFloat(Volc);
                float pR0;
                if (promV[0] > vc){
                    pR0 =(promI[0] * vc);
                }else {
                    pR0 = promI[0]*promV[0];
                }
                float p0 = promI[0]*promV[0];
                //*****

                float pR1;
                if (promV[1] > vc){
                    pR1 =(promI[1] * vc);
                }else {
                    pR1 = promV[1]*promI[1];
                }
                float p1 = promV[1]*promI[1];
                //*****

                float pR2;
                if (promV[2] > vc){
                    pR2 =(promI[2] * vc);
                }else {
                    pR2 = promV[2]*promI[2];
                }
                float p2 = promV[2]*promI[2];
                //******

                float pR3;
                if (promV[3] > vc){
                    pR3 =(promI[3] * vc);
                }else {
                    pR3 = promV[3]*promI[3];
                }
                float p3 = promV[3]*promI[3];
                //******

                float pR4;
                if (promV[4] > vc){
                    pR4 =(promI[4] * vc);
                }else {
                    pR4 = promV[4]*promI[4];
                }
                float p4 = promV[4]*promI[4];
                //******

                float pR5;
                if (promV[5] > vc){
                    pR5 =(promI[5] * vc);
                }else {
                    pR5 = promV[5]*promI[5];
                }
                float p5 = promV[5]*promI[5];
                //******

                float pR6;
                if (promV[6] > vc){
                    pR6 =(promI[6] * vc);
                }else {
                    pR6 = promV[6]*promI[6];
                }
                float p6 = promV[6]*promI[6];
                //******

                float pR7;
                if (promV[7] > vc){
                    pR7 =(promI[7] * vc);
                }else {
                    pR7 = promV[7]*promI[7];
                }
                float p7 = promV[7]*promI[7];
                //******

                float pR8;
                if (promV[8] > vc){
                    pR8 =(promI[8] * vc);
                }else {
                    pR8 = promV[8]*promI[8];
                }
                float p8 = promV[8]*promI[8];
                //******

                float pR9;
                if (promV[9] > vc){
                    pR9 =(promI[9] * vc);
                }else {
                    pR9 = promV[9]*promI[9];
                }
                float p9 = promV[9]*promI[9];
                //******

                float pR10;
                if (promV[10] > vc){
                    pR10 =(promI[10] * vc);
                }else {
                    pR10 = promV[10]*promI[10];
                }
                float p10 = promV[10]*promI[10];
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

                String Selection = GestionBD.DatosTabla.COLUMNA_ID_VH+"=?";

                String[] as0 = {"8"};

                ContentValues val0 = new ContentValues();
                val0.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[0]);
                val0.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[0]);
                val0.put(GestionBD.DatosTabla.COLUMNA_P_ar, p0);
                val0.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q0);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val0, Selection, as0);
                //*****

                String[] as1 = {"9"};

                ContentValues val1 = new ContentValues();
                val1.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[1]);
                val1.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[1]);
                val1.put(GestionBD.DatosTabla.COLUMNA_P_ar, p1);
                val1.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q1);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val1, Selection, as1);
                //*****

                String[] as2 = {"10"};

                ContentValues val2 = new ContentValues();
                val2.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[2]);
                val2.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[2]);
                val2.put(GestionBD.DatosTabla.COLUMNA_P_ar, p2);
                val2.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q2);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val2, Selection, as2);
                //*****

                String[] as3 = {"11"};

                ContentValues val3 = new ContentValues();
                val3.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[3]);
                val3.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[3]);
                val3.put(GestionBD.DatosTabla.COLUMNA_P_ar, p3);
                val3.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q3);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val3, Selection, as3);
                //*****

                String[] as4 = {"12"};

                ContentValues val4 = new ContentValues();
                val4.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[4]);
                val4.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[4]);
                val4.put(GestionBD.DatosTabla.COLUMNA_P_ar, p4);
                val4.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q4);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val4, Selection, as4);
                //*****

                String[] as5 = {"13"};

                ContentValues val5 = new ContentValues();
                val5.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[5]);
                val5.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[5]);
                val5.put(GestionBD.DatosTabla.COLUMNA_P_ar, p5);
                val5.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q5);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val5, Selection, as5);
                //*****

                String[] as6 = {"14"};

                ContentValues val6 = new ContentValues();
                val6.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[6]);
                val6.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[6]);
                val6.put(GestionBD.DatosTabla.COLUMNA_P_ar, p6);
                val6.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q6);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val6, Selection, as6);
                //*****

                String[] as7 = {"15"};

                ContentValues val7 = new ContentValues();
                val7.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[7]);
                val7.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[7]);
                val7.put(GestionBD.DatosTabla.COLUMNA_P_ar, p7);
                val7.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q7);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val7, Selection, as7);
                //*****

                String[] as8 = {"16"};

                ContentValues val8 = new ContentValues();
                val8.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[8]);
                val8.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[8]);
                val8.put(GestionBD.DatosTabla.COLUMNA_P_ar, p8);
                val8.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q8);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val8, Selection, as8);
                //*****

                String[] as9 = {"17"};

                ContentValues val9 = new ContentValues();
                val9.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[9]);
                val9.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[9]);
                val9.put(GestionBD.DatosTabla.COLUMNA_P_ar, p9);
                val9.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q9);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val9, Selection, as9);
                //*****

                String[] as10 = {"18"};

                ContentValues val10 = new ContentValues();
                val10.put(GestionBD.DatosTabla.COLUMNA_I_ar, promI[10]);
                val10.put(GestionBD.DatosTabla.COLUMNA_V_ar, promV[10]);
                val10.put(GestionBD.DatosTabla.COLUMNA_P_ar, p10);
                val10.put(GestionBD.DatosTabla.COLUMNA_Q_ar, q10);

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val10, Selection, as10);
                Toast.makeText(getActivity().getApplicationContext(), "Datos extraidos de Archivo", Toast.LENGTH_LONG).show();
                //*****

            /*Calendar K = Calendar.getInstance();
            int month = K.get(Calendar.MONTH) + 1;
            String mes = String.valueOf(month);
            //String sDate = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
            String fechaA = K.get(Calendar.DAY_OF_MONTH) + "-" +mes + "-" +K.get(Calendar.YEAR);
            String horaA = K.get(Calendar.HOUR_OF_DAY) + ":" + K.get(Calendar.MINUTE);
            String Sele = GestionBD.DatosTabla.COLUMNA_ID_DP+"=?";

            String[] prep = {"1"};

            ContentValues valoresU = new ContentValues();
            valoresU.put(GestionBD.DatosTabla.COLUMNA_ULT_HS, fechaA+" "+horaA);

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA3, valoresU, Sele, prep);*/
            }else{
                limpiaraFondo();
                Toast.makeText(getActivity().getApplicationContext(), "No se encontró datos de la fecha ingresada", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void limpiaraFondo() {
        limpiar();
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        for (int i=8; i <= 18; i=i+1) {

            String cadena = String.valueOf(i);
            String[] sel = {cadena};
            ContentValues valores = new ContentValues();
            valores.put(GestionBD.DatosTabla.COLUMNA_Q_ar, "0");// se pone en 1 la opcion Q se refiere a que cargo del archivo txt la ultima lectura guardada

            String Selec = GestionBD.DatosTabla.COLUMNA_ID_VH+"=?";

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, valores, Selec, sel);
        }

    }

    private void guardarTodo() {
        int variable=0;
        int variable1=0;
        int variable2=0;

        if (rbCU1.isChecked()){
            variable=3;
        }
        if (rbCU2.isChecked()){
            variable=2;
        }
        if (rbCU3.isChecked()){
            variable=1;
        }

        if (rbGC1.isChecked()){
            variable1=1;
        }
        if (rbGC2.isChecked()){
            variable1=2;
        }
        if (rbGC3.isChecked()){
            variable1=3;
        }

        if (rbNB1.isChecked()){
            variable2=1;
        }
        if (rbNB2.isChecked()){
            variable2=2;
        }
        if (rbNB3.isChecked()){
            variable2=3;
        }

        SQLiteDatabase db = gestionBD.getReadableDatabase();
        ContentValues val1 = new ContentValues();
        val1.put(GestionBD.DatosTabla.COLUMNA_NOMB_ZONA, nombZona.getText().toString());
        val1.put(GestionBD.DatosTabla.COLUMNA_MES, meselegido);
        val1.put(GestionBD.DatosTabla.COLUMNA_POT_SB, potSB.getText().toString());
        val1.put(GestionBD.DatosTabla.COLUMNA_TIEMPO_SB, tempoSB.getText().toString());
        val1.put(GestionBD.DatosTabla.COLUMNA_VC, volCarga.getText().toString());
        val1.put(GestionBD.DatosTabla.COLUMNA_CAP_USR, variable);
        val1.put(GestionBD.DatosTabla.COLUMNA_GAMA, variable1);
        val1.put(GestionBD.DatosTabla.COLUMNA_NIVEL_BRILLO, variable2);
        val1.put(GestionBD.DatosTabla.COLUMNA_HSP, hsp.getText().toString() );
        val1.put(GestionBD.DatosTabla.COLUMNA_P_MAX, potMax.getText().toString());

        String Selection = GestionBD.DatosTabla.COLUMNA_ID_DP+"=?";
        String[] argsel = {"1"};

        db.update(GestionBD.DatosTabla.NOMBRE_TABLA3, val1, Selection, argsel);

        String tSB = tempoSB.getText().toString();
        String potenciaSB = potSB.getText().toString();
        int timeSB = Integer.parseInt(tSB);
        float wattsSB = Float.parseFloat(potenciaSB);
        int hs = timeSB/60;
        float energiaSB = wattsSB * hs;
        String eneSB = String.valueOf(energiaSB);

        ContentValues val2 = new ContentValues();
        val2.put(GestionBD.DatosTabla.COLUMNA_E_i, eneSB);

        String Seleccion = GestionBD.DatosTabla.COLUMNA_ID_VH+"=?";
        String[] argsel1 = {"1"};

        db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val2, Seleccion, argsel1);

        //**********
        String numOpc = "";
        String[] asl11 = {"1"};
        String[] proj = {GestionBD.DatosTabla.COLUMNA_OPCS_Q};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, proj, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", asl11, null, null, null);

            c.moveToFirst();
            numOpc = c.getString(0);

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "no encontro datos", Toast.LENGTH_LONG).show();
        }

        if (numOpc.contains("0")) {
            ContentValues val33 = new ContentValues();
            val33.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ8.getText().toString());
            String[] as110 = {"8"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val33, Seleccion, as110);
            //**********

            ContentValues val3 = new ContentValues();
            val3.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ9.getText().toString());
            String[] as1 = {"9"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val3, Seleccion, as1);
            //**********
            ContentValues val4 = new ContentValues();
            val4.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ10.getText().toString());
            String[] as2 = {"10"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val4, Seleccion, as2);
            //**********
            ContentValues val5 = new ContentValues();
            val5.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ11.getText().toString());
            String[] as3 = {"11"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val5, Seleccion, as3);
            //**********
            ContentValues val6 = new ContentValues();
            val6.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ12.getText().toString());
            String[] as4 = {"12"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val6, Seleccion, as4);
            //**********
            ContentValues val7 = new ContentValues();
            val7.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ13.getText().toString());
            String[] as5 = {"13"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val7, Seleccion, as5);
            //**********
            ContentValues val8 = new ContentValues();
            val8.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ14.getText().toString());
            String[] as6 = {"14"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val8, Seleccion, as6);
            //**********
            ContentValues val9 = new ContentValues();
            val9.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ15.getText().toString());
            String[] as7 = {"15"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val9, Seleccion, as7);
            //**********
            ContentValues val10 = new ContentValues();
            val10.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ16.getText().toString());
            String[] as8 = {"16"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val10, Seleccion, as8);
            //**********
            ContentValues val11 = new ContentValues();
            val11.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ17.getText().toString());
            String[] as9 = {"17"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val11, Seleccion, as9);
            //**********
            ContentValues val110 = new ContentValues();
            val110.put(GestionBD.DatosTabla.COLUMNA_Q_i, etQ18.getText().toString());
            String[] as95 = {"17"};

            db.update(GestionBD.DatosTabla.NOMBRE_TABLA2, val110, Seleccion, as95);
            Toast.makeText(getActivity().getApplicationContext(), "Datos manuales cargados", Toast.LENGTH_LONG).show();
            //*******------------
        }
        Toast.makeText(getActivity().getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_LONG).show();
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


    public interface OnFragmentInteractionListener {
        // Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
