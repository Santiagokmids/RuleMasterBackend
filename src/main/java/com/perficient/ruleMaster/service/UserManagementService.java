package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.model.SecurityUser;
import com.perficient.ruleMaster.repository.UserRepository;
import lombok.AllArgsConstructor;
/*import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

 */
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserManagementService //implements UserDetailsService
{
    private final UserRepository userManagementRepository;

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userManagementRepository.findByEmail(username)
                .map(SecurityUser::new).orElseThrow(() -> new RuntimeException("Username "+username+" not found"));
    }

    */
}
