package vanilla.di;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Set;


@SupportedAnnotationTypes("vanilla.di.Vanilla")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class VanillaProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotatedClass : annotations) {
            try {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Processing annotations...");
                // Create a new Java file object for the modified class
                String className = annotatedClass.getSimpleName() + "Modified";
                JavaFileObject file = processingEnv.getFiler().createSourceFile(className);

                // Get the constructor for the class
                ExecutableElement constructor = null;
                for (Element element : annotatedClass.getEnclosedElements()) {
                    if (element.getKind() == ElementKind.CONSTRUCTOR) {
                        constructor = (ExecutableElement) element;
                        break;
                    }
                }

                // Create a new constructor that is a copy of the original, with the additional line of code appended
                MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                        .addModifiers(constructor.getModifiers())
                        .addCode(constructor.getSimpleName() + "() {\n")
                        .addStatement("super();")
                        .addStatement("vanilla.di.VanillaApplication.dependencyInjector.registerDependency(this.getClass(), this);");

                // Add any parameters from the original constructor
                for (VariableElement parameter : constructor.getParameters()) {
                    constructorBuilder.addParameter(TypeName.get(parameter.asType()), parameter.getSimpleName().toString());
                }

                // Build the modified class
                TypeSpec modifiedClass = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .superclass(TypeName.get(annotatedClass.asType()))
                        .addMethod(constructorBuilder.build())
                        .build();

                // Write the modified class to the file
                JavaFile javaFile = JavaFile.builder(annotatedClass.getEnclosingElement().toString(), modifiedClass)
                        .build();
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}


