package es.rafapuig.repaso

import android.Manifest.permission.RECORD_AUDIO
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import es.rafapuig.repaso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //private lateinit var escudo: ImageView
    //private lateinit var nombreEquipo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val escudoView = ImageView(this)
        escudoView.setImageResource(android.R.drawable.ic_dialog_email)*/

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_main)

        setContentView(binding.root)

        initViews()
        initListeners()
    }

    private fun initViews() {

        val escudoUrl = "https://github.com/rafapuig/PMDM7N_2024/" +
                "blob/master/escudos/levante-ud-logo-escudo-1.png?raw=true"

        //escudo = findViewById(R.id.escudo)
        //escudo.setImageResource(android.R.drawable.btn_star_big_on)

        Glide.with(binding.escudo).load(escudoUrl).into(binding.escudo)

        //nombreEquipo = findViewById(R.id.nombre_equipo)
        binding.nombreEquipo.text = "Levante U.D."
    }

    private fun initListeners() {
        binding.escudo.setOnClickListener { onEscudoClick(it) }
        binding.recordAudio.setOnClickListener { onRecordAudio(it) }
        binding.selectEquipo.setOnClickListener { onSelectEquipo(it) }
    }

    private fun onSelectEquipo(view: View) {
        val intent = Intent(this, SelectEquipoActivity::class.java)
        startActivity(intent)
    }

    private fun onRecordAudio(view: View) {
        onStartRecording()
    }

    /**
     *  Metodo principal para pedir el permiso antes de grabar
     */
    private fun onStartRecording() {
        // Si no tenemos el permiso de grabacion audio
        if (!isRecordingAudioPermissionGranted()) {
            // Si tenemos que explicar pq es necesario el permiso
            if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                requestRecordPermissionRationale()
            } else { // Si no, lo pedimos directamente
                requestRecordPermission()
            }
            Toast
                .makeText(this, "No tienes permiso de grabar audio", Toast.LENGTH_LONG)
                .show()
        } else {
            startRecording()
        }
    }


    private fun isRecordingAudioPermissionGranted() =
        checkSelfPermission(RECORD_AUDIO) == PERMISSION_GRANTED

    /**
     * Muestra un cuadro de dialogo para informar el motivo por el cual
     * es necesario que la app tenga el permiso de GRABAR AUDIO
     * Cuando el usuario pulse Aceptar se solicita el permiso
     */
    private fun requestRecordPermissionRationale() {

        val builder = AlertDialog.Builder(this)

        val dialog = builder
            .setMessage(
                "El permiso para acceder al microfono" +
                        " es necesario para  que la app pueda grabar audio"
            )
            .setTitle("Permiso de grabación de audio requerido")
            .setIcon(android.R.drawable.ic_dialog_alert)
            // Cuando el usuario clica en aceptar se solicita el permiso
            .setPositiveButton("Aceptar") { _, _ -> requestRecordPermission() }
            .create()

        dialog.show()


    }


    val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { onRequestRecordPermissionResult(it) }


    /**
     * Realiza la solicitud del permiso mediante el launcher
     */
    private fun requestRecordPermission() {
        //requestPermissions(arrayOf(RECORD_AUDIO), 12345)
        requestPermissionLauncher.launch(RECORD_AUDIO)
    }

    /**
     * Metodo callback que es llamado pasando un boolean que indica si el permiso
     * solicitado ha sido otorgado o no
     * Si se ha concedido el permiso se inicia el proceso de grabación
     */
    private fun onRequestRecordPermissionResult(isGranted: Boolean) {
        if (isGranted) { // Si se ha concedido el permiso
            startRecording() // Iniciamos la grabación
        } else { // Si no, mostramos un toast
            Toast
                .makeText(this, "NO SE PUEDE GRABAR SIN PERMISO", Toast.LENGTH_LONG)
                .show()
        }
    }


    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12345) {
            onRequestRecordPermissionResult(grantResults[0] == PERMISSION_GRANTED)
        }
    }*/

    private fun startRecording() {
        Toast
            .makeText(this, "GRABACION INICIADA", Toast.LENGTH_LONG)
            .show()
    }


    private fun onEscudoClick(view: View) {
        Toast
            .makeText(view.context, "ES EL MEJOR!!!", Toast.LENGTH_LONG)
            .show()
    }
}