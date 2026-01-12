package com.salao.loana.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    // Transformar o arquivo do celular em um arquivo no Windows
    public String salvarFoto(String nomeCliente, MultipartFile arquivo, String categoria) {
        try {
            String nomeLimpo = nomeCliente.trim().replaceAll("\\s+", "_");
            Path caminhoPasta = Paths.get(uploadDir).resolve(nomeLimpo);

            // Criamos um nome único: categoria + data_hora + nome original
            String nomeArquivo = categoria.toLowerCase() + "_" + System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
            Path caminhoCompleto = caminhoPasta.resolve(nomeArquivo);

            // Files.copy pega os "bits" da foto e joga para dentro da pasta
            Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            return "Sucesso! Foto de " + categoria + " salva em " + nomeLimpo;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo de foto", e);
        }
    }
}