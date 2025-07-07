# 🛒 Carrito de Compras - Proyecto de Ejemplo

Este repositorio contiene un proyecto desarrollado como parte del **período 66** de la asignatura **Programación Orientada a Objetos** en la Universidad Politécnica Salesiana.  
El objetivo principal es demostrar la aplicación de **patrones de diseño** en una aplicación Java con interfaz gráfica basada en **Swing**.

---
# Explicación del proyecto en video
https://youtu.be/tORv8mkXQq0
---

## 🎯 Objetivo del proyecto

Implementar un sistema educativo que simula un **carrito de compras**, utilizando buenas prácticas de diseño y una arquitectura desacoplada basada en:

- **MVC** (Modelo-Vista-Controlador)
- **DAO** (Data Access Object)
- Principios **SOLID** (SRP, DIP)

---

## 🛠️ Tecnologías utilizadas

- 💻 **Java 21**
- 🧰 **IntelliJ IDEA** (recomendado con el plugin de diseñador gráfico de interfaces Swing)
- ☕ **Swing** para la interfaz gráfica
- 📦 Estructura modular basada en paquetes: `modelo`, `dao`, `controlador`, `vista`, y `servicio`

---

## 🧱 Patrones de Diseño aplicados

- **MVC (Modelo - Vista - Controlador)**  
  Para separar la lógica de negocio de la interfaz gráfica.
- **DAO (Data Access Object)**  
  Para desacoplar el acceso a los datos, permitiendo migrar fácilmente entre diferentes fuentes (archivos, base de datos, etc.).
- **SRP y DIP** de los principios **SOLID**  
  Para asegurar una arquitectura mantenible, extensible y fácil de testear.

---

## 🚀 Instalación y ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Einar671/CarritoDeCompras.git
   ```
2. **Abrir el proyecto en IntelliJ IDEA** (o cualquier IDE compatible con Java).
3. **Compilar el proyecto** (el IDE lo hace automáticamente).
4. **Ejecutar desde la clase principal:**
   - Ubica y ejecuta `Main.java` dentro del proyecto.

---

## 💡 Uso básico

1. Al iniciar la aplicación, se desplegará la interfaz gráfica del carrito.
2. Puedes agregar, eliminar y modificar productos en el carrito.
3. Explora distintas implementaciones del DAO para ver cómo el patrón permite flexibilidad en el acceso a datos.
4. Prueba las funcionalidades para observar la separación entre la interfaz y la lógica de negocio.

---

## 📚 Recomendaciones

- Usa **IntelliJ IDEA** para aprovechar el editor visual de formularios `.form`.
- Experimenta cambiando las implementaciones del DAO para observar la flexibilidad del patrón y la arquitectura desacoplada.
- Consulta y modifica los paquetes `modelo`, `dao`, `controlador`, `vista` y `servicio` para entender y extender la aplicación.

---

## 📝 Ejemplo de código

```java
// Ejemplo: Crear un nuevo producto y agregarlo al carrito
Producto producto = new Producto("Teclado", 25.0, 1);
carrito.agregar(producto);
```

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si deseas sugerir mejoras o agregar nuevas funcionalidades, abre un Issue o Pull Request siguiendo las buenas prácticas de GitHub.

---

## © Créditos

Desarrollado como parte de la práctica académica en **Programación Orientada a Objetos – Periodo 66** en la Universidad Politécnica Salesiana.
