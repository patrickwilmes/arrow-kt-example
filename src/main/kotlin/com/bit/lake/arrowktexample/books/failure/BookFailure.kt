package com.bit.lake.arrowktexample.books.failure

import com.bit.lake.arrowktexample.books.ISBN

sealed interface BookFailure {
    val message: String

    class DuplicateEntryFailure private constructor(override val message: String) : BookFailure {
        companion object {
            fun of(isbn: ISBN): DuplicateEntryFailure = DuplicateEntryFailure("Book with isbn ${isbn.value} already exists!")
        }
    }

    class NotFoundFailure private constructor(override val message: String) : BookFailure {
        companion object {
            fun of(isbn: ISBN): NotFoundFailure = NotFoundFailure("Book with isbn ${isbn.value} not found!")
        }
    }

    data class ValidationFailure(override val message: String): BookFailure
}
