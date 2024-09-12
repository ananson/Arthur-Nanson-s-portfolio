package com.example.zmdb

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.zmdb.model.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [WatchlistWidgetConfigureActivity]
 */
class WatchlistWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {

        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = loadTitlePref(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.watchlist_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    //gets a random movie to watch
    val randomToWatch = CurrentUser.getBestList()[Random.nextInt(1, 11)]
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/movie/${randomToWatch}?language=en-US")
        .get()
        .addHeader("accept", "application/json")
        .addHeader(
            "Authorization",
            BuildConfig.API_KEY
        )
        .build()
    val t = GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            if (response.isSuccessful && responseBody != null){
                val jsonObject = JSONObject(responseBody)
                CurrentUser.setNextToWatch(jsonObject)
            }

        } catch (e: IOException) {

            e.printStackTrace()
        }

    }
    //sets what to do when clicking on the widget
    t.invokeOnCompletion {
        if (t.isCompleted && !t.isCancelled) {
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra("movieData", CurrentUser.getNextToWatch().toString())
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // PendingIntent is set when widget is clicked
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)

            // Widget has pendingIntent
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

/**
 * The widget's activity
 */
class MovieDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val movieData = intent.getStringExtra("movieData")
            ZMDBTheme {
                MovieDetailsScreen(movieData)
            }
        }
    }
}

/**
 * Displays the movie's details given as a parameter. The movie is chosen from an array of good movies.
 */
@Composable
fun MovieDetailsScreen(movieData: String?) {
    val jsonObject = movieData?.let { JSONObject(it) }

    jsonObject?.let {
        val title = it.getString("title")
        val posterPath = it.getString("poster_path")
        val overview = it.getString("overview")
        val voteAverage = it.getDouble("vote_average")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)

                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500$posterPath"),
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "${stringResource(id = R.string.rating)} $voteAverage",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)

                )
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.loading_failed),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp)
            )
        }
    }
}





