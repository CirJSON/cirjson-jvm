package org.cirjson.internal

import org.cirjson.reflect.GenericArrayKType
import org.cirjson.reflect.ParameterizedKType
import org.cirjson.reflect.WildcardKType
import java.io.Serializable
import java.lang.reflect.GenericArrayType
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.defaultType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.typeOf

object CirJsonTypeHelpers {

    internal val EMPTY_KTYPE_ARRAY = arrayOf<KType>()

    /**
     * Returns a new parameterized type, applying {@code typeArguments} to
     * {@code rawType} and enclosed by {@code ownerType}.
     *
     * @return a {@link java.io.Serializable serializable} parameterized type.
     */
    fun newParameterizedTypeWithOwner(ownerType: KType?, rawType: KType,
            vararg typeArguments: KType): ParameterizedKType {
        return ParameterizedKTypeImpl(ownerType, rawType, *typeArguments)
    }

    /**
     * Returns an array type whose elements are all instances of
     * {@code componentType}.
     *
     * @return a {@link java.io.Serializable serializable} generic array type.
     */
    fun genericArrayOf(componentType: KType): GenericArrayType {
        return GenericArrayTypeImpl(componentType)
    }

    /**
     * Returns a type that represents an unknown type that extends {@code bound}.
     * For example, if {@code bound} is {@code CharSequence.class}, this returns
     * {@code ? extends CharSequence}. If {@code bound} is {@code Object.class},
     * this returns {@code ?}, which is shorthand for {@code ? extends Object}.
     */
    fun subtypeOf(bound: KType): WildcardKType {
        val upperBounds: Array<KType> = if (bound is WildcardKType) bound.upperBounds else arrayOf(bound)
        return WildcardKTypeImpl(upperBounds, EMPTY_KTYPE_ARRAY)
    }

    /**
     * Returns a type that represents an unknown supertype of {@code bound}. For
     * example, if {@code bound} is {@code String.class}, this returns {@code ?
     * super String}.
     */
    fun supertypeOf(bound: KType): WildcardKType {
        val lowerBounds: Array<KType> = if (bound is WildcardKType) bound.lowerBounds else arrayOf(bound)
        return WildcardKTypeImpl(arrayOf(typeOf<Any?>()), lowerBounds)
    }

    /**
     * Returns a type that is functionally equal but not necessarily equal
     * according to {@link Object#equals(Object) Object.equals()}. The returned
     * type is {@link java.io.Serializable}.
     */
    fun canonicalize(type: KType): KType {
        if (type.classifier is KClass<*>) {
            val c = type.classifier as KClass<*>
            return if (c.starProjectedType == typeOf<Array<*>>()) {
                GenericArrayTypeImpl(canonicalize(type.arguments[0].type!!))
            } else {
                c.createType()
            }
        }

        if (type is ParameterizedKType) {
            return ParameterizedKTypeImpl(type.ownerType, type.rawType, *type.actualTypeArguments)
        }

        if (type is GenericArrayKType) {
            return GenericArrayTypeImpl(type.genericComponentType)
        }

        if (type is WildcardKType) {
            return WildcardKTypeImpl(type.upperBounds, type.lowerBounds)
        }


        // type is either serializable as-is or unsupported
        return type
    }

    fun getRawType(type: KType)

    private class ParameterizedKTypeImpl(ownerType: KType?, rawType: KType, vararg typeArguments: KType) :
            ParameterizedKType, Serializable {

        override val ownerType: KType?

        override val rawType: KType

        private val typeArguments: Array<out KType>

        init {
            require(ownerType != null && requiresOwnerType(rawType)) {
                "Must specify owner type for $rawType"
            }

            this.ownerType = if (ownerType != null) canonicalize(ownerType) else null
            this.rawType = canonicalize(rawType)
            this.typeArguments = Array(typeArguments.size) { i ->
                val typeArgument = typeArguments[i]
                checkNotPrimitive(typeArgument)
                canonicalize(typeArgument)
            }
        }

    }

    private class GenericArrayTypeImpl(componentType: KType) : GenericArrayKType, Serializable {

        private val componentType: KType = canonicalize(componentType)

    }

    private class WildcardKTypeImpl(upperBounds: Array<KType>, lowerBounds: Array<KType>) : WildcardKType,
            Serializable {

        private val upperBound: KType

        private val lowerBound: KType?

        init {
            require(lowerBounds.size <= 1)
            require(upperBounds.size == 1)

            if (lowerBounds.size == 1) {
                checkNotPrimitive(lowerBounds[0])
                require(upperBounds[0] == typeOf<Any?>())
                lowerBound = canonicalize(lowerBounds[0])
                upperBound = typeOf<Any?>()
            } else {
                checkNotPrimitive(upperBounds[0])
                lowerBound = null
                upperBound = canonicalize(upperBounds[0])
            }
        }

    }

}