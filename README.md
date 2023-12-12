<h1 align="center">Gitlab monitor 👋</h1>

> Gitlab-monitor is an open source discord gitlab project monitor providing option to monitor you gitlab projects events
>

## 📂 Guide
> Follow this guide to get a better understanding about the bot.
>
[Full bot guide](idankoblik.github.io/gitlab-monitor/)

### 🧭 Commands
> Every command powered by a slash command.

#### 📓 Webhook commands
* `webhook-init`-This command allows you to get notifications on gitlab webhook events.
* `remove-webhook-projects`-This command allows you to disconnect a project from a channel.
* `remove-webhook-channel`-This command allows you to disconnect a channel containing gitlab projects inside him from the bot.
* `webhook-tokens`-This command allows you to see every project webhook token that connected to a certain channel.

#### 📔 Quality of life commands
* `notify`—This command allows you to get mentioned every time that a pipeline fails.
* `remove-notify`—This command allows you to disable the notify command.

#### 📝 Issuer
* `issuer-init`-This command allows you to enable the feature of creating issues via a slash command.
* `issuer-remove-project`-This command allows you to disconnect a project from the bot.
* `issuer-tokens`-This command allows you to see every projectId(Including the project name) that connected to the bot.

#### 📜 Creating an issue
> To create an issue via a simple slash command write the following command.
>

> Keep in mind that the public version of the bot only supports gitlab.com.<br> So if you want to create an issue via the bot on self-hosted version of Gitlab, Self host the bot, so he would get access to your self-hosted version of Gitlab.
>

* `create-issue`-`[Issue title]` `[Issue description]` `[projectId]`

## Author
👤 [Idan Koblik](https://github.com/IdanKoblik)
* Discord: 0x62657461
* Github: @IdanKoblik

## 📝 License
Copyright © 2023 [Idan Koblik](https://github.com/IdanKoblik). <br>
This project is [Apache License 2.0](https://github.com/IdanKoblik/gitlab-monitor/blob/main/LICENSE) licensed.
