package com.task.maintask.repository

import android.content.Context
import com.google.gson.Gson
import com.task.maintask.model.MainGuestsItem
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.io.IOException

class Repository {

    suspend fun getGuests(appContext: Context, fileName: String) = flow<MainGuestsItem> {
        val jsonString = appContext.readAssests(fileName)
        val jSonObj = JSONObject(jsonString)
        val guestsItem = Gson().fromJson(jSonObj.toString(), MainGuestsItem::class.java)
        flowOf(guestsItem).catch {
            throw Exception("Parsing Issue")
        }.map {
            emit(it)
        }.collect()
    }

    @Throws(IOException::class)
    fun Context.readAssests(fileName: String): String {
        val input = assets.open(fileName)
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        return String(buffer, Charsets.UTF_8)
    }
}