# API de Gestión de Préstamos Personales

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![H2](https://img.shields.io/badge/H2-2.2-lightblue)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED)
![License](https://img.shields.io/badge/License-MIT-yellow)

API REST para gestionar solicitudes de préstamos personales desarrollada con **arquitectura hexagonal** (Ports & Adapters), siguiendo principios de **Clean Architecture** y **Domain-Driven Design**.

---

## ⚡ Inicio Rápido

Elige tu opción preferida (todas funcionan):

| Opción | Comando | Tiempo | Persistencia |
|--------|---------|--------|--------------|
| **1️⃣ H2 en Memoria** | `mvn spring-boot:run "-Dspring-boot.run.profiles=h2"` | ~30s | ❌ |
| **2️⃣ Docker DB + Local** | `docker-compose up -d && mvn spring-boot:run` | ~1min | ✅ |
| **3️⃣ Todo Docker** | `docker-compose -f docker-compose-full.yml up -d` | ~2min | ✅ |

👉 **Ver [INICIO-RAPIDO.md](INICIO-RAPIDO.md)** para instrucciones detalladas de cada opción.

**Acceso rápido:**
- 🌐 API: http://localhost:8080/api/prestamos
- 📚 Swagger: http://localhost:8080/swagger-ui.html
- 💾 H2 Console (solo Opción 1): http://localhost:8080/h2-console

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Tecnologías](#-tecnologías)
- [Prerrequisitos](#-prerrequisitos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [Endpoints](#-endpoints)
- [Testing](#-testing)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Reglas de Negocio](#-reglas-de-negocio)
- [Documentación API](#-documentación-api)

## ✨ Características

- **Arquitectura Hexagonal** - Separación clara entre dominio, aplicación e infraestructura
- **SOLID Principles** - Código mantenible y escalable
- **API RESTful** - Endpoints siguiendo convenciones REST
- **Validaciones** - Bean Validation en DTOs
- **Manejo de Errores** - Respuestas de error estandarizadas
- **Documentación OpenAPI** - Swagger UI integrado
- **Testing Completo** - Unitarios e integración con >80% cobertura
- **Migraciones BD** - Flyway para control de versiones de base de datos
- **Configuración por Profiles** - Dev, Prod

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                     │
│          (Controllers REST + DTOs + Swagger)                │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   CAPA DE APLICACIÓN                        │
│         (Servicios + Casos de Uso + Mapeadores)             │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                     CAPA DE DOMINIO                         │
│  (Entidades + Reglas de Negocio + Puertos + Excepciones)   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                 CAPA DE INFRAESTRUCTURA                     │
│       (Adaptadores JPA + PostgreSQL + Configuración)        │
└─────────────────────────────────────────────────────────────┘
```

### Estructura de Carpetas

```
src/main/java/com/bank/prestamos/
├── domain/                    # Núcleo del negocio
│   ├── modelo/               # Entidades de dominio
│   ├── excepcion/            # Excepciones del negocio
│   └── ports/
│       ├── in/               # Casos de uso (interfaces)
│       └── out/              # Repositorio (interface)
├── application/              # Capa de aplicación
│   ├── servicio/            # Implementación de casos de uso
│   └── mapeador/            # Mapeadores MapStruct
└── infrastructure/          # Adaptadores
    ├── adapter/
    │   ├── in/rest/        # Controllers + DTOs
    │   └── out/persistence/ # JPA + Entidades
    ├── configuracion/      # Configuración Spring
    └── excepcion/          # Manejador global de excepciones
```

## 🛠️ Tecnologías

| Categoría | Tecnología | Versión |
|-----------|-----------|---------|
| **Lenguaje** | Java | 17 |
| **Framework** | Spring Boot | 3.2.2 |
| **Web** | Spring Web | - |
| **Persistencia** | Spring Data JPA | - |
| **BD** | PostgreSQL | 15 |
| **Migraciones** | Flyway | - |
| **Validación** | Bean Validation | - |
| **Mapeo** | MapStruct | 1.5.5 |
| **Lombok** | Lombok | 1.18.30 |
| **Documentación** | SpringDoc OpenAPI | 2.3.0 |
| **Testing** | JUnit 5 + Mockito | - |
| **Cobertura** | JaCoCo | 0.8.11 |
| **Build** | Maven | - |

## 📦 Prerrequisitos

Dependiendo de la opción que elijas:

### Opción 1: Base de Datos en Memoria (H2)
- **Java 17** o superior ([Descargar](https://adoptium.net/))
- **Maven 3.8+** ([Descargar](https://maven.apache.org/download.cgi))

### Opción 2: PostgreSQL en Docker + App Local
- **Java 17** o superior ([Descargar](https://adoptium.net/))
- **Maven 3.8+** ([Descargar](https://maven.apache.org/download.cgi))
- **Docker Desktop** ([Descargar](https://www.docker.com/products/docker-desktop/))

### Opción 3: Todo en Docker
- **Docker Desktop** ([Descargar](https://www.docker.com/products/docker-desktop/))
- **Docker Compose** (incluido en Docker Desktop)

## 🚀 Inicio Rápido - 3 Opciones


### 🎯 Opción 1: Base de Datos en Memoria (H2)

```bash
# 1. Clonar y entrar al proyecto
git clone https://github.com/Delacruz-6/bank-api-rest.git
cd bank-api-rest/bank_api_rest

# 2. Compilar
mvn clean install -DskipTests

# 3. Ejecutar con perfil H2
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

**Listo!** La API estará en `http://localhost:8080`

**Características:**
- Sin instalaciones adicionales
- Base de datos en memoria (H2)
- Consola H2 disponible en: `http://localhost:8080/h2-console`
  - **JDBC URL**: `jdbc:h2:mem:prestamos_db`
  - **Usuario**: `sa`
  - **Contraseña**: *(vacío)*
- ⚠️ Los datos se pierden al reiniciar

---

### 🐳 Opción 2: PostgreSQL en Docker + Aplicación Local

```bash
# 1. Clonar y entrar al proyecto
git clone https://github.com/Delacruz-6/bank-api-rest.git
cd bank-api-rest/bank_api_rest

# 2. Levantar solo PostgreSQL en Docker
docker-compose up -d

# 3. Compilar el proyecto
mvn clean install -DskipTests

# 4. Ejecutar la aplicación localmente
mvn spring-boot:run
```

**Listo!** La API estará en `http://localhost:8080`

**Características:**
- PostgreSQL real en contenedor Docker
- Datos persistentes (no se pierden al reiniciar)
- Desarrollo local con hot-reload
- Fácil debugging desde el IDE

**Comandos útiles:**
```bash
# Ver logs de PostgreSQL
docker-compose logs -f postgres

# Acceder a PostgreSQL
docker exec -it prestamos-postgres psql -U postgres -d prestamos_db

# Detener PostgreSQL
docker-compose down

# Detener y eliminar datos
docker-compose down -v
```

---

### 🐳 Opción 3: Todo en Docker (PostgreSQL + API)

```bash
# 1. Clonar y entrar al proyecto
git clone https://github.com/Delacruz-6/bank-api-rest.git
cd bank-api-rest/bank_api_rest

# 2. Levantar todo el entorno
docker-compose -f docker-compose-full.yml up -d --build
```

**Listo!** La API estará en `http://localhost:8080`

**Características:**
- Entorno completamente aislado
- PostgreSQL + API en contenedores
- Datos persistentes
- Más cercano al entorno de producción

**Comandos útiles:**
```bash
# Ver logs de la aplicación
docker-compose -f docker-compose-full.yml logs -f app

# Ver logs de PostgreSQL
docker-compose -f docker-compose-full.yml logs -f postgres

# Reiniciar solo la aplicación
docker-compose -f docker-compose-full.yml restart app

# Reconstruir tras cambios en código
docker-compose -f docker-compose-full.yml up -d --build

# Detener todo
docker-compose -f docker-compose-full.yml down

# Detener y limpiar volúmenes
docker-compose -f docker-compose-full.yml down -v
```

---

## 🔗 Acceso a la Aplicación (todas las opciones)

Una vez iniciada la aplicación:

- **API Base**: http://localhost:8080/api/prestamos
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **H2 Console** (solo Opción 1): http://localhost:8080/h2-console

---

## 🛠️ Instalación Detallada (Opción 2)

### 1. Clonar el repositorio

```bash
git clone https://github.com/Delacruz-6/bank-api-rest.git
cd bank-api-rest/bank_api_rest
```

### 2. Levantar PostgreSQL en Docker

```bash
docker-compose up -d
```

### 3. Compilar el proyecto

```bash
mvn clean install
```

## ⚙️ Configuración

### Perfiles Disponibles

La aplicación soporta 3 perfiles de configuración:

#### 🔹 H2 en Memoria (`application-h2.properties`)
```properties
spring.datasource.url=jdbc:h2:mem:prestamos_db
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

#### 🔹 Development (`application-dev.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/prestamos_db
spring.datasource.username=postgres
spring.datasource.password=postgres
server.port=8080
```

#### Production (`application-prod.properties`)
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
```

### Cambiar Profile Activo

```bash
# Opción 1: Editar application.properties
spring.profiles.active=h2    # o dev, o prod

# Opción 2: Línea de comandos
mvn spring-boot:run -Dspring-boot.run.profiles=h2
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Opción 3: Variable de entorno
export SPRING_PROFILES_ACTIVE=h2
mvn spring-boot:run
```

## 🎯 Uso

### ▶️ Opción 1: H2 en Memoria

```bash
# Ejecutar con perfil H2
mvn spring-boot:run -Dspring-boot.run.profiles=h2

# O usando JAR
java -jar -Dspring.profiles.active=h2 target/bank-api-rest-1.0.0-SNAPSHOT.jar
```

### ▶️ Opción 2: Docker DB + App Local

```bash
# 1. Levantar PostgreSQL
docker-compose up -d

# 2. Ejecutar aplicación
mvn spring-boot:run

# O con JAR
java -jar target/bank-api-rest-1.0.0-SNAPSHOT.jar
```

### ▶️ Opción 3: Todo en Docker

```bash
# Levantar todo
docker-compose -f docker-compose-full.yml up -d --build

# Ver logs
docker-compose -f docker-compose-full.yml logs -f app
```

La aplicación estará disponible en: `http://localhost:8080`

### Acceder a Swagger UI

```
http://localhost:8080/swagger-ui.html
```

## 📡 Endpoints

### Base URL: `/api/prestamos`

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| **POST** | `/` | Crear nuevo préstamo | 201 |
| **GET** | `/` | Listar todos los préstamos | 200 |
| **GET** | `/{id}` | Obtener préstamo por ID | 200 |
| **PATCH** | `/{id}/estado` | Cambiar estado de préstamo | 200 |

### Ejemplos de Uso

#### 1. Crear Préstamo

**Request:**
```bash
curl -X POST http://localhost:8080/api/prestamos \
  -H "Content-Type: application/json" \
  -d '{
    "nombreSolicitante": "Juan Pérez García",
    "importeSolicitado": 15000.00,
    "divisa": "EUR",
    "documentoIdentificativo": "12345678A"
  }'
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nombreSolicitante": "Juan Pérez García",
  "importeSolicitado": 15000.00,
  "divisa": "EUR",
  "documentoIdentificativo": "12345678A",
  "estado": "PENDIENTE",
  "fechaCreacion": "2026-02-07T10:30:00",
  "fechaUltimaModificacion": null,
  "usuarioUltimaModificacion": null
}
```

#### 2. Listar Préstamos

**Request:**
```bash
curl http://localhost:8080/api/prestamos
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombreSolicitante": "Juan Pérez García",
    "importeSolicitado": 15000.00,
    "estado": "PENDIENTE",
    ...
  },
  {
    "id": 2,
    "nombreSolicitante": "María López Martínez",
    "importeSolicitado": 25000.00,
    "estado": "APROBADA",
    ...
  }
]
```

#### 3. Obtener Préstamo por ID

**Request:**
```bash
curl http://localhost:8080/api/prestamos/1
```

**Response:** `200 OK` o `404 Not Found`

#### 4. Cambiar Estado

**Request:**
```bash
curl -X PATCH http://localhost:8080/api/prestamos/1/estado \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "APROBADA",
    "usuarioModificacion": "gestor@banco.com"
  }'
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "estado": "APROBADA",
  "fechaUltimaModificacion": "2026-02-07T11:15:00",
  "usuarioUltimaModificacion": "gestor@banco.com",
  ...
}
```

### Respuestas de Error

#### 400 Bad Request - Validación
```json
{
  "marcaTiempo": "2026-02-07T10:30:00",
  "estado": 400,
  "error": "Solicitud Incorrecta",
  "mensaje": "Falló la validación",
  "ruta": "/api/prestamos",
  "erroresValidacion": {
    "nombreSolicitante": "El nombre es obligatorio",
    "importeSolicitado": "El importe debe ser positivo"
  }
}
```

#### 404 Not Found
```json
{
  "marcaTiempo": "2026-02-07T10:30:00",
  "estado": 404,
  "error": "No Encontrado",
  "mensaje": "Préstamo no encontrado con id: 999",
  "ruta": "/api/prestamos/999"
}
```

#### 409 Conflict - Transición Inválida
```json
{
  "marcaTiempo": "2026-02-07T10:30:00",
  "estado": 409,
  "error": "Conflicto",
  "mensaje": "Transición de estado inválida de RECHAZADA a APROBADA",
  "ruta": "/api/prestamos/1/estado"
}
```

## 🧪 Testing

### Ejecutar Tests

```bash
# Ejecutar todos los tests unitarios
mvn test

# Con reporte de cobertura
mvn clean test jacoco:report
```

### Ver Reporte de Cobertura

Después de ejecutar `mvn test jacoco:report`, abrir:
```
target/site/jacoco/index.html
```
**Cobertura Objetivo:** >80%

## 📊 Reglas de Negocio

### Estados de Préstamo

```
PENDIENTE → APROBADA
         ↘ RECHAZADA

APROBADA → CANCELADA

RECHAZADA  (Estado final)
CANCELADA  (Estado final)
```

### Transiciones Válidas

| Estado Actual | Estados Permitidos |
|--------------|-------------------|
| `PENDIENTE` | `APROBADA`, `RECHAZADA` |
| `APROBADA` | `CANCELADA` |
| `RECHAZADA` | ❌ Ninguno (estado final) |
| `CANCELADA` | ❌ Ninguno (estado final) |

### Validaciones

- **Nombre Solicitante**: Obligatorio, no vacío
- **Importe Solicitado**: Obligatorio, positivo
- **Divisa**: Obligatoria, código ISO 4217 de 3 letras (ej: EUR, USD)
- **Documento Identificativo**: 
  - DNI: 8 dígitos + letra (ej: 12345678A)
  - NIE: Letra (X/Y/Z) + 7 dígitos + letra (ej: X1234567A)
  - **Único**: No puede haber dos préstamos con el mismo documento

## 📖 Documentación API

### Swagger UI

Una vez la aplicación esté ejecutándose:

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON

```
http://localhost:8080/api-docs
```

## 📝 Convenciones de Código

- **Nombres en español**: Clases, métodos, variables, tablas
- **Carpetas en inglés**: `domain`, `application`, `infrastructure`
- **Anotaciones en inglés**: `@Service`, `@Entity`, etc.
- **Formato**: Google Java Style Guide
- **Tests**: Given-When-Then o Arrange-Act-Assert

## � Comandos Docker Útiles

### Gestión de Contenedores

```bash
# Ver estado de los servicios
docker-compose ps

# Ver logs de todos los servicios
docker-compose logs

# Ver logs solo de la API
docker-compose logs app

# Ver logs solo de PostgreSQL
docker-compose logs postgres

# Acceder al contenedor de la API
docker exec -it prestamos-api sh

# Acceder a PostgreSQL
docker exec -it prestamos-postgres psql -U postgres -d prestamos_db
```

### Mantenimiento

```bash
# Reconstruir imagen sin caché
docker-compose build --no-cache

# Limpiar volúmenes y empezar desde cero
docker-compose down -v
docker-compose up -d

# Ver uso de recursos
docker stats prestamos-api prestamos-postgres
```

### Troubleshooting

```bash
# Si el puerto 8080 ya está en uso
# Editar docker-compose.yml: ports: - "8081:8080"

# Si PostgreSQL no inicia
docker-compose down -v
docker volume prune
docker-compose up -d

# Ver errores detallados
docker-compose logs --tail=100 app
```
## 🐳 Comandos Docker por Opción

### 📦 Opción 2: Solo PostgreSQL

```bash
# Iniciar PostgreSQL
docker-compose up -d

# Ver logs
docker-compose logs -f postgres

# Acceder a psql
docker exec -it prestamos-postgres psql -U postgres -d prestamos_db

# Detener
docker-compose down

# Limpiar datos
docker-compose down -v
```

### 📦 Opción 3: PostgreSQL + API

```bash
# Iniciar todo
docker-compose -f docker-compose-full.yml up -d --build

# Ver logs de la API
docker-compose -f docker-compose-full.yml logs -f app

# Ver logs de PostgreSQL
docker-compose -f docker-compose-full.yml logs -f postgres

# Acceder al contenedor de la API
docker exec -it prestamos-api sh

# Reiniciar solo la API
docker-compose -f docker-compose-full.yml restart app

# Detener todo
docker-compose -f docker-compose-full.yml down

# Limpiar volúmenes
docker-compose -f docker-compose-full.yml down -v
```

## 🆘 Resolución de Problemas Comunes

### ❌ Puerto 8080 ocupado
```bash
# Ver qué proceso usa el puerto
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Cambiar puerto en application.properties
server.port=8081
```

### ❌ No conecta a PostgreSQL (Opción 2)
```bash
# Verificar que PostgreSQL esté corriendo
docker ps

# Ver logs
docker-compose logs postgres

# Verificar conexión
docker exec prestamos-postgres psql -U postgres -c "SELECT 1"
```

### ❌ Base de datos sin tablas
```bash
# Flyway crea las tablas automáticamente
# Si no se crearon, limpiar y reiniciar:
docker-compose down -v
docker-compose up -d
mvn clean spring-boot:run
```

### ❌ H2 Console no funciona (Opción 1)
```bash
# Asegúrate de usar perfil h2
mvn spring-boot:run -Dspring-boot.run.profiles=h2

# Acceder a: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:prestamos_db
# Usuario: sa
# Contraseña: (vacío)
```
## �📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo `LICENSE` para más detalles.


## 👤 Autor

- **Guillermo De la cruz Guzmán** - [gdlcruzguzman@gmail.com](mailto:gdlcruzguzman@gmail.com)

---

**¿Preguntas o Problemas?** Abre un [Issue](https://github.com/Delacruz-6/bank-api-rest/issues)

**Documentación Adicional:**
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [MapStruct](https://mapstruct.org/)
- [PostgreSQL](https://www.postgresql.org/docs/)
