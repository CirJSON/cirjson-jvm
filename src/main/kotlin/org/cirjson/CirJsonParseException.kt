package org.cirjson

/**
 * This exception is raised if there is a serious issue that occurs during parsing of a CirJson
 * string. One of the main usages for this class is for the CirJson infrastructure. If the incoming
 * CirJson is bad/malicious, an instance of this exception is raised.
 *
 * This exception is a [RuntimeException] because it is exposed to the client. Using a [RuntimeException] avoids bad coding practices on the client side where they catch the exception and do nothing. It is often the case that you want to blow up if there is a parsing error (i.e. often clients do not know how to recover from a [CirJsonParseException]).
 */
open class CirJsonParseException : RuntimeException {

    /**
     * Creates exception with the specified message.
     *
     * If you are wrapping another exception, consider using the constructor that uses a message and a cause
     * (`CirJsonParseException(String, Throwable)`) instead.
     *
     * @param message error message describing a possible cause of this exception.
     */
    constructor(message: String) : super(message)

    /**
     * Creates exception with the specified message and cause.
     *
     * @param message error message describing what happened.
     * @param cause root exception that caused this exception to be thrown.
     */
    constructor(message: String, cause: Throwable) : super(message, cause)

    /**
     * Creates exception with the specified cause.
     *
     * If you can describe what happened, consider using the constructor that uses a message and a cause
     * (`CirJsonParseException(String, Throwable)`) instead.
     *
     * @param cause root exception that caused this exception to be thrown.
     */
    constructor(cause: Throwable) : super(cause)

}