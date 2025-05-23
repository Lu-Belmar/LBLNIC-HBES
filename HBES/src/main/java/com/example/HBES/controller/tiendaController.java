package com.example.HBES.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tienda")
public class tiendaController {

    @Autowired
    private tiendaRepository tiendoRepository;

    @GetMapping
    public ResponseEntity<List<tiendaModel>>
}
