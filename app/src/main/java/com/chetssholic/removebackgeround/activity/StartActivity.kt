package com.chetssholic.removebackgeround.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.chetssholic.removebackgeround.R

import kotlinx.android.synthetic.main.activity_start.*
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chetssholic.removebackgeround.adapter.CreationAdapter2
import com.chetssholic.removebackgeround.adsclass.ShowIntertialads
import com.chetssholic.removebackgeround.adsclass.ShowNAtivrbannerAds
import com.chetssholic.removebackgeround.cutout.CutOutActivity
import com.chetssholic.removebackgeround.utils.BitmapUtiles
import com.chetssholic.removebackgeround.utils.TinyDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_exit.*
import kotlinx.android.synthetic.main.activity_start.frameLayout


import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import pub.devrel.easypermissions.EasyPermissions.RationaleCallbacks
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

import pub.devrel.easypermissions.AppSettingsDialog
import java.io.File


class StartActivity : AppCompatActivity(), PermissionCallbacks, RationaleCallbacks
{
    var mSelectedBitmap: Bitmap? = null
    var mPhotoFile: File? = null
    var mCompressor: FileCompressor? = null
    var myListAdapter: CreationAdapter2? = null
    var rm: ArrayList<File>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        val showNAtivrbannerAds = ShowNAtivrbannerAds()
        showNAtivrbannerAds.refreshAd(this@StartActivity, frameLayout!!)
        mCompressor =  FileCompressor(this)


        tv_seemore.setOnClickListener {



            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@StartActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {

                    startActivity(
                        Intent(this@StartActivity, MyCrationActivity::class.java)
                            .putExtra("type", "one")
                    )



                }
            })






        }


        cv_spiral.setOnClickListener {
            locationAndContactsTask(101, "drip", 1001)

        }




        cv_edit.setOnClickListener {
            locationAndContactsTask(102, "edit", 1002)


        }
        cv_background.setOnClickListener {
            locationAndContactsTask(103, "rg", 1003)


        }
        cv_spotrit.setOnClickListener {
            locationAndContactsTask(104, "potrait", 1004)


        }
        cv_filter.setOnClickListener {
            locationAndContactsTask(105, "filter", 1005)


        }

        cl_PSp.setOnClickListener {
            locationAndContactsTask(106, "filter", 1006)


        }


    }

    private fun hasLocationAndContactsPermissions1(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
    }


    private fun locationAndContactsTask(code: Int, value: String, code2: Int) {
        if (hasLocationAndContactsPermissions1()) {
            getDIslgShoe(code, value, code2)
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location_contacts),
                RC_LOCATION_CONTACTS_PERM,
                *LOCATION_AND_CONTACTS
            )
        }


    }




    private fun getDIslgShoe(code: Int, value: String, code2: Int) {


        val dialog = Dialog(this@StartActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.select_dialog)

        var cl_galler: ConstraintLayout = dialog.findViewById(R.id.cl_galler);


        var cl_camera: ConstraintLayout = dialog.findViewById(R.id.cl_camera);
        cl_galler.setOnClickListener {
            dialog.dismiss()

            getImage(code, value)

        }
        cl_camera.setOnClickListener {
            dialog.dismiss()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                var photoFile: File? = null
                try {
                    photoFile = getImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()

                }
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this, getPackageName() + ".provider",
                        photoFile
                    )
                    mPhotoFile = photoFile
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, code2)
                }





            }

        }

        dialog.show()


    }


    @Throws(IOException::class)
    private fun getImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "jpg_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir!!.mkdirs()
        val imageFile = File.createTempFile(imageName, ".jpg", storageDir)


        return imageFile
    }


    private fun getImage(requestCode: Int, type: String) {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, requestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult: __!11ewade" + data)

        if (requestCode == RC_LOCATION_CONTACTS_PERM) {


            if (hasLocationAndContactsPermissions1()) {
              Toast.makeText(this@StartActivity, "Click Again", Toast.LENGTH_SHORT).show()
            }




        }



        if (resultCode == Activity.RESULT_OK) {
            data?.data?.also {

                when (requestCode) {
                    101 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)


                                    startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    CutOutActivity::class.java
                                                ).putExtra("imageuri", uri.toString())
                                                    .putExtra("type", "drip")


                                            )

                                            finish()







                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }


                    102 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)



                                            startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    EditActivity::class.java
                                                ).putExtra("imageuri", uri.toString())


                                            )
                                            finish()






                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }

                    103 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)


                                            startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    CutOutActivity::class.java
                                                ).putExtra("imageuri", uri.toString())
                                                    .putExtra("type", "rmbg")
                                            )
                                            finish()







                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }
                    104 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)





                                            startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    CutOutActivity::class.java
                                                ).putExtra("imageuri", uri.toString())
                                                    .putExtra("type", "potr")
                                            )

                                            finish()






                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }
                    105 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)




                                            startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    EditActivity::class.java
                                                ).putExtra("imageuri", uri.toString())
                                                    .putExtra("type", "filter")
                                            )
                                            finish()






                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }
                    106 -> {
                        Glide.with(this@StartActivity)
                            .load(it)
                            .listener(object : RequestListener<Drawable?> {


                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                    Log.e("TAG", "onResourceReady: " + bitap)

                                    val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                        bitap,
                                        "phortedot",
                                        this@StartActivity
                                    );
                                    Log.e("TAG", "onResourceReady: " + uri)




                                            startActivity(
                                                Intent(
                                                    this@StartActivity,
                                                    CutOutActivity::class.java
                                                ).putExtra("imageuri", uri.toString())
                                                    .putExtra("type", "adjust")
                                            )
                                            finish()







                                    return true;
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }


                            })
                            .into(iv_image)


                    }


                }


            }





            if (requestCode == 1001) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )






                        startActivity(
                            Intent(
                                this@StartActivity,
                                CutOutActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                                .putExtra("type", "drip")
                        )
                        finish()








            }


            if (requestCode == 1002) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )





                        startActivity(
                            Intent(
                                this@StartActivity,
                                EditActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                        )
                        finish()






            }

            if (requestCode == 1003) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )







                        startActivity(
                            Intent(
                                this@StartActivity,
                                CutOutActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                                .putExtra("type", "rmbg")
                        )
                        finish()







            }
            if (requestCode == 1004) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )






                        startActivity(
                            Intent(
                                this@StartActivity,
                                CutOutActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                                .putExtra("type", "potr")
                        )
                        finish()







            }
            if (requestCode == 1005) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )




                        startActivity(
                            Intent(
                                this@StartActivity,
                                EditActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                                .putExtra("type", "filter")
                        )
                        finish()






            }
            if (requestCode == 1006) {
                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )





                        startActivity(
                            Intent(
                                this@StartActivity,
                                CutOutActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())
                                .putExtra("type", "adjust")
                        )
                        finish()




            }


        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }


    override fun onRationaleAccepted(requestCode: Int) {
        Log.d("TAG", "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d("TAG", "onRationaleDenied:$requestCode")
    }


    companion object {
        private const val TAG = "MainActivity"
        private val LOCATION_AND_CONTACTS =
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )

        private const val RC_LOCATION_CONTACTS_PERM = 124
    }

    override fun onResume() {
        super.onResume()


        val file = File(
            Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS,
            "HPhotoHollic"
        )


        val pictures = file.listFiles()

        if (pictures != null) {
            rm = ArrayList<File>()
            if (pictures.size > 0) {
                for (i in pictures.indices) {
                    rm!!.add(pictures[i])

                }

                rv_allprview.setVisibility(View.VISIBLE)
                cl_history.setVisibility(View.VISIBLE)


                Collections.reverse(rm);

                myListAdapter = CreationAdapter2(
                    rm, this@StartActivity
                ) { }
                rv_allprview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


                rv_allprview.setHasFixedSize(true)
                rv_allprview.setAdapter(myListAdapter)
            } else {

                rv_allprview.setVisibility(View.GONE)
                cl_history.setVisibility(View.GONE)
            }
        } else {

            rv_allprview.setVisibility(View.GONE)
            cl_history.setVisibility(View.GONE)
        }


    }


    override fun onBackPressed()
    {

        val showIntertialads = ShowIntertialads()
        showIntertialads.shaowinr2(this@StartActivity, object : ShowIntertialads.CAllBack {
            override fun callbac() {

                startActivity(Intent(this@StartActivity, ExitActivity::class.java))



            }
        })




    }




}