package com.example.pdfreader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pdfreader.databinding.ActivityPdfactivityBinding

class PDFActivity : AppCompatActivity() {

    private var binding : ActivityPdfactivityBinding? = null
    private val pdfSelectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedIntent = result.data
            if (selectedIntent != null) {
                val selectedPdf = selectedIntent.data
                pdfUri(selectedPdf)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        selectPdf()
    }

    private fun selectPdf() {
        val searchStorage = Intent(Intent.ACTION_GET_CONTENT)
        searchStorage.type = "application/pdf"
        searchStorage.addCategory(Intent.CATEGORY_OPENABLE)
        pdfSelectionLauncher.launch(Intent.createChooser(searchStorage, "Select PDF"))

    }

    private fun pdfUri(uri : Uri?){
        binding?.pdf?.fromUri(uri)?.defaultPage(0)?.spacing(10)?.load()
        Toast.makeText(this, "PDF Opened!", Toast.LENGTH_SHORT).show()
    }

    override fun onPause(){
        super.onPause()
        releaseInstance()
    }
}
