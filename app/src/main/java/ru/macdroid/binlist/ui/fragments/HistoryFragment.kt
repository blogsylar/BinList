package ru.macdroid.binlist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.macdroid.binlist.database.DBHelper
import ru.macdroid.binlist.databinding.FragmentHistoryBinding
import ru.macdroid.binlist.model.db.DBModel
import ru.macdroid.binlist.ui.adapters.BinAdapter
import ru.macdroid.binlist.viewModel.MainViewModel

class HistoryFragment : Fragment(), BinAdapter.Listener {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: BinAdapter
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observer()
    }

    private fun init() {
        dbHelper = DBHelper(activity as AppCompatActivity)

        binding.rcView.layoutManager = LinearLayoutManager(activity)
        adapter = BinAdapter(this)
        binding.rcView.adapter = adapter

        adapter.submitList(dbHelper.getBinList())
    }

    private fun observer() {
        model.observeBinNumberData().observe(viewLifecycleOwner) {

            if (it != null) {
                val status = dbHelper.insertIntoDb(it)

                if (status > -1) {
                    model.setHistoryOfBin(dbHelper.getBinList())
                }
            }
        }

        model.observeHistoryData().observe(viewLifecycleOwner) {
            binding.rcView.layoutManager?.scrollToPosition(0)
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onClick(item: DBModel) {
        model.setBin(item.bin)
    }
}