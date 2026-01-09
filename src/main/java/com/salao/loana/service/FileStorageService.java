package com.salao.loana.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${upload.dir}")
    private String uploadDir;

    public void criarPastaCliente(String nomeCliente) {
        try {
            String nomeLimpo = nomeCliente.trim().replaceAll("\\s+", "_");
            Path caminho = Paths.get(uploadDir).resolve(nomeLimpo);

            if (!Files.exists(caminho)) {
                Files.createDirectories(caminho);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pasta no disco", e);
        }
    }
}