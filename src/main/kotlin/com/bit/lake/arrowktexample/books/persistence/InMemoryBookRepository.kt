package com.bit.lake.arrowktexample.books.persistence

import com.bit.lake.arrowktexample.books.Book
import com.bit.lake.arrowktexample.books.Category
import com.bit.lake.arrowktexample.books.ISBN
import com.bit.lake.arrowktexample.books.Title
import com.bit.lake.arrowktexample.books.exceptions.DuplicateEntryException
import com.bit.lake.arrowktexample.books.exceptions.NotFoundException
import com.bit.lake.arrowktexample.books.ports.BookRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryBookRepository : BookRepository {
    private data class BookEntity(
        val title: String,
        val isbn: String,
        val category: String,
    ) {
        fun toDomain(): Book = Book(
            Title(title),
            ISBN(isbn),
            Category.valueOf(category),
        )
        companion object {
            fun of(book: Book): BookEntity = BookEntity(
                title = book.title.value,
                isbn = book.isbn.value,
                category = book.category.name,
            )
        }
    }

    private val storage: MutableList<BookEntity> = mutableListOf()

    override fun findAll(): List<Book> {
        if (storage.isEmpty()) throw NotFoundException()
        return storage.map { it.toDomain() }
    }

    override fun save(book: Book) {
        if (storage.any { it.isbn == book.isbn.value }) throw DuplicateEntryException.of(book.isbn)
        storage.add(BookEntity.of(book))
    }

    override fun findByISBN(isbn: ISBN): Book = storage.first { it.isbn == isbn.value }.toDomain()
}
