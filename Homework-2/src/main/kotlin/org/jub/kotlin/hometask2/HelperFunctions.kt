package org.jub.kotlin.hometask2

fun <K : Comparable<K>, V> getBst(collection: Iterable<Pair<K, V>>): BalancedSearchTree<K, V> {
    val map = AVLTreeMap<K, V>()
    collection.forEach { (k, v) -> map[k] = v }
    return map
}

fun <K : Comparable<K>, V> getBstMap(collection: Iterable<Pair<K, V>>): BalancedSearchTreeMap<K, V> {
    val map = AVLTreeMap<K, V>()
    collection.forEach { (k, v) -> map[k] = v }
    return map
}

fun <K : Comparable<K>, V> getMutableBstMap(collection: Iterable<Pair<K, V>>): MutableBalancedSearchTreeMap<K, V> {
    val map = AVLTreeMap<K, V>()
    collection.forEach { (k, v) -> map[k] = v }
    return map
}

fun <K : Comparable<K>, V> getBstList(collection: Iterable<Pair<K, V>>): BalancedSearchTreeList<Int, V> {
    val list = AVLTreeList<V>()
    collection.forEach { (_, v) -> list.add(v) }
    return list
}
