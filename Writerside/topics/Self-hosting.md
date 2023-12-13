# 📻 Self-hosting

## 📂 Guide

> Follow these steps to run the bot locally.
>
{style="note"}

* If you want to self-host the bot, you have to general options:

* First running the bot on a server using docker.
* Second, running the bot on your computer via Gradle.

## 📊 Pre requirements
> In both ways, you need to have these two files in your root dir.

## 🔧 Discord bot application
> Make sure you have a discord bot application for getting his token.

* Follow this link to create a discord application
  [](https://discord.com/developers/applications)

## 🧮 MongoDB database
> Make sure you have a running mongodb database that need to run when the bot online.

* Check out this tutorial from MongoDB for creating a MongoDB database:
  [](https://www.mongodb.com/basics/create-database)

## 📑 mongo.json
> This file includes the mongodb server config.

```json
{
    "database": "db name",
    "connection_string": "db connection string"
}
```

## 📑 config.json
> This file includes the general bot config include discordToken, websiteUrl and webhookUrl.

```json
{
	"discord_token": "bot discord token",
    "webhook_url": "your gitlab webhook url",
    "gitlab_url": "your gitlab url (format - gitlab.com)",
}
```