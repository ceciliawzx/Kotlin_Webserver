package webserver

import org.junit.Test

// provided files

class Request(val url: String, val authToken: String = "")
class Response(val status: Status, val body: String = "Hello, World!")

enum class Status(val code: Int) {
  OK(200),
  FORBIDDEN(403),
  NOT_FOUND(404)
}

fun helloHandler(re: Request): Response {
  val keyvalues = queryParams(re.url)
  val status = Status.OK
  for (k_v in keyvalues) {
    if (k_v.first == "name") {
      val name = k_v.second
      return Response(status = status, body = "Hello, $name!")
    }
  }
  return Response(status = status)
}

fun homeHandler(res: String): Response {
  return Response(status = Status.OK, body = "This is $res.")
}

fun route(request: Request): Response {
  val url = request.url
  val path = path(url)
  if (path == "/say-hello") {
    return helloHandler(Request(url = url))
  } else if (path == "/") {
    return homeHandler("Imperial")
  } else if (path == "/computing") {
    return homeHandler("DoC")
  }
  return Response(Status.NOT_FOUND)
}