package mg.edu.eni.mobilemoney.service;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.model.User;
import mg.edu.eni.mobilemoney.security.MyUserDetails;
import mg.edu.eni.mobilemoney.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User theUser = userService.searchByUsername(username);
        return new MyUserDetails(theUser);
    }
}
