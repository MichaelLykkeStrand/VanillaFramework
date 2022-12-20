package vanilla.di;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class VanillaApplication {
    public static DependencyInjector dependencyInjector;
    public VanillaApplication() throws IOException, ClassNotFoundException {
        dependencyInjector = new DependencyInjector();
        try {
            List<Class<?>> annotatedClasses = VanillaAnnotationProcessor.getClasses();
            List<Class<?>> instantiatedClasses = VanillaAnnotationProcessor.getInstantiatedClasses();
            this.resolveInstantiatedDependencies(instantiatedClasses);

            dependencyInjector.registerDependencies(annotatedClasses);
            dependencyInjector.resolveDependencies();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void resolveInstantiatedDependencies(List<Class<?>> classes) throws ClassNotFoundException, IllegalAccessException {
        for (Class cls: classes) {
            Object dependency = this.resolveInstantiatedDependency(cls);
            this.dependencyInjector.registerDependency(cls,dependency);

        }
    }


    private Object resolveInstantiatedDependency(Class cls) throws IllegalAccessException {
        // Get an array of Field objects representing the fields declared in the class
        Field[] fields = cls.getDeclaredFields();

        // Iterate over the array of fields and get the value of each field
        for (Field field : fields) {
            // Make the field accessible, in case it is private
            field.setAccessible(true);

            // Get the value of the field
            Object value = field.get(null);

            // If the value is an instance of the class, it is one of the instances you are looking for
            if (cls.isInstance(value)) {
                return value;
            }
        }

        // Repeat the above steps for each superclass of the class
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null) {
            fields = superclass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(null);
                if (superclass.isInstance(value)) {
                    return value;
                }
            }
            superclass = superclass.getSuperclass();
        }
        throw new RuntimeException("No instantiated class exists for class: "+cls.getName());
    }
}
