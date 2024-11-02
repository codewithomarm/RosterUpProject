package com.codewithomarm.rosterup.config;

import com.codewithomarm.rosterup.security.v1.user.model.ERole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupRole;
import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupRoleRepository;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RosterupRoleRepository roleRepository;
    private final RosterupUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RosterupUserRepository rosterupUserRepository;

    @Autowired
    public DataInitializer(RosterupRoleRepository roleRepository, RosterupUserRepository userRepository,
                           PasswordEncoder passwordEncoder, RosterupUserRepository rosterupUserRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rosterupUserRepository = rosterupUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create all roles if they do not exist
        List<ERole> EnumRoles = new ArrayList<>(Arrays.asList(ERole.values()));
        for (ERole roleName : EnumRoles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                RosterupRole role = new RosterupRole();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }

        // Create developer user if it does not exist
        if (!userRepository.existsByUsername("omar.montoya")) {
            RosterupUser user = new RosterupUser();
            user.setUsername("omar.montoya");
            user.setPassword(passwordEncoder.encode("Bred0420@@"));
            user.setEmail("codewithomarm@gmail.com");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Set<RosterupRole> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.DEV).orElseThrow());
            roles.add(roleRepository.findByName(ERole.ADMIN).orElseThrow());
            roles.add(roleRepository.findByName(ERole.MANAGER).orElseThrow());
            roles.add(roleRepository.findByName(ERole.SUP).orElseThrow());
            roles.add(roleRepository.findByName(ERole.USER).orElseThrow());

            user.setRosterupRoles(roles);

            rosterupUserRepository.save(user);
        }
    }
}
