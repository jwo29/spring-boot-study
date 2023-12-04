package org.zerock.ex4.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Slf4j
public class GuestBookController {

    protected final Logger logger = LoggerFactory.getLogger(GuestBookController.class);

    @GetMapping({"/", "/list"})
    public String list() {

        logger.info("list......");

        return "/guestbook/list";

    }

}
