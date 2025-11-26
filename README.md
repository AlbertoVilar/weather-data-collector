# 🌦️ Weather Data Collector API

Sistema de coleta e armazenamento de dados climáticos utilizando OpenWeather API, desenvolvido como desafio técnico para vaga de Desenvolvedor Júnior.

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D?logo=swagger)](https://swagger.io/)

Coleta dados climáticos via OpenWeather, persiste em PostgreSQL e expõe uma API REST documentada com Swagger. Inclui ambiente Docker (API + Postgres + pgAdmin) para execução reprodutível.

## Objetivo
- Atender à avaliação técnica: extração de dados via API, armazenamento em banco relacional, acesso remoto via API REST, conteinerização com Docker e organização clara no Git.

## Stack
- Java 21, Spring Boot 3.3.1
- Springdoc OpenAPI 2.6.0 (Swagger UI)
- PostgreSQL 15 (alpine), pgAdmin
- Maven, Docker, Docker Compose

## Funcionalidades
- GET na OpenWeather com `apiKey` e parâmetros dinâmicos
- Persistência dos dados coletados em `weather_data` (PostgreSQL)
- API REST para consulta do dado atual e histórico por cidade
- Documentação automática via Swagger UI

## Executar com Docker (recomendado)
### Pré‑requisitos
- Docker e Docker Compose instalados
- Chave de API OpenWeather (gratuita)

### 1. Obtenha sua chave de API OpenWeather
1. Acesse `https://openweathermap.org/api`
2. Crie uma conta gratuita e copie sua chave em "API keys"
3. Observação: a chave pode levar até 2 horas para ativar no plano gratuito
4. Plano gratuito permite até 1.000 chamadas/dia; acima disso, há cobrança por call

### 2. Configure as variáveis de ambiente
- Copie o arquivo de exemplo:
```
# Linux/Mac
cp .env.example .env

# Windows (PowerShell)
copy .env.example .env

# Windows (CMD)
copy .env.example .env
```
- Edite o arquivo `.env` e substitua `your_api_key_here` pela sua chave:
```
OPENWEATHER_API_KEY=sua_chave_real_aqui
```

### 3. Suba os serviços
`docker compose up -d --build`

### 4. Aguarde a inicialização (~30 segundos) e acompanhe logs
`docker compose logs -f app`
Quando aparecer `Started WeatherCollectorApplication`, está pronto.

### 5. Teste
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- API docs (JSON): `http://localhost:8080/v3/api-docs`
- Health: `http://localhost:8080/actuator/health`
- Exemplo de chamada: `GET http://localhost:8080/weather/rio%20de%20janeiro`

Comandos úteis:
- Parar só a API: `docker compose stop app`
- Iniciar a API: `docker compose start app`
- Reiniciar a API: `docker compose restart app`
- Derrubar tudo: `docker compose down`
- Logs da API: `docker logs -f weather-app`

## Executar localmente (sem Docker)
1. Pré‑requisitos: Java 21, Maven, PostgreSQL local
2. Configure a API key:
   - Windows: `set OPENWEATHER_API_KEY=SEU_TOKEN_AQUI && mvn spring-boot:run`
3. Configure o banco (por padrão):
   - `spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db`
   - `spring.datasource.username=postgres`
   - `spring.datasource.password=postgres`
4. Endpoints: iguais aos do Docker (porta 8080)

## Endpoints
- `GET /weather/{city}`
  - Busca dados atuais da OpenWeather para a cidade, persiste e retorna o registro como DTO.
- `GET /weather/history?city={city}`
  - Lista histórico local da cidade, ordenado por `collectedAt` desc.
- Swagger UI: `GET /swagger-ui/index.html`
- API docs: `GET /v3/api-docs`
- Health: `GET /actuator/health`

## Banco de Dados
- Tabela: `weather_data`
  - Campos principais: `id`, `city`, `country`, `temperature`, `feels_like`, `humidity`, `description`, `wind_speed`, `collected_at`, `created_at`
- Busca acento‑insensível no histórico via `unaccent`:
  - Caso necessário, habilite a extensão no Postgres: `CREATE EXTENSION IF NOT EXISTS unaccent;`

## Arquitetura
- `api/controller`: endpoints REST e documentação OpenAPI
- `business/service`: regras de negócio, validações e integração com client
- `infrastructure/client`: Feign client para OpenWeather e `ErrorDecoder`
- `infrastructure/entity` e `infrastructure/repository`: persistência JPA
- `infrastructure/mapper`: conversões entre `OpenWeatherResponse ↔ WeatherData ↔ WeatherResponseDTO`
- `api/handlers`: tratamento global de exceções (escopo ao `WeatherController`)

## Tratamento de Erros
- Exceptions de domínio mapeadas no handler global:
  - `BadRequestException` → 400
  - `InvalidApiKeyException` → 401
  - `CityNotFoundException` → 404
  - `RateLimitExceededException` → 429
- Padrão de resposta: `StandardError { timestamp, status, error, message, path }`
- Erros da OpenWeather são traduzidos pelo `OpenWeatherErrorDecoder`

## Docker Compose
- Serviços:
  - `postgres` (com healthcheck)
  - `pgadmin`
  - `app` (API Spring Boot)
- Rede interna: `weather-network`
- `depends_on`: `app` aguarda `postgres` saudável
- `restart: unless-stopped` para `app`

## Configuração
- Variáveis de ambiente principais:
  - `OPENWEATHER_API_KEY` (obrigatória)
  - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` (compostas no Compose)
- Perfil `prod` no container para execução enxuta.

## Versionamento
- Branch principal de trabalho: `develop`
- Commits semânticos e PRs claros.

## Estrutura final dos arquivos
```
weather-collector/
├── .env                    ← NO .gitignore (não versionado)
├── .env.example            ← versão de exemplo (versionado)
├── .gitignore              ← ignora .env, mantém .env.example
├── Dockerfile
├── docker-compose.yml
└── README.md               ← instruções claras
```

## 🐳 Comandos Docker úteis
```
# Iniciar serviços
docker compose up -d

# Ver logs em tempo real
docker compose logs -f app

# Parar apenas a API
docker compose stop app

# Iniciar a API novamente
docker compose start app

# Reiniciar a API
docker compose restart app

# Derrubar tudo
docker compose down

# Ver status dos serviços
docker compose ps
```

## Contato
- José Alberto Vilar Pereira
- Email: `albertovilar1@gmail.com`
- LinkedIn: `alberto-vilar-316725ab`
- GitHub: `@albertovilar`
