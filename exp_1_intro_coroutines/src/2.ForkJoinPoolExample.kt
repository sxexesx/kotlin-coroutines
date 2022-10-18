import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask
import kotlin.system.measureTimeMillis

var limit = 100_000_000

fun main() {
    val list = mutableListOf<Int>()
    while (limit > 0) {
        list.add(limit--)
    }
    val data = list.sorted().toIntArray()
    var multiResult: Long = 0
    val multiTime1 = measureTimeMillis {
        multiResult = Sum.sumArray(data)
    }
    println("$multiResult in ${multiTime1}ms")

    multiResult = 0
    val multiTime2 = measureTimeMillis {
        multiResult = Sum.sumArray(data)
    }
    println("$multiResult in ${multiTime2}ms")

    multiResult = 0
    val multiTime3 = measureTimeMillis {
        multiResult = Sum.sumArray(data)
    }
    println("$multiResult in ${multiTime3}ms")

    multiResult = 0
    val multiTime4 = measureTimeMillis {
        multiResult = Sum.sumArray(data)
    }
    println("$multiResult in ${multiTime4}ms")
}

class Sum(
    private var array: IntArray,
    private var low: Int,
    private var high: Int
) : RecursiveTask<Long>() {
    companion object {
        val SEQUENTIAL_THRESHOLD = 5000

        fun sumArray(array: IntArray): Long {
            return ForkJoinPool.commonPool().invoke(Sum(array, 0, array.size))
        }
    }

    override fun compute(): Long {
        return if (high - low <= SEQUENTIAL_THRESHOLD) {
            (low until high)
                .sumOf { array[it].toLong() }
        } else {
            val mid = low + (high - low) / 2
            val left = Sum(array, low, mid)
            val right = Sum(array, mid, high)
            left.fork()
            val rightAns = right.compute()
            val leftAns = left.join()
            leftAns + rightAns
        }
    }
}

// result
//5000000050000000 in 89ms
//5000000050000000 in 20ms
//5000000050000000 in 18ms
//5000000050000000 in 17ms