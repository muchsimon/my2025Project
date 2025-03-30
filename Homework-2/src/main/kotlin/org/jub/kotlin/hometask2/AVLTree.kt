package org.jub.kotlin.hometask2

open class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V> {
    private class Node<K : Comparable<K>, V>(
        var key: K,
        var value: V,
        var height: Int = 1,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null
    )

    private var root: Node<K, V>? = null

    override val height: Int get() = root?.height ?: 0

    protected fun isRootNull(): Boolean = root == null
    protected fun getRootKey(): K = root?.key ?: throw NoSuchElementException("Tree is empty")
    protected fun getRootValue(): V = root?.value ?: throw NoSuchElementException("Tree is empty")
    protected fun setRoot(key: K, value: V) {
        root = Node(key, value)
    }
    protected fun clearRoot() {
        root = null
    }

    private fun getHeight(node: Node<K, V>?): Int = node?.height ?: 0

    private fun getBalance(node: Node<K, V>?): Int = getHeight(node?.right) - getHeight(node?.left)

    private fun updateHeight(node: Node<K, V>) {
        node.height = maxOf(getHeight(node.left), getHeight(node.right)) + 1
    }

    private fun rotateRight(y: Node<K, V>): Node<K, V> {
        val x = y.left!!
        val T2 = x.right

        x.right = y
        y.left = T2

        updateHeight(y)
        updateHeight(x)

        return x
    }

    private fun rotateLeft(x: Node<K, V>): Node<K, V> {
        val y = x.right!!
        val T2 = y.left

        y.left = x
        x.right = T2

        updateHeight(x)
        updateHeight(y)

        return y
    }

    protected fun insert(key: K, value: V) {
        fun insertRec(node: Node<K, V>?, key: K, value: V): Node<K, V> {
            if (node == null) return Node(key, value)

            when {
                key < node.key -> node.left = insertRec(node.left, key, value)
                key > node.key -> node.right = insertRec(node.right, key, value)
                else -> {
                    node.value = value
                    return node
                }
            }

            updateHeight(node)

            val balance = getBalance(node)

            // Left Left Case
            if (balance < -1 && key < node.left!!.key)
                return rotateRight(node)

            // Right Right Case
            if (balance > 1 && key > node.right!!.key)
                return rotateLeft(node)

            // Left Right Case
            if (balance < -1 && key > node.left!!.key) {
                node.left = rotateLeft(node.left!!)
                return rotateRight(node)
            }

            // Right Left Case
            if (balance > 1 && key < node.right!!.key) {
                node.right = rotateRight(node.right!!)
                return rotateLeft(node)
            }

            return node
        }

        root = insertRec(root, key, value)
    }

    protected open fun removeNode(key: K) {
        fun removeRec(node: Node<K, V>?, key: K): Node<K, V>? {
            if (node == null) return null

            when {
                key < node.key -> node.left = removeRec(node.left, key)
                key > node.key -> node.right = removeRec(node.right, key)
                else -> {
                    if (node.left == null || node.right == null) {
                        val temp = node.left ?: node.right
                        if (temp == null) return null
                        else return temp
                    } else {
                        val temp = findMin(node.right!!)
                        node.key = temp.key
                        node.value = temp.value
                        node.right = removeRec(node.right, temp.key)
                    }
                }
            }

            updateHeight(node)

            val balance = getBalance(node)

            // Left Left Case
            if (balance < -1 && getBalance(node.left) <= 0)
                return rotateRight(node)

            // Left Right Case
            if (balance < -1 && getBalance(node.left) > 0) {
                node.left = rotateLeft(node.left!!)
                return rotateRight(node)
            }

            // Right Right Case
            if (balance > 1 && getBalance(node.right) >= 0)
                return rotateLeft(node)

            // Right Left Case
            if (balance > 1 && getBalance(node.right) < 0) {
                node.right = rotateRight(node.right!!)
                return rotateLeft(node)
            }

            return node
        }

        root = removeRec(root, key)
    }

    private fun findMin(node: Node<K, V>): Node<K, V> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    private fun findMax(node: Node<K, V>): Node<K, V> {
        var current = node
        while (current.right != null) {
            current = current.right!!
        }
        return current
    }

    override fun maximumKey(): K {
        if (root == null) throw NoSuchElementException("Tree is empty")
        return findMax(root!!).key
    }

    override fun minimumKey(): K {
        if (root == null) throw NoSuchElementException("Tree is empty")
        return findMin(root!!).key
    }

    override fun maximumValue(): V {
        if (root == null) throw NoSuchElementException("Tree is empty")
        return findMax(root!!).value
    }

    override fun minimumValue(): V {
        if (root == null) throw NoSuchElementException("Tree is empty")
        return findMin(root!!).value
    }

    protected open fun get(key: K): V? {
        var current = root
        while (current != null) {
            when {
                key < current.key -> current = current.left
                key > current.key -> current = current.right
                else -> return current.value
            }
        }
        return null
    }

    fun inorderTraversal(action: (K, V) -> Unit) {
        fun inorderRec(node: Node<K, V>?) {
            if (node != null) {
                inorderRec(node.left)
                action(node.key, node.value)
                inorderRec(node.right)
            }
        }
        inorderRec(root)
    }
} 