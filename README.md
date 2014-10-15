# API Orchestration Demo

This repository is demo of API Orchestration.

## Start Servers

Run command `foreman start`, start orchestration server (RxNetty) on localhost:8080 and start RESTful API server (Ruby on Rails) on localhost:3000.

```sh
$ foreman start
03:08:31 rest.1    | started with pid 3470
03:08:31 rxnetty.1 | started with pid 3471
03:08:31 rxnetty.1 | 03:08:31 rxnetty.1 | started with pid 3472
03:08:31 rest.1    | 03:08:31 guard.1 | started with pid 3473
03:08:31 rest.1    | 03:08:31 rails.1 | started with pid 3474
03:08:33 rest.1    | 03:08:33 rails.1 | => Booting WEBrick
03:08:33 rest.1    | 03:08:33 rails.1 | => Rails 4.1.0 application starting in development on http://0.0.0.0:3000
03:08:33 rest.1    | 03:08:33 rails.1 | => Run `rails server -h` for more startup options
03:08:33 rest.1    | 03:08:33 rails.1 | => Notice: server is listening on all interfaces (0.0.0.0). Consider using 127.0.0.1 (--binding option)
03:08:33 rest.1    | 03:08:33 rails.1 | => Ctrl-C to shutdown server
03:08:33 rest.1    | 03:08:33 rails.1 | Prefix Verb URI Pattern          Controller#Action
03:08:33 rest.1    | 03:08:33 rails.1 |   user GET  /users/:id(.:format) users#show
03:08:33 rest.1    | 03:08:33 rails.1 | [2014-10-16 03:08:33] INFO  WEBrick 1.3.1
03:08:33 rest.1    | 03:08:33 rails.1 | [2014-10-16 03:08:33] INFO  ruby 2.0.0 (2014-05-08) [universal.x86_64-darwin13]
03:08:33 rest.1    | 03:08:33 rails.1 | [2014-10-16 03:08:33] INFO  WEBrick::HTTPServer#start: pid=3474 port=3000
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 |
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | :processResources UP-TO-DATE
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | :classes
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | :run
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | => Booting Orchestration Layer
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | => RxNetty application starting on http://0.0.0.0:8080
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | GET /top_page
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | GET /users
03:08:34 rxnetty.1 | 03:08:34 rxnetty.1 | GET /recipe
```
