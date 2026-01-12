# 🌸 Loana Estética - Controle de Progresso

Sistema desenvolvido para gestão de clientes e acompanhamento visual de procedimentos estéticos.

## 🚀 Funcionalidades Atuais
- [x] Cadastro de clientes com dados completos (Nome, Idade, Sexo, Telefone, E-mail).
- [x] Criação automática de pastas físicas no servidor para armazenamento de fotos.
- [x] Listagem de clientes em formato JSON para integração mobile.

## 🛠️ Tecnologias Utilizadas
- **Java 21** (LTS)
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database** (Banco de dados em memória para desenvolvimento)

## 📍 Endpoints para Teste
- **Cadastrar:** `http://localhost:8080/api/clientes/cadastrar?nome=Exemplo&idade=25&sexo=F&telefone=123&email=ana@email.com`
- **Listar:** `http://localhost:8080/api/clientes/listar`

## 📝 Atualização (Ficha de Anamnese)
- [x] Migração de GET para POST no cadastro para maior segurança.
- [x] Implementação de campos de texto longo para rotinas e alergias.
- [x] Integração com Swagger UI para testes de interface facilitados.