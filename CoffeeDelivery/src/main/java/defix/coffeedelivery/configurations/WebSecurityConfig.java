package defix.coffeedelivery.configurations;

import defix.coffeedelivery.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AccountService accountService;

    @Autowired
    public WebSecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(accountService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(header -> header
                .contentSecurityPolicy(pd -> pd.policyDirectives("script-src 'self'"))
                                .frameOptions(ops -> ops.deny())
                ).csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        httpCustomizer ->
                                httpCustomizer
                                        .requestMatchers("/api/**", "/instruction", "/js/**", "/src/**", "/site_error", "/css/**", "/images/**", "/home/**", "/", "/authentication", "/authentication/**", "/remember_password")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(loginPage ->
                        loginPage
                                .usernameParameter("log-phoneNumber")
                                .passwordParameter("log-password")
                                .loginPage("/authentication")
                                .defaultSuccessUrl("/home")
                                .permitAll()
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/perform_logout")
                                        .logoutSuccessUrl("/success_logout")
                                        .permitAll()
                );//.addFilterBefore(new BasicAuthenticationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }
}
