# jackson-jsonld [![License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](http://www.opensource.org/licenses/MIT) [![Build Status](https://travis-ci.org/io-informatics/jackson-jsonld.svg)](https://travis-ci.org/io-informatics/jackson-jsonld) [![Join the chat at https://gitter.im/io-informatics/jackson-jsonld](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/io-informatics/jackson-jsonld?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

JSON-LD Module for Jackson

## Install it
If you use maven, just add the dependency to your pom.xml
```xml
<dependency>
    <groupId>com.io-informatics.oss</groupId>
    <artifactId>jackson-jsonld</artifactId>
    <version>0.0.5</version>
</dependency>
```

## Use it
The first think you need is to register the module in jackson. The module constructor takes a function that returns the jsonld context of your application.
```java
    // this configure the JsonldModule with an empty default context.
   objectMapper.registerModule(new JsonldModule());
```
If you want to provide a default JSONLD context for your application check the other constructors of [JsonldModule](https://github.com/io-informatics/jackson-jsonld/blob/master/src/main/java/ioinformarics/oss/jackson/module/jsonld/JsonldModule.java#L25)


Now we can have annotated java beans which can serialized using Jsonld. For instance:

```java
    @JsonldType("http://schema.org/Person")
    public class Person {
        @JsonldId
        public  String id;
        @JsonldProperty("http://schema.org/name")
        public String name;
        @JsonldProperty("http://schema.org/jobTitle")
        public String jobtitle;
        @JsonldProperty("http://schema.org/url")
        public String url;
    }
```

Instances of Person can we wrapped inside a JsonldResource or JsonldGraph/HydraCollections. To do this you can use the builders that the library provides:

```java
    Person alex = new Person();
    alex.id = "mailto:me@alexdeleon.name";
    alex.name = "Alex De Leon";
    alex.jobtitle = "Software Developer";
    alex.url = "http://alexdeleon.name";

    objectMapper.writer().writeValue(System.out, JsonldResource.Builder.create().build(alex));
```
The above will generate the following jsonld representation:

```json
    {
        "@context": {
            "name": "http://schema.org/name",
            "jobtitle": "http://schema.org/jobTitle",
            "url": "http://schema.org/url"
        },
        "@type": "http://schema.org/Person",
        "name": "Alex De Leon",
        "jobtitle": "Software Developer",
        "url": "http://alexdeleon.name",
        "@id": "mailto:me@alexdeleon.name"
    }
```
