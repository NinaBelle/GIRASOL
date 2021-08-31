package com.example.girasolv10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformacFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformacFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformacFragment extends Fragment {

    private TextView FechaAct, nombZona, UltimaLectHS, PotEnt, hsp, pmax, temperatura;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GestionBD gestionBD=null;
    String nombZ, HSP1,PMAX1,hsultima,TEMP1, potEnt,part1,part2;
    private String NameOfFolder = "/CarpetaGirasol";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InformacFragment() {
        // Required empty public constructor
    }


    public static InformacFragment newInstance(String param1, String param2) {
        InformacFragment fragment = new InformacFragment();
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
        View view=inflater.inflate(R.layout.fragment_informac, container, false);
        gestionBD = new GestionBD(getActivity().getApplicationContext());

        nombZona = (TextView)view.findViewById(R.id.nombZona);
        UltimaLectHS =(TextView)view.findViewById(R.id.horaUltLect);
        PotEnt =(TextView)view.findViewById(R.id.potEntregada);
        FechaAct = (TextView)view.findViewById(R.id.horaactual);
        hsp = (TextView)view.findViewById(R.id.txtHSP);
        pmax = (TextView)view.findViewById(R.id.potMax);
        temperatura = (TextView)view.findViewById(R.id.txtTemp);

        buscarUtimosDatos();
        try {
            leerDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    private void leerDatos() throws IOException {
        Calendar K = Calendar.getInstance();
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
        String lastLine = "";
        String linea;
        if (ban){
            while ((linea = reader.readLine()) != null) {
                    lastLine=linea;
            }
            String[] parts = lastLine.split(";");
            String mes = parts[0];
            String dia = parts[1];
            String hora = parts[2];
            String iemp = parts[3];
            String vemp = parts[4];
            String temp = parts[5];
            part1= dia+" / "+mes+" / "+K.get(Calendar.YEAR);
            part2= hora+" : 00";
            FechaAct.setText(part1);
            UltimaLectHS.setText(part2);
            String[] partes = temp.split(",");
            TEMP1 = partes[0];
            temperatura.setText(TEMP1+" º");
            String cadi1 = iemp.replace(",", ".");
            String cadi2 = vemp.replace(",", ".");
            float piI = Float.parseFloat(cadi1);
            float piV = Float.parseFloat(cadi2);
            float x= piI*piV;
            float xemp = round (x,2);
            PotEnt.setText(xemp+" W");
        }

    }

    private void buscarUtimosDatos() {
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String[] argsel = {"1"};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_NOMB_ZONA, GestionBD.DatosTabla.COLUMNA_HSP, GestionBD.DatosTabla.COLUMNA_P_MAX};//, GestionBD.DatosTabla.COLUMNA_E_T
        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA3, projection, GestionBD.DatosTabla.COLUMNA_ID_DP+"=?", argsel, null, null, null);
            c.moveToFirst();
            nombZ = c.getString(0);
            HSP1 = c.getString(1);
            PMAX1 = c.getString(2);
            //potEnt = c.getString(3);

            nombZona.setText(nombZ);
            hsp.setText(HSP1);
            pmax.setText(PMAX1+" W");
            //PotEnt.setText(potEnt+" W");

            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "NO encontro datos"+e, Toast.LENGTH_LONG).show();
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
        // Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
