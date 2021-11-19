package webserver

//

fun scheme(url: String): String {
  return url.substringBefore(':')
}

fun host(url: String): String {
  return url.substringAfter("://").substringBefore('/')
}

fun path(url: String): String {
  return ('/' + (url.split("//"))[1].substringAfter('/').substringBefore('?'))
}

fun queryParams(url: String): List<Pair<String, String>> {
  var list = listOf<Pair<String, String>>()
  val keys_values = url.split('?')
  if (keys_values.size > 1) {
    val keyvalue = keys_values[1].split('&')
    for (k_v in keyvalue) {
      val key = k_v.split('=')[0].toString()
      val value = k_v.split('=')[1].toString()
      list = list.plus(Pair(key, value))
    }
  } else {
    return listOf()
  }
  return list
}

// http handlers for a particular website...

fun homePageHandler(request: Request): Response = Response(Status.OK, "This is Imperial.")
