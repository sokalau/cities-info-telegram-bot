# Tourist Telegram Bot
Simple tourist Telegram bot is written on with Spring Boot

@awesome_tourist_bot

# How to set up the project
 - JDK 11
 - MySQL database. Configure datasource in application.yml (username, password, url). Then run sql scripts to configure database
 - Download Ngrok from [here](https://ngrok.com/download) and run it as follow,

```bash
$ ./ngrok http 8080 -> then open localhost:4040 and copy https ngrok public url
```
 - Run ./setWebhook.sh (configure telegram token stored in application.yml and saved ngrok public url)
 
 # Telegram Bot Management:
 To manage cities information use next REST API
 - Add or replace: POST /cities/add name and description string params
 - Remove: DELETE /cities/remove/{name}
 - Read: GET /cities/about/{name}
 
 - Data stored in db in russian, but you can enter city name in any language and get translated info using Yandex Translate API
 - If there is no such city in db then bot connected to GeoDB Cities and try to find something there
 
 Examples:
 - Moscow -> don't forget to visit red square. well, cum can not go)))
 - Москва -> Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))
 - Берлин -> Город: Берлин
Страна: Германия
Широта: 52.516666666
Долготы: 13.383333333 (using GebDB Cities API)
 
