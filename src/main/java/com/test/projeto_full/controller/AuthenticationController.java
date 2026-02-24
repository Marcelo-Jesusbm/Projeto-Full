package com.test.projeto_full.controller;

import com.test.projeto_full.domain.dtos.LoginDTO;
import com.test.projeto_full.domain.dtos.JwtResponse;
import com.test.projeto_full.domain.entity.Pessoa;
import com.test.projeto_full.exceptions.ObjectNotFoundException;
import com.test.projeto_full.repositories.PessoaRepository;
import com.test.projeto_full.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDTO) {
        // Busca o usuário pelo CPF
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(loginDTO.getCpf());

        if (!pessoaOptional.isPresent()) {
            throw new ObjectNotFoundException("Usuário não encontrado com o CPF: " + loginDTO.getCpf());
        }

        Pessoa pessoa = pessoaOptional.get();

        // Valida a senha
        if (!encoder.matches(loginDTO.getSenha(), pessoa.getSenha())) {
            throw new ObjectNotFoundException("CPF ou senha inválidos!");
        }

        // Gera o token JWT
        String token = tokenProvider.generateToken(pessoa.getCpf());

        return ResponseEntity.ok(new JwtResponse(token, 3600000L)); // 1 hora em ms
    }

    /**
     * Endpoint para validar e obter informações do token
     * @param token - JWT token
     * @return ResponseEntity com informações do token
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            return ResponseEntity.ok(new JwtResponse("Token válido para: " + username, 3600000L));
        }
        return ResponseEntity.status(401).body("Token inválido ou expirado");
    }
}

