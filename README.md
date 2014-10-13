# API Orchestration Demo

This repository is demo of API Orchestration.

## Start Servers

Run command `foreman start`, start orchestration server (RxNetty) on localhost:8080 and start RESTful API server (Ruby on Rails) on localhost:3000.

```sh
$ foreman start
22:12:58 rest.1    | started with pid 44494
22:12:58 rxnetty.1 | started with pid 44495
22:12:58 rxnetty.1 | 22:12:58 jetty.1 | started with pid 44496
22:12:58 rest.1    | 22:12:58 guard.1 | started with pid 44497
22:12:58 rest.1    | 22:12:58 rails.1 | started with pid 44498
22:12:58 rest.1    | 22:12:58 rails.1 | Warning: You're using Rubygems 2.0.14 with Spring. Upgrade to at least Rubygems 2.1.0 and run `gem pristine --all` for better startup performance.
22:12:58 rest.1    | 22:12:58 guard.1 | 22:12:58 - INFO - Guard is using Tmux to send notifications.
22:12:58 rest.1    | 22:12:58 guard.1 | 22:12:58 - INFO - Guard is using TerminalTitle to send notifications.
22:12:58 rest.1    | 22:12:58 guard.1 | 22:12:58 - INFO - Guard is now watching at '/Users/kentaro/workspace/api-orchestration-demo/rest'
22:12:59 rest.1    | 22:12:59 rails.1 | => Booting WEBrick
22:12:59 rest.1    | 22:12:59 rails.1 | => Rails 4.1.0 application starting in development on http://0.0.0.0:3000
22:12:59 rest.1    | 22:12:59 rails.1 | => Run `rails server -h` for more startup options
22:12:59 rest.1    | 22:12:59 rails.1 | => Notice: server is listening on all interfaces (0.0.0.0). Consider using 127.0.0.1 (--binding option)
22:12:59 rest.1    | 22:12:59 rails.1 | => Ctrl-C to shutdown server
22:12:59 rest.1    | 22:12:59 rails.1 | Prefix Verb URI Pattern          Controller#Action
22:12:59 rest.1    | 22:12:59 rails.1 |   user GET  /users/:id(.:format) users#show
22:12:59 rest.1    | 22:12:59 rails.1 | [2014-10-13 22:12:59] INFO  WEBrick 1.3.1
22:12:59 rest.1    | 22:12:59 rails.1 | [2014-10-13 22:12:59] INFO  ruby 2.0.0 (2014-05-08) [universal.x86_64-darwin13]
22:12:59 rest.1    | 22:12:59 rails.1 | [2014-10-13 22:12:59] INFO  WEBrick::HTTPServer#start: pid=44498 port=3000
22:13:00 rxnetty.1 | 22:13:00 jetty.1 | :compileJava UP-TO-DATE
22:13:00 rxnetty.1 | 22:13:00 jetty.1 | :processResources UP-TO-DATE
22:13:00 rxnetty.1 | 22:13:00 jetty.1 | :classes UP-TO-DATE
22:13:01 rxnetty.1 | 22:13:01 jetty.1 | :run
22:13:01 rxnetty.1 | 22:13:01 jetty.1 | GET /users
```
