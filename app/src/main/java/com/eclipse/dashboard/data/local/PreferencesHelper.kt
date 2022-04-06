package com.eclipse.dashboard.data.local

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.eclipse.dashboard.ui.SignInActivity

object PreferencesHelper {
	private const val fileName: String = "preferences"
	private const val encryptedFileName: String = "encryptedPreferences"

	fun get(context: Context): SharedPreferences {
		return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
	}

	fun getEncrypted(context: Context): SharedPreferences {
		val masterKey: MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
			.setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
		return EncryptedSharedPreferences.create(
			context,
			encryptedFileName,
			masterKey,
			EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
			EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
		)
	}

	fun signOut(context: Context, activity: Activity) {
		val preferences: SharedPreferences = get(context)
		val encryptedPreferences: SharedPreferences = getEncrypted(context)
		preferences.edit().putBoolean("isAuthenticated", false).apply()
		encryptedPreferences.edit().putString("token", "").apply()

		activity.startActivity(Intent(context, SignInActivity::class.java))
		activity.finish()
	}
}
