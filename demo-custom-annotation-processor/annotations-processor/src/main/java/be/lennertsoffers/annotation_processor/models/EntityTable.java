package be.lennertsoffers.annotation_processor.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects the data to create a table in the database from a Java class
 */
public class EntityTable {
    private final String tableName;
    private final String entityName;
    private final String fullName;
    private final List<EntityColumn> columns = new ArrayList<>();

    public EntityTable(String tableName, String entityName, String fullName) {
        this.tableName = tableName;
        this.entityName = entityName;
        this.fullName = fullName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getFullName() {
        return fullName;
    }

    public List<EntityColumn> getColumns() {
        return columns;
    }

    public void addColumn(EntityColumn entityColumn) {
        this.columns.add(entityColumn);
    }
}
