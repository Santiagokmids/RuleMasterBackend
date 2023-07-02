package com.perficient.ruleMaster.config;

/*import com.perficient.ruleMaster.model.SecurityUser;
import com.perficient.ruleMaster.security.CustomAuthentication;
import com.perficient.ruleMaster.service.UserManagementService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


 */
//@Component
public class RuleMasterAuthenticationManager //extends DaoAuthenticationProvider
{
/*
    public RuleMasterAuthenticationManager(UserManagementService userManagementService, PasswordEncoder passwordEncoderConfiguration){
        this.setUserDetailsService(userManagementService);
        this.setPasswordEncoder(passwordEncoderConfiguration);
    }

    @Override
    public Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails){
        UsernamePasswordAuthenticationToken successAuthentication = (UsernamePasswordAuthenticationToken) super.createSuccessAuthentication(
                principal, authentication, userDetails
        );

        SecurityUser securityUser = (SecurityUser) userDetails;

        return new CustomAuthentication(successAuthentication, securityUser.ruleMasterUser().getUserId().toString());
    }

 */

}
