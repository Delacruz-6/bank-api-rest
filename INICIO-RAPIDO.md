# 🚀 Guía Rápida de Inicio

## 📝 Tres formas de ejecutar la aplicación

### ⚡ Opción 1: H2 en Memoria (MÁS RÁPIDA)
```bash
cd bank-api-rest/bank_api_rest
mvn spring-boot:run "-Dspring-boot.run.profiles=h2"
```
✅ **Listo en 30 segundos**  
✅ Sin Docker, sin PostgreSQL  
⚠️ Los datos se pierden al reiniciar  

**Acceso:**
- API: http://localhost:8080/api/prestamos
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:prestamos_db`
  - Usuario: `sa`
  - Password: *(vacío)*

---

### 🐳 Opción 2: PostgreSQL Docker + App Local (RECOMENDADA)
```bash
cd bank-api-rest/bank_api_rest

# Terminal 1: Levantar PostgreSQL
docker-compose up -d

# Terminal 2: Ejecutar aplicación
mvn spring-boot:run
```
✅ **Base de datos real**  
✅ Datos persistentes  
✅ Fácil debugging  

**Acceso:**
- API: http://localhost:8080/api/prestamos
- Swagger: http://localhost:8080/swagger-ui.html
- PostgreSQL: `localhost:5432`

**Comandos útiles:**
```bash
# Ver logs de PostgreSQL
docker-compose logs -f postgres

# Acceder a psql
docker exec -it prestamos-postgres psql -U postgres -d prestamos_db

# Detener
docker-compose down

# Limpiar datos
docker-compose down -v
```

---

### 🐳 Opción 3: Todo en Docker (MÁS COMPLETA)
```bash
cd bank-api-rest/bank_api_rest
docker-compose -f docker-compose-full.yml up -d --build
```
✅ **Entorno aislado**  
✅ Como en producción  
✅ PostgreSQL + API en contenedores  

**Acceso:**
- API: http://localhost:8080/api/prestamos
- Swagger: http://localhost:8080/swagger-ui.html

**Comandos útiles:**
```bash
# Ver logs de la API
docker-compose -f docker-compose-full.yml logs -f app

# Ver logs de PostgreSQL
docker-compose -f docker-compose-full.yml logs -f postgres

# Reiniciar solo la API
docker-compose -f docker-compose-full.yml restart app

# Detener todo
docker-compose -f docker-compose-full.yml down

# Limpiar volúmenes
docker-compose -f docker-compose-full.yml down -v
```

---

## 🧪 Probar la API

### Crear un préstamo
```bash
curl -X POST http://localhost:8080/api/prestamos \
  -H "Content-Type: application/json" \
  -d '{
    "nombreSolicitante": "Juan Pérez",
    "importeSolicitado": 15000.00,
    "divisa": "EUR",
    "documentoIdentificativo": "12345678A"
  }'
```

### Listar préstamos
```bash
curl http://localhost:8080/api/prestamos
```

### Ver préstamo específico
```bash
curl http://localhost:8080/api/prestamos/1
```

### Cambiar estado
```bash
curl -X PATCH http://localhost:8080/api/prestamos/1/estado \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "APROBADA",
    "usuarioModificacion": "admin@banco.com"
  }'
```

---

## 🆘 Problemas Comunes

### Puerto 8080 ocupado
```bash
# Ver qué proceso usa el puerto (Windows)
netstat -ano | findstr :8080

# Cambiar puerto en application.properties
server.port=8081
```

### No conecta a PostgreSQL
```bash
# Verificar que Docker esté corriendo
docker ps

# Ver logs
docker-compose logs postgres
```

### Base de datos vacía
```bash
# Flyway crea las tablas automáticamente
# Si no se crearon, limpiar y reiniciar:
docker-compose down -v
docker-compose up -d
```

---

## 📚 Documentación Completa

Ver [README.md](README.md) para documentación detallada.
