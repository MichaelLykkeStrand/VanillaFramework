package vanilla.di;

import vanilla.di.args.VanillaLaunchArgs;
import vanilla.di.processors.VanillaAnnotationProcessor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class VanillaApplication {
    public static DependencyInjector dependencyInjector;

    public VanillaApplication(Class cls){
        new VanillaApplication(cls,new VanillaLaunchArgs(""));
    }
    public VanillaApplication(Class cls, VanillaLaunchArgs vanillaLaunchArgs) {
        dependencyInjector = new DependencyInjector();
        try {
            String pkg = cls.getPackageName();
            List<Class<?>> annotatedClasses = VanillaAnnotationProcessor.getClasses(pkg, vanillaLaunchArgs.excludeClassesContaining());

            dependencyInjector.registerDependencies(annotatedClasses);
            dependencyInjector.resolveDependencies();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
