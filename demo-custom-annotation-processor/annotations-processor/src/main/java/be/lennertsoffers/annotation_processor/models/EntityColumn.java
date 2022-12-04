package be.lennertsoffers.annotation_processor.models;

/**
 * Collects the data to create a table in the database from a Java field
 */
public class EntityColumn {
    private final String fieldName;
    private final String sqlType;
    private final String javaType;
    private final String cursorType;
    private final String javaFieldName;
    private final boolean primaryKey;

    public EntityColumn(String fieldName, String sqlType, String javaType, String cursorType, String javaFieldName, boolean primaryKey) {
        this.fieldName = fieldName;
        this.sqlType = sqlType;
        this.javaType = javaType;
        this.cursorType = cursorType;
        this.javaFieldName = javaFieldName;
        this.primaryKey = primaryKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getCursorType() {
        return cursorType;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * Converts the name of the column into uppercase separated by an underscore
     * @return The uppercase name of the entity
     */
    public String getUppercaseName() {
        StringBuilder stringBuilder = new StringBuilder();

        char[] characters = this.fieldName.toCharArray();
        for (char character : characters) {
            if (Character.isUpperCase(character)) stringBuilder.append('_');
            stringBuilder.append(Character.toUpperCase(character));
        }

        return stringBuilder.toString();
    }
}
