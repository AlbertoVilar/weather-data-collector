# 🌦️ Weather Data Collector API

> Sistema de coleta e armazenamento de dados climáticos utilizando OpenWeather API, desenvolvido como desafio técnico para vaga de Desenvolvedor Júnior.

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D?logo=swagger)](https://swagger.io/)

---
## 📋 Sobre o Projeto

Esta API REST permite:

- 🌍 Buscar dados climáticos atuais de qualquer cidade via OpenWeather API
- 💾 Armazenar histórico de consultas em PostgreSQL
- 📊 Consultar histórico de dados climáticos por cidade
- 📚 Documentação interativa via Swagger/OpenAPI
- 🐳 Ambiente completo containerizado com Docker

---

## 🛠️ Stack Tecnológica

### Backend
- ☕ **Java 21**
- 🍃 **Spring Boot 3.3.1**
- 📦 **Spring Data JPA**
- 🔗 **Spring Cloud OpenFeign** - Cliente HTTP declarativo
- 🗄️ **PostgreSQL 15** - Banco de dados relacional
- 🔧 **Lombok** - Redução de boilerplate

### Documentação & DevOps
- 📖 **Swagger/OpenAPI 2.6.0** - Documentação interativa
- 🐳 **Docker & Docker Compose** - Containerização
- 🔄 **GitHub Actions** - CI/CD automatizado
- 📦 **Maven** - Gerenciamento de dependências

### API Externa
- 🌤️ **OpenWeather API** - Fonte de dados climáticos

---

## 📁 Estrutura do Projeto

```
weather-collector/
├── 📂 src/main/java/com/gntech/weather_collector/
│   ├── 📂 api/
│   │   ├── 🎮 controller/          # Endpoints REST
│   │   ├── 📦 dto/                 # Objetos de transferência
│   │   ├── ⚠️ exceptions/          # Exceções customizadas
│   │   └── 🛡️ handlers/            # Tratamento global de erros
│   ├── 📂 business/
│   │   └── 💼 service/             # Lógica de negócio
│   └── 📂 infrastructure/
│       ├── 🔌 client/              # Feign Client (OpenWeather)
│       ├── 🗃️ entity/              # Entidades JPA
│       ├── 🔄 mapper/              # Conversores de dados
│       └── 💾 repository/          # Repositórios JPA
├── 🐳 docker-compose.yml           # Configuração Docker
├── 📦 pom.xml                      # Dependências Maven
└── 📖 README.md                    # Documentação
```

---

## 🚀 Como Executar

### 📋 Pré-requisitos

- ☕ **Java 21** ou superior
- 📦 **Maven 3.8+**
- 🐳 **Docker** e **Docker Compose**
- 🔑 **Chave de API OpenWeather** (gratuita)

---

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/albertovilar/weather-collector.git
cd weather-collector
```

---

### 2️⃣ Obtenha sua Chave OpenWeather API

1. 🌐 Acesse [https://openweathermap.org/api](https://openweathermap.org/api)
2. 📝 Crie uma conta gratuita
3. 🔑 Acesse **"API keys"** e copie sua chave
4. ⏰ **Importante:** A chave pode levar até 2 horas para ativar (plano free)

---

### 3️⃣ Configure as Variáveis de Ambiente

**Copie o arquivo de exemplo:**

```bash
# Linux/Mac
cp .env.example .env

# Windows (PowerShell)
copy .env.example .env

# Windows (CMD)
copy .env.example .env
```

**Edite o arquivo `.env` e substitua pela sua chave:**

```env
OPENWEATHER_API_KEY=sua_chave_aqui
```

---

### 4️⃣ Suba os Serviços com Docker

```bash
docker compose up --build -d
```

**Aguarde ~30 segundos para a inicialização completa.**

---

### 5️⃣ Verifique se Está Rodando

**Acompanhe os logs:**
```bash
docker compose logs -f app
```

**Quando ver `Started WeatherCollectorApplication`, está pronto!** ✅

---

### 6️⃣ Acesse a Aplicação

| Serviço | URL | Descrição |
|---------|-----|-----------|
| 🎨 **Swagger UI** | http://localhost:8080/swagger-ui/index.html | Interface interativa da API |
| 📄 **OpenAPI Docs** | http://localhost:8080/v3/api-docs | Documentação JSON |
| ❤️ **Health Check** | http://localhost:8080/actuator/health | Status da aplicação |
| 🗄️ **pgAdmin** | http://localhost:5050 | Gerenciador do banco |

**Credenciais pgAdmin:**
- 📧 Email: `admin@example.com`
- 🔒 Senha: `admin`

---

## 📡 Endpoints da API

### 🌤️ Buscar Clima Atual

Busca dados climáticos atuais de uma cidade e salva no banco.

```http
GET /weather/{city}
```

**📝 Exemplo:**
```bash
curl -X GET "http://localhost:8080/weather/rio%20de%20janeiro"
```

**✅ Resposta (200 OK):**
```json
{
  "id": 1,
  "city": "Rio de Janeiro",
  "country": "BR",
  "temperature": 28.5,
  "feelsLike": 30.2,
  "humidity": 65,
  "description": "scattered clouds",
  "windSpeed": 5.66,
  "collectedAt": "2025-11-26T16:30:00",
  "createdAt": "2025-11-26T16:30:05"
}
```

**📊 Códigos de Resposta:**

| Código | Descrição |
|--------|-----------|
| ✅ `200` | Dados obtidos com sucesso |
| ❌ `404` | Cidade não encontrada |
| 🔐 `401` | Chave de API inválida |
| ⚠️ `429` | Limite de requisições excedido |

---

### 📊 Consultar Histórico

Retorna o histórico de consultas de uma cidade (busca no banco local).

```http
GET /weather/history?city={city}
```

**📝 Exemplo:**
```bash
curl -X GET "http://localhost:8080/weather/history?city=rio%20de%20janeiro"
```

**✅ Resposta (200 OK):**
```json
[
  {
    "id": 3,
    "city": "Rio de Janeiro",
    "country": "BR",
    "temperature": 28.5,
    "feelsLike": 30.2,
    "humidity": 65,
    "description": "scattered clouds",
    "windSpeed": 5.66,
    "collectedAt": "2025-11-26T16:30:00",
    "createdAt": "2025-11-26T16:30:05"
  },
  {
    "id": 2,
    "city": "Rio de Janeiro",
    "country": "BR",
    "temperature": 26.1,
    "feelsLike": 27.8,
    "humidity": 70,
    "description": "clear sky",
    "windSpeed": 4.12,
    "collectedAt": "2025-11-26T14:15:00",
    "createdAt": "2025-11-26T14:15:03"
  }
]
```

**📊 Códigos de Resposta:**

| Código | Descrição |
|--------|-----------|
| ✅ `200` | Histórico retornado (lista pode estar vazia) |
| ❌ `400` | Parâmetro city inválido |

---

## 🐳 Comandos Docker Úteis

```bash
# 🚀 Iniciar serviços
docker compose up -d

# 📊 Ver logs em tempo real
docker compose logs -f app

# ⏸️ Parar serviços
docker compose stop

# ▶️ Iniciar serviços parados
docker compose start

# 🔄 Reiniciar um serviço específico
docker compose restart app

# 🗑️ Parar e remover containers
docker compose down

# 💣 Parar e remover TUDO (incluindo volumes)
docker compose down -v

# 📋 Ver status dos containers
docker ps
```

---

## 🗄️ Banco de Dados

### Estrutura da Tabela `weather_data`

| Campo | Tipo | Descrição |
|-------|------|-----------|
| 🆔 `id` | BIGSERIAL | Identificador único (PK) |
| 🏙️ `city` | VARCHAR(100) | Nome da cidade |
| 🏳️ `country` | VARCHAR(10) | Código do país (ISO) |
| 🌡️ `temperature` | NUMERIC(5,2) | Temperatura em °C |
| 🤒 `feels_like` | NUMERIC(5,2) | Sensação térmica em °C |
| 💧 `humidity` | INTEGER | Umidade relativa (%) |
| 📝 `description` | VARCHAR(100) | Descrição do clima |
| 💨 `wind_speed` | NUMERIC(5,2) | Velocidade do vento (m/s) |
| 📅 `collected_at` | TIMESTAMP | Data/hora da coleta |
| ⏰ `created_at` | TIMESTAMP | Data/hora do registro |

Para busca acento‑insensível, habilite no Postgres: `CREATE EXTENSION IF NOT EXISTS unaccent;`

---

## 🏗️ Arquitetura

### Camadas da Aplicação

```
🎮 Controller  → Recebe requisições HTTP
     ↓
💼 Service     → Lógica de negócio
     ↓
🔌 Client      → Chama API externa (OpenWeather)
     ↓
🔄 Converter   → Transforma objetos
     ↓
💾 Repository  → Acessa banco de dados
     ↓
🗄️ Database    → Armazena dados
```

---

## 🛡️ Tratamento de Erros

A API possui tratamento global de exceções com respostas padronizadas.

**📝 Exemplo de erro 404:**
```json
{
  "timestamp": "2025-11-26T16:30:00-03:00",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Cidade 'XYZ' não encontrada",
  "path": "/weather/XYZ"
}
```

**⚠️ Códigos de erro tratados:**

| Código | Descrição |
|--------|-----------|
| `400` | Parâmetros inválidos |
| `401` | Chave de API inválida |
| `404` | Cidade não encontrada |
| `429` | Limite de requisições excedido |
| `500` | Erro interno do servidor |
| `503` | Serviço externo indisponível |

---

## 🧪 Executar Testes

```bash
mvn test
```

Os testes utilizam banco **H2 em memória** (sem necessidade de Docker).

---

## 🔄 CI/CD

O projeto possui **pipeline automatizado no GitHub Actions** que:

- ✅ Executa build Maven
- ✅ Roda todos os testes
- ✅ Valida a compilação

---

## 💡 Decisões Técnicas

### Por que Feign Client?

Escolhi **Spring Cloud OpenFeign** porque:
- ✅ Código declarativo e limpo (apenas interface)
- ✅ Padrão em arquiteturas de microserviços
- ✅ Integração nativa com Spring Cloud
- ✅ Facilita manutenção e testes

### Por que BigDecimal?

Usei **BigDecimal** para temperatura porque:
- ✅ Precisão decimal exata (Double usa ponto flutuante binário)
- ✅ Evita erros de arredondamento
- ✅ Essencial para medições que trafegam JSON → Banco → JSON

### Por que busca case-insensitive?

Implementei busca **case-insensitive** porque:
- ✅ Melhor experiência do usuário
- ✅ "Rio de Janeiro", "rio de janeiro" e "RIO" retornam os mesmos dados

---

## 🚧 Melhorias Futuras

- [ ] 🗑️ Endpoint DELETE /weather/{id}
- [ ] ✏️ Endpoint PUT /weather/{id}
- [ ] 🔍 Filtros avançados (data, temperatura)
- [ ] 📄 Paginação no histórico
- [ ] ⚡ Cache com Redis
- [ ] 🧪 Testes de integração
- [ ] 🔐 Autenticação e autorização

---

## 📞 Contato

**José Alberto Vilar Pereira**

- 📧 Email: albertovilar1@gmail.com
- 💼 LinkedIn: [alberto-vilar-316725ab](www.linkedin.com/in/albertovilar1)
- 👨‍💻 GitHub: [@albertovilar](https://github.com/albertovilar)

---

## 📄 Licença

Este projeto foi desenvolvido como desafio técnico para processo seletivo.

---

<div align="center">

⭐ **Desenvolvido com dedicação para GnTech Exames** ⭐

</div>
