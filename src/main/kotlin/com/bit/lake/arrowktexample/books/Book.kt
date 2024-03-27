package com.bit.lake.arrowktexample.books

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.bit.lake.arrowktexample.books.failure.BookFailure

@JvmInline
value class ISBN(val value: String) {
    companion object {
        fun of(value: String): Either<BookFailure, ISBN> = either {
            ensure(value.isNotBlank()) { BookFailure.ValidationFailure("ISBN must not be blank!") }
            ISBN(value)
        }
    }
}

@JvmInline
value class Title(val value: String) {
    companion object {
        fun of(value: String): Either<BookFailure, Title> = either {
            ensure(value.isNotBlank()) { BookFailure.ValidationFailure("Title must not be blank!") }
            Title(value)
        }
    }
}

enum class Category {
    NonFiction, Fiction
}

data class Book(
    val title: Title,
    val isbn: ISBN,
    val category: Category,
) {
    companion object {
        fun of(title: String, isbn: String, category: String): Either<BookFailure, Book> = either {
            Book(
                Title.of(title).bind(),
                ISBN.of(isbn).bind(),
                Either.catch {
                    Category.valueOf(category)
                }.mapLeft { BookFailure.ValidationFailure("Invalid category!") }.bind(),
            )
        }
    }
}
