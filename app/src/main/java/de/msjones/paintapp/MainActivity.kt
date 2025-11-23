package de.msjones.paintapp

import android.app.Dialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import de.msjones.paintapp.databinding.ActivityMainBinding
import de.msjones.paintapp.databinding.DialogBrushSizeBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: DialogBrushSizeBinding

    private var imageButtonCurrentPaint: ImageButton? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // Hier kannst du die URI verwenden, um das Hintergrundbild zu setzen.
            // Du müsstest dafür eine ImageView zu deiner activity_main.xml hinzufügen
            // und dann hier das Bild setzen, z.B.:
             binding.ivBackground.setImageURI(uri)
        } else {
            Toast.makeText(this, "Kein Bild ausgewählt", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.drawingView.setSizeForBrush(20f)
        val paintLayout = binding.llPaintColors

        if (paintLayout.childCount > 1) {
            imageButtonCurrentPaint = paintLayout.getChildAt(1) as ImageButton
            imageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
        }

        binding.ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        binding.ibGallery.setOnClickListener {
            // Startet den Photo Picker. Es sind keine manuellen Berechtigungs-Checks mehr nötig.
            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)
        dialogBinding = DialogBrushSizeBinding.inflate(layoutInflater)
        brushDialog.setContentView(dialogBinding.root)
        brushDialog.setTitle("Strichstärke:")

        dialogBinding.ibBrushSmall.setOnClickListener {
            binding.drawingView.setSizeForBrush(10f)
            brushDialog.dismiss()
        }

        dialogBinding.ibBrushMedium.setOnClickListener {
            binding.drawingView.setSizeForBrush(20f)
            brushDialog.dismiss()
        }

        dialogBinding.ibBrushLarge.setOnClickListener {
            binding.drawingView.setSizeForBrush(30f)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun paintClicked(view: View) {
        if (view !== imageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            binding.drawingView.setColor(colorTag)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
            imageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )
            imageButtonCurrentPaint = imageButton
        }
    }
}
