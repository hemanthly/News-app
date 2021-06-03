package com.example.newsapp

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private val newsList = ArrayList<News>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var madapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        madapter = NewsListAdapter(this)
        binding.recyclerView.adapter = madapter

        }

    private fun fetchData() {
        // val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=9b64094202da46dabe4b2c6458bfe71f"
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        //val url = "http://api.mediastack.com/v1/news?access_key=55c31f0d02206c780ed27f708e33b8f6&countries=in&categories=sports&date=2021-05-01,2021-05-23"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val properties = newsJsonArray.getJSONObject(i)
                    //val source = properties.getJSONObject("source")
                    //val source_name = source.getString("name")
                    val news = News(
                        properties.getString("title"),
                        properties.getString("content"),
                        properties.getString("url"),
                        properties.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                madapter.updateNews(newsArray)

            },
            {

            }
        )
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        var builder : CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        //builder.setActionButton(icon, description, pendingIntent, tint);
        var customTabsIntent : CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
        val colorInt : Int = Color.parseColor("#FF0000")
        builder.setColorScheme(colorInt)


    }
}



