package com.eclipse.bot.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.eclipse.bot.R
import com.eclipse.bot.databinding.FragmentSettingsBinding
import com.eclipse.bot.util.LanguageUtil
import java.util.*

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val enLocale: String = getString(R.string.english_lang)
        val ruLocale: String = getString(R.string.russian_lang)

        val locale: Locale = resources.configuration.locales[0]
        val currentLanguage: String = if (locale.language.equals("ru")) ruLocale else enLocale
        val languages = listOf(enLocale, ruLocale)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_language, languages)

        val textView: AutoCompleteTextView? = (binding.languageMenuInput.editText as? AutoCompleteTextView)
        textView?.setText(currentLanguage, false)
        textView?.setAdapter(adapter)

        textView?.setOnItemClickListener { _, _, i, _ ->
            LanguageUtil.changeLanguage(context, resources, if (adapter.getItem(i)!! == ruLocale) "ru" else "en")

            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}