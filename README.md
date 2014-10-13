# API Orchestration Demo

This repository is demo of API Orchestration.

## Start Servers

Run command `foreman start`, start orchestration server (RxNetty) on localhost:8080 and start RESTful API server on localhost:3000.

```
$ foreman start
11:34:49 rest.1    | started with pid 8288
11:34:49 rxnetty.1 | started with pid 8289
11:34:49 rest.1    | 11:34:49 guard.1 | started with pid 8290
11:34:49 rest.1    | 11:34:49 rails.1 | started with pid 8291
11:34:49 rxnetty.1 | 11:34:49 jetty.1 | started with pid 8292
11:34:51 rest.1    | 11:34:51 guard.1 | 11:34:51 - INFO - Guard is using TerminalTitle to send notifications.
11:34:51 rest.1    | 11:34:51 guard.1 | 11:34:51 - INFO - Guard is now watching at '/Users/kentaro-takiguchi/workspace/api-orchestration-demo/rest'
11:34:51 rest.1    | 11:34:51 guard.1 | 11:34:51 - INFO - LiveReload is waiting for a browser to connect.
11:34:53 rxnetty.1 | 11:34:53 jetty.1 | Parallel execution is an incubating feature.
11:34:56 rxnetty.1 | 11:34:56 jetty.1 | :compileJava UP-TO-DATE
11:34:56 rxnetty.1 | 11:34:56 jetty.1 | :processResources UP-TO-DATE
11:34:56 rxnetty.1 | 11:34:56 jetty.1 | :classes UP-TO-DATE
11:34:56 rxnetty.1 | 11:34:56 jetty.1 | :run
11:34:56 rxnetty.1 | 11:34:56 jetty.1 | /
```
