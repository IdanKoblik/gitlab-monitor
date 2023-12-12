# 🐬 Docker

## 📦 Running in the docker way

> Follow these steps to run the bot via a docker container.
>
{style="note"}

* To run the gitlab monitor self-hosted on a server via docker,
1) Make sure you have a working server that you can run the bot.
2) Make sure you have an url for the gitlab webhook.
3) Check if you have [`config.json`](Self-hosting.md#config-json) and [`mongo.json`](Self-hosting.md#mongo-json) files on the root dir of your server.

> Be aware that if you are using the docker way and a new update of the bot comes out to update the bot just reseat the server.
>
{style="tip"}

## 💾 Server requirements

> Your server should at least have 250Mb of ram.
>
{style="warning"}

## 📥 Pulling the image
* To pull the image to your server use:
```bash
docker pull ghcr.io/idankoblik/gitlab-monitor:main
```

> Make sure that every time you reseat your server this command is used.
>
{style="tip"}

## 🔗 Running the bot from the image
* After you pulled the image to your server, you should use the following command to run the jar file:
```bash
java -jar /app/gitlab-monitor.jar
```