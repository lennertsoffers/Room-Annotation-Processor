package be.lennertsoffers.annotation_processor.models.generators;

import org.apache.velocity.VelocityContext;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;

/**
 * A generator specifically use to generate Java source files<br/>
 */
public final class SourceFileGenerator extends Generator {
    public SourceFileGenerator(String templateName, String outputFileName, ProcessingEnvironment processingEnv, VelocityContext velocityContext) {
        super(templateName, outputFileName, processingEnv, velocityContext);
    }

    @Override
    public FileObject getFileObject() throws IOException {
        return this.getProcessingEnv().getFiler().createSourceFile(this.getOutputFileName());
    }
}
