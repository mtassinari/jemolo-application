package it.laziocrea.jemoloapp.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientForwardController {
	 private final Logger log = LoggerFactory.getLogger(ClientForwardController.class);

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward() {
    	log.debug("FORWARD >>>>>>>> ");
        return "forward:/";
    }
}
