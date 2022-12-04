package be.lennertsoffers.annotation_processor.models.generators;

import org.apache.velocity.VelocityContext;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

import be.lennertsoffers.annotation_processor.models.EntityTable;

/**
 * Defines a generator that will generate the Contract which stores info about all entities and their tables<br/>
 * The Contract will also contain create and drop tables SQL statements<br/>
 */
public final class ContractGenerator {
    public final static String CONTRACT_NAME = "Contract";

    public static void generate(List<EntityTable> entityTables, ProcessingEnvironment processingEnvironment, String packageName) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("entityTables", entityTables);
        velocityContext.put("packageName", packageName);

        new SourceFileGenerator(
                "contractTemplate.vm",
                CONTRACT_NAME,
                processingEnvironment,
                velocityContext
        ).generateFile();
    }
}
