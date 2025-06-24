package com.jwtsecurity.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Run the data initializers in sequence to populate DB tables
@Component
public class MasterInitializer implements CommandLineRunner {

    private final RoleDataInitializer roleDataInitializer;
    private final UserDataInitializer userDataInitializer;

    public MasterInitializer(RoleDataInitializer firstInitializer, UserDataInitializer secondInitializer) {
        this.roleDataInitializer = firstInitializer;
        this.userDataInitializer = secondInitializer;
    }

    @Override
    public void run(String... args) {
        // Initialize the data in sequence
        roleDataInitializer.init();
        userDataInitializer.init();
    }
}