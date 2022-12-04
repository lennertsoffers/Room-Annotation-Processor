package be.lennertsoffers.annotation_processor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import be.lennertsoffers.annotations_library.Column;
import be.lennertsoffers.annotations_library.Configure;
import be.lennertsoffers.annotations_library.Entity;
import be.lennertsoffers.annotations_library.PrimaryKey;
import be.lennertsoffers.annotation_processor.models.EntityColumn;
import be.lennertsoffers.annotation_processor.models.EntityTable;
import be.lennertsoffers.annotation_processor.models.generators.ContractGenerator;
import be.lennertsoffers.annotation_processor.models.generators.DatabaseGenerator;
import be.lennertsoffers.annotation_processor.models.generators.RepositoryGenerator;

/**
 * Annotation processor file that serves as entrypoint hooked into the Javac compile<br/>
 * Collects data about the configuration of the database and the different tables with their fields<br/>
 * Validates if all the required information is available and generates the source files to easily setup the database<br/>
 */
@SupportedAnnotationTypes({
        "be.lennertsoffers.annotations_library.Entity",
        "be.lennertsoffers.annotations_library.Configure",
        "be.lennertsoffers.annotations_library.Database"
})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class EntityProcessor extends AbstractProcessor {
    // If the 'Configure' annotation was already read
    private boolean configured = false;
    // Name of the package where the source files should be generated
    private String packageName = null;
    // Name of the database
    private String databaseName = null;
    // Version of the database
    private Integer databaseVersion = null;

    /**
     * Called by the Javac compiler to start the processing of the annotations<br/>
     * @param annotations List of the found annotations
     * @param roundEnv Javac round environment
     * @return If the supported annotations can still be processed by other annotation processors
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Collect the elements annotated with the 'Configure' annotation
        Set<? extends Element> configurationElements = roundEnv.getElementsAnnotatedWith(Configure.class);
        // Collect the elements annotated with the 'Entity' annotation
        Set<? extends Element> entityElements = roundEnv.getElementsAnnotatedWith(Entity.class);

        // Throw exception if there are more than one 'Configure' annotations
        if (this.configured && !configurationElements.isEmpty()) throw new RuntimeException("You can only have one Configure annotation");

        // If there are entities to be persisted in the database
        if (!entityElements.isEmpty()) {
            // Configure the database if it is not configured yet
            if (!this.configured) {
                // Throw an error if there is no 'Configure' annotation in the provided source code
                if (configurationElements.size() < 1) throw new RuntimeException("You need to configure easyPersistence by adding the Configuration annotation");

                // Get the configure annotation instance
                Configure configuration = configurationElements.iterator().next().getAnnotation(Configure.class);

                // Get the package name and the database name from the configuration
                // Also check if the provided values are not blank
                String packageName = configuration.packageLocation();
                if (packageName.isBlank()) throw new RuntimeException("You cannot have an empty package location in your configuration");
                String databaseName = configuration.databaseName();
                if (databaseName.isBlank()) throw new RuntimeException("You cannot have an empty database name in your configuration");

                // Set the configuration values
                this.configured = true;
                this.packageName = packageName;
                this.databaseName = databaseName;
                this.databaseVersion = configuration.databaseVersion();
            }

            // Map the elements to a list of entity tables which store the information on how to create a table for the entity
            List<EntityTable> entityTables = entityElements
                    .stream()
                    .map(this::toEntityTable)
                    .collect(Collectors.toList());

            // Generate the contract file
            ContractGenerator.generate(entityTables, this.processingEnv, this.packageName);
            // Generate the repositories
            RepositoryGenerator.generate(entityTables, this.processingEnv, this.packageName);
            // Generate the database file
            DatabaseGenerator.generate(entityTables, this.processingEnv, this.packageName, this.databaseName, this.databaseVersion);
        }

        return true;
    }

    /**
     * Maps an element to an entityTable<br/>
     * EntityTables store all the necessary information on how to create a table for this entity in the database<br/>
     * @param element The element to convert to an entity
     * @return The entityTable converted from the given element
     */
    private EntityTable toEntityTable(Element element) {
        // If the table name is provided while defining the 'Entity' annotation, this will become the table name
        // The name of the class is used otherwise
        String tableName = element.getAnnotation(Entity.class).tableName();
        if (tableName.isBlank()) tableName = element.getSimpleName().toString();

        // Instantiation of the entityTable with the table name, name of the class and the fully qualified name for this class
        EntityTable entityTable = new EntityTable(tableName, element.getSimpleName().toString(), this.processingEnv.getElementUtils().getPackageOf(element).getQualifiedName() + "." + element.getSimpleName());

        // Declare boolean indicating if there is a primary key column or not
        AtomicBoolean foundPrimaryKey = new AtomicBoolean(false);
        // Create and add a colum for all fields of the class
        element.getEnclosedElements()
                .stream()
                .filter(enclosedElement -> enclosedElement.getKind().equals(ElementKind.FIELD))
                .forEach(enclosedElement -> this.addColumn(enclosedElement, foundPrimaryKey, entityTable));

        // Throw an exception if there was no primary key found in the entity
        if (!foundPrimaryKey.get()) throw new RuntimeException("You have not provided a primary key for " + element.getSimpleName().toString());

        return entityTable;
    }

    /**
     * Creates and adds a column to the provided entityTable<br/>
     * The column is created from the current element<br/>
     * @param element The field for which an new column must be created and added to the entityTable
     * @param foundPrimaryKey State indicating if the primary key was found or not
     * @param entityTable The entityTable on which the column must be added
     */
    private void addColumn(Element element, AtomicBoolean foundPrimaryKey, EntityTable entityTable) {
        // Check if the field is annotated with 'PrimaryKey'
        boolean primaryKey = element.getAnnotation(PrimaryKey.class) != null;
        // The primary key is found if this field is annotated with 'PrimaryKey'
        if (primaryKey) foundPrimaryKey.set(true);

        // Get the Column instance holding the values for the column
        Column columnAnnotation = element.getAnnotation(Column.class);

        // If there is no 'Column' or 'PrimaryKey' annotation, this field is ignored
        if (columnAnnotation == null && !primaryKey) return;

        // The column name is the name provided in the 'Column' annotation or the name of the field
        String columnName;
        if (columnAnnotation == null || columnAnnotation.name().isBlank()) {
            columnName = element.getSimpleName().toString();
        } else {
            columnName = columnAnnotation.name();
        }

        // Read the type of the field
        TypeMirror typeMirror = element.asType();
        String type;

        // If the type is primitive, the type can be found in the kind of the typeMirror
        if (typeMirror.getKind().isPrimitive()) type = typeMirror.getKind().toString().toLowerCase();
        // If the type is declared, cast to declared type and get SimpleName of this element
        else if (typeMirror.getKind().equals(TypeKind.DECLARED)) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            type = declaredType.asElement().getSimpleName().toString().toLowerCase();
        }
        // The type cannot be found so throw an error
        else throw new RuntimeException("Cannot find type of " + element.getSimpleName().toString());

        // We need to store three different types to generate the repositories and the SQL
        // Type used in SQL language
        String sqlType;
        // Type used in Java
        // The javaType cannot be primitive because we want them to be nullable in the repositories
        String javaType;
        // Type used in the name of the cursor method to get the value
        // For example 'cursor.getInt()' -> 'Int' is here the type of cursor
        String cursorType;
        switch (type) {
            case "int": {
                sqlType = "INTEGER";
                javaType = "Integer";
                cursorType = "Int";
                break;
            }
            case "double": {
                sqlType = "DECIMAL";
                javaType = "Double";
                cursorType = "Double";
                break;
            }
            case "string": {
                sqlType = "TEXT";
                javaType = "String";
                cursorType = "String";
                break;
            }
            default:
                throw new RuntimeException("Unsupported type '" + type + "'");
        }

        // Create and add the column to the entityTable
        entityTable.addColumn(new EntityColumn(columnName, sqlType, javaType, cursorType, element.getSimpleName().toString(), primaryKey));
    }
}
