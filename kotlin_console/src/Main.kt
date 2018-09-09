import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val list = (1..10000).toList()

    //TODO http://kirimin.hatenablog.com/entry/2015/08/24/093646 Sequenceを使用した方が良さそうな関数を調べる

    // 単純にリストへ変換
    var elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.toList()
        }
    }
    println("ConvertAsList max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // シーケンスにしてからリストに変換
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.asSequence().toList()
        }
    }
    println("ConvertAsSequence max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // filterとmapを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.filter { it % 2 == 0 }.map { it.toString() }.toList()
        }
    }
    println("ConvertFilterMapAsList max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // シーケンスにしてからfilterとmapを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.asSequence().filter { it % 2 == 0 }.map { it.toString() }.toList()
        }
    }
    println("ConvertFilterMapAsSequence max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // filterとtakeを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.filter { it % 2 == 0 }.take(10000).toList()
        }
    }
    println("ConvertFilterMap2AsList max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // シーケンスにしてからfilterとtakeを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.asSequence().filter { it % 2 == 0 }.take(10000).toList()
        }
    }
    println("ConvertFilterMap2AsSequence max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

}

fun repeatExecution(list: Collection<Int>, action: (Collection<Int>) -> Long): List<Long> {
    val result = arrayListOf<Long>()

    repeat(10) {
        result.add(action(list.shuffled()))
    }

    return result
}
