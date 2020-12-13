package com.english.teacherapp.ui.videos

import android.content.Intent
import android.os.Build
import android.os.RemoteException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.english.teacherapp.databinding.RowVideosBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class VideosRecyclerView(
    options: FirestoreRecyclerOptions<ModelVideo>,
    private val lifecycle: Lifecycle
) :
    FirestoreRecyclerAdapter<ModelVideo, VideosRecyclerView.ViewHolderV>(options) {

    private var _binding: RowVideosBinding? = null

    inner class ViewHolderV(private val binding: RowVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelVideo: ModelVideo) {

            val youtubePlayerView = binding.viewPlayerLive
            lifecycle.addObserver(youtubePlayerView)

            youtubePlayerView.visibility = View.VISIBLE
            binding.playButton.visibility = View.GONE


            youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    Log.d("VIDEODDDD", "onStateChange: ")
                }

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {
                    Log.d("VIDEODDDD", "onError: $error")
                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val id = getYoutube(modelVideo.link)
                    youTubePlayer.cueVideo(id, 0F)

                }
            })

            binding.descTextViewLiveStream.text = modelVideo.description

        }

        private fun getYoutube(url: String): String {
            if (url.contains("watch")) {
                var ret = ""
                var i = 32
                while (i <= 42) {
                    ret += url[i]
                    i++
                }
                return ret
            } else {
                var ret = ""
                var i = 17
                while (i <= 27) {
                    ret += url[i]
                    i++
                }
                return ret
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderV {
        _binding = RowVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderV(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderV, position: Int, model: ModelVideo) {
        holder.onBind(model)
    }

}