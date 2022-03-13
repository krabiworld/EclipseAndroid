package com.eclipse.bot.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PreferencesHelper {
    companion object {
		private const val fileName: String = "preferences"
        private const val encryptedFileName: String = "encryptedPreferences"

		fun get(context: Context?): SharedPreferences {
			if (context == null) throw NullPointerException("Context is null.")
			return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
		}

        fun getEncrypted(context: Context?): SharedPreferences {
			if (context == null) throw NullPointerException("Context is null.")
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
    }
}
