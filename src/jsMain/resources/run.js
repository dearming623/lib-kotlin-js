import * as Stdlib from "./kotlin-kotlin-stdlib.js";
import * as AtomicfuRuntime from "./kotlin-kotlinx-atomicfu-runtime.js";
import * as Atomicfu from "./kotlinx-atomicfu.js";
import * as CoroutinesCore from "./kotlinx-coroutines-core.js";
import * as LibMath from "./lib-math.js";

const libKotlinJs = globalThis["lib-kotlin-js"];
// 创建 Calculator 实例
const calculator = new libKotlinJs.Calculator();

// 调用 add 方法
const result = calculator.add(5, 3);
console.log(result); // 输出 8

// 调用 hello 方法
libKotlinJs.hello(); // 输出 'hi'

libKotlinJs.hello2();

// 使用协程
libKotlinJs.hello4();