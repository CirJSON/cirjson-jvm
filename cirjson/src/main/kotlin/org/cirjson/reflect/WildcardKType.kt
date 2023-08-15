package org.cirjson.reflect

import java.lang.reflect.MalformedParameterizedTypeException
import kotlin.reflect.KType

/**
 * WildcardKType represents a wildcard type expression, such as `*`, `out Number`, or `in Int`.
 */
interface WildcardKType: KType {

    /**
     * Returns an array of `KType` objects representing the  upper
     * bound(s) of this type variable.  If no upper bound is
     * explicitly declared, the upper bound is `Object`.
     *
     * For each upper bound B :
     *
     *  * if B is a parameterized type or a type variable, it is created,
     *  (see [ParameterizedKType] for the details of the creation process for parameterized types).
     *
     *  *Otherwise, B is resolved.
     *
     * @apiNote While to date a wildcard may have at most one upper
     * bound, callers of this method should be written to accommodate
     * multiple bounds.
     *
     * @return an array of Types representing the upper bound(s) of this
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     bounds refer to a parameterized type that cannot be instantiated
     *     for any reason
     */
    val upperBounds: Array<KType>

    /**
     * Returns an array of `KType` objects representing the
     * lower bound(s) of this type variable.  If no lower bound is
     * explicitly declared, the lower bound is the type of `null`.
     * In this case, a zero length array is returned.
     *
     * For each lower bound B :
     *
     * * if B is a parameterized type or a type variable, it is created,
     *  (see [ParameterizedKType] for the details of the creation process for parameterized types).
     *
     *  * Otherwise, B is resolved.
     *
     * @apiNote While to date a wildcard may have at most one lower
     * bound, callers of this method should be written to accommodate
     * multiple bounds.
     *
     * @return an array of Types representing the lower bound(s) of this
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     bounds refer to a parameterized type that cannot be instantiated
     *     for any reason
     */
    val lowerBounds: Array<KType>

}