package com.zyuniversita.data.local.assets

import android.content.Context
import android.util.Log
import com.zyuniversita.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

interface ReadWordsFileDataSource {
    suspend fun getInstructions(): String?
}

class ReadWordsFileDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ReadWordsFileDataSource {

    companion object {
        private const val TAG = "READ FILE DATA SOURCE TAG"
    }

    override suspend fun getInstructions(): String? {
        try {
            return withContext(Dispatchers.IO) {
                val stream = context.resources.openRawResource(R.raw.instructions)

                stream.bufferedReader().use { reader ->
                    reader.readText()
                }
            }

        } catch (e: IOException) {
            Log.e(TAG, "Error while trying to read the file.")
            return null
        }
    }
}