package ru.macdroid.binlist.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import ru.macdroid.binlist.viewModel.MainViewModel
import ru.macdroid.binlist.databinding.FragmentMainBinding
import ru.macdroid.binlist.ui.adapters.ViewPagerAdapter

class MainFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    lateinit var binding: FragmentMainBinding

    private val listFragments = listOf(
        CurrentFragment.newInstance(),
        HistoryFragment.newInstance()
    )

    private val listFragmentsTitles = listOf(
        "Bank",
        "History"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observer()
    }

    private fun observer() {
        model.getBin().observe(viewLifecycleOwner) {
            binding.etBinInput.setText(it)

            binding.tlTabs.selectTab(binding.tlTabs.getTabAt(0))
        }
    }

    private fun init() {

        binding.etBinInput.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etBinInput.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty() && s.length % 4 == 0) {
                    model.requestCardData(s.toString().toLong())
                }

                if (s.length < 4 || s.length % 4 != 0) {
                    model.nullState()
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        val adapter = ViewPagerAdapter(activity as FragmentActivity, listFragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tlTabs, binding.viewPager) { tab, pos ->
            tab.text = listFragmentsTitles[pos]
        }.attach()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}