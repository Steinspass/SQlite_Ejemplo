package com.example.ndpsh.sqlite_ejemplo.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ndpsh.sqlite_ejemplo.Activities.Adapters.MyAdapter;
import com.example.ndpsh.sqlite_ejemplo.Activities.DataBase.CarsSQLiteHelper;
import com.example.ndpsh.sqlite_ejemplo.Activities.Models.Car;
import com.example.ndpsh.sqlite_ejemplo.R;

import java.util.ArrayList;
import java.util.List;




/**
 *  Created by Naim on 5/11/18
 */

public class MainActivity extends AppCompatActivity {

    private Button btnCreate;
    private Button btnDelete;

    private CarsSQLiteHelper carsHelper;
    private SQLiteDatabase db;

    private ListView listview;
    private MyAdapter adapter;

    private List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView);
        cars = new ArrayList<Car>();

        btnCreate = (Button) findViewById(R.id.buttonCreate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                create();
                update();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAll();
                update();
            }
        });

        //Abrimos la base de datos 'DBTest1' en modo escritura
                carsHelper = new CarsSQLiteHelper(this, "DBTest1", null, 1);
        db = carsHelper.getWritableDatabase();

        adapter = new MyAdapter(this, cars, R.layout.itemdb);
        listview.setAdapter(adapter);

        update();

    }

    private List<Car> getAllCars() {
       // Selecionamos todos los registros de la tabla Cars
       Cursor cursor = db.rawQuery("select * from Cars", null);
       List<Car> list = new ArrayList<Car>();

       if (cursor.moveToFirst()) {
           // Iteramos sobre el cursor de resultados,
           // y vamos rellenando el array que posteriormente devolvemos
           while (cursor.isAfterLast() == false) {

               int VIN = cursor.getInt(cursor.getColumnIndex("VIN"));
               String name = cursor.getString(cursor.getColumnIndex("name"));
               String color = cursor.getString(cursor.getColumnIndex("color"));

               list.add(new Car(VIN, name, color));
               cursor.moveToNext();
           }
       }
        return list;

    }


    private void create() {
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues nuevoRegistro = new ContentValues();
            // El ID es auto incrementable como declaramos en nuestro CarsSQLiteHelper
            nuevoRegistro.put("name", "Seat");
            nuevoRegistro.put("color", "Black");

            //Insertamos el registro en la base de datos
            db.insert("Cars", null, nuevoRegistro);
        }
    }

    private void removeAll() {
        db.delete("Cars", "", null);
    }

    private void update() {
        // borramos todos los elementos
        cars.clear();
        // cargamos todos los elementos
        cars.addAll(getAllCars());
        // refrescamos el adaptador
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        // cerramos conexión base de datos antes de destruir el activity
        db.close();
        super.onDestroy();
    }
}
