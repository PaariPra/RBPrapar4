package com.chetssholic.removebackgeround.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.filter.ThumbnailCallback
import com.chetssholic.removebackgeround.filter.ThumbnailItem
import com.chetssholic.removebackgeround.filter.ThumbnailsAdapter
import com.chetssholic.removebackgeround.filter.ThumbnailsManager
import com.chetssholic.removebackgeround.utils.BitmapUtiles
import com.zomato.photofilters.SampleFilters
import com.zomato.photofilters.imageprocessors.Filter
import kotlinx.android.synthetic.main.activity_filter_demo.*

import java.util.*

class FilterActivity : AppCompatActivity() , ThumbnailCallback {
    private var imageView8: ImageView? = null
    private var mImageView: ImageView? = null
    private var imageView7: ImageView? = null
    private var bitmap: Bitmap? = null
    companion object {
        init {
            System.loadLibrary("NativeImageProcessor")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_demo)





        imageView8 = findViewById(R.id.imageView8)
        mImageView = findViewById(R.id.mainImageView)
        imageView7 = findViewById(R.id.imageView7)


        imageView8!!.setOnClickListener(View.OnClickListener { finish() })
        val uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@FilterActivity, uri)
        var w = bitmap!!.getWidth()
        var h = bitmap!!.getHeight()
        val s = Math.max(w / 2048.0f, h / 2048.0f)
        if (s > 1.0f) {
            w /= s.toInt()
            h /= s.toInt()
            bitmap = Bitmap.createScaledBitmap(bitmap!!, w, h, false)
        } else {
            bitmap = bitmap
        }
        mImageView!!.setImageBitmap(bitmap);
        menuLayout!!.setHasFixedSize(true)
        menuLayout!!.setLayoutManager(LinearLayoutManager(this, RecyclerView.HORIZONTAL, false))
        bindDataToAdapter()

        imageView7!!.setOnClickListener {

            mImageView!!.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(  mImageView!!.getDrawingCache())
            mImageView!!.setDrawingCacheEnabled(false)


            var uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                this@FilterActivity
            );
            BitmapUtiles.bitmsaparray.add(bitmap)


            var intent: Intent = Intent();
            intent.putExtra("uri", uri.toString())
            setResult(RESULT_OK, intent)

            finish()

        }

    }

    private fun bindDataToAdapter() {

        val handler = Handler()
        val r = Runnable {


            val t1 = ThumbnailItem()
            val t2 = ThumbnailItem()
            val t3 = ThumbnailItem()
            val t4 = ThumbnailItem()
            val t5 = ThumbnailItem()
            val t6 = ThumbnailItem()
            val t7 = ThumbnailItem()
            val t8 = ThumbnailItem()


            var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t1.image = bmp2

            var bmp3 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t2.image = bmp3

            var bmp4 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t3.image = bmp4


            var bmp5 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t4.image = bmp5

            var bmp6 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t5.image = bmp6

            var bmp7 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t6.image = bmp7

            var bmp8 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t7.image = bmp8


            var bmp9 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t8.image = bmp9



            ThumbnailsManager.clearThumbs()
            ThumbnailsManager.addThumb(t1)

            t2.filter = SampleFilters.getStarLitFilter()
            ThumbnailsManager.addThumb(t2)

            t3.filter = SampleFilters.getBlueMessFilter()
            ThumbnailsManager.addThumb(t3)
            t4.filter = SampleFilters.getAweStruckVibeFilter()
            ThumbnailsManager.addThumb(t4)
            t5.filter = SampleFilters.getLimeStutterFilter()
            ThumbnailsManager.addThumb(t5)


            t6.filter = SampleFilters.getNightWhisperFilter()
            ThumbnailsManager.addThumb(t6)

            t7.filter = SampleFilters.getAweStruckVibeFilter()
            ThumbnailsManager.addThumb(t7)


            t8.filter = SampleFilters.getStarLitFilter()
            ThumbnailsManager.addThumb(t8)

            val thumbs: List<ThumbnailItem> = ThumbnailsManager.processThumbs(this@FilterActivity)

            val adapter = ThumbnailsAdapter(
                thumbs,
                this@FilterActivity
                        as ThumbnailCallback?,
                this@FilterActivity
            )
            menuLayout!!.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
        handler.post(r)
    }

    override fun onThumbnailClick(filter: Filter?) {
        var bmp9 = bitmap!!.copy(bitmap!!.getConfig(), true);
        mImageView!!.setImageBitmap(filter!!.processFilter(bmp9));


    }


}