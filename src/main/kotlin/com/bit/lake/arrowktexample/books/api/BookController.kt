package com.bit.lake.arrowktexample.books.api

import com.bit.lake.arrowktexample.books.Book
import com.bit.lake.arrowktexample.books.BookService
import com.bit.lake.arrowktexample.books.Category
import com.bit.lake.arrowktexample.books.ISBN
import com.bit.lake.arrowktexample.books.Title
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService,
) {
    data class BookResource(
        val title: String,
        val isbn: String,
        val category: String,
    ) {
        fun toDomain() = Book(
            Title(title),
            ISBN(isbn),
            Category.valueOf(category)
        )

        companion object {
            fun of(book: Book): BookResource = BookResource(
                book.title.value,
                book.isbn.value,
                book.category.name,
            )
        }
    }

    @GetMapping("/books")
    fun findAll(): ResponseEntity<List<BookResource>> = bookService.findAll()
        .fold({
            ResponseEntity.notFound().build()
        }) {
            ResponseEntity.ok(it.map { BookResource.of(it) })
        }

    @PostMapping("/books")
    fun save(@RequestBody book: BookResource): ResponseEntity<Unit> =
        bookService.save(book.toDomain()).fold({
            ResponseEntity.internalServerError().build()
        }) {
            ResponseEntity.accepted().build()
        }

    @GetMapping("/books/{isbn}")
    fun findByISBN(@PathVariable isbn: String): ResponseEntity<BookResource> =
        bookService.findByISBN(ISBN(isbn)).fold({
            ResponseEntity.notFound().build()
        }) {
            ResponseEntity.ok(BookResource.of(it))
        }
}
