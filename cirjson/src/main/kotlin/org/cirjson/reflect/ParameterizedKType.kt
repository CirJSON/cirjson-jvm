package org.cirjson.reflect

import java.lang.reflect.MalformedParameterizedTypeException
import java.lang.reflect.TypeVariable
import kotlin.reflect.KType

/**
 * ParameterizedType represents a parameterized type such as `Collection<String>`.
 *
 * A parameterized type is created the first time it is needed by a
 * reflective method, as specified in this package. When a
 * parameterized type p is created, the generic class or interface declaration
 * that p instantiates is resolved, and all type arguments of p are created
 * recursively. See [TypeVariable] for details on the creation process for type
 * variables. Repeated creation of a parameterized type has no effect.
 *
 * Instances of classes that implement this interface must implement
 * an `equals()` method that equates any two instances that share the
 * same generic class or interface declaration and have equal type parameters.
 */
interface ParameterizedKType : KType {

    /**
     * Returns an array of `KType` objects representing the actual type
     * arguments to this type.
     *
     * <p>Note that in some cases, the returned array be empty. This can occur
     * if this type represents a non-parameterized type nested within
     * a parameterized type.
     *
     * @return an array of `KType` objects representing the actual type
     *     arguments to this type
     *
     * @throws TypeNotPresentException if any of the actual type arguments
     *     refers to a non-existent class or interface declaration
     *
     * @throws MalformedParameterizedTypeException if any of the
     *     actual type parameters refer to a parameterized type that cannot
     *     be instantiated for any reason
     */
    val actualTypeArguments: Array<KType>

    /**
     * Returns the `KType` object representing the class or interface
     * that declared this type.
     *
     * @return the `KType` object representing the class or interface
     *     that declared this type
     * @since 1.5
     */
    val rawType: KType

    /**
     * Returns a `KType` object representing the type that this type
     * is a member of.  For example, if this type is `O<T>.I<S>`,
     * return a representation of `O<T>`.
     *
     * If this type is a top-level type, `null` is returned.
     *
     * @return a `KType` object representing the type that
     *     this type is a member of. If this type is a top-level type,
     *     `null` is returned
     *
     * @throws TypeNotPresentException if the owner type
     *     refers to a non-existent class or interface declaration
     *
     * @throws MalformedParameterizedTypeException if the owner type
     *     refers to a parameterized type that cannot be instantiated
     *     for any reason
     */
    val ownerType: KType?

}