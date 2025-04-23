#!/bin/bash

# Crear carpeta bin si no existe
mkdir -p bin

echo "🛠️ Compilando el proyecto..."
javac -d bin src/game/*.java src/game/control/*.java src/game/entities/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
    echo "🚀 Ejecutando el juego..."
    java -cp bin game.Main
else
    echo "❌ Error de compilación"
fi
