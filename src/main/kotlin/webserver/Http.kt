package webserver

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
      return Response(status, "Hello, $name!")
    }
  }
  return Response(status)
}

fun homeHandler(re: Request): Response {
  val res =
    when (path(re.url)) {
        "/" -> "Imperial"
        "/computing" -> "DoC"
        else -> "Nothing"
    }
  return Response(Status.OK, "This is $res.")
}

fun route(request: Request): Response {
  val url = request.url
  val path = path(url)
  if (path == "/say-hello") {
    return helloHandler(Request(url))
  } else if (path == "/computing" || path == "/") {
    return homeHandler(Request(url))
  }
  return Response(Status.NOT_FOUND)
}

typealias HttpHandler = (Request) -> Response

val configs = listOf<Pair<String, HttpHandler>>(
  "/say-hello" to ::helloHandler,
  "/" to ::homeHandler,
  "/computing" to ::homeHandler,
  "/exam-marks" to requireToken("password1", ::restrictedPageHandler)
)

fun configureRoutes(request: Request): Response {
  val path = path(request.url)
  for (config in configs) {
    when (path) {
        config.first -> return config.second(request)
    }
  }
  return Response(Status.NOT_FOUND)
}

fun requireToken(token: String, wrapped: HttpHandler): HttpHandler {
  return wrapped
}

fun restrictedPageHandler(request: Request): Response {
  if (request.authToken == "password1") {
    return Response(Status.OK, "This is very secret.")
  }
  return Response(Status.FORBIDDEN)
}