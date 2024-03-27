package com.bit.lake.arrowktexample.books.persistence

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import com.bit.lake.arrowktexample.books.Book
import com.bit.lake.arrowktexample.books.Category
import com.bit.lake.arrowktexample.books.ISBN
import com.bit.lake.arrowktexample.books.Title
import com.bit.lake.arrowktexample.books.failure.BookFailure
import com.bit.lake.arrowktexample.books.ports.BookRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryBookRepository : BookRepository {
    private data class BookEntity(
        val title: String,
        val isbn: String,
        val category: String,
    ) {
        fun toDomain(): Either<BookFailure, Book> = Book.of(
            title,
            isbn,
            category,
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

    override fun findAll(): Either<BookFailure, List<Book>> = either {
        storage.map { it.toDomain().bind() }
    }

    override fun save(book: Book): Either<BookFailure, Unit> = either {
        ensure(storage.none { it.isbn == book.isbn.value }) {
            BookFailure.DuplicateEntryFailure.of(
                book.isbn
            )
        }
        storage.add(BookEntity.of(book))
    }

    override fun findByISBN(isbn: ISBN): Either<BookFailure, Book> = either {
        ensure(storage.any { it.isbn == isbn.value }) {
            BookFailure.NotFoundFailure.of(isbn)
        }
        storage.first { it.isbn == isbn.value }.toDomain().bind()
    }
}
