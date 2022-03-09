package com.eclipse.bot.ui.home

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
import com.eclipse.bot.util.ThemeUtil
import com.google.android.material.switchmaterial.SwitchMaterial
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
        val adapter = ArrayAdapter(requireContext(), R.layout.list_language, listOf(enLocale, ruLocale))

        val textView: AutoCompleteTextView? = (binding.languageMenuInput.editText as? AutoCompleteTextView)
        textView?.setText(currentLanguage, false)
        textView?.setAdapter(adapter)

        textView?.setOnItemClickListener { _, _, i, _ ->
            LanguageUtil.changeLanguage(context, resources, if (adapter.getItem(i)!! == ruLocale) "ru" else "en")

            activity?.recreate()
        }

		val darkThemeSwitch: SwitchMaterial = binding.darkThemeSwitch
		darkThemeSwitch.isChecked = ThemeUtil.isDark(context)

		darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
			ThemeUtil.changeTheme(context, isChecked)
		}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
