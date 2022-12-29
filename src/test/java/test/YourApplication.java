package test;

import org.junit.jupiter.api.Test;
import vanilla.di.VanillaApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class YourApplication {

    public static void main(String[] args){
        VanillaApplication vanillaApplication = new VanillaApplication(YourApplication.class);
    }
}
