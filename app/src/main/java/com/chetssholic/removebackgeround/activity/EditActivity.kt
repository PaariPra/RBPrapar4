package com.chetssholic.removebackgeround.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.adsclass.ShowIntertialads
import com.chetssholic.removebackgeround.cutout.CutOutActivity
import com.chetssholic.removebackgeround.utils.BitmapUtiles
import com.chetssholic.removebackgeround.utils.TinyDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.lyrebirdstudio.aspectratiorecyclerviewlib.aspectratio.model.AspectRatio
import com.lyrebirdstudio.croppylib.Croppy
import com.lyrebirdstudio.croppylib.main.CropRequest
import com.lyrebirdstudio.croppylib.main.CroppyTheme
import com.lyrebirdstudio.croppylib.main.StorageType
import com.lyrebirdstudio.croppylib.util.file.FileCreator
import com.lyrebirdstudio.croppylib.util.file.FileOperationRequest
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    var bitmap: Bitmap? = null
    var uri: Uri? = null


    var undonePaths: ArrayList<Bitmap> = ArrayList()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)



        imageView8.setOnClickListener {

            BitmapUtiles.bitmsaparray.clear()
            finish()
            startActivity(Intent(this@EditActivity, StartActivity::class.java))

        }


        uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@EditActivity, uri)
        var w: Int = bitmap!!.getWidth()
        var h: Int = bitmap!!.getHeight()
        val s = Math.max(w / 2048.0f, h / 2048.0f)

        if (s > 1.0f) {
            w /= s.toInt()
            h /= s.toInt()
            bitmap = Bitmap.createScaledBitmap(bitmap!!, w, h, false)
        } else {
            bitmap = bitmap
        }

        BitmapUtiles.bitmsaparray.add(bitmap)









        if (intent.extras!!.getString("type").equals("drip")) {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }


            var intent: Intent = Intent(this@EditActivity, DripActivity::class.java)
            intent.putExtra("imageuri", uri.toString())
            startActivityForResult(intent, 101)


        }



        if (intent.extras!!.getString("type").equals("rmbg")) {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }

            var intent: Intent = Intent(this@EditActivity, RemoveBAckground::class.java)
            intent.putExtra("imageuri", uri.toString())
            startActivityForResult(intent, 101)

        }


        if (intent.extras!!.getString("type").equals("potr")) {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }

            var intent: Intent = Intent(this@EditActivity, PotraiActivity::class.java)
            intent.putExtra("imageuri", uri.toString())
            startActivityForResult(intent, 101)

        }
        if (intent.extras!!.getString("type").equals("filter")) {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }

            var intent: Intent = Intent(this@EditActivity, FilterActivity::class.java)
            intent.putExtra("imageuri", uri.toString())
            startActivityForResult(intent, 101)

        }


        if (intent.extras!!.getString("type").equals("adjust")) {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }

            var intent: Intent = Intent(this@EditActivity, SpiralActivty::class.java)
            intent.putExtra("imageuri", uri.toString())
            startActivityForResult(intent, 101)

        }












        iv_show.setImageBitmap(bitmap)


        cl_crop.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }

            startCroppy()
        }

        cl_fitt.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }




            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {


                    var intent: Intent = Intent(this@EditActivity, FitActivity::class.java)
                    intent.putExtra("imageuri", uri.toString())
                    startActivityForResult(intent, 101)



                }
            })





        }





        cl_drp.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }


            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {

                    var intent: Intent = Intent(this@EditActivity, CutOutActivity::class.java)
                    intent.putExtra("imageuri", uri.toString())
                    intent.putExtra("type","drip")
                    startActivityForResult(intent, 101)


                }
            })





        }







        cl_filter.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }


            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {


                    var intent: Intent = Intent(this@EditActivity, FilterActivity::class.java)
                    intent.putExtra("imageuri", uri.toString())
                    startActivityForResult(intent, 101)


                }
            })







        }


        cl_bg.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }


            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {


                    var intent: Intent = Intent(this@EditActivity, CutOutActivity::class.java)
                    intent.putExtra("imageuri", uri.toString())
                    intent.putExtra("type", "rmbg")
                    startActivityForResult(intent, 101)



                }
            })





        }


        cl_pot.setOnClickListener {
            if (undonePaths.size > 0) {
                undonePaths.clear()
            }


            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {


                    var intent: Intent = Intent(this@EditActivity, CutOutActivity::class.java)
                    intent.putExtra("imageuri", uri.toString())
                    intent.putExtra("type", "potr")
                    startActivityForResult(intent, 101)




                }
            })



        }





        cl_undo.setOnClickListener {

            onClickUndo()

        }
        cl_redo.setOnClickListener {
            onClickRedo()

        }

        imageView7.setOnClickListener {


            iv_show.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(iv_show.getDrawingCache())
            iv_show.setDrawingCacheEnabled(false)

            val df: DateFormat = SimpleDateFormat("EEE, d MMM yyyy, HH:mm")
            val date = df.format(Calendar.getInstance().time)


            var uri: Uri = BitmapUtiles.storeImage(
                bitmap,

                this@EditActivity
            );


            finish()
            startActivity(Intent(this@EditActivity, StartActivity::class.java))


        }


    }

    public fun onClickUndo() {

        if (BitmapUtiles.bitmsaparray.size > 1) {
            undonePaths.add(BitmapUtiles.bitmsaparray.removeAt(BitmapUtiles.bitmsaparray.size - 1))
            iv_redo.imageTintList =
                ColorStateList.valueOf(getResources().getColor(R.color.purple_200));
            tv_redo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_200)));
            iv_undo.imageTintList =
                ColorStateList.valueOf(getResources().getColor(R.color.purple_200));
            tv_undo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_200)));

        } else {

            iv_undo.imageTintList = ColorStateList.valueOf(getResources().getColor(R.color.black));
            tv_undo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

        }




        iv_show.setImageBitmap(BitmapUtiles.bitmsaparray[BitmapUtiles.bitmsaparray.size - 1])


        uri = BitmapUtiles.saveToCacheAndGetUri(
             BitmapUtiles.bitmsaparray[BitmapUtiles.bitmsaparray.size - 1],
            "phortedot",
            this@EditActivity
        );






    }


    public fun onClickRedo() {


        if (undonePaths.size > 0) {
            iv_undo.imageTintList =
                ColorStateList.valueOf(getResources().getColor(R.color.purple_200));
            tv_undo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_200)));
            iv_redo.imageTintList =
                ColorStateList.valueOf(getResources().getColor(R.color.purple_200));
            tv_redo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_200)));

            BitmapUtiles.bitmsaparray.add(undonePaths.removeAt(undonePaths.size - 1))

        } else {
            iv_redo.imageTintList = ColorStateList.valueOf(getResources().getColor(R.color.black));
            tv_redo.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));


        }

        iv_show.setImageBitmap(BitmapUtiles.bitmsaparray[BitmapUtiles.bitmsaparray.size - 1])



        uri = BitmapUtiles.saveToCacheAndGetUri(
            BitmapUtiles.bitmsaparray[BitmapUtiles.bitmsaparray.size - 1],
            "phortedot",
            this@EditActivity
        );



    }


    override fun onBackPressed() {
        super.onBackPressed()

        BitmapUtiles.bitmsaparray.clear()

        finish()
        startActivity(Intent(this@EditActivity, StartActivity::class.java))

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_CROP_IMAGE) {
            data?.data?.let {

                iv_show.setImageURI(it)


                BitmapUtiles.bitmsaparray.add(BitmapUtiles.decodeUriToBitmap(this@EditActivity, it))

            }
        }



        if (resultCode == RESULT_OK) {


            if (requestCode == 101) {

                var urida: String? = data!!.extras!!.getString("uri")


                iv_show.setImageURI(Uri.parse(urida));


            }


        }


    }

    private fun startCroppy() {

        val externalCropRequest = CropRequest.Auto(
            sourceUri = uri!!,
            requestCode = RC_CROP_IMAGE
        )

        //Saves to cache and return uri
        val cacheCropRequest = CropRequest.Auto(
            sourceUri = uri!!,
            requestCode = RC_CROP_IMAGE,
            storageType = StorageType.CACHE
        )

        val destinationUri =
            FileCreator
                .createFile(FileOperationRequest.createRandom(), applicationContext)
                .toUri()

        Log.e("TAG", "startCroppy: " + destinationUri.toString())

        val manualCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE
        )

        val excludeAspectRatiosCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE,
            excludedAspectRatios = arrayListOf(AspectRatio.ASPECT_FREE)
        )

        Log.e("TAG", "startCroppy: " + uri)

        val themeCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE,
            croppyTheme = CroppyTheme(R.color.purple_200)
        )

        Log.e("TAG", "startCroppy1111: " + uri)
        Log.e("TAG", "startCroppy1111: " + themeCropRequest)

        Croppy.start(this, themeCropRequest)
        Log.e("TAG", "startCroppy111144: " + themeCropRequest)

    }

    companion object {
        private const val RC_CROP_IMAGE = 102

    }


}