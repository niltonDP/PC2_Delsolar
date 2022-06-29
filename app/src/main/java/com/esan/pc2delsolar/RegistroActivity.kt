package com.esan.pc2delsolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.widget.Toast
import com.esan.pc2delsolar.databinding.ActivityRegistroBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnRegistrar.setOnClickListener{
            val nuevoDni = binding.txtDni.text.toString()
            val nuevoNom = binding.txtNombres.text.toString()
            val nuevoPa1 = binding.txtClave1.text.toString()
            val nuevoPa2 = binding.txtClave2.text.toString()

            if(nuevoPa1 != nuevoPa2){
                Toast.makeText(this,"Password no coincide",Toast.LENGTH_LONG).show()
            }else{
                if(nuevoDni.length < 8){
                    Toast.makeText(this,"NRO DE DNI INCOMPLETO (8 dígitos)",Toast.LENGTH_LONG).show()
                } else{
                    if(nuevoNom.length < 4){
                        Toast.makeText(this,"NOMBBRE NO VALIDO (Muy corto)",Toast.LENGTH_LONG).show()
                    }else{
                        if(nuevoNom.length < 4){
                            Toast.makeText(this,"ClAVE NO VALIDA (Minimo 4 caracteres)",Toast.LENGTH_LONG).show()
                        }else{
                            funValidar1(nuevoDni, nuevoNom, nuevoPa1)
                        }

                    }
                }
            }


        }
    }


    fun funValidar1(nuevoDni: String,nuevoNom : String, nuevoPa1:String ){

        val  db = FirebaseFirestore.getInstance()
        var datos = ""
        db.collection("permisos").whereEqualTo( "dni", nuevoDni.toInt()).get().addOnSuccessListener {

                for(documentos in it){
                    datos += "-----------> ${documentos.id}: ${documentos.data}\n"
                 }

                if(datos.length > 0){
                   Toast.makeText(this,"DNI : YA EXISTE",Toast.LENGTH_LONG).show()
                }else{
                    //Toast.makeText(this,"DNI: Aceptado",Toast.LENGTH_SHORT).show()
                    funGuardar(nuevoDni, nuevoNom , nuevoPa1)
                }
        }
    }

    fun funGuardar(nuevoDni: String,nuevoNom: String, nuevoPa1:String){


        val  db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "dni" to nuevoDni.toString().toInt() ,
            "nombre" to nuevoDni,
            "clave" to  nuevoPa1
        )

// Add a new document with a generated ID
        db.collection("permisos").add(user)
            .addOnSuccessListener {documentReference ->
                //Toast.makeText(this,"USUARIO REGISTRADO CON EXITO ${documentReference.id}  ",Toast.LENGTH_LONG).show()
                val myToast = Toast.makeText(applicationContext,"* * USUARIO REGISTRADO CON EXITO * *",Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,100,5)
                myToast.show()

                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
             Toast.makeText(this,"ERROR : NO SE GUARDARON LOS DATOS",Toast.LENGTH_SHORT).show()
            }
    }


    }
