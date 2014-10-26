# API Orchestration Demo

This repository is demo of API Orchestration.

```
[Android App] <--> [RxNetty (API Orchestration)] <--> [Garage (RESTful API)]
```

## Start Servers

Run command `foreman start`, start orchestration server (RxNetty) on localhost:8080 and start RESTful API server (Garage) on localhost:3000.

## Install Android Demo App

```
$ cd android-client
$ ./gradlew assemble
$ adb install app/build/outputs/apk/app-debug.apk
```

<img src="images/android-client.png" width="300px">
