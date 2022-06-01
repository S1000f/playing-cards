package tool

inline fun <T> Collection<T>.sumSkipTake(take: Int, skip: Int = 0, f: (T) -> Int) =
    this.drop(skip)
        .take(take)
        .map(f)
        .sumOf { it }
