package com.chetssholic.removebackgeround.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chetssholic.removebackgeround.R



import java.io.File
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_preview.imageView8


class PreviewActivity : AppCompatActivity() {
    var uri: Uri? = null
    var path: String? = null
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)


        uri = Uri.parse(intent.extras!!.getString("uri"))
        path =intent.extras!!.getString("path")

        if (Build.VERSION.SDK_INT >=30) {
            iv_delete.visibility= View.GONE
        }

        iv_delete.setOnClickListener {

            var file=  File(path)
            file.delete()

            finish()
        }
        imageView8.setOnClickListener {


            finish()

        }







        iv_effect2.setImageURI(uri)
        iv_share.setOnClickListener {

            funktionTeilen(uri);


        }







    }


    fun funktionTeilen(Datei: Uri?) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
//            val fileURI = FileProvider.getUriForFile(
//                this@PreviewActivity, packageName + ".provider",
//                Datei!!
//            )
            putExtra(Intent.EXTRA_STREAM, Datei)
        }
        startActivity(shareIntent)
    }




}