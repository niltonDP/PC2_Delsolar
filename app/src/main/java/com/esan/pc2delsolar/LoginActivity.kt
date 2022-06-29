package com.esan.pc2delsolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.esan.pc2delsolar.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1Ingresar.setOnClickListener{funLogin()}
        binding.btn1Crearc.setOnClickListener{openCrear()}
    }

    private fun openCrear(){
        intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }


    private  fun funLogin(){
        val logDni = binding.lgTxtDni.text.toString().toInt()
        val logCla = binding.lgTxtClave.text.toString()

        val  db = FirebaseFirestore.getInstance()
        var  datos = ""

        db.collection("permisos")
            .whereEqualTo( "dni", logDni)
            .whereEqualTo ("clave", logCla)
            .get().addOnSuccessListener {

            for(documentos in it){
                datos += "-----------> ${documentos.id}: ${documentos.data}\n"
            }

            if(datos.length > 0){
                Toast.makeText(this,"<< ACCESO PERMITIDO >>", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"<< EL USUARIO y/o CLAVE NO EXISTE EN EL SISTEMA>>", Toast.LENGTH_SHORT).show()
            }
        }

    }




}