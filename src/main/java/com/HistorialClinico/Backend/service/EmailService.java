package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Cita;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender; // Asegúrate de que esta línea esté present

    public void enviarCorreoRecordatorio(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(destinatario);
        mailMessage.setSubject(asunto);
        mailMessage.setText(mensaje);
        mailSender.send(mailMessage);
    }


    public void enviarRecordatorioCita(String destinatario, Cita cita) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recordatorio de Cita Médica");
        mensaje.setText("Estimado(a) " + cita.getUsuario().getUsername() +
                ", este es un recordatorio de su cita con el doctor " +
                cita.getMedico().getUsername() + " en la especialidad de " +
                cita.getEspecialidad().getNombre() + ". Fecha y hora: " +
                cita.getHorario());

        javaMailSender.send(mensaje);
    }
}

