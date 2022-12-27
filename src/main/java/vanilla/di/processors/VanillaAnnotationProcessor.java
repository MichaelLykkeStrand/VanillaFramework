package vanilla.di.processors;

import com.google.common.reflect.ClassPath;
import vanilla.di.annotations.Vanilla;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VanillaAnnotationProcessor {
    public static List<Class<?>> getClasses() throws IOException {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());

        // Get all the classes on the classpath
        for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
            if(classInfo.getName().toLowerCase().contains("mixin") || !classInfo.getPackageName().toLowerCase().contains("net.fabricmc.unbreakable")) continue;
            // Load the class
            Class<?> cls;
            try {
                 cls = Class.forName(classInfo.getName());
            }catch (Throwable throwable){
                continue;
            }

            // Check if the class is annotated with the vanilla.di.annotations.Vanilla annotation
            if (cls.isAnnotationPresent(Vanilla.class)) {
                System.out.println("Vanilla Found Class: "+cls.getName());
                annotatedClasses.add(cls);
            }
        }
        return annotatedClasses;
    }

    public static List<Class<?>> getInstantiatedClasses() throws IOException {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());

        // Get all the classes on the classpath
        for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
            if(!classInfo.getName().toLowerCase().contains("mixin") && !classInfo.getPackageName().toLowerCase().contains("net.fabricmc.unbreakable")) continue; //TODO make this generic
            // Load the class
            Class<?> cls;
            try {
                cls = Class.forName(classInfo.getName());
            }catch (Throwable throwable){
                continue;
            }

            // Check if the class is annotated with the vanilla.di.annotations.Vanilla annotation
            if (cls.isAnnotationPresent(Vanilla.class)) {
                System.out.println("Vanilla Found Class: "+cls.getName());
                annotatedClasses.add(cls);
            }
        }
        return annotatedClasses;
    }
}
