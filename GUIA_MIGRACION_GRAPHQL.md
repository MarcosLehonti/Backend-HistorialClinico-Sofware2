# Guía de Migración de REST API a GraphQL

## ✅ Cambios Realizados

### 1. Dependencias Agregadas (pom.xml)
- `spring-boot-starter-graphql`: Starter de Spring Boot para GraphQL
- `graphql-java-extended-scalars`: Soporte para tipos Date y DateTime

### 2. Esquema GraphQL Creado
**Ubicación:** `src/main/resources/graphql/schema.graphqls`

Este archivo define:
- **Types**: Usuario, Rol, Especialidad, Cita, Triaje, Diagnostico, etc.
- **Queries**: Equivalentes a tus endpoints GET de REST
- **Mutations**: Equivalentes a tus endpoints POST, PUT, DELETE de REST
- **Inputs**: Objetos de entrada para las mutations

### 3. Resolvers Creados (Reemplazan Controllers)

#### UsuarioResolver
**Ubicación:** `src/main/java/com/HistorialClinico/Backend/resolver/UsuarioResolver.java`

Queries disponibles:
- `usuarios`: Lista todos los usuarios
- `usuario(id)`: Obtiene un usuario por ID
- `usuarioPorUsername(username)`: Obtiene usuario por username
- `medicos`: Lista todos los médicos
- `perfil`: Obtiene perfil del usuario autenticado

Mutations disponibles:
- `login(input)`: Autenticación de usuario
- `registro(input)`: Registro de nuevo usuario
- `actualizarUsuario(id, input)`: Actualizar usuario
- `eliminarUsuario(id)`: Eliminar usuario
- `asignarRol(usuarioId, rol)`: Asignar rol a usuario
- `asignarEspecialidades(usuarioId, especialidadIds)`: Asignar especialidades
- `asignarHorarios(usuarioId, horarioIds)`: Asignar horarios

#### CitaResolver
**Ubicación:** `src/main/java/com/HistorialClinico/Backend/resolver/CitaResolver.java`

Queries y Mutations para gestión de citas.

#### EspecialidadResolver
**Ubicación:** `src/main/java/com/HistorialClinico/Backend/resolver/EspecialidadResolver.java`

Queries y Mutations para especialidades.

### 4. Configuración de GraphQL
**Ubicación:** `src/main/java/com/HistorialClinico/Backend/config/GraphQLConfig.java`

Configura los scalars personalizados (Date, DateTime).

### 5. Seguridad Actualizada
**Ubicación:** `src/main/java/com/HistorialClinico/Backend/security/WebSecurityConfig.java`

Se agregaron permisos para:
- `/graphql`: Endpoint principal de GraphQL
- `/graphiql`: Interfaz gráfica para probar queries

### 6. Application Properties
**Ubicación:** `src/main/resources/application.properties`

Configuración agregada:
```properties
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
spring.graphql.path=/graphql
spring.graphql.schema.printer.enabled=true
spring.graphql.cors.allowed-origins=http://localhost:4200,https://frontend-historial-clinico.vercel.app
spring.graphql.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
```

## 🚀 Cómo Usar GraphQL

### 1. Compilar el Proyecto
```bash
mvn clean install
```

### 2. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

### 3. Acceder a GraphiQL
Abre tu navegador en: `http://localhost:8080/graphiql`

GraphiQL es una interfaz gráfica donde puedes probar tus queries y mutations.

## 📝 Ejemplos de Uso

### Ejemplo 1: Login (Mutation)
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

### Ejemplo 2: Listar Usuarios (Query)
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

### Ejemplo 3: Crear Usuario (Mutation)
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

### Ejemplo 4: Obtener Médicos (Query)
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

### Ejemplo 5: Crear Cita (Mutation)
```graphql
mutation {
  crearCita(input: {
    usuarioId: "1"
    medicoId: "2"
    especialidadId: "1"
    turnoId: "1"
    diaId: "1"
    horarioId: "1"
    horario: "09:00-10:00"
    nombreUsuarioLogeado: "Juan Perez"
  }) {
    id
    horario
    fecha
  }
}
```

### Ejemplo 6: Query con Autenticación
Para queries que requieren autenticación, agrega el header:
```
{
  "Authorization": "Bearer tu_token_jwt_aqui"
}
```

## 🔄 Comparación REST vs GraphQL

### REST API (Antes)
```
GET    /api/usuarios
POST   /api/usuarios/registro
GET    /api/usuarios/{id}
PUT    /api/usuarios/{id}
DELETE /api/usuarios/{id}
POST   /api/usuarios/login
```

### GraphQL (Ahora)
```graphql
# Un solo endpoint: POST /graphql

# Queries (equivalente a GET)
query {
  usuarios { ... }
  usuario(id: "1") { ... }
}

# Mutations (equivalente a POST/PUT/DELETE)
mutation {
  registro(input: {...}) { ... }
  login(input: {...}) { ... }
  actualizarUsuario(id: "1", input: {...}) { ... }
  eliminarUsuario(id: "1")
}
```

## 🎯 Ventajas de GraphQL

1. **Un solo endpoint**: `/graphql` para todas las operaciones
2. **Solicita solo lo que necesitas**: El cliente define exactamente qué campos quiere
3. **Sin over-fetching**: No recibes datos innecesarios
4. **Sin under-fetching**: Puedes obtener datos relacionados en una sola query
5. **Tipado fuerte**: El esquema define todos los tipos de datos
6. **Documentación automática**: GraphiQL genera documentación interactiva
7. **Versionado no necesario**: Puedes agregar campos sin romper clientes existentes

## 📋 Próximos Pasos

### Tareas Pendientes:

1. **Completar todos los Resolvers**: Crear resolvers para:
   - TriajeResolver
   - DiagnosticoResolver
   - AntecedenteResolver
   - BitacoraResolver
   - HorarioResolver

2. **Ajustar los Services**: Algunos servicios necesitan métodos adicionales que los resolvers esperan (ej: `findAllEspecialidades()`, `findById()` en EspecialidadService)

3. **Implementar Autenticación JWT en GraphQL**: Crear un interceptor para validar tokens JWT en las queries/mutations que lo requieran

4. **Manejo de Errores**: Implementar manejo de errores personalizado para GraphQL

5. **Testing**: Crear tests para los resolvers GraphQL

6. **Optimización**: Implementar DataLoader para evitar el problema N+1 en queries anidadas

## 🔧 Solución de Problemas

### Error: "Cannot resolve type"
- Verifica que el esquema GraphQL esté en `src/main/resources/graphql/`
- Asegúrate que el nombre del archivo sea `schema.graphqls`

### Error: "Method not found in Service"
- Algunos resolvers llaman métodos que no existen en los services
- Necesitas agregar esos métodos a los services correspondientes

### Error de compilación en Resolvers
- Revisa que los imports sean correctos
- Verifica que los tipos de retorno coincidan con el esquema GraphQL

## 📚 Recursos Adicionales

- [Spring for GraphQL Documentation](https://spring.io/projects/spring-graphql)
- [GraphQL Java Documentation](https://www.graphql-java.com/)
- [GraphQL Official Website](https://graphql.org/)

## 🔄 Convivencia REST + GraphQL

**Importante**: Tu API REST sigue funcionando. GraphQL se agregó como una capa adicional.

Puedes:
- Mantener ambas APIs funcionando simultáneamente
- Migrar gradualmente de REST a GraphQL
- Usar REST para algunas operaciones y GraphQL para otras

Los Controllers REST originales NO fueron modificados ni eliminados.

## 💡 Recomendaciones

1. **Prueba primero con GraphiQL**: Es la forma más fácil de familiarizarte con GraphQL
2. **Lee el esquema**: El archivo `schema.graphqls` es tu documentación principal
3. **Usa variables**: En GraphiQL puedes usar variables para hacer queries más dinámicas
4. **Explora la documentación automática**: GraphiQL tiene un panel "Docs" con toda la API

## 🎓 Ejemplo Completo de Flujo

```graphql
# 1. Login
mutation Login {
  login(input: {
    username: "admin"
    password: "admin123"
  }) {
    token
    usuarioId
    rol
  }
}

# 2. Obtener perfil (con token en headers)
query MiPerfil {
  perfil {
    id
    username
    email
    roles {
      nombre
    }
    especialidades {
      nombre
    }
  }
}

# 3. Listar especialidades
query Especialidades {
  especialidades {
    id
    nombre
  }
}

# 4. Crear cita
mutation CrearCita {
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
    medico {
      username
    }
    especialidad {
      nombre
    }
  }
}
```

---

**¡Tu aplicación ahora soporta GraphQL!** 🎉

Para cualquier duda, revisa la documentación de Spring for GraphQL o prueba las queries en GraphiQL.
