package com.rodvar.cwbprep.data

import com.rodvar.cwbprep.CwbPrepApplication.Companion.context
import java.nio.charset.Charset

/**
 * Created by rodvar on 21/1/18.
 */
class ResourceReader {

    companion object {
        fun loadJSONFromAsset(fileName : String): String {
            var json: String?
            try {
                val `is` = context.assets.open(fileName)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: Exception) {
                ex.printStackTrace()
                return ""
            }
            return json
        }
    }
}