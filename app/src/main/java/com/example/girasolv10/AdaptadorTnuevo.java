package com.example.girasolv10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorTnuevo extends BaseAdapter {
    Context contex;
    List<Tareas> Listado;

    public AdaptadorTnuevo(Context contex, List<Tareas> listado) {
        this.contex = contex;
        Listado = listado;
    }

    @Override
    public int getCount() {
        return Listado.size();
    }

    @Override
    public Object getItem(int position) {
        return Listado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Listado.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        LayoutInflater inflado = LayoutInflater.from(contex);
        vista = inflado.inflate(R.layout.itemlistatareas,null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imagenApp);
        TextView nombT = (TextView)vista.findViewById(R.id.nombreApp);
        TextView potT = (TextView)vista.findViewById(R.id.potenciaApp);
        TextView tiemT = (TextView)vista.findViewById(R.id.tiempoApp);
        TextView interT = (TextView)vista.findViewById(R.id.interaccionApp);
        TextView repeT = (TextView)vista.findViewById(R.id.repeApp);

        imagen.setImageResource(Listado.get(position).getImagen());
        nombT.setText(Listado.get(position).getNombTarea());
        potT.setText(Listado.get(position).getPotencia());
        tiemT.setText(Listado.get(position).getTiempo());
        interT.setText(Listado.get(position).getInteraccion());
        repeT.setText(Listado.get(position).getRepeticion());

        return vista;
    }
}
