@file:OptIn(ExperimentalJsExport::class)

@JsExport
fun greet(name: String): String {
    return "Hello, $name!"
}

@JsExport
class Person(val name: String) {
    fun sayHello(): String {
        return "Hello from $name"
    }
}