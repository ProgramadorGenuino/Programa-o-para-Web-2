package com.example.loja.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetaisConfig userDetaisConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests() //define com as requisições HTTP devem ser tratadas com relação à segurança.
                .antMatchers("/webjars/**") //definir autorizações para o caminho especificado por parâmetro. (Biblioteca do Bootstrap).
                .permitAll() //definir todas as autorizações para tal caminho (/webjars/**).
                .antMatchers("/cadastrar/**").permitAll()
                .antMatchers("/produto/**").hasAnyRole("ADMIN") //Limitar permissão apenas para usuário com definições de regras ADMIN.
                .antMatchers("/clientepf/form").permitAll() //Liberar permissão para geral.
                .antMatchers("/clientepf/**").hasAnyRole("ADMIN") //Limitar permissão apenas para usuário com definições de regras de ADMIN.
                .anyRequest() //define que a configuração é válida para qualquer requisição.
                .authenticated() //define que o usuário precisa estar autenticado.
                .and()
                .formLogin() //define que a autenticação pode ser feita via formulário de login.
                .loginPage("/login") // passamos como parâmetro a URL para acesso à página de login que criamos
                .permitAll() //define que essa página pode ser acessada por todos, independentemente do usuário estar autenticado ou não.
                .and()
                .logout() //para configurar o botão "Sair" no navbar.
                .permitAll();//permitir que logout seja feito pelo botão "Sair".
    }

    @Autowired
    public void configureUserDetails(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetaisConfig).passwordEncoder(new BCryptPasswordEncoder());
                
//                .inMemoryAuthentication()
//                .withUser("cliente").password(passwordEncoder().encode("123")).roles("CLIENTE")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("donodabagaca")).roles("ADMIN");
    }

     /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o controle dessa instância como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar disso, ele já terá o que precisa configurado.
     * @return 
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
}
