# ⚔ JuegoLucha — Refinamiento con Patrones de Diseño

![Java CI](https://github.com/TU_USUARIO/juego-lucha-patrones/actions/workflows/ci.yml/badge.svg)
![Coverage](https://img.shields.io/badge/coverage-80%25%2B-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8-orange)

> Refinamiento arquitectónico de un juego de lucha por turnos aplicando
> patrones de diseño creacionales y estructurales, pruebas unitarias con
> JUnit 5 + Mockito, cobertura con JaCoCo y CI/CD con GitHub Actions.

---

## 📐 Patrones de Diseño Implementados

### 🏭 Factory Method (Creacional)

**Propósito:** Desacoplar la creación de personajes del resto del código.
En lugar de usar `new Personaje()` directamente, cada fábrica concreta
decide qué tipo de personaje instanciar.

| Clase | Descripción |
|---|---|
| `PersonajeFactory` | Interfaz con el método `crearPersonaje(nombre)` |
| `PersonajeNormalFactory` | Crea un `Personaje` base (HP 100, daño 10–30) |
| `GuerreroFactory` | Crea un `Personaje` + `GuerreroDecorator` |
| `MagoFactory` | Crea un `Personaje` + `MagoDecorator` |

```java
// Sin patrón (rígido):
Personaje p = new Personaje("Thor");

// Con Factory Method (flexible):
PersonajeFactory factory = new GuerreroFactory();
Personaje p = factory.crearPersonaje("Thor");
```

---

### 🎨 Decorator (Estructural)

**Propósito:** Agregar responsabilidades a los objetos `Personaje`
dinámicamente, sin modificar la clase base ni crear explosión de subclases.

| Clase | Modificación |
|---|---|
| `PersonajeDecorator` | Clase abstracta que envuelve un `Personaje` |
| `GuerreroDecorator` | Armadura: −20% daño recibido · Espada: +5 daño |
| `MagoDecorator` | Daño mágico ×1.5 · HP inicial reducido a 90 |

```java
// Decorator en acción:
Personaje base    = new Personaje("Arthas");
Personaje guerrero = new GuerreroDecorator(base);   // +armadura +espada
// guerrero.atacar(oponente) → daño 15–35
// guerrero.recibirDano(100) → solo recibe 80
```

---

## 🏗 Estructura del Proyecto

```
juego-lucha-patrones/
├── .github/
│   └── workflows/
│       └── ci.yml                  ← Pipeline GitHub Actions
├── src/
│   ├── main/java/com/juego/
│   │   ├── model/
│   │   │   └── Personaje.java      ← Componente base
│   │   ├── patrones/
│   │   │   ├── factory/
│   │   │   │   ├── PersonajeFactory.java
│   │   │   │   ├── PersonajeNormalFactory.java
│   │   │   │   ├── GuerreroFactory.java
│   │   │   │   └── MagoFactory.java
│   │   │   └── decorator/
│   │   │       ├── PersonajeDecorator.java
│   │   │       ├── GuerreroDecorator.java
│   │   │       └── MagoDecorator.java
│   │   └── juego/
│   │       └── JuegoLucha.java     ← Flujo del juego (iniciarPelea)
│   └── test/java/com/juego/
│       ├── model/
│       │   └── PersonajeTest.java
│       ├── patrones/
│       │   ├── DecoratorTest.java
│       │   └── FactoryTest.java
│       └── juego/
│           └── JuegoLuchaTest.java
└── pom.xml
```
##Diagrama de clase
<img width="392" height="598" alt="image" src="https://github.com/user-attachments/assets/7af8d3da-c98b-4802-a747-783e7affe39d" />


---

## 🧪 Pruebas Unitarias

- **Framework:** JUnit 5 (Jupiter)
- **Mocks:** Mockito 5
- **Cobertura:** JaCoCo (mínimo 80%)

| Clase de prueba | Qué prueba |
|---|---|
| `PersonajeTest` | Creación, HP, daño, estaVivo, atacar |
| `DecoratorTest` | GuerreroDecorator y MagoDecorator con Mockito |
| `FactoryTest` | Cada fábrica crea el tipo correcto de personaje |
| `JuegoLuchaTest` | Flujo del juego, ganador, turnos con Mockito |

### Ejecutar el juego (modo interactivo)

```bash
# Compilar y ejecutar — podrás elegir nombres y clases en consola
mvn clean compile exec:java
```

El programa te pedirá:
1. Nombre y clase del Jugador 1 (Normal / Guerrero / Mago)
2. Nombre y clase del Jugador 2
3. En cada turno presionas **Enter** para que el jugador activo ataque

### Ejecutar pruebas

```bash
# Compilar
mvn clean compile

# Ejecutar todas las pruebas
mvn test

# Prueba específica
mvn test -Dtest=PersonajeTest

# Generar reporte de cobertura
mvn jacoco:report
# Ver en: target/site/jacoco/index.html
```

---

## ⚙️ GitHub Actions

El pipeline se ejecuta automáticamente en cada `push` a `main` o `develop`
y en cada Pull Request. Pasos:

1. ✅ Checkout del código
2. ✅ Configurar JDK 17
3. ✅ `mvn clean compile`
4. ✅ `mvn test`
5. ✅ `mvn jacoco:report`
6. 📦 Subir reporte de cobertura como artefacto
7. 📦 Subir resultados de pruebas como artefacto

---

## 🚀 Correr en GitHub Codespaces

1. En el repositorio, clic en **Code → Codespaces → Create codespace on main**
2. Esperar ~2 minutos a que el entorno esté listo
3. En la terminal:

```bash
mvn clean test
```

---

## 👥 Autores
Jhosed Alzate Garcia
Jose David Gonzales
Laura Vanessa Botina Delgado

- Estudiantes del curso de Ingeniería de Software II
- Docente: Ing. Jhon Haide Cano Beltran MSc.
