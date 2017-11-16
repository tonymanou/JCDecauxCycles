package com.tonymanou.jcdecauxcycles.exception

import okhttp3.HttpUrl
import java.io.IOException

class RestResponseException(url: HttpUrl, code: Int)
    : IOException("Got status $code while querying $url")
