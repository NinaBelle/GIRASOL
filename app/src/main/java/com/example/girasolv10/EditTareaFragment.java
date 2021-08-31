package com.example.girasolv10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EditTareaFragment extends Fragment {
    private Spinner opcInter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnGuardar;
    EditText Pot, Inter, Tiempo, Repe;
    TextView muestra, nombApp;
    private String mParam1, dato, eleccion="", interaccion="0";
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditTareaFragment() {
        // Required empty public constructor
    }

    public static EditTareaFragment newInstance(String param1, String param2) {
        EditTareaFragment fragment = new EditTareaFragment();
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
        View vista= inflater.inflate(R.layout.fragment_edit_tarea, container, false);
        muestra = (TextView)vista.findViewById(R.id.respuesta);
        nombApp = (TextView)vista.findViewById(R.id.txtElnombTA);
        dato = getArguments().getString("idTabla");
        //muestra.setText(dato);
        btnGuardar = (Button)vista.findViewById(R.id.btnGuardarTA);
        Pot = (EditText)vista.findViewById(R.id.etPotencia);
        opcInter = (Spinner) vista.findViewById(R.id.etInteraccion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opcInteracciones, android.R.layout.simple_spinner_item);
        opcInter.setAdapter(adapter);

        Tiempo = (EditText)vista.findViewById(R.id.etTiempo);
        Repe = (EditText)vista.findViewById(R.id.etValor);
        cargaDatos();
        final GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());

        opcInter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eleccion = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity().getApplicationContext(), "Eleccion: "+eleccion, Toast.LENGTH_LONG).show();
                switch (eleccion){
                    case "Elejir":
                                 interaccion="0";
                                 break;
                    case "Bajo":
                                interaccion="1";
                                break;
                    case "Medio":
                                interaccion="2";
                                break;
                    case "Alto":
                                interaccion="3";
                                break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = gestionBD.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(GestionBD.DatosTabla.COLUMNA_POTENCIA, Pot.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_TIEMPO, Tiempo.getText().toString());
                valores.put(GestionBD.DatosTabla.COLUMNA_INTERACCION, interaccion);
                valores.put(GestionBD.DatosTabla.COLUMNA_REPETICION, Repe.getText().toString());
                String Selection = GestionBD.DatosTabla.COLUMNA_ID+"=?";
                String[] argsel = {dato};

                db.update(GestionBD.DatosTabla.NOMBRE_TABLA, valores, Selection, argsel);
                Toast.makeText(getActivity().getApplicationContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
            }
        });


        return vista;
    }

    private void cargaDatos() {
        GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String[] argsel = {dato};
        String[] projection = {GestionBD.DatosTabla.COLUMNA_NOMB_TAREA,GestionBD.DatosTabla.COLUMNA_POTENCIA, GestionBD.DatosTabla.COLUMNA_TIEMPO,GestionBD.DatosTabla.COLUMNA_INTERACCION, GestionBD.DatosTabla.COLUMNA_REPETICION};

        try {
            Cursor c = db.query(GestionBD.DatosTabla.NOMBRE_TABLA, projection, GestionBD.DatosTabla.COLUMNA_ID+"=?", argsel, null, null, null);

            c.moveToFirst();
            nombApp.setText(c.getString(0));
            Pot.setText(c.getString(1));
            Tiempo.setText(c.getString(2));
            //Inter.setText(c.getString(3));
            String intertraido = c.getString(3);
            //Toast.makeText(getActivity().getApplicationContext(), "elecc Traido: "+intertraido, Toast.LENGTH_LONG).show();
            switch (intertraido){
                case "1": opcInter.setSelection(1); break;
                case "2": opcInter.setSelection(2); break;
                case "3": opcInter.setSelection(3); break;
            }
            Repe.setText(c.getString(4));
            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "El registro no existe", Toast.LENGTH_LONG).show();
        }
    }


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

        void onFragmentInteraction(Uri uri);
    }
}
