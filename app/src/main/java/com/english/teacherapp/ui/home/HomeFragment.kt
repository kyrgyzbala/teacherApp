package com.english.teacherapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.teacherapp.R
import com.english.teacherapp.databinding.FragmentHomeBinding
import com.english.teacherapp.ui.home.util.ModelLevel
import com.english.teacherapp.util.toast


class HomeFragment : Fragment(), LevelRecyclerView.LevelClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private var levels = mutableListOf<ModelLevel>()

    private lateinit var adapter: LevelRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateLevels()
        binding.recyclerViewHome.setHasFixedSize(true)
        adapter = LevelRecyclerView(this)
        binding.recyclerViewHome.adapter = adapter
        adapter.submitList(levels)
    }

    private fun generateLevels() {
        levels = mutableListOf(
            ModelLevel(1, "Beginner Level", R.drawable.absbeginner),
            ModelLevel(2, "Elementary Level", R.drawable.beginner),
            ModelLevel(3, "Pre-Intermediate Level", R.drawable.preint),
            ModelLevel(4, "Intermediate Level", R.drawable.intermadiate),
            ModelLevel(5, "Upper-Intermediate Level", R.drawable.upper),
            ModelLevel(6, "Advanced Level", R.drawable.advanced)
        )
    }

    override fun onLevelClick(position: Int) {
        requireActivity().toast("clicked $position")
    }
}