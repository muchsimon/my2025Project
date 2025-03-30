package org.jub.kotlin.hometask2

class AVLTreeMap<K : Comparable<K>, V> : AVLTree<K, V>(), MutableBalancedSearchTreeMap<K, V> {
    private var _size: Int = 0

    override val size: Int get() = _size

    override fun isEmpty(): Boolean = _size == 0

    override fun maximumKey(): K {
        if (isEmpty()) throw NoSuchElementException("Map is empty")
        return super.maximumKey()
    }

    override fun containsKey(key: K): Boolean = get(key) != null

    override fun containsValue(value: V): Boolean {
        var found = false
        super.inorderTraversal { _, v -> if (v == value) found = true }
        return found
    }

    override fun get(key: K): V? = super.get(key)

    override fun put(key: K, value: V): V? {
        val oldValue = get(key)
        insert(key, value)
        if (oldValue == null) _size++
        return oldValue
    }

    protected override fun remove(key: K) {
        super<AVLTree>.remove(key)
    }

    private fun removeAndGet(key: K): V? {
        val oldValue = get(key)
        if (oldValue != null) {
            remove(key)
            _size--
        }
        return oldValue
    }

    override fun remove(key: K): V? = removeAndGet(key)

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (k, v) -> put(k, v) }
    }

    override fun clear() {
        clearRoot()
        _size = 0
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = object : AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
        override val size: Int get() = this@AVLTreeMap.size

        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> = object : MutableIterator<MutableMap.MutableEntry<K, V>> {
            private val entries = mutableListOf<MutableMap.MutableEntry<K, V>>()
            private var index = 0
            private var lastReturned: MutableMap.MutableEntry<K, V>? = null

            init {
                super@AVLTreeMap.inorderTraversal { k, v ->
                    entries.add(object : MutableMap.MutableEntry<K, V> {
                        override val key: K = k
                        override var value: V = v
                        override fun setValue(newValue: V): V {
                            val oldValue = value
                            value = newValue
                            this@AVLTreeMap.put(key, newValue)
                            return oldValue
                        }
                    })
                }
            }

            override fun hasNext(): Boolean = index < entries.size

            override fun next(): MutableMap.MutableEntry<K, V> {
                if (!hasNext()) throw NoSuchElementException()
                lastReturned = entries[index]
                index++
                return lastReturned!!
            }

            override fun remove() {
                checkNotNull(lastReturned) { "Call next() before remove()" }
                this@AVLTreeMap.remove(lastReturned!!.key)
                lastReturned = null
            }
        }

        override fun add(element: MutableMap.MutableEntry<K, V>): Boolean {
            throw UnsupportedOperationException("Add operation is not supported")
        }

        override fun clear() = this@AVLTreeMap.clear()

        override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean {
            return this@AVLTreeMap.remove(element.key) != null
        }
    }

    override val keys: MutableSet<K> = object : AbstractMutableSet<K>() {
        override val size: Int get() = this@AVLTreeMap.size

        override fun iterator(): MutableIterator<K> = object : MutableIterator<K> {
            private val entryIterator = entries.iterator()
            private var lastReturned: K? = null

            override fun hasNext(): Boolean = entryIterator.hasNext()

            override fun next(): K {
                val entry = entryIterator.next()
                lastReturned = entry.key
                return lastReturned!!
            }

            override fun remove() {
                entryIterator.remove()
            }
        }

        override fun add(element: K): Boolean {
            throw UnsupportedOperationException("Add operation is not supported")
        }

        override fun clear() = this@AVLTreeMap.clear()

        override fun remove(element: K): Boolean {
            return this@AVLTreeMap.remove(element) != null
        }
    }

    override val values: MutableCollection<V> = object : AbstractMutableCollection<V>() {
        override val size: Int get() = this@AVLTreeMap.size

        override fun iterator(): MutableIterator<V> = object : MutableIterator<V> {
            private val entryIterator = entries.iterator()
            private var lastReturned: V? = null

            override fun hasNext(): Boolean = entryIterator.hasNext()

            override fun next(): V {
                val entry = entryIterator.next()
                lastReturned = entry.value
                return lastReturned!!
            }

            override fun remove() {
                entryIterator.remove()
            }
        }

        override fun add(element: V): Boolean {
            throw UnsupportedOperationException("Add operation is not supported")
        }

        override fun clear() = this@AVLTreeMap.clear()
    }

    override fun merge(other: MutableBalancedSearchTreeMap<K, V>): MutableBalancedSearchTreeMap<K, V> {
        if (!other.isEmpty() && !this.isEmpty() && other.minimumKey() <= this.maximumKey()) {
            throw IllegalArgumentException("Keys in other map must be larger than keys in this map")
        }

        val result = AVLTreeMap<K, V>()
        this.forEach { (k, v) -> result[k] = v }
        other.forEach { (k, v) -> result[k] = v }
        return result
    }
} 