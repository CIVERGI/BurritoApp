package com.alohadevelop.burritoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    private Context context; //Context
    private ArrayList<Burrito> burritosArrayList; //Data source of the list adapter

    //Public constructor
    public CustomAdapter (Context context, ArrayList<Burrito> burritosArrayList){
        this.context = context;
        this.burritosArrayList = burritosArrayList;
    }

    @Override
    public int getCount() {
        return burritosArrayList.size(); //Returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return burritosArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override //Contiene viewHolder pattern
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        //Inflate the layout for each list row
        if (view == null){
            view = LayoutInflater.from(context).
                    inflate(R.layout.listview_child, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //get current item to be displayed
        //currentBurrito = burrito actual:)
        Burrito currentBurrito = (Burrito) getItem(position);
        viewHolder.tvNombre.setText(currentBurrito.getNombre());
        viewHolder.tvDescripcion.setText(currentBurrito.getDescripcion());
        viewHolder.tvCant.setText(currentBurrito.getCant());

        return view;
    }

    private class ViewHolder{
        TextView tvNombre;
        TextView tvDescripcion;
        TextView tvCant;
        Button btnSuma;
        Button btnResta;

        public ViewHolder(View view){
            tvNombre = (TextView) view.findViewById(R.id.tv_nombre);
            tvDescripcion = (TextView) view.findViewById(R.id.tv_descripcion);
            tvCant = (TextView) view.findViewById(R.id.tv_cant);
            btnSuma = (Button) view.findViewById(R.id.btn_mas);
            btnResta = (Button) view.findViewById(R.id.btn_menos);
        }


    }
}
