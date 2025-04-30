package com.example.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper = new ObjectMapper(); // O inyecta un ObjectMapper bean
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        //log.warn("Fallo de autenticación para {}: {}", request.getRequestURI(), authException.getMessage());
        // Opcionalmente, puedes inspeccionar 'authException' para ver si es específicamente
        // un error de token expirado si necesitas lógica diferente para otros fallos de autenticación.
        // Por ejemplo: if (authException instanceof JwtExpiredTokenException) { ... }
        // Pero cuidado, la excepción específica depende de la implementación de tu servidor de recursos.

        // Establecer la respuesta como Service Unavailable (503)
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // O el tipo de contenido que prefieras

        // Crear un cuerpo de respuesta personalizado (opcional)
        Map<String, String> errorResponse = Map.of(
                "error", "Servicio no disponible",
                "message", "Problema de autenticación o servicio temporalmente inaccesible."
                // "detail", authException.getMessage() // Podrías incluir el mensaje original si es seguro hacerlo
        );

        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
        response.getOutputStream().flush();
    }
}
