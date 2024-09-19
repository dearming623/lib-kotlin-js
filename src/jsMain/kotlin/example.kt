import kotlinx.coroutines.*
import kotlin.js.Promise

@OptIn(ExperimentalJsExport::class)
@JsExport
fun hello() { console.info("hi") }

@OptIn(ExperimentalJsExport::class)
@JsExport
fun hello2() {
   println("hi2")
}

//@OptIn(ExperimentalJsExport::class)
//@JsExport
//fun hello3() {
//   fn1()
//}

@OptIn(ExperimentalJsExport::class, DelicateCoroutinesApi::class)
@JsExport
fun hello4(): Promise<Any> = GlobalScope.promise {
   fn1()
}


suspend fun fn1() = coroutineScope {
   launch {
      delay(1000)
      println("Kotlin Coroutines World!")
   }
   println("Hello")
}
