package org.sjbanerjee.urlshortener.api;

import org.json.JSONObject;
import org.sjbanerjee.urlshortener.exception.BadRequestException;
import org.sjbanerjee.urlshortener.service.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

@RestControllerAdvice
@RestController
public class RestAPI {

    @Autowired
    UrlShortener shortener;

    @RequestMapping(
            value = "/shorten",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "text/plain")
    public String shorten(@RequestBody String requestBody){
        JSONObject obj = new JSONObject(requestBody);
        return shortener.shorten(obj.getString("url"));
    }

    @RequestMapping(
            value = "/sh/{encoded}",
            method = RequestMethod.GET)
    public RedirectView redirect(@PathVariable String encoded){
        //TODO: Cache
        //TODO: Handle no record found
        String redirect_url = shortener.decodeAlias(encoded);
        System.out.println(redirect_url);
        return new RedirectView(redirect_url);
    }


    /**
     * Handles Bad request Exceptions
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
