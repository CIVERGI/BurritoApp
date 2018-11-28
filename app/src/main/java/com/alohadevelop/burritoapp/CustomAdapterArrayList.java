package com.alohadevelop.burritoapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterArrayList extends ArrayAdapter<Burrito> {
    private Activity context;
    List<Burrito> burritos;

    //Nuestra referencia de objeto de firebase
    DatabaseReference mDataBase;

    public CustomAdapterArrayList(Activity context, List<Burrito> burritos) {
        super(context, R.layout.listview_child, burritos);
        this.context = context;
        this.burritos = burritos;
    }

    @Override // Implementacion ViewHolder
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;


        if (convertView == null) { // <-Esta es la primera vez que se crea la instancia del chil de listview
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_child, null, true);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Burrito currentBurrito = burritos.get(position);
        viewHolder.tvNombre.setText(currentBurrito.getNombre());
        viewHolder.tvDescripcion.setText(currentBurrito.getDescripcion());
        viewHolder.tvCant.setText("" + currentBurrito.getCant()); //getCant() devuelve int

        //Generamos una referencia especifica de la base de datos, cada vez que se muestre
        //String id = currentBurrito.getId();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        viewHolder.btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Actualizando burrito en base de datos
                String nombre = currentBurrito.getNombre();
                String descripcion = currentBurrito.getDescripcion();
                String tag = currentBurrito.getTag();
                int cantidad = currentBurrito.getCant() + 1; // Aqui incrementamos el burrito

                String id = currentBurrito.getId();
                Burrito burrito = new Burrito(nombre, descripcion, tag, cantidad, id);

                mDataBase.child("Menu").child(id).setValue(burrito);

                Log.v("SumaTag", "Nombre: " + nombre +
                        "\nDescripcion: " + descripcion +
                "\nTag: " + tag +
                "\nCantidad: " + cantidad +
                "\nId: " + id);
            }
        });

        viewHolder.btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Actualizando burrito en base de datos
                String nombre = currentBurrito.getNombre();
                String descripcion = currentBurrito.getDescripcion();
                String tag = currentBurrito.getTag();
                int cantidad = currentBurrito.getCant() - 1; // Aqui incrementamos el burrito

                String id = currentBurrito.getId();

                Burrito burrito = new Burrito(nombre, descripcion, tag, cantidad, id);

                mDataBase.child("Menu").child(id).setValue(burrito);

                Log.v("RestaTag", "Nombre: " + nombre +
                        "\nDescripcion: " + descripcion +
                        "\nTag: " + tag +
                        "\nCantidad: " + cantidad +
                        "\nId: " + id);

            }
        });

        return convertView;
    }

    private class ViewHolder {
        //Este objeto nos sirve para almacenar las referencias de memoria del xml de cada widget
        TextView tvNombre;
        TextView tvDescripcion;
        TextView tvCant;
        Button btnSuma;
        Button btnResta;

        public ViewHolder(View view) { // <- Se le pasa una vista para acceder a ella
            tvNombre = (TextView) view.findViewById(R.id.tv_nombre);
            tvDescripcion = (TextView) view.findViewById(R.id.tv_descripcion);
            tvCant = (TextView) view.findViewById(R.id.tv_cant);
            btnSuma = (Button) view.findViewById(R.id.btn_mas);
            btnResta = (Button) view.findViewById(R.id.btn_menos);
            //Solamente realiza el findViewById una sola vez.
        }

    }

}
