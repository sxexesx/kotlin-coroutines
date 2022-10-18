import kotlin.system.measureTimeMillis

const val SOME_THRESHOLD = 5000

fun main() {
    var limit = 100_000_000
    val list = mutableListOf<Int>()
    while (limit > 0) {
        list.add(limit--)
    }
    val data = list.sorted().toIntArray()

    var result: Long = 0
    val time1 = measureTimeMillis {
        compute(data, 0, data.size)
    }
    println("$result in ${time1}ms")

    result = 0
    val time2 = measureTimeMillis {
        result = compute(data, 0, data.size)
    }
    println("$result in ${time2}ms")

    result = 0
    val time3 = measureTimeMillis {
        result = compute(data, 0, data.size)
    }
    println("$result in ${time3}ms")

    result = 0
    val time4 = measureTimeMillis {
        result = compute(data, 0, data.size)
    }
    println("$result in ${time4}ms")
}

fun compute(array: IntArray, low: Int, high: Int): Long {
//    print("low: $low, high: $high on ${Thread.currentThread().name})
    return if (high - low <= SOME_THRESHOLD) {
        (low until high)
            .sumOf { array[it].toLong() }
    } else {
        val mid = low + (high - low) / 2
        val left = compute(array, low, mid)
        val right = compute(array, mid, high)
        return left + right
    }
}

// result
//5000000050000000 in 73ms
//5000000050000000 in 73ms
//5000000050000000 in 73ms