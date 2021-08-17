package com.example.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/library/book"){

            get("/{bookId}"){
                val bookId = call.parameters["bookId"]!!
                val book = Book(bookId, "Lord of the Rings", "JRR Tolkien")
                val hypermediaLinks = listOf(
                    HypermediaLink("http://localhost:8080/library/book/$bookId/checkout",
                        "checkout", "GET"),
                    HypermediaLink("http://localhost:8080/library/book/$bookId/reserve",
                        "reserve", "GET")
                )
                val bookResponse = BookResponse(book, hypermediaLinks)
                call.respond(bookResponse)
            }

            get("/{bookId}/checkout"){
                val bookId = call.parameters["bookId"]!!
                val message = "You checked out the book $bookId."
                call.respond(BookCheckedOutResponse(message, emptyList()))
            }
            get("/{bookId}/reserve"){
                val bookId = call.parameters["bookId"]!!
                val message = "You reserved the book $bookId."
                call.respond(BookCheckedOutResponse(message, emptyList()))
            }
        }



    }
}


data class Book(val id: String, val title: String, val author: String)
data class BookResponse(val book: Book, val links: List<HypermediaLink>)
data class BookReservedResponse(val message: String, val links: List<HypermediaLink>)
data class BookCheckedOutResponse(val message: String, val links: List<HypermediaLink>)
data class HypermediaLink(val href: String, val rel: String, val type: String)