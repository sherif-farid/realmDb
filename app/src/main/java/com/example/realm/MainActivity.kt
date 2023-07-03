package com.example.realm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.idBtnAddData.setOnClickListener {
            if (binding.idEdtName.text?.toString()?.isEmpty() == true &&
                binding.idEdtAge.text?.toString()?.isEmpty() == true
            ) {
                Toast.makeText(
                    applicationContext,
                    "Please enter your name and age",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = binding.idEdtName.text?.toString()
                val age =   binding.idEdtAge.text?.toString()
                Repository.addUser(name, age) { model ->
                    Toast.makeText(
                        this@MainActivity,
                        "user ${model.name} has been added to database..",
                        Toast.LENGTH_SHORT
                    ).show()
                    getAllData()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getAllData()
    }

    private fun getAllData(){
        Repository.getAllUsers { userList ->
            val sb = StringBuilder()
            for (model in userList) {
                sb.append(model.name).append(model.age).append("\n")
            }
            binding.savedValueTv.text = sb.toString()
        }
    }
}