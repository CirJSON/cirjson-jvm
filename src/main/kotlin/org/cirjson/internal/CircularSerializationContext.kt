package org.cirjson.internal

import org.cirjson.CirJsonComplexValue
import org.cirjson.identityHashCode

class CircularSerializationContext {

    @Suppress("SetterBackingFieldAssignment")
    private var nextId = 1
        get() {
            return field++
        }
        set(value) {}

    private val alreadyExistingComplexValues = HashMap<String, CirJsonComplexValue>()

    fun getId(complexValue: CirJsonComplexValue): String {
        for (entry in alreadyExistingComplexValues.entries) {
            if (complexValue.identityHashCode() == entry.value.identityHashCode()) {
                return entry.key
            }
        }

        val id = nextId.toString()
        alreadyExistingComplexValues[id] = complexValue
        return id
    }

    fun getComplexValue(id: String): CirJsonComplexValue? {
        return alreadyExistingComplexValues[id]
    }

}