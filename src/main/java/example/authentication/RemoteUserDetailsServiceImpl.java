package example.authentication;

import com.google.common.collect.Maps;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoteUserDetailsServiceImpl implements UserDetailsService {
    private Map<String, UserDetails> userMap = Maps.newHashMap();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();
        this.userMap.put(user.getUsername(),user);
        return userMap.get(username);
    }
}
