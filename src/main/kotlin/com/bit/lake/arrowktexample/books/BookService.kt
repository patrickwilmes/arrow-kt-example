package com.bit.lake.arrowktexample.books

import arrow.core.Either
import com.bit.lake.arrowktexample.books.failure.BookFailure
import com.bit.lake.arrowktexample.books.ports.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun findAll(): Either<BookFailure, List<Book>> = bookRepository.findAll()

    fun save(book: Book): Either<BookFailure, Unit> = bookRepository.save(book)

    fun findByISBN(isbn: ISBN): Either<BookFailure, Book> =
        bookRepository.findByISBN(isbn)
}
