package com.bit.lake.arrowktexample.books

@JvmInline
value class ISBN(val value: String) {
    init {
        // maybe other meaningful isbn validation
        require(value.isNotBlank())
    }
}

@JvmInline
value class Title(val value: String) {
    init {
        require(value.isNotBlank())
    }
}

enum class Category {
    NonFiction, Fiction
}

data class Book (
    val title: Title,
    val isbn: ISBN,
    val category: Category,
)
