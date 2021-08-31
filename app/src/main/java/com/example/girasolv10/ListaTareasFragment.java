package com.example.girasolv10;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListaTareasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button agregar, configurar, guardar;

    ListView listaResultado;
    ArrayList<DatosTareas> lista;
    ListView listaTareas;
    ArrayList<Tareas> listaT;
    String array[];
    TextView etiqueta;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaTareasFragment() {
        // Required empty public constructor
    }

    public static ListaTareasFragment newInstance(String param1, String param2) {
        ListaTareasFragment fragment = new ListaTareasFragment();
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
        View vista=inflater.inflate(R.layout.fragment_lista_tareas, container, false);
        etiqueta = (TextView)vista.findViewById(R.id.etiqSelecc);
        configurar = (Button)vista.findViewById(R.id.btnAgregar);
        agregar = (Button)vista.findViewById(R.id.btnCarga);
        guardar = (Button)vista.findViewById(R.id.btnGuard);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CargaActivity.class);
                startActivity(intent);
            }
        });
        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity().getApplicationContext(), ConfigTareasActivity.class);
                //startActivity(intent);
                configuracion();
            }
        });
        listaResultado = (ListView)vista.findViewById(R.id.tareas_LV);
        listaTareas = (ListView)vista.findViewById(R.id.listaT_Edit);
        cargaPrimera();
        return vista;
    }

    private void configuracion() {
        configurar.setVisibility(View.INVISIBLE);
        guardar.setVisibility(View.VISIBLE);
        etiqueta.setVisibility(View.VISIBLE);
        listaResultado.setVisibility(View.VISIBLE);
        listaTareas.setVisibility(View.INVISIBLE);
        lista = new ArrayList<DatosTareas>();

        array = new String[50];

        final GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
        final SQLiteDatabase db = gestionBD.getWritableDatabase();
        DatosTareas tareas = null;
        lista.add(new DatosTareas(0, R.drawable.icon1, "ID", "Nomb Tarea", "Potencia", "Tiempo","Interaccion" ));
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
                int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                tareas.setImagen(imageResource);

                tareas.setNroT(numCadena);
                tareas.setNombTarea(c.getString(1));

                tareas.setPotencia(c.getString(3)+" w");
                tareas.setTiempo(c.getString(4)+ "m");

                String intertraido = c.getString(5);
                String paraponer="";
                switch (intertraido){
                    case "1": paraponer="Bajo"; break;
                    case "2": paraponer="Medio"; break;
                    case "3": paraponer="Alto"; break;
                }
                tareas.setInteraccion(paraponer);

                lista.add(tareas);
                resu = numCadena;
            }
            //Toast.makeText(getActivity().getApplicationContext(), "Resultado : "+resu, Toast.LENGTH_LONG).show();
            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "No hay datos en tabla", Toast.LENGTH_LONG).show();
        }

        AdaptadorDT miadaptador = new AdaptadorDT(getActivity().getApplicationContext(), lista);
        listaResultado.setAdapter(miadaptador);

        listaResultado.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(listaResultado.isItemChecked(position)){
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
                Toast.makeText(getActivity().getApplicationContext(), "Se guardo selección", Toast.LENGTH_LONG).show();
                cambio();
            }
        });
    }

    private void cambio() {
        configurar.setVisibility(View.VISIBLE);
        guardar.setVisibility(View.INVISIBLE);
        etiqueta.setVisibility(View.INVISIBLE);
        listaTareas.setVisibility(View.VISIBLE);
        listaResultado.setVisibility(View.INVISIBLE);
        cargaPrimera();
    }

    private void ponerACeros() {
        GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
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

    private void cargaPrimera() {
        GestionBD gestionBD = new GestionBD(getActivity().getApplicationContext());
        SQLiteDatabase db = gestionBD.getWritableDatabase();
        Tareas tareas = null;
        listaT = new ArrayList<Tareas>();

       // lista.add(new DatosTareas(0, R.drawable.icon1, "ID", "Nomb Tarea", "Potencia", "Tiempo","Interaccion" ));
        //lista.add(new DatosTareas(1, R.drawable.search, "1", "Google Search", "1540 w", "300 seg", "Medio" ));
        //lista.add(new DatosTareas(2, R.drawable.whatsapp, "2", "Whatsapp", "1700 w", "500 seg", "Alto" ));
        try {
            String resu= "";
            String[] selectionArgs = {"1"};
            Cursor c = db.rawQuery("SELECT * FROM ListaTareas WHERE valor = ?", selectionArgs);
            while (c.moveToNext()){
                tareas = new Tareas();

                String numCadena = c.getString(0); //el id en la tabla
                int numEntero = Integer.parseInt(numCadena);
                tareas.setId(numEntero);

                String rutaI= c.getString(2);
                String uri = "@drawable/"+rutaI;
                //int imageresource = getResources().getIdentifier("@drawable/your_image", "drawable", getActivity().getPackageName());
                //image.setImageResource(imageresource);
                int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                tareas.setImagen(imageResource);

                tareas.setNombTarea("ID:"+c.getString(0)+"      "+c.getString(1));

                tareas.setPotencia("Potencia: "+c.getString(3)+"  watts");
                tareas.setTiempo("Tiempo: "+c.getString(4)+"  minutos");

                String intertraido = c.getString(5);
                String paraponer="";
                switch (intertraido){
                    case "1": paraponer="Bajo"; break;
                    case "2": paraponer="Medio"; break;
                    case "3": paraponer="Alto"; break;
                }
                tareas.setInteraccion("Interaccion: "+paraponer);

                tareas.setRepeticion("Repeticiones: "+c.getString(6));
                listaT.add(tareas);
                resu = numCadena;
            }
            //Toast.makeText(getActivity().getApplicationContext(), "Resultado : "+resu, Toast.LENGTH_LONG).show();
            c.close();
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "No hay datos en tabla", Toast.LENGTH_LONG).show();
        }
        AdaptadorTnuevo miadapt = new AdaptadorTnuevo(getActivity().getApplicationContext(), listaT);
        listaTareas.setAdapter(miadapt);
        listaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("idTabla", String.valueOf(listaT.get(position).getId()));
                EditTareaFragment myFrag = new EditTareaFragment();
                myFrag.setArguments(bundle);
                FragmentManager fm= getFragmentManager();
                fm.beginTransaction().replace(R.id.contenedor, myFrag).addToBackStack(null).commit();
            }
        });
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
