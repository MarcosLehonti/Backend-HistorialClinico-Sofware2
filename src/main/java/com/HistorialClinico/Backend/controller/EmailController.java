package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/correos")
public class EmailController {

    @Autowired
    private EmailService emailService;

//    @PostMapping("/enviar")
//    public ResponseEntity<String> enviarCorreo(@RequestParam String destinatario,
//                                               @RequestParam String asunto,
//                                               @RequestParam String contenido) {
//        emailService.enviarCorreo(destinatario, asunto, contenido);
//        return ResponseEntity.ok("Correo enviado exitosamente");
//    }
}
