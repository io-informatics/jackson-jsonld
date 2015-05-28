# jackson-jsonld [![License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](http://www.opensource.org/licenses/MIT) [![Build Status](https://travis-ci.org/io-informatics/jackson-jsonld.svg)](https://travis-ci.org/io-informatics/jackson-jsonld)
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
    // we just provide an empty context object here, but you should use a method reference or a lambda to generate your context
   objectMapper.registerModule(new JsonldModule(() -> objectMapper.createObjectNode()));
```

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