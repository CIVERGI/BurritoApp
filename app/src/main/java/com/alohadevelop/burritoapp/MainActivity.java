package com.alohadevelop.burritoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewBurritos;

    //Una lista para almacenar todos los burritos de firebase
    List<Burrito> burritos;

    //Nuestra referencia de objeto de firebase
    DatabaseReference databaseBurrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos la referencia de el nodo de burritos
        databaseBurrito = FirebaseDatabase.getInstance().getReference("Menu");

        //getting views
        listViewBurritos = (ListView) findViewById(R.id.lv_burritos);

        //lista para almacenar a los burritos
        burritos = new ArrayList<>();


        //Agregamos un listener a nuestro list view
        listViewBurritos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Burrito burrito = burritos.get(i);
                showUpdateDeleteDialog(burrito.getId(), burrito.getNombre());

                return true;
            }
        });

    }

    private void showUpdateDeleteDialog( final String burritoId, String burritoName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog,null);
        dialogBuilder.setView(dialogView);

        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnEliminar);
        final Button btnCancelar = (Button) dialogView. findViewById(R.id.btnCancelar);

        dialogBuilder.setTitle(burritoName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBurrito(burritoId);
                b.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    private boolean deleteBurrito(String id){
        //Obtenemos la referencia del burrito especifica
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Menu").child(id);
        //Removemos el burrito
        dR.removeValue();

        return true;
    }

    @Override
    protected void  onStart() {
        super.onStart();
        // ataching value event listener
        databaseBurrito.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clearing the previus burritos list
                burritos.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){ // <-for mejorado
                    //getting artist
                    Burrito burritoMomentaneo = postSnapshot.getValue(Burrito.class);
                    ////adding burritos to the list
                    burritos.add(burritoMomentaneo);
                    //Toast.makeText(MainActivity.this, "Iteracion", Toast.LENGTH_SHORT).show();
                }

                //creating adapter
                //CustomAdapter customAdapter = new CustomAdapter(this, burritos); <-No funciona:(
                CustomAdapterArrayList adapter = new CustomAdapterArrayList(MainActivity.this, burritos);
                //ataching adapter to listview
                listViewBurritos.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void newBurrito(View view) {
        Intent intent = new Intent(MainActivity.this, AddBurrito.class);
        startActivity(intent);
    }
}
