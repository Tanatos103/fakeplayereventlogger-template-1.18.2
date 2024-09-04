# FakePlayerEventLogger ![GitHub](https://img.shields.io/badge/github-FakePlayerEventLogger-blue?logo=github) ![Minecraft](https://img.shields.io/badge/Minecraft-Mod-blue?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAACmElEQVR42mJ0fqosKi4OHDx48A6GMdcTg4G3AvISjLf6HnPvfgax+IHCAB3SgMrCCoJDw75+C0c3nz5uWefv69k5dY5cnTp0/GpRsRAwMAZVUBH39/+v7yQv7O4I7gnIv+TQ9tGgRf9TgyMyDFDFTYtdJFCz6UlH/jwnHzj3gAeqkH+/fvX7x4MdUOHDgRzV2JiQAIA2CNHj179+5NrwDw9DhCzBTyJ1IAw3lP9MZ3gIJiJm4Ii+vr2zZ89GoQi6fPnycOfOhXC1oS0EABIz8z9PPYgHgFwUwLSkOhMtiMiADQy1ovVzsQzfrhw4fo9PD5YkQl8RZQAAINiMeGjNzJkFIKnhv6FLJgfEBDHDp06e/fv2cPHiwdVJS0sfMzAMhOAZFAAAFDBAAc5AGAbU+AAkBDCBRDtgCcfK+ANCAOH8Fg8PAxMnTp1evXrwDS+4RQ0cZQEDBsH/9em4sR7h2CiwCiiBvHQagFAAB2LgMSaAVzicAkj5dJgdEBGACV8f/h87AHiABX3AiP3Z4f/AeJAEAhJSkZWFn7S+oFGjRuXKzA1kBqCY8ePAgnr8lADFnLnPnv1M4WZ+4d6QhQaGnTx8Rk1f8KmpwHA7h9HrY8fUoGBgZ0+eLFU0uWAEBraAQgWywcEIL0kSE5FUpgAw7JAwMDAIChpSk0l3cBDkwMjCQkJKBCA1hlQWvwr5xMEODI1zUAyOwwGQAHDIAHY2KiAjEoiqQESATDU2IKhDQECtCCaO1C4MGDawDKMDR8+XJw+fZrw4cMbGRnTHx8fphOTgyIDFQFgB9Wi4NB/f//Q0pUBf0RkQzL05MQcO3aMyO/sZWB0gA6Bz8DA4KBCVBiBgKDEMA0KoBN0aCzAxtAQATFB09+eBFMnUAkVBmAEBR+XFCqAQCLAEjL9CIyBCXAlAAUJHjV7evfuWz0UkpUSUVJBYeQFYhJsCeg1gBw6JevDhwdGzZsiUbwDJwC4cwKAYwgkGLFoAIAVPB+/Lh48SJUp7YJHfbwTAoj24CBdyOhwgqIBgJQ9oFwA/5gEpgGxyUgZ9QAUAADU9IgAAAAg1OUlKBF0cAAAAASUVORK5CYII=)

FakePlayerEventLogger es una extensión de mod para Minecraft del mod Carpet, diseñada para registrar eventos relacionados con jugadores falsos (bots) en el juego. Envía estos eventos a un servidor de socket especificado para su posterior procesamiento o análisis.

## Características

- Registra eventos de aparición de jugadores falsos
- Nombre de equipo de bot, host del servidor y puerto configurables
- Comunicación de socket asíncrona para un impacto mínimo en el juego
- Formato JSON para facilitar el análisis de datos

## Requisitos

- Minecraft 1.18.2
- Fabric Loader >=0.14.2
- Java >=17
- Mod Carpet

## Instalación

1. Asegúrate de tener instalados Fabric y el mod Carpet.
2. Descarga la última versión de FakePlayerEventLogger desde la página de lanzamientos.
3. Coloca el archivo `.jar` descargado en tu carpeta de `mods` de Minecraft.
4. Inicia Minecraft con el cargador de Fabric.

## Configuración

El mod utiliza un archivo de configuración ubicado en `config/carpet/envetlogs.properties`. Si no existe, se creará con valores predeterminados en la primera ejecución.

Puedes configurar las siguientes propiedades:

- `bot_team_name`: El nombre del equipo para los bots (predeterminado: "zBot")
- `server_host`: La dirección del host para el servidor de socket (predeterminado: "localhost")
- `server_port`: El puerto para el servidor de socket (predeterminado: 25566)

## Uso

Una vez instalado y configurado, el mod registrará automáticamente los eventos de aparición de jugadores falsos. Estos eventos se envían al servidor de socket especificado en formato JSON.

Ejemplo de un evento registrado:

```json
{
  "name": "JugadorFalso1",
  "uuid": "00000000-0000-0000-0000-000000000001",
  "coords": [100.5, 64.0, -200.5],
  "action": "spawn",
  "dimension": "minecraft:overworld",
  "name_player_executor": "JugadorReal",
  "uuid_player_executor": "12345678-1234-5678-abcd-1234567890ab"
}
```

## Desarrollo

### Estructura del Proyecto

- `SocketClient.java`: Maneja la comunicación asíncrona con el servidor de socket.
- `PlayerInfo.java`: Representa la estructura de datos para la información del jugador.
- `Config.java`: Gestiona la configuración del mod.
- `FakePlayerLoggerMixin.java`: Contiene el mixin para interceptar eventos de aparición de jugadores falsos.

### Compilación

1. Clona el repositorio
2. Configura tu entorno de desarrollo para modding de Fabric
3. Ejecuta `./gradlew build` para compilar el mod

## Contribuir

![GitHub](https://img.shields.io/badge/github-FakePlayerEventLogger-blue?logo=github) ![Minecraft](https://img.shields.io/badge/Minecraft-Mod-blue?logo=minecraft) ![Modding](https://img.shields.io/badge/Modding-Java-blue?logo=java) 

¡Las contribuciones son bienvenidas! No dudes en enviar un Pull Request.

## Licencia

Este proyecto está licenciado bajo la Licencia CC0-1.0 - consulta el archivo LICENSE para más detalles.

## Agradecimientos

- Al equipo del mod Carpet por proporcionar la base para esta extensión
- A la comunidad de Fabric por sus herramientas de modding y soporte