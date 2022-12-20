package vanilla.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyInjector {
    private final Map<Class, Object> dependencies;

    public DependencyInjector() {
        dependencies = new HashMap<>();
    }

    public void registerDependency(Class cls){
        try {
            if(dependencies.containsKey(cls)) return;
            Object dependency = cls.newInstance();
            dependencies.put(cls, dependency);
            System.out.println("vanilla.di.Vanilla Added new Instance of: "+dependency.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error registering dependency " + cls.getName(), e);
        }
    }

    public void registerDependency(Class cls, Object dependency){
        try {
            if(dependencies.containsKey(cls)) return;
            dependencies.put(cls, dependency);
            System.out.println("vanilla.di.Vanilla Added new Instance of: "+dependency.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error registering dependency " + cls.getName(), e);
        }
    }

    public void registerDependencies(List<Class<?>> classes) {
        classes.forEach(
                        cls -> {
                            registerDependency(cls);
                        });
    }

    public <T> T getDependency(Class<T> interfaceType) {
        System.out.println("vanilla.di.Vanilla getting Dependency for Interface Type: "+interfaceType);
        Object dependency = dependencies.get(interfaceType);
        if (dependency == null) {
            throw new RuntimeException("Dependency not found: " + interfaceType.getName());
        }
        return interfaceType.cast(dependency);
    }

    public void injectDependencies(Object object) {
        System.out.println("vanilla.di.Vanilla Injecting Dependency on:"+ object.getClass());
        Class<?> cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        System.out.println("vanilla.di.Vanilla Found Fields: "+fields);
        for (Field field: fields) {
            for (Annotation a: field.getAnnotations()) {
                System.out.println("vanilla.di.Vanilla Found Annotation: "+a.annotationType());
            }

            if(field.getAnnotation(VanillaAutoInject.class) == null) continue;
            Class<?> fieldType = field.getType();
            System.out.println("vanilla.di.Vanilla Injecting Dependency of Type:"+ fieldType);
            Object dependency = getDependency(fieldType);
            field.setAccessible(true);
            try {
                field.set(object, dependency);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error injecting dependency into field " + field.getName(), e);
            }
        }
    }

    public void resolveDependencies() {
        System.out.println("vanilla.di.Vanilla Resolving dependencies");
        for (Object object:dependencies.values()) {
            injectDependencies(object);
        }
    }
}


