@file:OptIn(ExperimentalJsExport::class)

@JsExport
class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}