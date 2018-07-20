# UrlShortener
A simple URL shortening program
[Follow the blog here](http://sjbanerjee.com/2017/08/18/writing-a-url-shortening-service/)

# Build
```mvn clean install```

# Run
```java -jar UrlShortener-1.0-SNAPSHOT.jar org.sjbanerjee.urlshortener.UrlShorenerApplication```

# Test
## To shorten a URL
```curl -X POST localhost:8080/shorten -d "{\"url\":\"http:\/\/google.com\"}" -H "Content-Type:application/json"```
>You may also use Postman or any other Rest client

>Test by hitting the shortened URL from browser
