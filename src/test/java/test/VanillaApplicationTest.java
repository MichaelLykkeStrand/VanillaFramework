package test;

import org.junit.jupiter.api.Test;
import vanilla.di.VanillaApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class VanillaApplicationTest {
    @Test
    void should_start_new_vanilla_application(){

        // Arrange & Act
        VanillaApplication vanillaApplication = new VanillaApplication(VanillaApplicationTest.class);

        // Assert
        assertNotNull(VanillaApplication.dependencyInjector);
    }
}
