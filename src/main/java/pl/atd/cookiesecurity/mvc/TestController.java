package pl.atd.cookiesecurity.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller to check access rights
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/sayHello")
    public String sayHello(@RequestParam("name") String name) {
        String message = "Hello " + name + " !";
        logger.info("Message prepared {}", message);
        return message;
    }
}
