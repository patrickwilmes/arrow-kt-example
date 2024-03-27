package com.bit.lake.arrowktexample.books.ports

import arrow.core.Either
import com.bit.lake.arrowktexample.books.Book
import com.bit.lake.arrowktexample.books.ISBN
import com.bit.lake.arrowktexample.books.failure.BookFailure

interface BookRepository {
    fun findAll(): Either<BookFailure, List<Book>>
    fun save(book: Book): Either<BookFailure, Unit>
    fun findByISBN(isbn: ISBN): Either<BookFailure, Book>
}
