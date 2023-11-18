# 💻 Computer

## 📋 Running in the home way

> Follow these steps to run the bot via a docker container.
>
{style="note"}

* To run the gitlab monitor self-hosted on a server via docker,
1) Make sure you have a folder that you can store and run the bot.
2) Make sure you have an url for the gitlab webhook.
3) Check if you have [`config.json`](Self-hosting.md#config-json) and [`mongo.json`](Self-hosting.md#mongo-json) files on the root dir of your server.

## 💾 Computer requirements

> Your server should at least have 250Mb of ram.
>
{style="warning"}


## 🪛 Building and Running

1) First clone the repository
```Bash
git clone https://github.com/IdanKoblik/gitlab-monitor.git
cd gitlab-monitor/
```

2) Building the project
<tabs>
    <tab title="Linux & Mac">
        <code-block lang="gradle">
        ./gradlew bootJar
        </code-block>
    </tab>
    <tab title="Windows">
        <code-block lang="gradle">
        ./gradlew.bat bootJar
        </code-block>
    </tab>
</tabs>

3) Moving the jar file to the root dir
> Make sure you are moving the jar file from the root dir
>
{style="warning"}

<tabs>
    <tab title="Linux & Mac">
        <code-block lang="bash">
        mv build/libs/gitlab-monitor.jar .
        </code-block>
    </tab>
    <tab title="Windows">
        <code-block lang="console">
        move build\libs\gitlab-monitor.jar .
        </code-block>
    </tab>
</tabs>

4) Running the jar file
```Bash
java -jar gitlab-monitor.jar
```