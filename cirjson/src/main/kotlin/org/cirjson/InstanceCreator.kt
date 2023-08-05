package org.cirjson

import java.lang.reflect.Type

/**
 * This interface is implemented to create instances of a class that does not define a no-args
 * constructor. If you can modify the class, you should instead add a private, or public
 * no-args constructor. However, that is not possible for library classes, such as JDK classes, or
 * a third-party library that you do not have source-code of. In such cases, you should define an
 * instance creator for the class. Implementations of this interface should be registered with
 * [CirJsonBuilder.registerTypeAdapter] method before CirJson will be able to use
 * them.
 *
 * Let us look at an example where defining an InstanceCreator might be useful. The
 * `Id` class defined below does not have a default no-args constructor.
 *
 * ```
 * class Id<T>(private clazz: KClass<T>, private val value: Long)
 * ```
 *
 * If CirJson encounters an object of type `Id` during deserialization, it will throw an
 * exception. The easiest way to solve this problem will be to add a (public or private) no-args
 * constructor as follows:
 *
 * ```
 * private Id() {
 *   this(Object.class, 0L);
 * }
 * ```
 *
 * However, let us assume that the developer does not have access to the source-code of the
 * `Id` class, or does not want to define a no-args constructor for it. The developer
 * can solve this problem by defining an `InstanceCreator` for `Id`:
 *
 * ```
 * class IdInstanceCreator : InstanceCreator<Id> {
 *   override fun createInstance(Type type): Id {
 *     return Id(Any::class, 0L);
 *   }
 * }
 * ```
 *
 * Note that it does not matter what the fields of the created instance contain since CirJson will
 * overwrite them with the deserialized values specified in CirJson. You should also ensure that a
 * *new* object is returned, not a common object since its fields will be overwritten.
 * The developer will need to register `IdInstanceCreator` with CirJson as follows:
 *
 * ```
 * val cirJson = CirJsonBuilder().registerTypeAdapter(Id::class.java, IdInstanceCreator()).create();
 * ```
 *
 * @param T the type of object that will be created by this implementation.
 */
interface InstanceCreator<T> {

    /**
     * CirJson invokes this call-back method during deserialization to create an instance of the
     * specified type. The fields of the returned instance are overwritten with the data present
     * in the CirJson. Since the prior contents of the object are destroyed and overwritten, do not
     * return an instance that is useful elsewhere. In particular, do not return a common instance,
     * always use `new` to create a new instance.
     *
     * @param type the parameterized `T` represented as a [Type].
     *
     * @return a default object instance of type `T`.
     */
    fun createInstance(type: Type): T

}