package org.cirjson

object CirJsonNull : CirJsonElement() {

    override fun deepCopy(): CirJsonNull {
        return this
    }

    /**
     * All instances of `CirJsonNull` have the same hash code since they are indistinguishable.
     */
    override fun hashCode(): Int {
        return CirJsonNull::class.java.hashCode()
    }

}