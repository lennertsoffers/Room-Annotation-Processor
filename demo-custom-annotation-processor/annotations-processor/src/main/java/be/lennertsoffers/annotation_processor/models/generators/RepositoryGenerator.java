package be.lennertsoffers.annotation_processor.models.generators;

import org.apache.velocity.VelocityContext;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

import be.lennertsoffers.annotation_processor.models.EntityTable;
import be.lennertsoffers.annotation_processor.utils.StringUtils;

/**
 * Defines the generator that will generate one base repository file and one repository file for each class annotated with 'Entity'<br/>
 * A repository will contains insert, findAll, findById, update and delete method<br/>
 */
public final class RepositoryGenerator {
    public final static String repositorySuffix = "Repository";

    public static void generate(List<EntityTable> entityTables, ProcessingEnvironment processingEnvironment, String packageName) {
        VelocityContext baseRepositoryContext = new VelocityContext();
        baseRepositoryContext.put("packageName", packageName);
        new SourceFileGenerator(
                "baseRepository.vm",
                "BaseRepository",
                processingEnvironment,
                baseRepositoryContext
        ).generateFile();

        entityTables.forEach(entityTable -> {
            String repositoryName = entityTable.getEntityName() + repositorySuffix;

            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("entityTable", entityTable);
            velocityContext.put("packageName", packageName);
            velocityContext.put("repositoryName", repositoryName);
            velocityContext.put("contractName", ContractGenerator.CONTRACT_NAME);
            velocityContext.put("stringUtils", StringUtils.class);

            new SourceFileGenerator(
                    "repositoryTemplate.vm",
                    repositoryName,
                    processingEnvironment,
                    velocityContext
            ).generateFile();
        });
    }
}
