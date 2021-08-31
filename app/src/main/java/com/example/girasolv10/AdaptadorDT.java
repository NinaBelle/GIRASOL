package com.example.girasolv10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorDT  extends BaseAdapter {

    Context contexto;
    List<DatosTareas> ListaObjetos;

    public AdaptadorDT(Context contexto, List<DatosTareas> listaObjetos) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        return ListaObjetos.size();
    }

    @Override
    public Object getItem(int i) {
        return ListaObjetos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListaObjetos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;
        LayoutInflater inflado = LayoutInflater.from(contexto);
        vista = inflado.inflate(R.layout.itemlvtareas,null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.iconT);
        TextView indT = (TextView)vista.findViewById(R.id.indT);
        TextView nombT = (TextView)vista.findViewById(R.id.nombT);
        TextView potT = (TextView)vista.findViewById(R.id.potenciaT);
        TextView tiemT = (TextView)vista.findViewById(R.id.tiempoT);
        TextView interT = (TextView)vista.findViewById(R.id.interaccionT);

        indT.setText(ListaObjetos.get(i).getNroT());
        nombT.setText(ListaObjetos.get(i).getNombTarea());
        potT.setText(ListaObjetos.get(i).getPotencia());
        tiemT.setText(ListaObjetos.get(i).getTiempo());
        interT.setText(ListaObjetos.get(i).getInteraccion());
        imagen.setImageResource(ListaObjetos.get(i).getImagen());

        return vista;
    }
}
