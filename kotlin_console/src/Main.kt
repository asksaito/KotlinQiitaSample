import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val list = (1..10000000).toList()

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

    // mapとfilterを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.map { it -> it.toString() }.filter { it.startsWith("1") }.toList()
        }
    }
    println("ConvertMapFilterAsList max: ${elapsedResultList.max()} " +
            "/ min:${elapsedResultList.min()} / average: ${elapsedResultList.average()} (msec)")

    // シーケンスにしてからmapとfilterを実施する
    elapsedResultList = repeatExecution(list) { collection ->
        measureTimeMillis {
            collection.asSequence().map { it -> it.toString() }.filter { it.startsWith("1") }.toList()
        }
    }
    println("ConvertMapFilterAsSequence max: ${elapsedResultList.max()} " +
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


/* 実行結果

// 10,000,000件で実行
ConvertAsList max: 73 / min:6 / average: 30.8 (msec)
ConvertAsSequence max: 126 / min:100 / average: 108.1 (msec)
ConvertMapFilterAsList max: 5562 / min:3956 / average: 4424.0 (msec)
ConvertMapFilterAsSequence max: 2331 / min:1561 / average: 1699.4 (msec)
ConvertFilterMapAsList max: 1183 / min:1136 / average: 1157.6 (msec)
ConvertFilterMapAsSequence max: 2282 / min:1075 / average: 1560.0 (msec)
ConvertFilterMap2AsList max: 522 / min:331 / average: 380.8 (msec)
ConvertFilterMap2AsSequence max: 19 / min:1 / average: 3.5 (msec)

// 1,000,000件で実行
ConvertAsList max: 5 / min:0 / average: 1.4 (msec)
ConvertAsSequence max: 26 / min:5 / average: 11.8 (msec)
ConvertMapFilterAsList max: 329 / min:95 / average: 155.5 (msec)
ConvertMapFilterAsSequence max: 140 / min:91 / average: 101.2 (msec)
ConvertFilterMapAsList max: 128 / min:62 / average: 72.9 (msec)
ConvertFilterMapAsSequence max: 124 / min:60 / average: 71.3 (msec)
ConvertFilterMap2AsList max: 34 / min:20 / average: 23.1 (msec)
ConvertFilterMap2AsSequence max: 5 / min:1 / average: 1.6 (msec)

// 100件で実行
ConvertAsList max: 0 / min:0 / average: 0.0 (msec)
ConvertAsSequence max: 11 / min:0 / average: 1.3 (msec)
ConvertMapFilterAsList max: 12 / min:0 / average: 1.5 (msec)
ConvertMapFilterAsSequence max: 5 / min:0 / average: 0.7 (msec)
ConvertFilterMapAsList max: 1 / min:0 / average: 0.1 (msec)
ConvertFilterMapAsSequence max: 1 / min:0 / average: 0.1 (msec)
ConvertFilterMap2AsList max: 0 / min:0 / average: 0.0 (msec)
ConvertFilterMap2AsSequence max: 1 / min:0 / average: 0.1 (msec)

 */