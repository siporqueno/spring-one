package com.porejemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.porejemplo.service.UserRepr;
import com.porejemplo.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("usernameFilter") Optional<String> usernameFilter,
                           @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                           @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        logger.info("List page requested");

        Page<UserRepr> users = userService.findWithFilter(
                usernameFilter.filter(s -> !s.isBlank()).orElse(null),
                ageMinFilter.orElse(null),
                ageMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(3)
        );
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "user_form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UserRepr user, BindingResult result, Model model) {
        logger.info("Update endpoint requested");

        if (result.hasErrors()) {
            return "user_form";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "user_form";
        }

        logger.info("Updating user with id {}", user.getId());
        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/new")
    public String create(Model model) {
        logger.info("Create new user request");

        model.addAttribute("user", new UserRepr());
        return "user_form";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Long id) {
        logger.info("User delete request");

        userService.delete(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
