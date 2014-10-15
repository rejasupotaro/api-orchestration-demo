# API Orchestration Demo

This repository is demo of API Orchestration.

## Start Servers

Run command `foreman start`, start orchestration server (RxNetty) on localhost:8080 and start RESTful API server (Ruby on Rails) on localhost:3000.

```sh
$ foreman start
00:10:32 rest.1    | started with pid 64488
00:10:32 rxnetty.1 | started with pid 64489
00:10:32 rxnetty.1 | 00:10:32 jetty.1 | started with pid 64490
00:10:32 rest.1    | 00:10:32 guard.1 | started with pid 64491
00:10:32 rest.1    | 00:10:32 rails.1 | started with pid 64492
00:10:32 rest.1    | 00:10:32 rails.1 | Warning: You're using Rubygems 2.0.14 with Spring. Upgrade to at least Rubygems 2.1.0 and run `gem pristine --all` for better startup performance.
00:10:33 rest.1    | 00:10:33 guard.1 | 00:10:33 - INFO - Guard is using Tmux to send notifications.
00:10:33 rest.1    | 00:10:33 guard.1 | 00:10:33 - INFO - Guard is using TerminalTitle to send notifications.
00:10:33 rest.1    | 00:10:33 guard.1 | 00:10:33 - INFO - Guard is now watching at '/Users/kentaro/workspace/api-orchestration-demo/rest'
00:10:34 rest.1    | 00:10:34 rails.1 | => Booting WEBrick
00:10:34 rest.1    | 00:10:34 rails.1 | => Rails 4.1.0 application starting in development on http://0.0.0.0:3000
00:10:34 rest.1    | 00:10:34 rails.1 | => Run `rails server -h` for more startup options
00:10:34 rest.1    | 00:10:34 rails.1 | => Notice: server is listening on all interfaces (0.0.0.0). Consider using 127.0.0.1 (--binding option)
00:10:34 rest.1    | 00:10:34 rails.1 | => Ctrl-C to shutdown server
00:10:34 rest.1    | 00:10:34 rails.1 | Prefix Verb URI Pattern          Controller#Action
00:10:34 rest.1    | 00:10:34 rails.1 |   user GET  /users/:id(.:format) users#show
00:10:34 rest.1    | 00:10:34 rails.1 | [2014-10-16 00:10:34] INFO  WEBrick 1.3.1
00:10:34 rest.1    | 00:10:34 rails.1 | [2014-10-16 00:10:34] INFO  ruby 2.0.0 (2014-05-08) [universal.x86_64-darwin13]
00:10:34 rest.1    | 00:10:34 rails.1 | [2014-10-16 00:10:34] INFO  WEBrick::HTTPServer#start: pid=64492 port=3000
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | :compileJava UP-TO-DATE
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | :processResources UP-TO-DATE
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | :classes UP-TO-DATE
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | :run
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | GET /top_page
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | GET /users
00:10:36 rxnetty.1 | 00:10:36 jetty.1 | GET /recipe
```
