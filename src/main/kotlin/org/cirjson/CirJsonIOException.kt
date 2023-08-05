package org.cirjson

/**
 * This exception is raised when CirJson was unable to read an input stream or write to one.
 */
class CirJsonIOException: CirJsonParseException {

    /**
     * Creates exception with the specified message.
     *
     * If you are wrapping another exception, consider using the constructor that uses a message and a cause
     * (`CirJsonIOException(String, Throwable)`) instead.
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
     * (`CirJsonIOException(String, Throwable)`) instead.
     *
     * @param cause root exception that caused this exception to be thrown.
     */
    constructor(cause: Throwable) : super(cause)

}