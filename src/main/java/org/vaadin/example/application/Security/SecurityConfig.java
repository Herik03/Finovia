package org.vaadin.example.application.Security;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.vaadin.example.application.views.LoginView;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        super.configure(http);
        setLoginView(http, LoginView.class);

        http.formLogin().defaultSuccessUrl("/uebersicht", true);
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        LoggerFactory.getLogger(SecurityConfig.class)
                .warn("Noch nicht final");
        var user= User.withUsername("user").password("{noop}user").roles("USER").build();
        var admin= User.withUsername("admin").password("{noop}admin").roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user,admin);
    }
}
