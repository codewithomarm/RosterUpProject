package com.codewithomarm.rosterup.config;

import com.codewithomarm.rosterup.security.v1.user.model.ERole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RosterupRoleRepository roleRepository;

    @Autowired
    public DataInitializer(RosterupRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<ERole> EnumRoles = new ArrayList<>(Arrays.asList(ERole.values()));
        for (ERole roleName : EnumRoles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                RosterupRole role = new RosterupRole();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }


}
