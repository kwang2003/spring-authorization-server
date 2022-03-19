package example.authentication;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoteUserDetailsServiceImpl implements UserDetailsService, InitializingBean {
    private Map<String, UserDetails> userMap = Maps.newHashMap();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.get(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();
        this.userMap.put(user.getUsername(),user);
    }
}
