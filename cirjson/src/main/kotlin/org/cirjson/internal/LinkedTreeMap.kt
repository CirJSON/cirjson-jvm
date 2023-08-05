package org.cirjson.internal

import java.io.*
import java.util.TreeMap
import kotlin.math.max

/**
 * A map of comparable keys to values. Unlike [TreeMap], this class uses
 * insertion order for iteration order. Comparison order is only used as an
 * optimization for efficient insertion and removal.
 *
 * This implementation was derived from Android 4.1's TreeMap class.
 *
 * @constructor Create a tree map ordered by `comparator`. This map's keys may only
 * be null if `comparator` permits.
 *
 * @param comparator the comparator to order elements with, or `null` to use the natural ordering.
 * @param allowNullValues whether `null` is allowed as entry value
 */
internal class LinkedTreeMap<K, V>(comparator: Comparator<in K>?, private val allowNullValues: Boolean) :
        AbstractMutableMap<K, V>(), Serializable {

    @Suppress("UNCHECKED_CAST") private val comparator: Comparator<in K> =
            (comparator ?: NATURAL_ORDER) as Comparator<in K>

    var root: Node<K, V>? = null

    override var size = 0
        private set

    var modCount = 0

    // Used to preserve iteration order
    val header: Node<K, V> = Node(allowNullValues)

    /**
     * Create a natural order, empty tree map whose keys must be mutually
     * comparable and non-null, and whose values can be `null`.
     */
    @Suppress("UNCHECKED_CAST")
    constructor() : this(NATURAL_ORDER as Comparator<in K?>, true)

    /**
     * Create a natural order, empty tree map whose keys must be mutually
     * comparable and non-null.
     *
     * @param allowNullValues whether `null` is allowed as entry value
     */
    constructor(allowNullValues: Boolean) : this(NATURAL_ORDER as Comparator<in K?>, allowNullValues)

    override operator fun get(key: K): V? {
        return findByObject(key)?.value
    }

    override fun containsKey(key: K): Boolean {
        return findByObject(key) != null
    }

    override fun put(key: K, value: V): V {
        if (key == null) {
            throw java.lang.NullPointerException("key == null")
        }
        if (value == null && !allowNullValues) {
            throw java.lang.NullPointerException("value == null")
        }
        val created = find(key, true)!!
        val result = created.value
        created.innerValue = value
        return result
    }

    override fun clear() {
        root = null
        size = 0
        modCount++

        // Clear iteration order
        val header = header
        header.prev = header
        header.next = header.prev
    }

    override fun remove(key: K): V? {
        return removeInternalByKey(key)?.value
    }

    internal fun find(key: K, create: Boolean): Node<K, V>? {
        val comparator = comparator
        var nearest = root
        var comparison = 0

        if (nearest != null) {

            // Micro-optimization: avoid polymorphic calls to Comparator.compare().
            @Suppress("UNCHECKED_CAST")
            val comparableKey: Comparable<Any?>? = if (comparator === NATURAL_ORDER) key as Comparable<Any?> else null

            while (true) {
                comparison = comparableKey?.compareTo(nearest!!.key) ?: comparator.compare(key, nearest!!.key)

                // We found the requested key.
                if (comparison == 0) {
                    return nearest!!
                }

                // If it exists, the key is in a subtree. Go deeper.
                val child: Node<K, V> = (if (comparison < 0) nearest!!.left else nearest!!.right) ?: break
                nearest = child
            }
        }

        // The key doesn't exist in this tree.
        if (!create) {
            return null
        }

        // Create the node and add it to the tree or the table.
        val header: Node<K, V> = header
        val created: Node<K, V>
        if (nearest == null) {
            // Check that the value is comparable if we didn't do any comparisons.
            if (comparator === NATURAL_ORDER && key !is Comparable<*>) {
                throw ClassCastException(key!!::class.simpleName + " is not Comparable")
            }
            created = Node(allowNullValues, nearest, key, header, header.prev!!)
            root = created
        } else {
            created = Node(allowNullValues, nearest, key, header, header.prev!!)
            if (comparison < 0) { // nearest.key is higher
                nearest.left = created
            } else { // comparison > 0, nearest.key is lower
                nearest.right = created
            }
            rebalance(nearest, true)
        }
        size++
        modCount++

        return created
    }

    @Suppress("UNCHECKED_CAST")
    internal fun findByObject(key: Any?): Node<K, V>? {
        return try {
            if (key != null) find(key as K, false) else null
        } catch (e: ClassCastException) {
            null
        }
    }

    /**
     * Returns this map's entry that has the same key and value as `entry`, or null if this map has no such entry.
     *
     * This method uses the comparator for key equality rather than `equals`. If this map's comparator isn't consistent with equals (such as
     * `String.CASE_INSENSITIVE_ORDER`), then `remove()` and `contains()` will violate the collections API.
     */
    internal fun findByEntry(entry: Map.Entry<*, *>): Node<K, V>? {
        val mine: Node<K, V>? = findByObject(entry.key)
        val valuesEqual = mine != null && mine.value == entry.value
        return if (valuesEqual) mine else null
    }

    internal fun removeInternal(node: Node<K, V>, unlink: Boolean) {
        if (unlink) {
            node.prev!!.next = node.next
            node.next!!.prev = node.prev
        }

        var left: Node<K, V>? = node.left
        var right: Node<K, V>? = node.right
        val originalParent: Node<K, V>? = node.parent

        if (left != null && right != null) {/*
             * To remove a node with both left and right subtrees, move an
             * adjacent node from one of those subtrees into this node's place.
             *
             * Removing the adjacent node may change this node's subtrees. This
             * node may no longer have two subtrees once the adjacent node is
             * gone!
             */

            val adjacent: Node<K, V> = if (left.height > right.height) left.last() else right.first()
            removeInternal(adjacent, false) // takes care of rebalance and size--

            var leftHeight = 0
            left = node.left
            if (left != null) {
                leftHeight = left.height
                adjacent.left = left
                left.parent = adjacent
                node.left = null
            }
            var rightHeight = 0
            right = node.right
            if (right != null) {
                rightHeight = right.height
                adjacent.right = right
                right.parent = adjacent
                node.right = null
            }

            adjacent.height = (max(leftHeight.toDouble(), rightHeight.toDouble()) + 1).toInt()
            replaceInParent(node, adjacent)
            return
        } else if (left != null) {
            replaceInParent(node, left)
            node.left = null
        } else if (right != null) {
            replaceInParent(node, right)
            node.right = null
        } else {
            replaceInParent(node, null)
        }

        rebalance(originalParent, false)
        size--
        modCount++
    }

    fun removeInternalByKey(key: Any?): Node<K, V>? {
        val node = findByObject(key)
        if (node != null) {
            removeInternal(node, true)
        }
        return node
    }

    private fun replaceInParent(node: Node<K, V>, replacement: Node<K, V>?) {
        val parent = node.parent
        node.parent = null
        if (replacement != null) {
            replacement.parent = parent
        }
        if (parent != null) {
            if (parent.left === node) {
                parent.left = replacement
            } else {
                assert(parent.right === node)
                parent.right = replacement
            }
        } else {
            root = replacement
        }
    }

    /**
     * Rebalances the tree by making any AVL rotations necessary between the newly-unbalanced node and the tree's root.
     *
     * @param insert true if the node was unbalanced by an insert; false if it was by a removal.
     */
    private fun rebalance(unbalanced: Node<K, V>?, insert: Boolean) {
        var node = unbalanced
        while (node != null) {
            val left = node.left
            val right = node.right
            val leftHeight = left?.height ?: 0
            val rightHeight = right?.height ?: 0
            val delta = leftHeight - rightHeight
            if (delta == -2) {
                val rightLeft = right!!.left
                val rightRight = right.right
                val rightRightHeight = rightRight?.height ?: 0
                val rightLeftHeight = rightLeft?.height ?: 0
                val rightDelta = rightLeftHeight - rightRightHeight
                if (rightDelta == -1 || rightDelta == 0 && !insert) {
                    rotateLeft(node) // AVL right right
                } else {
                    assert(rightDelta == 1)
                    rotateRight(right) // AVL right left
                    rotateLeft(node)
                }
                if (insert) {
                    break // no further rotations will be necessary
                }
            } else if (delta == 2) {
                val leftLeft = left!!.left
                val leftRight = left.right
                val leftRightHeight = leftRight?.height ?: 0
                val leftLeftHeight = leftLeft?.height ?: 0
                val leftDelta = leftLeftHeight - leftRightHeight
                if (leftDelta == 1 || leftDelta == 0 && !insert) {
                    rotateRight(node) // AVL left left
                } else {
                    assert(leftDelta == -1)
                    rotateLeft(left) // AVL left right
                    rotateRight(node)
                }
                if (insert) {
                    break // no further rotations will be necessary
                }
            } else if (delta == 0) {
                node.height = leftHeight + 1 // leftHeight == rightHeight
                if (insert) {
                    break // the insert caused balance, so rebalancing is done!
                }
            } else {
                assert(delta == -1 || delta == 1)
                node.height = max(leftHeight, rightHeight) + 1
                if (!insert) {
                    break // the height hasn't changed, so rebalancing is done!
                }
            }
            node = node.parent
        }
    }

    private fun rotateLeft(root: Node<K, V>) {
        val left = root.left
        val pivot: Node<K, V> = root.right!!
        val pivotLeft = pivot.left
        val pivotRight = pivot.right

        // move the pivot's left child to the root's right
        root.right = pivotLeft
        if (pivotLeft != null) {
            pivotLeft.parent = root
        }
        replaceInParent(root, pivot)

        // move the root to the pivot's left
        pivot.left = root
        root.parent = pivot

        // fix heights
        root.height = max((left?.height ?: 0), (pivotLeft?.height ?: 0)) + 1
        pivot.height = max(root.height, (pivotRight?.height ?: 0)) + 1
    }

    /**
     * Rotates the subtree so that its root's left child is the new root.
     */
    private fun rotateRight(root: Node<K, V>) {
        val pivot = root.left!!
        val right = root.right
        val pivotLeft = pivot.left
        val pivotRight = pivot.right

        // move the pivot's right child to the root's left
        root.left = pivotRight
        if (pivotRight != null) {
            pivotRight.parent = root
        }
        replaceInParent(root, pivot)

        // move the root to the pivot's right
        pivot.right = root
        root.parent = pivot

        // fixup heights
        root.height = max((right?.height ?: 0), (pivotRight?.height ?: 0)) + 1
        pivot.height = max(root.height, (pivotLeft?.height ?: 0)) + 1
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> by lazy {
        EntrySet()
    }

    override val keys: MutableSet<K> by lazy {
        KeySet()
    }

    internal class Node<K, V> : MutableMap.MutableEntry<K, V> {

        var parent: Node<K, V>? = null

        var left: Node<K, V>? = null

        var right: Node<K, V>? = null

        var next: Node<K, V>? = null

        var prev: Node<K, V>? = null

        val innerKey: K?

        val allowNullValue: Boolean

        var innerValue: V? = null

        var height = 0

        /** Create the header entry  */
        constructor(allowNullValue: Boolean) {
            innerKey = null
            this.allowNullValue = allowNullValue
            prev = this
            next = prev
        }

        /** Create a regular entry  */
        constructor(allowNullValue: Boolean, parent: Node<K, V>?, key: K, next: Node<K, V>, prev: Node<K, V>) {
            this.parent = parent
            this.innerKey = key
            this.allowNullValue = allowNullValue
            height = 1
            this.next = next
            this.prev = prev
            prev.next = this
            next.prev = this
        }

        @Suppress("UNCHECKED_CAST") override val key: K
            get() = this.innerKey as K

        @Suppress("UNCHECKED_CAST") override val value: V
            get() = this.innerValue as V

        override fun setValue(newValue: V): V {
            if (value == null && !allowNullValue) {
                throw NullPointerException("value == null")
            }
            val oldValue = value
            innerValue = value
            return oldValue
        }

        /**
         * Returns the first node in this subtree.
         */
        fun first(): Node<K, V> {
            var node = this
            var child = node.left
            while (child != null) {
                node = child
                child = node.left
            }
            return node
        }

        /**
         * Returns the last node in this subtree.
         */
        fun last(): Node<K, V> {
            var node = this
            var child = node.right
            while (child != null) {
                node = child
                child = node.right
            }
            return node
        }

        override fun equals(other: Any?): Boolean {
            if (other is Map.Entry<*, *>) {
                val (key1, value1) = other
                return ((if (key == null) key1 == null else key == key1) && if (value == null) value1 == null else value == value1)
            }
            return false
        }

        override fun hashCode(): Int {
            return ((if (key == null) 0 else key.hashCode()) xor if (value == null) 0 else value.hashCode())
        }

        override fun toString(): String {
            return key.toString() + "=" + value
        }

    }

    private abstract inner class LinkedTreeMapIterator<T> : MutableIterator<T> {

        var next: Node<K, V>? = header.next

        var lastReturned: Node<K, V>? = null

        var expectedModCount = modCount

        override fun hasNext(): Boolean {
            return next !== header
        }

        fun nextNode(): Node<K, V> {
            val e = next
            if (e === header) {
                throw NoSuchElementException()
            }

            if (modCount != expectedModCount) {
                throw ConcurrentModificationException()
            }

            next = e!!.next
            return e.also { lastReturned = it }
        }

        override fun remove() {
            removeInternal(checkNotNull(lastReturned), true)
            lastReturned = null
            expectedModCount = modCount
        }

    }

    internal inner class EntrySet : AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {

        override val size: Int
            get() {
                return this@LinkedTreeMap.size
            }

        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
            return object : LinkedTreeMapIterator<MutableMap.MutableEntry<K, V>>() {

                override operator fun next(): MutableMap.MutableEntry<K, V> {
                    return nextNode()
                }

            }
        }

        override operator fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
            return findByEntry(element) != null
        }

        override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean {
            val node = findByEntry(element) ?: return false
            removeInternal(node, true)
            return true
        }

        override fun clear() {
            this@LinkedTreeMap.clear()
        }

        override fun add(element: MutableMap.MutableEntry<K, V>): Boolean = throw UnsupportedOperationException()

    }

    internal inner class KeySet : AbstractMutableSet<K>() {

        override val size: Int
            get() {
                return this@LinkedTreeMap.size
            }

        override fun iterator(): MutableIterator<K> {
            return object : LinkedTreeMapIterator<K>() {

                override operator fun next(): K {
                    return nextNode().key
                }

            }
        }

        override operator fun contains(element: K): Boolean {
            return containsKey(element)
        }

        override fun remove(element: K): Boolean {
            return removeInternalByKey(element) != null
        }

        override fun clear() {
            this@LinkedTreeMap.clear()
        }

        override fun add(element: K): Boolean = throw UnsupportedOperationException()

    }

    /**
     * If somebody is unlucky enough to have to serialize one of these, serialize
     * it as a LinkedHashMap so that they won't need Gson on the other side to
     * deserialize it. Using serialization defeats our DoS defence, so most apps
     * shouldn't use it.
     */
    @Throws(ObjectStreamException::class)
    private fun writeReplace(): Any {
        return LinkedHashMap(this)
    }

    @Suppress("UNUSED_PARAMETER")
    @Throws(IOException::class)
    private fun readObject(input: ObjectInputStream) {
        // Don't permit directly deserializing this class; writeReplace() should have written a replacement
        throw InvalidObjectException("Deserialization is unsupported")
    }

    companion object {

        private val NATURAL_ORDER = Comparator<Comparable<Any?>> { a, b -> a.compareTo(b) }

    }

}