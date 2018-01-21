package com.rodvar.cwbprep.data

import android.util.Log
import com.rodvar.cwbprep.domain.AccountDetails
import io.reactivex.Observable
import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by rodvar on 21/1/18.
 */
class AccountDetailsRepository : Repository<AccountDetails> {

    companion object {
        val JSON_URL = "https://www.dropbox.com/s/tewg9b71x0wrou9/data.json?dl=1"
    }

    override fun add(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<List<AccountDetails>> {
        throw NotImplementedError("N/A")
    }

    override fun get(): Observable<AccountDetails> {
        return Observable.create { emitter ->
            try {
                Log.d(javaClass.simpleName, "Fetching from the web..")
                var accountDetails = fetchAccountDetailsFromWeb()
                if (accountDetails == null) {
                    Log.d(javaClass.simpleName, "Failed! Fetching from the resources..")
                    accountDetails = fetchAccountDetailsFromResources()
                }
                if (accountDetails == null)
                    emitter.onError(IllegalArgumentException("Failed to load data"))
                else
                    emitter.onNext(accountDetails)
                emitter.setCancellable {
                    // Cancel API call / DB read if not needed any more
                }
            } catch (e : Exception) {
                emitter.onError(e)
            }
            emitter.onComplete()
        }
    }

    private fun fetchAccountDetailsFromWeb(): AccountDetails? {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(JSON_URL)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val stream = connection.getInputStream()
            reader = BufferedReader(InputStreamReader(stream))

            val buffer = StringBuffer()
            var line : String? = ""

            do {
                try {
                    line = reader.readLine()
                    buffer.append(line + "\n")
                } catch (e : Exception) {
                    Log.d(javaClass.simpleName, "Failed to read line")
                    line = null
                }
            } while (line != null)

            return parseJson(buffer.toString())

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (connection != null) {
                connection.disconnect()
            }
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun fetchAccountDetailsFromResources(): AccountDetails? {
        val jsonText = ResourceReader.loadJSONFromAsset("exercise.json")
        return parseJson(jsonText)
    }

    private fun parseJson(jsonText: String): AccountDetails? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<AccountDetails>(AccountDetails::class.java)
        val accountDetails = jsonAdapter.fromJson(jsonText)
        return accountDetails
    }
}