package com.alohadevelop.burritoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBurrito extends AppCompatActivity {

    EditText etNombre, etDescripcion, etTag, etCant;

    // Declaramos un objeto de la base de datos
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_burrito);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etTag = (EditText) findViewById(R.id.etTag);
        etCant = (EditText) findViewById(R.id.etCant);

        // Obtenemos su referencia
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addBurrito(View view) {

        if (etTag.getText().toString().equals("") ||
                etDescripcion.getText().toString().equals("") ||
                etNombre.getText().toString().equals("") ||
                etCant.getText().toString().equals("")){

            Toast.makeText(this, "Favor de rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        else {
            //Obtenemos los datos a salvar
            String nombre = etNombre.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            String tag = "#"+ etTag.getText().toString();
            int cant = Integer.parseInt(etCant.getText().toString());

            newBurrito(nombre, descripcion, tag, cant);
        }

    }

    public void newBurrito(String nombre, String descripcion, String tag, int cant){
        //Obtenemos un id unico usando push().getKey() (metodo)
        //Creara un id unico que usaremos como clave primaria para nuestro burrito
        String id = mDatabase.push().getKey();

        //Creamos una instancia de burrito
        Burrito burrito = new Burrito(nombre, descripcion, tag, cant, id);

        //No se si es la mejor implementacion...
        //Me sobreescribe mis datos "Menu?"
        //Es mejor obtener un snapshot?
        mDatabase.child("Menu").child(id).setValue(burrito);


        finish();
    }
}
