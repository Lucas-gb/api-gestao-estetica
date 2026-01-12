package com.salao.loana.controller;

import com.salao.loana.model.Cliente;
import com.salao.loana.repository.ClienteRepository;
import com.salao.loana.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// ADICIONADOS PARA FORCAR O BOTAO DE UPLOAD DE FOTO NO SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private FileStorageService fileService;

    // 1. LISTAR CLIENTES (O App do celular vai usar isso)
    @GetMapping("/listar")
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    // 2. CADASTRAR COM MAIS DADOS
    @PostMapping("/cadastrar")
    public String cadastrar(@RequestBody Cliente cliente) {
        // 1. Salva todos os dados (nome, rotina, etc) no banco
        repository.save(cliente);

        // 2. Cria a pasta física usando o nome do cliente
        fileService.criarPastaCliente(cliente.getId(), cliente.getNome());

        return "Cliente " + cliente.getNome() + " cadastrado com ID" + cliente.getId();
    }

    // 3. UPLOAD DE FOTO POR CATEGORIA
    // @PostMapping("/{id}/upload-foto")
    // public String uploadFoto(
    // @PathVariable Long id,
    // @RequestPart("foto") org.springframework.web.multipart.MultipartFile foto, //
    // Mudamos de @RequestParam para @RequestPart
    // @RequestParam("categoria") String categoria) {

    // Cliente cliente = repository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Cliente não encontrada"));

    // return fileService.salvarFoto(cliente.getNome(), foto, categoria);
    // }
    @Operation(summary = "Fazer upload de foto da cliente (Rosto, Cintura, etc)")
    @PostMapping(value = "/{id}/upload-foto", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFoto(
            @PathVariable Long id,

            // Avisando ao Swagger que o formato é BINÁRIO
            @Parameter(content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))) @RequestPart("foto") MultipartFile foto,

            @RequestParam("categoria") String categoria) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrada"));

        return fileService.salvarFoto(cliente.getId(), cliente.getNome(), foto, categoria);
    }

}
