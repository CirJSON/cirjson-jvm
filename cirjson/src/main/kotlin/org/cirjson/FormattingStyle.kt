package org.cirjson

/**
 * A class used to control what the serialization output looks like.
 *
 * It currently has the following configuration methods, but more methods might be added in the future:
 * * [withNewline]
 * * [withIndent]
 * * [withSpaceAfterSeparators]
 *
 * @see CirJsonBuilder.setFormattingStyle
 * @see CirJsonWriter.setFormattingStyle
 * @see <a href="https://en.wikipedia.org/wiki/Newline">Wikipedia Newline article</a>
 */
class FormattingStyle private constructor(val newLine: String, val indent: String,
        val hasSpaceAfterSeparators: Boolean) {

    init {
        require(this.newLine.matches(
                "[\r\n]*".toRegex())) { "Only combinations of \\n and \\r are allowed in newline." }
        require(this.indent.matches(
                "[ \t]*".toRegex())) { "Only combinations of spaces and tabs are allowed in indent." }
    }

    /**
     * Creates a [FormattingStyle] with the specified newline setting.
     *
     * It can be used to accommodate certain OS convention, for example hardcode `"\n"` for Linux and macOS, `"\r\n"`
     * for Windows, or call [java.lang.System.lineSeparator] to match the current OS.
     *
     *
     * Only combinations of `\n` and `\r` are allowed.
     *
     * @param newLine the string value that will be used as newline.
     *
     * @return a newly created [FormattingStyle]
     */
    fun withNewline(newLine: String?): FormattingStyle {
        return FormattingStyle(newLine!!, this.indent, this.hasSpaceAfterSeparators)
    }

    /**
     * Creates a [FormattingStyle] with the specified indent string.
     *
     *
     * Only combinations of spaces and tabs allowed in indent.
     *
     * @param indent the string value that will be used as indent.
     * @return a newly created [FormattingStyle]
     */
    fun withIndent(indent: String?): FormattingStyle {
        return FormattingStyle(this.newLine, indent!!, this.hasSpaceAfterSeparators)
    }

    /**
     * Creates a [FormattingStyle] which either uses a space after the separators `','` and `':'` in the JSON output, or
     * not.
     *
     * This setting has no effect on the [configured newline][.withNewline]. If a non-empty newline is configured, it
     * will always be added after `','` and no space is added after the `','` in that case.
     *
     * @param hasSpaceAfterSeparators whether to output a space after `','` and `':'`.
     * @return a newly created [FormattingStyle]
     */
    fun withSpaceAfterSeparators(hasSpaceAfterSeparators: Boolean): FormattingStyle {
        return FormattingStyle(this.newLine, this.indent, hasSpaceAfterSeparators)
    }

    companion object {

        /**
         * The default compact formatting style:
         *
         *  * no newline
         *  * no indent
         *  * no space after `','` and `':'`
         *
         */
        val COMPACT = FormattingStyle("", "", false)

        /**
         * The default pretty printing formatting style:
         *
         *  * `"\n"` as newline
         *  * two spaces as indent
         *  * a space between `':'` and the subsequent value
         *
         */
        val PRETTY = FormattingStyle("\n", "  ", true)

    }

}