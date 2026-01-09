package com.salao.loana.controller;

import com.salao.loana.model.Cliente;
import com.salao.loana.repository.ClienteRepository;
import com.salao.loana.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

        //2. Cria a pasta física usando o nome do cliente
        fileService.criarPastaCliente(cliente.getNome());

        return "Ficha de " + cliente.getNome() + " salva com sucesso!";
    }
}