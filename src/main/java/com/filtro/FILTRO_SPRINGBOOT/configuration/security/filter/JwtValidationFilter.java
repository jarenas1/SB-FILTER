package com.filtro.FILTRO_SPRINGBOOT.configuration.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.filtro.FILTRO_SPRINGBOOT.configuration.security.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.filtro.FILTRO_SPRINGBOOT.configuration.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager); //Pasamos a la clase padre
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //LLAMAMOS EL HEADER LLAMADO AUTHORIZATION POR MEDIO DE LA CONSTANTE CREADA
        String header = request.getHeader(HEADER_AUTHORIZATION);

        //VERIFIVAMOS QUE SI EL TOKEN ES NULL O NO CONTIENE EL BEARER "
        if (header == null || !header.startsWith(PREFIX_TOKEN)){ //PREFIX CONTIENE EL "BAREER "
            chain.doFilter(request,response); //continuamos con los demas filtros
            return; //si no contiene nos sacara
        }
        String token = header.replace(PREFIX_TOKEN, ""); //eliminamos bareer del token para tener solo el token

        //COMENZAMOS A VALIDAR
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload(); //verificamos con la secretKey creada en el otro archivo
            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities"); //REAEMOS LOS ROLES, RECORDAR QUE LOS SETEAMOS EN LA CREACION DEL TOKEN

            //pasamos los roles del token a greatedAuthorities para poderlo pasar a la auth
            Collection<? extends GrantedAuthority> authorities =Arrays.asList(
                    new ObjectMapper()
            //creamos una clase y con esta creamos un mixin para acoplar el constructor de la clase creada en la otra clase
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            //verificamos la auth                                                            pasamos         username/pasword/roles
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //creamos la autentucacion
            chain.doFilter(request,response); //continuamos con los demas filtros
        }catch (JwtException e){
            Map<String,String> body = new HashMap<>();
            body.put("Error", e.getMessage());
            body.put("message", "JWT INVALIDO");

            //transformamos a JSON
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);

        }
    }
}
