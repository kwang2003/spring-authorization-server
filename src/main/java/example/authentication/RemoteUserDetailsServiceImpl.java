package example.authentication;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class RemoteUserDetailsServiceImpl implements UserDetailsService {
    private Map<String, UserDetails> userMap = Maps.newHashMap();
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("USER"));
        AuthUser user = new AuthUser("user1",passwordEncoder.encode("password"),authorities);
        user.setUserId(123L);
        this.userMap.put(user.getUsername(),user);
        return userMap.get(username);
    }
}
