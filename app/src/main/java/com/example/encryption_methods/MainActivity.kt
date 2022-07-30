package com.example.encryption_methods


import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.encryption.AESEncryptor
import com.example.encryption.KeyGeneration


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        KeyStoreEncription()

        //AESEncryption()

        //CryptoLibrary()
    }

    private fun CryptoLibrary() {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            "Encrypted File",
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val editor = sharedPreferences.edit()
        editor.apply {
            putString("Name", "Daniel")
            putBoolean("Is_developer", true)
        }.apply()

    }

    private fun KeyStoreEncription() {
        val TAG = "KeyStoreEncryption"

        val keyGenerator = javax.crypto.KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            "MyKeyAlias",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()


        KeyGeneration()

        val pair = KeyGeneration().encryptData("Test this encryption")
        val decryptedData = KeyGeneration().decryptData(pair.first, pair.second)

        val encrypted = pair.second.toString(Charsets.UTF_8)
        Log.d(TAG, "KeyStoreEncripted: $encrypted")
        Log.d(TAG, "KeyStoreDecrypted: $decryptedData")


    }


    private fun AESEncryption() {
        val TAG = "AESEncryption"
        val secretKey = "662ede816988e58fb6d057d9d85605e0"

        //var encryptor = AESEncryptorGCM()
        val encryptor = AESEncryptor()

        val encryptedValue: String? = encryptor.encrypt("Rohail", secretKey)
        Log.d(TAG, "AESEncryption: $encryptedValue")

        val decryptedValue: String? = encryptor.decryptWithAES(secretKey, encryptedValue)
        Log.d(TAG, "AESDecryption: $decryptedValue")
    }
}