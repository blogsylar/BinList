package ru.macdroid.binlist.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.macdroid.binlist.databinding.FragmentCurrentBinding
import ru.macdroid.binlist.viewModel.MainViewModel

class CurrentFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
    }

    private fun observer() {
        model.observeCardData().observe(viewLifecycleOwner) {

            println("happy $it")

            binding.tvSchemeField.text = it?.scheme ?: "?"
            binding.tvPrepaidField.text = if (it != null) spannedText(it.prepaid, "YES / NO") else "?"
            binding.tvBrandField.text = it?.brand ?: "?"
            binding.tvTypeField.text = it?.type ?: "?"
            binding.tvCardNumberLenghtField.text = "${it?.number?.length ?: "?"}"
            binding.tvCardNumberLuhnField.text = if (it != null) spannedText(it.number.luhn, "YES / NO") else "?"
            binding.tvCountryNameField.text = if (it != null && it.country != null) "${it.country.emoji} ${it.country.name}" else "?"
            binding.tvCountryCoodrdinateField.text = if (it != null && it.country != null) "(lat: ${it.country.latitude}, lon: ${it.country.longitude})" else "?"
            binding.tvBankFieldName.text = if (it != null && it.bank != null && it.bank.name != null) it.bank.name ?: "?" else "?"
            binding.tvBankFieldUrl.text = if (it != null && it.bank != null && it.bank.url != null) it.bank.url else "?"
            binding.tvBankFieldPhone.text = if (it != null && it.bank != null && it.bank.phone != null) it.bank.phone else "?"

            binding.tvCountryCoodrdinateField.setOnClickListener { click->

                val lat = if (it != null && it.country != null) it.country.latitude else "0"
                val lon = if (it != null && it.country != null) it.country.longitude else "0"

                showMap( "geo:$lat,$lon".toUri())
            }

            binding.tvBankFieldUrl.setOnClickListener { click ->

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(if (it != null && it.bank != null && it.bank.url != null) "http://" + it.bank.url else "https://ya.ru")
                startActivity(intent)
            }

        }
    }

    private fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        if (intent.resolveActivity((activity as AppCompatActivity).packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun spannedText(bool: Boolean?, text: String): Spannable {
        val outPutColoredText: Spannable = SpannableString(text)

        println("happy $bool")

        if (bool == true) {
            outPutColoredText.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            outPutColoredText.setSpan(
                ForegroundColorSpan(Color.BLACK),
                3, 8,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return outPutColoredText
    }


    companion object {
        @JvmStatic
        fun newInstance() = CurrentFragment()
    }
}