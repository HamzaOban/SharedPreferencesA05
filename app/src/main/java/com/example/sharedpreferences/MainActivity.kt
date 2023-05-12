package com.example.sharedpreferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("SharedPreference", MODE_PRIVATE)
        binding.prefNameTextView.text = sharedPref.getString("name","Adınız")
        binding.prefJobTextView.text = sharedPref.getInt("age",0).toString()
        binding.prefAgeTextView.text = sharedPref.getString("job","Mesleğiniz")
        binding.saveButton.setOnClickListener {
            save()
        }
        binding.deleteButton.setOnClickListener {
            delete()
        }
    }
    private fun isNumeric(text: String): Boolean {
        return text.matches("-?\\d+(\\.\\d+)?".toRegex())
    }


    private fun save(){
        if(binding.nameTextField.editText?.text.isNullOrEmpty() ||
            binding.ageTextField.editText?.text.isNullOrEmpty() ||
            binding.jobTextField.editText?.text.isNullOrEmpty())
        {
            Toast.makeText(this,"Boş Bırakılamaz",Toast.LENGTH_LONG).show()
            return
        }
        if (!isNumeric(binding.ageTextField.editText?.text.toString())){
            Toast.makeText(this,"Sayı Giriniz",Toast.LENGTH_LONG).show()
            return

        }

        val name = binding.nameTextField.editText?.text.toString()
        val age = binding.ageTextField.editText?.text.toString().toIntOrNull()
        val job = binding.jobTextField.editText?.text.toString()



        binding.prefNameTextView.text = name
        binding.prefJobTextView.text = job
        binding.prefAgeTextView.text = age.toString()


        sharedPref.apply {
            edit().putString("name",name).apply()
            if (age != null) {
                edit().putInt("age",age).apply()
            }
            edit().putString("job",job).apply()
        }


    }
    private fun delete(){
        sharedPref.apply {
            edit().remove("name").apply()
            edit().remove("age").apply()
            edit().remove("job").apply()
            val age = sharedPref.getInt("age",-1)
            if (age == -1){
                binding.prefJobTextView.text = "Yaşınız"
            }
            binding.prefNameTextView.text = sharedPref.getString("name","Adınız")

            binding.prefAgeTextView.text = sharedPref.getString("job","Mesleğiniz")
        }


    }


}