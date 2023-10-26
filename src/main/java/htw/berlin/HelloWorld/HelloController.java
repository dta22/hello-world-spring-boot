
package htw.berlin.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloController {

    @GetMapping (path = "/")
    public ModelAndView showHelloWorldPage () {
        return new ModelAndView("HelloWorld");
    }

}




