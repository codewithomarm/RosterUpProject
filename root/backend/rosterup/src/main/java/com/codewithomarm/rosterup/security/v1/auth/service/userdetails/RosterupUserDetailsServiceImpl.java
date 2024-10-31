package com.codewithomarm.rosterup.security.v1.auth.service.userdetails;

import com.codewithomarm.rosterup.security.v1.user.model.RosterupUser;
import com.codewithomarm.rosterup.security.v1.user.repository.RosterupUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RosterupUserDetailsServiceImpl implements UserDetailsService {

    private final RosterupUserRepository rosterupUserRepository;

    public RosterupUserDetailsServiceImpl(RosterupUserRepository rosterupUserRepository) {
        this.rosterupUserRepository = rosterupUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<RosterupUser> user = rosterupUserRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        RosterupUser rosterupUser = user.get();

        return new RosterupUserDetailsImpl(rosterupUser);
    }
}
