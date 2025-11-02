package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.dto.EmailNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

/**
 * Servicio para comunicación con el microservicio de notificaciones por email
 */
@Service
public class EmailNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
    
    private final RestTemplate restTemplate;
    
    @Value("${email.microservice.url:http://localhost:3000}")
    private String microserviceUrl;
    
    public EmailNotificationService() {
        this.restTemplate = new RestTemplate();
    }
    
    /**
     * Envía una notificación de confirmación de cita al paciente
     * @param emailData Datos para el correo
     */
    public void sendAppointmentConfirmation(EmailNotificationDTO emailData) {
        String url = microserviceUrl + "/api/emails/appointment-confirmation";
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<EmailNotificationDTO> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("✅ Correo de confirmación enviado exitosamente a: {}", emailData.getEmail());
            } else {
                logger.warn("⚠️ Respuesta inesperada del microservicio: {}", response.getStatusCode());
            }
            
        } catch (RestClientException e) {
            logger.error("❌ Error al enviar correo de confirmación a {}: {}", 
                        emailData.getEmail(), e.getMessage());
            // No lanzamos excepción para no interrumpir el flujo principal
        }
    }
    
    /**
     * Envía una notificación de diagnóstico al paciente
     * @param emailData Datos para el correo
     */
    public void sendDiagnosisNotification(EmailNotificationDTO emailData) {
        String url = microserviceUrl + "/api/emails/diagnosis-notification";
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<EmailNotificationDTO> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("✅ Notificación de diagnóstico enviada exitosamente a: {}", emailData.getEmail());
            } else {
                logger.warn("⚠️ Respuesta inesperada del microservicio: {}", response.getStatusCode());
            }
            
        } catch (RestClientException e) {
            logger.error("❌ Error al enviar notificación de diagnóstico a {}: {}", 
                        emailData.getEmail(), e.getMessage());
            // No lanzamos excepción para no interrumpir el flujo principal
        }
    }
    
    /**
     * Verifica si el microservicio de email está disponible
     * @return true si está disponible, false en caso contrario
     */
    public boolean isServiceAvailable() {
        String url = microserviceUrl + "/api/emails/health";
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException e) {
            logger.warn("⚠️ Microservicio de email no disponible: {}", e.getMessage());
            return false;
        }
    }
}
