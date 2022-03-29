package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

var url:String ?= null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Loadmeme()
    }


    private fun Loadmeme()
    {
        val p = findViewById<ProgressBar>(R.id.Progress)
        p.visibility = View.VISIBLE
       url = "https://meme-api.herokuapp.com/gimme"
        //val queue = Volley.newRequestQueue(this)
        val JSONObject = JsonObjectRequest(Request.Method.GET,url,null,
            { response ->
                url = response.getString("url")
                val image:ImageView = findViewById(R.id.MemeImageView)
                Glide.with(this).load(url).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        p.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        p.visibility = View.GONE
                        return false
                    }
                }).into(image)

            }, {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })
        //queue.add(JSONObject)
        MySingleton.getInstance(this).addToRequestQueue(JSONObject)
    }

    fun OnNext(view: View) {
        Loadmeme()
    }
    fun OnShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,"HEY! CHECKOUT THIS COOL MEME $url")
        startActivity(Intent.createChooser(intent, "Share this meme using"))
    }
}