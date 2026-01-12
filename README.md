# 🌸 Loana Estética - Backend

Sistema de gestão e acompanhamento visual para procedimentos estéticos. Este projeto automatiza o controle de fichas de anamnese e organiza o histórico fotográfico de clientes diretamente no servidor.

## 📍 Interface de Testes (Swagger)
A forma mais fácil de testar as funcionalidades é através da interface interativa:
👉 `http://localhost:8080/swagger-ui/index.html`

---

## 🚀 Funcionalidades Principais

### 📋 Gestão de Clientes & Anamnese
- **Cadastro Completo:** Coleta de dados básicos e fichas discursivas (rotina, alergias).
- **Segurança de Dados:** Uso de `@PostMapping` para proteção de informações sensíveis e textos longos. 
- **Listagem Dinâmica:** Endpoint pronto para integração com aplicativos mobile (JSON). 

### 📸 Inteligência de Armazenamento
- **Isolamento de Diretórios:** Criação automática de pastas no padrão `{ID}_{Nome}` para evitar conflito entre clientes homônimos. 
- **Organização Cronológica:** Fotos nomeadas com `categoria_timestamp`, garantindo que o histórico de evolução nunca seja sobrescrito. 
- **Upload Binário:** Suporte a arquivos reais (MultipartFile) via API.

---

## 🛠️ Tecnologias
- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 3
- **Persistência:** Spring Data JPA / H2 Database
- **Documentação:** OpenAPI 3 (Swagger UI)

---

## 🧪 Exemplo de Payload (Cadastro)
Ao utilizar o endpoint `/api/clientes/cadastrar`, envie o seguinte JSON:

```json
{
  "nome": "Mariana Souza",
  "idade": 28,
  "sexo": "Feminino",
  "telefone": "41888888888",
  "email": "mariana@email.com",
  "rotinaDiaria": "Descrição da rotina...",
  "alergiasEObservacoes": "Alergia a iodo"
}