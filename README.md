# UrlShortener
A simple URL shortening program.


[Follow the blog here](http://sjbanerjee.com/2017/08/18/writing-a-url-shortening-service/)

# Build
```mvn clean install```

# Run
```java -jar UrlShortener-1.0-SNAPSHOT.jar org.sjbanerjee.urlshortener.UrlShorenerApplication```

# Test
## To shorten a URL
```curl -X POST localhost:8080/shorten -d "{\"url\":\"http:\/\/google.com\"}" -H "Content-Type:application/json"```
>You may also use Postman or any other Rest client. Test by hitting the shortened URL from browser

We all have come across social and professional networking sites, and have encountered posts containing minified (shortened) links that look something like bit.ly/bla or goo.gl/blah or lnkd.in/blahhh. We have got so used to these that we almost never pay attention to logic or design behind this philosophy. Alteast, I didn't, until recently when someone asked me to write a similar service. And I happy embarked on the journey.
<h3><strong>Why do I need to shorten a URL?</strong></h3>
It depends on the use cases. I can think of a few use cases, but there may be more:
1. It makes your URL's neat (and clean), small and handy
2. For security measures. Imagine an encrypted URL to reset your account password
3. In some cases, it might be required to increase your SEO score

Almost all networking platforms like twitter, linkedin and facebook have their own shortening service, and they may have their own reasons for it.
<h3><strong>What to expect from this blog?</strong></h3>
I am not going to share code as part of this blog. Rather, will discuss a thing or two on how to write a simple shortening service. Having said that, I may use a code snippets from a sample application I have written (and is available for fork in github.. link in reference below).

To start with, lets think of an algorithm to shorten URL. The easiest option is generating a hash out of the URL and store it in a database. Well that's not a bad idea. But, after tinkering around with this idea for a while, I realized that the hashing algorithm has to take into account a lot of things (like based on what, we want to generate the hash) and  there might be cases where for two long URL's, the hashes may be equal and there will be conflicts (if not implemented correctly). A better idea is to store it in a database and hash the ID (the unique field) of the record entry. Then, if we encode the ID into characters, we get a unique URL.
<h4><strong>The algorithm:</strong></h4>
<ol>
 	<li>Take a base URL</li>
 	<li>Get the last id of records inserted in a database</li>
 	<li>Convert or encode the id into character set</li>
 	<li>insert it into the database</li>
 	<li>Done</li>
</ol>
This is a very simple and most widely used algorithm that you will encounter almost everywhere.
<h4><strong>How to encode the ID?</strong></h4>
Now, the alphabets (A to Z) in upper case comprise of 26 characters, another 26 for the lower case (a to z), and there are 10 digits (from 0 to 9). That totals to 62. If you ask me, it's all about base conversion from a decimal system (base 10) to a base62 system. Here is a sample code snippet that I have used.
<pre>// initialize an array
private static final String[] elements = {
"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
"5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
"Y", "Z"
};</pre>
Then, convert it into a base62 like this:
<pre>// Convert to base 62
public String encode(long index) {
    if(index == 0){
        return elements[0];
    }
    int counter = 0;
        StringBuilder sb = new StringBuilder("");
        while (index != 0) {
            long i = index % base_bit;
            sb.append(elements[(int) i]);
            index = index / base_bit;
            counter++;
        }

        return sb.reverse().toString();
}</pre>
And we are pretty much done!

<em>I have the entire code in <strong><a href="https://github.com/talk2sjb/UrlShortener">github</a></strong>. Feel free to have a look. I wanted to host it  as cut.me or strip.it but couldn't due to lack of a domain availability (or insanely high price of the domains).</em>
<ul>
 	<li>I have used spring primarily because I wanted to use an embedded database (I have used H2 here). In a production environment, of course, we would like to have a SQL database with proper replication and sharding.</li>
 	<li>I have also used JPA that made my life a lot simpler. The <em><strong>JPARepository</strong> </em>is something to checkout, if you haven't used them already!</li>
</ul>
<h3><strong>A few edge-cases</strong></h3>
<ol>
 	<li>What if the same URL has been entered to shortify multiple times? By design, our code will go ahead and insert another record blindly. That's sheer waste of our code range, and I was too lazy to implement a robust mechanism, and since it was my own project, I leveraged <strong>@Cacheale </strong>annotation provided by Spring. That way, it won't encode the same URL twice.</li>
 	<li>For now, all the URL's that are once shortened, are there in the records forever! However, we can be a little intelligent about it, and purge/delete all the encoded URL's after an expiry date. I have added provision for it. Every time, there is a hit for the shortened URL, just increase the expiry by some time. This way, it remains in our records as long as it actively being used. Again, I have tried to reduce some overhead using <strong>@Cacheable.</strong></li>
 	<li>We may handle the errors if we are not able to generate the records</li>
 	<li>We may also add support for custom shortened URL's</li>
</ol>
Enough food for thought. Now go ahead and build your own service to shorten URL's.
<em><strong>May the force be with you!</strong></em>
<h3><strong>References:</strong></h3>
<ul>
 	<li>https://github.com/talk2sjb/UrlShortener</li>
 	<li>https://blog.codinghorror.com/url-shortening-hashes-in-practice/</li>
</ul>
