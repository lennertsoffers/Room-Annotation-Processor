package be.lennertsoffers.annotation_processor.models.generators;

import org.apache.velocity.VelocityContext;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

import be.lennertsoffers.annotation_processor.models.EntityTable;
import be.lennertsoffers.annotation_processor.utils.StringUtils;

/**
 * Defines a generator which will generate a DatabaseHelper source file and an EasyPersistenceDatabase source file<br/>
 * The databaseHelper file will be used to provide the SQLite database instance and also overrides create and upgrade methods<br/>
 * The EasyPersistenceDatabase will be the main entrypoint for development<br/>
 * It will contain getters for all the repositories as wel as a database close function<br/>
 */
public final class DatabaseGenerator {
    public static void generate(List<EntityTable> entityTables, ProcessingEnvironment processingEnvironment, String packageName, String databaseName, int databaseVersion) {
        VelocityContext databaseContext = new VelocityContext();
        databaseContext.put("packageName", packageName);
        databaseContext.put("databaseName", databaseName);
        databaseContext.put("databaseVersion", databaseVersion);
        databaseContext.put("contractName", ContractGenerator.CONTRACT_NAME);

        new SourceFileGenerator(
                "databaseHelper.vm",
                "DatabaseHelper",
                processingEnvironment,
                databaseContext
        ).generateFile();

        databaseContext.put("entityTables", entityTables);
        databaseContext.put("repositorySuffix", RepositoryGenerator.repositorySuffix);
        databaseContext.put("stringUtils", StringUtils.class);

        new SourceFileGenerator(
                "easyPersistenceDatabase.vm",
                "EasyPersistenceDatabase",
                processingEnvironment,
                databaseContext
        ).generateFile();
    }
}
