package com.example.proyecto19reto5f2023

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et1=findViewById<EditText>(R.id.et1)
        val et2=findViewById<EditText>(R.id.et2)
        val et4=findViewById<EditText>(R.id.et4)
        val et5=findViewById<EditText>(R.id.et5)
        val et3=findViewById<EditText>(R.id.et3)
        val boton1=findViewById<Button>(R.id.boton1)
        val boton2=findViewById<Button>(R.id.boton2)
        val boton3=findViewById<Button>(R.id.boton3)
        val boton4=findViewById<Button>(R.id.boton4)
        val boton5=findViewById<Button>(R.id.boton5)
        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", et1.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("direccion", et4.getText().toString())
            registro.put("numero", et5.getText().toString())
            registro.put("precio", et3.getText().toString())
            bd.insert("articulos", null, registro)
            bd.close()
            et1.setText("")
            et2.setText("")
            et4.setText("")
            et5.setText("")
            et3.setText("")
            Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion,precio,direccion,numero from articulos where codigo=${et1.text.toString()}", null)
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0))
                et3.setText(fila.getString(1))
                et4.setText(fila.getString(2))
                et5.setText(fila.getString(3))
            } else
                Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }

        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,precio,direccion,numero from articulos where descripcion='${et2.text.toString()}'", null)
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
                et4.setText(fila.getString(2))
                et5.setText(fila.getString(3))
            } else
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        boton4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("articulos", "codigo=${et1.text.toString()}", null)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            et4.setText("")
            et5.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show()
        }

        boton5.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("descripcion", et2.text.toString())
            registro.put("precio", et3.text.toString())
            registro.put("direccion", et4.text.toString())
            registro.put("numero", et5.text.toString())
            val cant = bd.update("articulos", registro, "codigo=${et1.text.toString()}", null)
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "no existe un artículo con el código ingresado", Toast.LENGTH_SHORT).show()
        }
    }
}