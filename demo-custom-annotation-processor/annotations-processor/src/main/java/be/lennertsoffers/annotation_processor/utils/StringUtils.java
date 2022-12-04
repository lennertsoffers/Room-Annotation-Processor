package be.lennertsoffers.annotation_processor.utils;

/**
 * Collection of String methods to convert cases
 */
public final class StringUtils {
    /**
     * Convert PascalCase case to camelCase<br/>
     * Example: ExampleWordString -> exampleWordString<br/>
     * @param string The string in PascalCase
     * @return The string in camelCase
     */
    public static String toCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    /**
     * Converts strings in uppercase separated by underscores to camelCase<br/>
     * Example: EXAMPLE_WORD_STRING -> exampleWordString<br/>
     * @param string The string in uppercase
     * @return The string in camelCase
     */
    public static String upperToCamelCase(String string) {
        boolean capitalizeNext = false;
        StringBuilder stringBuilder = new StringBuilder();

        char[] characters = string.toCharArray();
        for (char character : characters) {
            if (capitalizeNext) character = Character.toUpperCase(character);
            else character = Character.toLowerCase(character);

            capitalizeNext = character == '_';
            if (!capitalizeNext) stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

    /**
     * Capitalizes a string<br/>
     * Example: exampleWordString -> ExampleWordString<br/>
     * @param string The string that should be capitalized
     * @return The string that starts with a capital letter
     */
    public static String capitalize(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
