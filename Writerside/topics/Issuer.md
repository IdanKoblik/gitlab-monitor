# 📝 Issuer

> Issuer is a self-hosting feature that allows a user to create an issue via a simple slash command.
>

## 📊 Pre requirements

* `Access token`—Create new User or use an existing one and generate an access token. The user that has the access token that you put is the user who will send the issues. <br>
* `Gitlab url`-A gitlab domain you can either use gitlab.com or a self-hosted version.

## 📂 Guide

> Follow these steps to run the bot via a docker container.
>
{style="note"}

* Start by using `issuer-init` slash command and pass a `project-id` as an argument, this argument will be stored in the database and will provide auto completing when create an issue.

## 📬 Creating an issue.

* To create an issue, write `create-issue` slash command and pass issue `title`, `description`, and `project id`.

## 📔 Remove a project from DB

* To remove an issue, write `remove-issuer-project` slash command and pass the `project-id` as an argument.




