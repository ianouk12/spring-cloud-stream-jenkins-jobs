package springcloudstream

import org.springframework.jenkins.springcloudstream.ci.SpringCloudStreamBuildMarker
import org.springframework.jenkins.springcloudstream.ci.SpringCloudStreamPhasedBuildMaker
import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this

boolean masterRelease = false
String releaseType = "" // possible values are - milestone or ga

// Master builds (Elmhurst)
new SpringCloudStreamPhasedBuildMaker(dsl).build(['spring-cloud-stream-binder-kafka':'master',
                                                  'spring-cloud-stream-binder-rabbit':'master'], masterRelease, releaseType)
// Spring Cloud Stream Ditmars builds (1.3.x)
new SpringCloudStreamPhasedBuildMaker(dsl).build("1.3.x", "Ditmars.x", "spring-cloud-stream-Ditmars-x-builds",
                                                ['spring-cloud-stream-binder-kafka':'1.3.x',
                                                'spring-cloud-stream-binder-rabbit':'1.3.x'], false, "")
// Spring Cloud Stream Chelsea builds (1.2.x)
new SpringCloudStreamPhasedBuildMaker(dsl).build("1.2.x", "Chelsea.x", "spring-cloud-stream-Chelsea-x-builds",
                                                ['spring-cloud-stream-binder-kafka':'1.2.x',
                                                'spring-cloud-stream-binder-rabbit':'1.2.x'], false, "")
// Spring Cloud Stream Brooklyn builds (1.1.x)
new SpringCloudStreamPhasedBuildMaker(dsl).build("1.1.x", "Brooklyn.x", "spring-cloud-stream-Brooklyn-x-builds",
                                                ['spring-cloud-stream-binder-kafka':'1.1.x',
                                                 'spring-cloud-stream-binder-rabbit':'1.1.x'], false, "")
// 1.0.x builds
new SpringCloudStreamBuildMarker(dsl, "spring-cloud", "spring-cloud-stream",
        "1.0.x", [KAFKA_TIMEOUT_MULTIPLIER: '60']).deploy(true, false, "clean deploy -Pfull,spring")

// Google PubSub Binders builds
new SpringCloudStreamBuildMarker(dsl, 
                                 "spring-cloud", 
                                 "spring-cloud-stream-binder-google-pubsub", "master", [:])
                      .deploy()

// JMS Binders builds
new SpringCloudStreamBuildMarker(dsl,
        "spring-cloud",
        "spring-cloud-stream-binder-jms", "master", [:])
        .deploy()

// AWS Kinesis Binders builds
new SpringCloudStreamBuildMarker(dsl,
        "spring-cloud",
        "spring-cloud-stream-binder-aws-kinesis", "master", [:])
        .deploy()

// 0.11 Kafka build
new SpringCloudStreamBuildMarker(dsl, "spring-cloud", "spring-cloud-stream-binder-kafka",
        "0.11", [KAFKA_TIMEOUT_MULTIPLIER: '60']).deploy()

//new SpringCloudStreamBuildMarker(dsl, "spring-cloud", "spring-cloud-stream-binder-kafka",
//        "0.11", [KAFKA_TIMEOUT_MULTIPLIER: '60']).deploy(true, false, "",
//        null, null, null, false, true, "ga")
