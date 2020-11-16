package fr.utbm.hellondk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.utbm.hellondk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLeft.setOnClickListener { translateDirection("LEFT") }
        binding.btnRight.setOnClickListener { translateDirection("RIGHT") }
        binding.btnUp.setOnClickListener { translateDirection("UP") }
        binding.btnDown.setOnClickListener { translateDirection("DOWN") }

        binding.btnWrite.setOnClickListener {transformInput { write(it) }}
        binding.btnRead.setOnClickListener {transformInput { read(it) }}
    }

    private fun transformInput(f: (Int) -> String) {
        try {
            binding.label.text = f(Integer.valueOf(binding.input.text.toString()))
        } catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.activity_main_error_input, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun translateDirection(str: String) {
        val translated = directionInJapanese(str)
        binding.label.text = translated
    }

    private external fun directionInJapanese(dir: String): String
    private external fun write(a: Int): String
    private external fun read(a: Int): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
