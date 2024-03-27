package com.bit.lake.arrowktexample.books.exceptions

import com.bit.lake.arrowktexample.books.ISBN

class DuplicateEntryException private constructor(
    isbn: ISBN,
): RuntimeException() {
    override val message: String = "Entry with ISBN ${isbn.value} already exists!"
    companion object {
        fun of(isbn: ISBN): DuplicateEntryException = DuplicateEntryException(isbn)
    }
}
