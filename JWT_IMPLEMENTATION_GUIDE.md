# Implementação de JWT - Guia Completo

## O que foi criado para usar JWT

Para implementar JWT (JSON Web Tokens) no seu projeto Spring Boot, foram criados os seguintes componentes:

### 1. **JwtTokenProvider** (`security/JwtTokenProvider.java`)
- Responsável por **gerar**, **validar** e **extrair informações** dos tokens JWT
- Principais métodos:
  - `generateToken(String username)` - Gera um novo token
  - `validateToken(String token)` - Valida se o token é válido
  - `getUsernameFromToken(String token)` - Extrai o usuário do token
  - `extractTokenFromHeader(String authorizationHeader)` - Extrai o token do header

### 2. **JwtAuthenticationFilter** (`security/JwtAuthenticationFilter.java`)
- Filtro que intercepta requisições HTTP
- Valida o token JWT em cada requisição
- Define a autenticação no contexto de segurança do Spring
- Procura o token no header `Authorization: Bearer <token>`

### 3. **LoginDTO** (`domain/dtos/LoginDTO.java`)
- DTO (Data Transfer Object) para receber as credenciais de login
- Contém: `cpf` e `senha`

### 4. **JwtResponse** (`domain/dtos/JwtResponse.java`)
- DTO para retornar o token JWT gerado
- Contém: `accessToken`, `tokenType` e `expiresIn`

### 5. **AuthenticationController** (`controller/AuthenticationController.java`)
- Controller REST com endpoints de autenticação
- Endpoints:
  - `POST /auth/login` - Realiza o login e retorna o JWT token
  - `GET /auth/validate` - Valida um token JWT

### 6. **SecurityConfig** (Atualizado)
- Adicionado:
  - Bean `BCryptPasswordEncoder` - Para criptografar senhas
  - Bean `JwtAuthenticationFilter` - Para injetar o filtro JWT
  - Permitir acesso ao `/auth/**` sem autenticação
  - Adicionar o filtro JWT à cadeia de segurança

### 7. **application.properties** (Atualizado)
- Adicionadas configurações do JWT:
  ```properties
  jwt.secret=mySecretKeyForJWT
  jwt.expiration=3600000
  ```

## Como usar

### 1. **Fazer Login**
```bash
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "cpf": "12345678901",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

### 2. **Usar o Token em Requisições**
Adicione o token no header `Authorization`:
```bash
GET http://localhost:8080/tecnicos
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### 3. **Validar Token**
```bash
GET http://localhost:8080/auth/validate?token=eyJhbGciOiJIUzUxMiJ9...
```

## Fluxo de Autenticação

1. **Usuário faz login** com CPF e senha
2. **AuthenticationController** valida as credenciais
3. **JwtTokenProvider** gera um token JWT
4. **Token é retornado** para o cliente
5. **Cliente envia o token** em cada requisição no header `Authorization: Bearer <token>`
6. **JwtAuthenticationFilter** intercepta a requisição
7. **Valida o token** e configura a autenticação no Spring Security
8. **Requisição é processada** com o usuário autenticado

## Configurações Importantes

### Alterar Secret Key (RECOMENDADO PARA PRODUÇÃO)
No arquivo `application.properties`:
```properties
jwt.secret=sua-chave-super-secreta-muito-longa-e-complexa
jwt.expiration=3600000  # Tempo em milissegundos (3600000 = 1 hora)
```

### Para diferentes ambientes
No `application-dev.properties`:
```properties
jwt.secret=chave-dev
jwt.expiration=3600000
```

No `application-prod.properties`:
```properties
jwt.secret=chave-prod-super-secreta
jwt.expiration=1800000  # 30 minutos
```

## Notas de Segurança

1. **Nunca hardcode a secret key** em produção
2. **Use variáveis de ambiente** para armazenar a chave secreta
3. **Mantenha a chave secreta em segredo** - compartilhe apenas com servidores de confiança
4. **Use HTTPS sempre** quando trabalhar com JWT
5. **Defina um tempo de expiração apropriado** para seus tokens

## Dependências Necessárias

A dependência JWT já está no seu `pom.xml`:
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>
```

Se precisar adicionar manualmente, execute:
```bash
mvn clean install
```

## Próximas Melhorias (Opcional)

1. **Refresh Token** - Implementar um mecanismo de refresh token
2. **Roles/Authorities** - Adicionar permissões (TÉCNICO, CLIENTE, ADMIN)
3. **Token Blacklist** - Implementar logout (adicionar tokens à blacklist)
4. **Rate Limiting** - Limitar tentativas de login
5. **Auditoria** - Registrar tentativas de login bem-sucedidas e falhadas

