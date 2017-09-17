package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return listOf(Pair(year,other.year),Pair(month,other.month),Pair(dayOfMonth,other.dayOfMonth))
            .map { it.first.compareTo(it.second) }
            .firstOrNull { it != 0 } ?: 0
    }

    operator fun plus(ti: TimeInterval) = addTimeIntervals(ti, 1)
    operator fun plus(mti: MultipleTimeIntervals) = addTimeIntervals(mti.interval, mti.quantity)
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this,other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(multiplier: Int) = MultipleTimeIntervals(this,multiplier)
}

class MultipleTimeIntervals(val interval: TimeInterval, val quantity: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator() = object : Iterator<MyDate> {
        var nextDate = start
        override fun hasNext() = nextDate <= endInclusive
        override fun next(): MyDate {
            val currentDate = nextDate
            nextDate = nextDate.nextDay()
            return currentDate
        }
    }
}
