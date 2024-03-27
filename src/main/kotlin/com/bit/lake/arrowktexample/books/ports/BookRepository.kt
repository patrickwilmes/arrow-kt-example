package com.bit.lake.arrowktexample.books.ports

import com.bit.lake.arrowktexample.books.Book
import com.bit.lake.arrowktexample.books.ISBN

interface BookRepository {
    fun findAll(): List<Book>
    fun save(book: Book)
    fun findByISBN(isbn: ISBN): Book
}
