# PvZ_Concurrente (Plantas vs Zombis en Consola con Java)

Este proyecto es una implementación por consola inspirada en *Plantas vs Zombis*, utilizando **Java y concurrencia**. Cada zombi, planta y proyectil es un hilo independiente sincronizado con `ReentrantLock`, y el tablero se representa como una matriz compartida con acceso seguro.

---

## 🧱 Arquitectura del Proyecto

```
src/
└── game/
    ├── Main.java               # Punto de entrada
    ├── Game.java               # Control general del juego
    ├── GameLoop.java           # Imprime el tablero en bucle
    ├── Board.java              # Representa el tablero con celdas

    ├── control/
    │   ├── Cell.java         # Celda con Lock
    │   ├── EntityType.java   # Enum de tipos de entidad
    │   ├── ConsoleLogger.java # Registro de eventos thread-safe
    │   └── InputHandler.java  # Entrada por consola

    └── entities/
        ├── Zombie.java           # Hilo de zombi que avanza
        ├── ZombieSpawner.java    # Generador automático de zombis
        ├── PeaShooter.java       # Planta que dispara
        ├── Projectile.java       # Proyectil que impacta zombis
        └── Plant.java           # (Opcional) clase base para plantas
```

---

## 🕹️ Cómo jugar

### 1. Ejecutar con `run_game.sh` (recomendado)

```bash
chmod +x run_game.sh
./run_game.sh
```

Este script compila todos los archivos necesarios y lanza el juego directamente.

### 2. Alternativamente: compilar y ejecutar manualmente

```bash
javac -d bin src/game/*.java src/game/control/*.java src/game/entities/*.java
java -cp bin game.Main
```

### 3. Comandos disponibles en el juego

| Comando         | Descripción                               |
|----------------|---------------------------------------------|
| `plant <fila>` | Planta una nueva PeaShooter en esa fila     |
| `info`         | Muestra el estado actual del tablero         |
| `exit`         | Finaliza el juego de forma segura            |

---

## 🔧 Funcionamiento interno

- Cada celda del tablero se protege con un `ReentrantLock`
- Los proyectiles avanzan y eliminan zombis si colisionan
- Los zombis también mueren si pisan un proyectil
- La consola muestra el tablero y los mensajes del juego cada segundo

---

## 💡 Ideas futuras

- Agregar vida (`hp`) a los zombis
- Nuevos tipos de plantas
- Sistema de soles o energía para limitar plantaciones
- Condición de victoria y derrota en tiempo real

---

## 👤 Autor
Jerónimo Toro C - Proyecto de concurrencia en Java estilo PvZ

