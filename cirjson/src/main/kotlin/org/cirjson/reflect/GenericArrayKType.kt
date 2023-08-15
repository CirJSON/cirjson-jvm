package org.cirjson.reflect

import java.lang.reflect.MalformedParameterizedTypeException
import kotlin.reflect.KType

/**
 * `GenericArrayKType` represents an array type whose component
 * type is either a parameterized type or a type variable.
 */
interface GenericArrayKType : KType {

    /**
     * Returns a `KType` object representing the component type
     * of this array. This method creates the component type of the
     * array.  See the declaration of [ParameterizedKType] for the
     * semantics of the creation process for parameterized types and
     * see [TypeVariable] for the creation process for type variables.
     *
     * @return  a `KType` object representing the component type
     *     of this array
     *
     * @throws TypeNotPresentException if the underlying array type's component
     *     type refers to a non-existent class or interface declaration
     *
     * @throws MalformedParameterizedTypeException if  the
     *     underlying array type's component type refers to a
     *     parameterized type that cannot be instantiated for any reason
     */
    val genericComponentType: KType


}