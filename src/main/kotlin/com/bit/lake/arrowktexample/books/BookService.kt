package com.bit.lake.arrowktexample.books

import com.bit.lake.arrowktexample.books.ports.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun findAll(): List<Book> = bookRepository.findAll()

    fun save(book: Book) {
        bookRepository.save(book)
    }

    fun findByISBN(isbn: ISBN): Book =
        bookRepository.findByISBN(isbn)
}
