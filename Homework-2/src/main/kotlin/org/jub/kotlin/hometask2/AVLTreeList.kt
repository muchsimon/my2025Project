package org.jub.kotlin.hometask2

class AVLTreeList<V> : AVLTree<Int, V>(), BalancedSearchTreeList<Int, V> {
    private var _size: Int = 0

    override val size: Int get() = _size

    override fun isEmpty(): Boolean = _size == 0

    override fun contains(element: V): Boolean {
        var found = false
        super.inorderTraversal { _, v -> if (v == element) found = true }
        return found
    }

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): V {
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
        return super.get(index) ?: throw IllegalStateException("Element not found at index $index")
    }

    override fun indexOf(element: V): Int {
        var index = -1
        var found = false
        super.inorderTraversal { k, v ->
            if (!found && v == element) {
                index = k
                found = true
            }
        }
        return index
    }

    override fun lastIndexOf(element: V): Int {
        var lastIndex = -1
        super.inorderTraversal { k, v ->
            if (v == element) {
                lastIndex = k
            }
        }
        return lastIndex
    }

    override fun listIterator(): ListIterator<V> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<V> {
        if (index !in 0..size) throw IndexOutOfBoundsException("Index: $index, Size: $size")

        return object : ListIterator<V> {
            private var cursor = index
            private var lastRet = -1

            override fun hasNext(): Boolean = cursor < size

            override fun next(): V {
                if (!hasNext()) throw NoSuchElementException()
                lastRet = cursor
                cursor++
                return get(lastRet)
            }

            override fun hasPrevious(): Boolean = cursor > 0

            override fun previous(): V {
                if (!hasPrevious()) throw NoSuchElementException()
                cursor--
                lastRet = cursor
                return get(lastRet)
            }

            override fun nextIndex(): Int = cursor

            override fun previousIndex(): Int = cursor - 1
        }
    }

    override fun iterator(): Iterator<V> = listIterator()

    override fun subList(fromIndex: Int, toIndex: Int): List<V> {
        throw UnsupportedOperationException("subList operation is not supported")
    }

    override fun toArray(): Array<Any?> {
        val result = arrayOfNulls<Any?>(size)
        var index = 0
        super.inorderTraversal { _, v -> result[index++] = v }
        return result
    }

    override fun <T> toArray(array: Array<T?>): Array<T?> {
        @Suppress("UNCHECKED_CAST")
        val result = if (array.size < size) {
            Array<Any?>(size) { null } as Array<T?>
        } else {
            array
        }
        var index = 0
        super.inorderTraversal { _, v -> result[index++] = v as T }
        if (index < result.size) {
            result[index] = null
        }
        return result
    }

    fun add(element: V): Boolean {
        insert(_size, element)
        _size++
        return true
    }

    fun add(index: Int, element: V) {
        if (index !in 0..size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
        
        // Shift all elements from index onwards
        for (i in (size - 1) downTo index) {
            insert(i + 1, get(i))
        }
        
        insert(index, element)
        _size++
    }

    fun removeAt(index: Int): V {
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
        
        val oldValue = get(index)
        
        // Shift all elements after index
        for (i in index until size - 1) {
            insert(i, get(i + 1))
        }
        
        remove(size - 1)
        _size--
        
        return oldValue
    }

    fun remove(element: V): Boolean {
        val index = indexOf(element)
        if (index == -1) return false
        removeAt(index)
        return true
    }

    fun clear() {
        clearRoot()
        _size = 0
    }
} 