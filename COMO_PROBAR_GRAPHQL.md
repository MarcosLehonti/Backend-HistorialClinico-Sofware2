# 🚀 Cómo Probar tu API GraphQL

GraphiQL tiene problemas de CORS con unpkg.com. Aquí tienes **3 alternativas mejores** para probar tu API GraphQL:

---

## ✅ Opción 1: Postman (Recomendado - Más Fácil)

### Paso 1: Abre Postman
Si no lo tienes, descárgalo de: https://www.postman.com/downloads/

### Paso 2: Crea una nueva Request
- Click en **"New"** → **"HTTP Request"**
- Método: **POST**
- URL: `http://localhost:8080/graphql`

### Paso 3: Configura el Body
- Selecciona la pestaña **"Body"**
- Selecciona **"GraphQL"** (si está disponible) o **"raw"** → **"JSON"**

### Paso 4: Prueba tu primera Query

#### Ejemplo 1: Listar Especialidades
```json
{
  "query": "query { especialidades { id nombre } }"
}
```

#### Ejemplo 2: Listar Usuarios
```json
{
  "query": "query { usuarios { id username email roles { nombre } } }"
}
```

#### Ejemplo 3: Login
```json
{
  "query": "mutation { login(input: { username: \"admin\", password: \"password123\" }) { token usuarioId rol } }"
}
```

#### Ejemplo 4: Crear Especialidad
```json
{
  "query": "mutation { crearEspecialidad(nombre: \"Cardiología\") { id nombre } }"
}
```

### Paso 5: Para queries con autenticación
- Ve a la pestaña **"Headers"**
- Agrega:
  - Key: `Authorization`
  - Value: `Bearer tu_token_aqui`

---

## ✅ Opción 2: cURL (Línea de Comandos)

### Ejemplo 1: Listar Especialidades
```bash
curl -X POST http://localhost:8080/graphql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"query { especialidades { id nombre } }\"}"
```

### Ejemplo 2: Login
```bash
curl -X POST http://localhost:8080/graphql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"mutation { login(input: { username: \\\"admin\\\", password: \\\"password123\\\" }) { token usuarioId rol } }\"}"
```

### Ejemplo 3: Con Variables
```bash
curl -X POST http://localhost:8080/graphql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"query($id: ID!) { usuario(id: $id) { username email } }\", \"variables\": {\"id\": \"1\"}}"
```

---

## ✅ Opción 3: Extensión de VS Code

### Instala la extensión "Thunder Client"
1. Abre VS Code
2. Ve a Extensions (Ctrl+Shift+X)
3. Busca **"Thunder Client"**
4. Instala la extensión

### Usar Thunder Client
1. Click en el icono de Thunder Client en la barra lateral
2. New Request → POST
3. URL: `http://localhost:8080/graphql`
4. Body → JSON
5. Pega tu query GraphQL

---

## ✅ Opción 4: Altair GraphQL Client

### Descarga Altair
- Web: https://altairgraphql.dev/
- O instala la extensión de Chrome/Firefox

### Configuración
1. URL: `http://localhost:8080/graphql`
2. Escribe tus queries directamente
3. Tiene autocompletado y documentación integrada

---

## 📝 Ejemplos Completos de Queries

### 1. Obtener todos los usuarios con sus roles
```graphql
query {
  usuarios {
    id
    username
    email
    roles {
      id
      nombre
    }
  }
}
```

**En JSON para Postman/cURL:**
```json
{
  "query": "query { usuarios { id username email roles { id nombre } } }"
}
```

---

### 2. Login y obtener token
```graphql
mutation {
  login(input: {
    username: "admin"
    password: "password123"
  }) {
    token
    usuarioId
    rol
  }
}
```

**En JSON:**
```json
{
  "query": "mutation { login(input: { username: \"admin\", password: \"password123\" }) { token usuarioId rol } }"
}
```

---

### 3. Obtener médicos con especialidades
```graphql
query {
  medicos {
    id
    username
    email
    especialidades {
      id
      nombre
    }
  }
}
```

**En JSON:**
```json
{
  "query": "query { medicos { id username email especialidades { id nombre } } }"
}
```

---

### 4. Crear una cita
```graphql
mutation {
  crearCita(input: {
    usuarioId: "1"
    medicoId: "2"
    especialidadId: "1"
    turnoId: "1"
    diaId: "1"
    horarioId: "5"
    horario: "10:00-11:00"
    nombreUsuarioLogeado: "Juan Perez"
  }) {
    id
    horario
    fecha
  }
}
```

**En JSON:**
```json
{
  "query": "mutation { crearCita(input: { usuarioId: \"1\", medicoId: \"2\", especialidadId: \"1\", turnoId: \"1\", diaId: \"1\", horarioId: \"5\", horario: \"10:00-11:00\", nombreUsuarioLogeado: \"Juan Perez\" }) { id horario fecha } }"
}
```

---

### 5. Registro de nuevo usuario
```graphql
mutation {
  registro(input: {
    username: "nuevo_usuario"
    password: "password123"
    email: "usuario@example.com"
  }) {
    id
    username
    email
  }
}
```

**En JSON:**
```json
{
  "query": "mutation { registro(input: { username: \"nuevo_usuario\", password: \"password123\", email: \"usuario@example.com\" }) { id username email } }"
}
```

---

### 6. Query con Variables (Recomendado para queries complejas)

**Query:**
```graphql
query ObtenerUsuario($id: ID!) {
  usuario(id: $id) {
    id
    username
    email
    roles {
      nombre
    }
  }
}
```

**Variables:**
```json
{
  "id": "1"
}
```

**En Postman/cURL:**
```json
{
  "query": "query ObtenerUsuario($id: ID!) { usuario(id: $id) { id username email roles { nombre } } }",
  "variables": {
    "id": "1"
  }
}
```

---

## 🔐 Queries con Autenticación

Para queries que requieren autenticación, agrega el header:

### En Postman:
- Headers → Add
- Key: `Authorization`
- Value: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

### En cURL:
```bash
curl -X POST http://localhost:8080/graphql ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer tu_token_aqui" ^
  -d "{\"query\": \"query { perfil { username email } }\"}"
```

---

## 📊 Ventajas de cada herramienta

| Herramienta | Ventajas | Desventajas |
|-------------|----------|-------------|
| **Postman** | Interfaz gráfica, fácil de usar, guarda requests | Requiere instalación |
| **cURL** | Rápido, no requiere instalación extra | Sintaxis compleja |
| **Thunder Client** | Integrado en VS Code, ligero | Menos features que Postman |
| **Altair** | Diseñado específicamente para GraphQL | Requiere instalación |

---

## 🎯 Mi Recomendación

**Para empezar: Usa Postman**
- Es el más fácil de usar
- Tiene interfaz gráfica
- Puedes guardar tus queries
- Soporta colecciones y variables

**Para desarrollo rápido: Thunder Client en VS Code**
- No necesitas cambiar de aplicación
- Ligero y rápido
- Perfecto si ya usas VS Code

---

## 🔍 Verificar que GraphQL está funcionando

### Test rápido con cURL:
```bash
curl -X POST http://localhost:8080/graphql -H "Content-Type: application/json" -d "{\"query\": \"query { especialidades { id nombre } }\"}"
```

Si recibes una respuesta JSON con datos, ¡GraphQL está funcionando! ✅

---

## 📚 Recursos Adicionales

- **Esquema completo**: Ver archivo `src/main/resources/graphql/schema.graphqls`
- **Guía de migración**: Ver `GUIA_MIGRACION_GRAPHQL.md`
- **Resumen de cambios**: Ver `RESUMEN_CAMBIOS.md`

---

## ❓ Solución de Problemas

### Error: "Connection refused"
- Verifica que la aplicación esté corriendo: `.\mvnw spring-boot:run`
- Verifica el puerto: debe ser 8080

### Error: "Cannot query field..."
- Revisa el esquema en `schema.graphqls`
- Verifica que el nombre del campo sea correcto

### Error: "Unauthorized" o 403
- Agrega el header `Authorization: Bearer token`
- Verifica que el token sea válido

---

**¡Ahora puedes probar tu API GraphQL sin problemas!** 🎉
