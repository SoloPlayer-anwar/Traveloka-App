package com.example.pullcode.ui.dashboard.explore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pullcode.databinding.ActivityVideoBinding
import com.example.pullcode.response.explore.Data
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Data>("data")

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(data?.video.toString(), 0f)
                }
            })
        }
    }
}