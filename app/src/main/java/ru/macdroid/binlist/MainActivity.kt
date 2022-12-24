package ru.macdroid.binlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.macdroid.binlist.databinding.ActivityMainBinding
import ru.macdroid.binlist.extensions.openFragmentFromActivity
import ru.macdroid.binlist.ui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFragmentFromActivity(R.id.fragmentContainer, MainFragment.newInstance())

    }
}