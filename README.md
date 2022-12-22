
# # Vanilla - A Lightweight IOC Framework
Vanilla is a lightweight and minimalistic IOC (Inversion of Control) framework designed to work seamlessly with modding frameworks such as Fabric.

**#Features**
 1. Easy to use and integrate into your project
 2. Support for runtime changes to the classpath through "plugin" jar files (available in future updates)
 3. Annotation based with @Vanilla to instantiate classes and @VanillaAutoInject to inject fields at runtime
 4. No need for getters, setters, or constructor injection

**#Usage**
To use Vanilla, simply annotate your classes with @Vanilla and the framework will handle the instantiation and injection of fields marked with @VanillaAutoInject.
Example
```java
    @Vanilla
    public class ExampleClass {
      @VanillaAutoInject
      private Dependency dependency;

      // Other code here
    }
```
It is possible to inject dependencies into Mixins as well. Classes annotated with both @Mixin and @Vanilla will not be accessible in the IOC registry. However it is still possible to inject dependencies from the IOC registry into your Mixin attributes.
Example 2
```java
    @Vanilla
    @Mixin(MinecraftClient.class)
    public class ExampleMixinClass {
      @VanillaAutoInject
      private Dependency dependency; // Will work!
      @VanillaAutoInject
      private ExampleMixinClass myMixin // Won't work!

      // Other code here
    }
```

Vanilla is a great choice for those looking for a simple yet powerful IOC solution for their modding projects. 
Give it a try and see how it can streamline your development process.


