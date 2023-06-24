# Dropwizard Max Connections

By default, Dropwizard allows 1024 concurrent connections. I am troubleshooting a situation wherein an errant client exhausts all the connections.

## Problem

Trigger consumption of all available concurrent connections.

```java
WARN  [2023-06-16 16:09:11.836] [org.eclipse.jetty.util.thread.QueuedThreadPool] InstrumentedQueuedThreadPool[dw]@67a3dd86{STARTED,8<=1024<=1024,i=0,r=-1,q=1024}[ReservedThreadExecutor@38ff5515{reserved=0/1,pending=1}] rejected Accept@727af385[java.nio.channels.SocketChannel[connected local=/10.x.x.x:8080 remote=/10.x.x.x:52428]]
```

## Quick Start

This project requires Java, Maven, Dropwizard, and optionally, Docker.

### Installing

Clone this repository.

```console
git clone https://github.com/gkhays/dw-max-connections.git
cd dw-max-connections
```

Build the Java library (JAR).

```console
mvn package
```

## How to start the ConnectionTest application

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/dw-max-connections-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`

## Troubleshooting

```java
127.0.0.1 - - [23/Jun/2023:22:49:24 +0000] "GET /message HTTP/1.1" 200 30 "-" "Jersey/2.33 (HttpUrlConnection 1.8.0_232)" 2
INFO  [2023-06-23 22:49:24,907] io.github.dropwizard.client.ClientThread: OK
WARN  [2023-06-23 22:49:24,928] org.eclipse.jetty.util.thread.QueuedThreadPool: InstrumentedQueuedThreadPool[dw]@ba1f559{STARTED,4<=4<=4,i=0,r=-1,q=8}[ReservedThreadExecutor@2a09a4d5{reserved=0/1,pending=1}] rejected org.eclipse.jetty.io.ManagedSelector$DestroyEndPoint@2832a6b7
WARN  [2023-06-23 22:49:24,928] org.eclipse.jetty.util.thread.QueuedThreadPool: InstrumentedQueuedThreadPool[dw]@ba1f559{STARTED,4<=4<=4,i=0,r=-1,q=8}[ReservedThreadExecutor@2a09a4d5{reserved=0/1,pending=1}] rejected org.eclipse.jetty.io.ManagedSelector$DestroyEndPoint@f7b3a04
WARN  [2023-06-23 22:49:24,930] org.eclipse.jetty.util.thread.QueuedThreadPool: InstrumentedQueuedThreadPool[dw]@ba1f559{STARTED,4<=4<=4,i=0,r=-1,q=8}[ReservedThreadExecutor@2a09a4d5{reserved=0/1,pending=1}] rejected CEP:SocketChannelEndPoint@73e21ba1{l=/127.0.0.1:8080,r=/127.0.0.1:56643,OPEN,fill=FI,flush=-,to=89/60000}{io=1/0,kio=1,kro=1}->HttpConnection@551dd63b[p=HttpParser{s=START,0 of -1},g=HttpGenerator@1c0ecf3c{s=START}]=>HttpChannelOverHttp@4a70e995{s=HttpChannelState@2d38d302{s=IDLE rs=BLOCKING os=OPEN is=IDLE awp=false se=false i=true al=0},r=1,c=false/false,a=IDLE,uri=null,age=0}:runFillable:BLOCKING
WARN  [2023-06-23 22:49:24,937] org.eclipse.jetty.util.thread.strategy.EatWhatYouKill:
! java.util.concurrent.RejectedExecutionException: CEP:SocketChannelEndPoint@73e21ba1{l=/127.0.0.1:8080,r=/127.0.0.1:56643,OPEN,fill=FI,flush=-,to=91/60000}{io=1/0,kio=1,kro=1}->HttpConnection@551dd63b[p=HttpParser{s=START,0 of -1},g=HttpGenerator@1c0ecf3c{s=START}]=>HttpChannelOverHttp@4a70e995{s=HttpChannelState@2d38d302{s=IDLE rs=BLOCKING os=OPEN is=IDLE awp=false se=false i=true al=0},r=1,c=false/false,a=IDLE,uri=null,age=0}:runFillable:BLOCKING
! at org.eclipse.jetty.util.thread.QueuedThreadPool.execute(QueuedThreadPool.java:716)
! at org.eclipse.jetty.util.thread.strategy.EatWhatYouKill.execute(EatWhatYouKill.java:375)
! at org.eclipse.jetty.util.thread.strategy.EatWhatYouKill.doProduce(EatWhatYouKill.java:310)
! at org.eclipse.jetty.util.thread.strategy.EatWhatYouKill.tryProduce(EatWhatYouKill.java:173)
! at org.eclipse.jetty.util.thread.strategy.EatWhatYouKill.run(EatWhatYouKill.java:131)
! at org.eclipse.jetty.util.thread.ReservedThreadExecutor$ReservedThread.run(ReservedThreadExecutor.java:409)
! at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:883)
! at org.eclipse.jetty.util.thread.QueuedThreadPool$Runner.run(QueuedThreadPool.java:1034)
! at java.lang.Thread.run(Thread.java:748)

Call stack
QueuedThreadPool.java:716
EatWhatYouKill.java:375
EatWhatYouKill.java:310
EatWhatYouKill.java:173
EatWhatYouKill.java:131
ReservedThreadExecutor.java:409
QueuedThreadPool.java:883
QueuedThreadPool.java:1034
Thread.java:748
```

## Acknowledgements

Request settings informed by [TimeLock issue #2181](https://github.com/palantir/atlasdb/issues/2181).

## Reference Links

- [Optimizing Jetty | High Load](https://www.eclipse.org/jetty/documentation/jetty-9/index.html#high-load)
- [TimeLock: Async all the things (Leadership checks)](https://github.com/palantir/atlasdb/issues/2181)
- [Dropwizard Client](https://www.dropwizard.io/en/stable/manual/client.html)
