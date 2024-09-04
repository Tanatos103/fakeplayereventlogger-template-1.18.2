
# FakePlayerEventLogger

![GitHub](https://img.shields.io/badge/github-FakePlayerEventLogger-blue?logo=github) ![Minecraft](https://img.shields.io/badge/Minecraft-Mod-blue?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAACmElEQVR42mJ0fqosKi4OHDx48A6GMdcTg4G3AvISjLf6HnPvfgax+IHCAB3SgMrCCoJDw75+C0c3nz5uWefv69k5dY5cnTp0/GpRsRAwMAZVUBH39/+v7yQv7O4I7gnIv+TQ9tGgRf9TgyMyDFDFTYtdJFCz6UlH/jwnHzj3gAeqkH+/fvX7x4MdUOHDgRzV2JiQAIA2CNHj179+5NrwDw9DhCzBTyJ1IAw3lP9MZ3gIJiJm4Ii+vr2zZ89GoQi6fPnycOfOhXC1oS0EABIz8z9PPYgHgFwUwLSkOhMtiMiADQy1ovVzsQzfrhw4fo9PD5YkQl8RZQAAINiMeGjNzJkFIKnhv6FLJgfEBDHDp06e/fv2cPHiwdVJS0sfMzAMhOAZFAAAFDBAAc5AGAbU+AAkBDCBRDtgCcfK+ANCAOH8Fg8PAxMnTp1evXrwDS+4RQ0cZQEDBsH/9em4sR7h2CiwCiiBvHQagFAAB2LgMSaAVzicAkj5dJgdEBGACV8f/h87AHiABX3AiP3Z4f/AeJAEAhJSkZWFn7S+oFGjRuXKzA1kBqCY8ePAgnr8lADFnLnPnv1M4WZ+4d6QhQaGnTx8Rk1f8KmpwHA7h9HrY8fUoGBgZ0+eLFU0uWAEBraAQgWywcEIL0kSE5FUpgAw7JAwMDAIChpSk0l3cBDkwMjCQkJKBCA1hlQWvwr5xMEODI1zUAyOwwGQAHDIAHY2KiAjEoiqQESATDU2IKhDQECtCCaO1C4MGDawDKMDR8+XJw+fZrw4cMbGRnTHx8fphOTgyIDFQFgB9Wi4NB/f//Q0pUBf0RkQzL05MQcO3aMyO/sZWB0gA6Bz8DA4KBCVBiBgKDEMA0KoBN0aCzAxtAQATFB09+eBFMnUAkVBmAEBR+XFCqAQCLAEjL9CIyBCXAlAAUJHjV7evfuWz0UkpUSUVJBYeQFYhJsCeg1gBw6JevDhwdGzZsiUbwDJwC4cwKAYwgkGLFoAIAVPB+/Lh48SJUp7YJHfbwTAoj24CBdyOhwgqIBgJQ9oFwA/5gEpgGxyUgZ9QAUAADU9IgAAAAg1OUlKBF0cAAAAASUVORK5CYII=)


## 🌟 Características

- **Registro de eventos de bots**: Captura eventos de aparición, acciones, muerte y desconexión de FakePlayers/bots.
- **Registro de comando de muerte**: Captura eventos cuando se usa el comando `/player {nombre del bot} kill`.
- **Configuración personalizada**: Configura la dirección del servidor y el puerto.
- **Comunicación asíncrona**: Comunicación de socket para minimizar el impacto en el rendimiento del juego.
- **Formato JSON**: Los eventos se registran en formato JSON para facilitar su análisis.

### 🔧 Próximas Mejoras

- Optimización de la gestión de eventos y rendimiento.
- Soporte para más tipos de eventos de jugadores falsos.

## 🛠 Requisitos

- **Minecraft**: 1.18.2
- **Fabric Loader**: >= 0.14.2
- **Java**: >= 17
- **Mod Carpet**

## 🚀 Instalación

1. **Pre-requisitos**: Asegúrate de tener **Fabric** y el mod **Carpet** instalados.
2. **Descarga**: Obtén la última versión de **FakePlayerEventLogger** desde la [página de lanzamientos](https://github.com/tu-repositorio/releases).
3. **Instalación**: Coloca el archivo `.jar` descargado en tu carpeta `mods` de Minecraft.
4. **Inicio**: Ejecuta Minecraft con el cargador de **Fabric**.

## ⚙️ Configuración

El mod crea y utiliza un archivo de configuración ubicado en `config/carpet/eventlogs.properties`. Este archivo se genera automáticamente con valores predeterminados en la primera ejecución.

### Opciones de Configuración

- **`server_host`**: Establece la dirección del host para el servidor de socket (valor predeterminado: `"localhost"`).
- **`server_port`**: Configura el puerto para el servidor de socket (valor predeterminado: `25566`).

# FakePlayerEventLogger



## 📋 Uso

Una vez instalado y configurado, el mod registrará automáticamente los eventos de jugadores falsos. Estos eventos se enviarán al servidor de socket configurado en formato JSON.

### Ejemplos de Eventos Registrados

#### Evento de Spawn
```json
{
  "name": "JugadorFalso1",
  "uuid": "00000000-0000-0000-0000-000000000001",
  "coords": [100.5, 64.0, -200.5],
  "action": "spawn",
  "dimension": "minecraft:overworld",
  "tagserver": "fabric 1.18.2",
  "name_player_executor": "JugadorReal",
  "uuid_player_executor": "12345678-1234-5678-abcd-1234567890ab"
}
```

#### Evento de Muerte
```json
{
  "name": "JugadorFalso1",
  "uuid": "00000000-0000-0000-0000-000000000001",
  "coords": [105.5, 70.0, -195.5],
  "action": "player_death",
  "dimension": "minecraft:overworld",
  "tagserver": "fabric 1.18.2",
  "name_player_executor": "JugadorFalso1",
  "uuid_player_executor": "00000000-0000-0000-0000-000000000001",
  "reason": "fall"
}
```

#### Evento de Desconexión
```json
{
  "name": "JugadorFalso1",
  "uuid": "00000000-0000-0000-0000-000000000001",
  "coords": [110.5, 65.0, -205.5],
  "action": "disconnect",
  "dimension": "minecraft:overworld",
  "tagserver": "fabric 1.18.2",
  "name_player_executor": "JugadorFalso1",
  "uuid_player_executor": "00000000-0000-0000-0000-000000000001",
  "reason": "Disconnected"
}
```


## 🛠 Desarrollo

### Estructura del Proyecto

- **`SocketClient.java`**: Maneja la comunicación asíncrona con el servidor de socket.
- **`PlayerInfo.java`**: Representa la estructura de datos para la información del jugador.
- **`Config.java`**: Gestiona la configuración del mod.
- **`FakePlayerEventMixin.java`**: Contiene el mixin para interceptar eventos de muerte y desconexión de jugadores falsos.
- **`PlayerKillCommandMixin.java`**: Contiene el mixin para interceptar el comando de matar jugadores falsos.

### Compilación

1. Clona el repositorio.
2. Configura tu entorno de desarrollo para **Fabric**.
3. Ejecuta `./gradlew build` para compilar el mod.

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Si deseas mejorar este proyecto, no dudes en enviar un **Pull Request**. 

### 🧩 Contribuciones específicas que puedes hacer:

- Mejorar la eficiencia del manejo de eventos.
- Añadir soporte para versiones adicionales de **Minecraft**.
- Integrar nuevas características como un sistema de notificaciones en tiempo real.
- Expandir los tipos de eventos registrados para jugadores falsos.

## 📝 Licencia

Este proyecto está licenciado bajo la **Licencia CC0-1.0**. Consulta el archivo LICENSE para más detalles.

## 💬 Agradecimientos

- Agradecemos al equipo del mod **Carpet** por proporcionar la base de esta extensión.
- Gracias a la comunidad de **Fabric** por sus excelentes herramientas de modding y soporte continuo.
