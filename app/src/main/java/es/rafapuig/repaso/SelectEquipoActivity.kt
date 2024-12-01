package es.rafapuig.repaso


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SelectEquipoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_equipo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initSpinner()
        intSpinnerListener()
    }

    private fun intSpinnerListener() {
        val spinner = findViewById<Spinner>(R.id.equipos_spinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val nombre = parent?.getItemAtPosition(position) as String
                callShowEquipo(nombre)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    val activityResultLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val puntos = result.data?.getIntExtra(ShowEquipoActivity.PUNTOS, 0)
        }

    private fun callShowEquipo(nombre: String) {
        val intent = Intent(this, ShowEquipoActivity::class.java)
        intent.putExtra(ShowEquipoActivity.NOMBRE, nombre)
        //startActivity(intent)
        activityResultLauncher.launch(intent)
    }

    private fun initSpinner() {

        val equipos = arrayOf("valencia","barcelona","real_madrid")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            equipos)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.equipos_spinner)

        spinner.adapter = adapter
    }
}