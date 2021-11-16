package webserver

// provided files

class Request(val url: String, val authToken: String = "https://teaching.doc.ic.ac.uk/labts/lab_exercises/2122/exercises/616/repository/73487?milestone=881")

class Response(val status: Status, val body: String = "")

enum class Status(val code: Int) {
  OK(200),
  FORBIDDEN(403),
  NOT_FOUND(404)
}
