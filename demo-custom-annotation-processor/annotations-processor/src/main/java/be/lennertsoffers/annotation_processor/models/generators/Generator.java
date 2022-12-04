package be.lennertsoffers.annotation_processor.models.generators;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;

/**
 * Base class for all classes that generate files using Apache velocity
 */
public abstract class Generator {
    private final String templateName;
    private final String outputFileName;
    private final ProcessingEnvironment processingEnv;
    private final VelocityContext velocityContext;

    public Generator(String templateName, String outputFileName, ProcessingEnvironment processingEnv, VelocityContext velocityContext) {
        this.templateName = templateName;
        this.outputFileName = outputFileName;
        this.processingEnv = processingEnv;
        this.velocityContext = velocityContext;
    }

    public ProcessingEnvironment getProcessingEnv() {
        return this.processingEnv;
    }

    public String getOutputFileName() {
        return this.outputFileName;
    }

    /**
     * Generates a file using the properties of this instance
     */
    public void generateFile() {
        try {
            // The properties object that will be used to configure our VelocityEngine
            Properties properties = new Properties();
            // Get the url from the properties file containing the properties that configure our VelocityEngine
            URL url = this.getClass().getClassLoader().getResource("velocity.properties");
            // Throw an exception if the properties file cannot be found
            if (url == null) throw new FileNotFoundException("velocity.properties was not found in resources folder");
            // Load the values of this file into the properties object
            properties.load(url.openStream());

            // Create a new engine using the properties
            VelocityEngine velocityEngine = new VelocityEngine(properties);
            velocityEngine.init();

            // Get the template file
            Template template = velocityEngine.getTemplate(this.templateName);

            // Generate the output file
            FileObject fileObject = this.getFileObject();
            Writer writer = fileObject.openWriter();
            template.merge(this.velocityContext, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public abstract FileObject getFileObject() throws IOException;
}
