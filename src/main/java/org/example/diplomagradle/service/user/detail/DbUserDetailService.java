package org.example.diplomagradle.service.user.detail;

import org.example.diplomagradle.datamodel.entity.users.User;
import org.example.diplomagradle.datamodel.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//имплементация UserDetailService
public class DbUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public DbUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found: "+username));

        return new AppUserDetails(user);
    }

}
