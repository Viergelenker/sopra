package com.desj.controller;

import com.desj.model.LearningGroup;
import com.desj.model.LearningGroupRepository;
import com.desj.model.User;
import com.desj.model.UserRepository;
import com.desj.service.LearningGroupService;
import com.desj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Julien on 07.06.16.
 */
@Controller
public class CreateGroupController {

    @Autowired
    private LearningGroupRepository learningGroupRepository;

    @Autowired
    private LearningGroupService learningGroupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    /**
     * I'm really sorry about this mess! I will do some refactoring as soon as possible!
     * The button within the navbar (Create Groups) sets the Id to null, so if this pages loads for the first time,
     * no success callout gets shown.
     * As soon as you've created a new learning group, the redirect loads the correct id of the new learning group.
     * This way we can show a button to the newly created group, which is really convenient.
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/createGroup{id}", method = RequestMethod.GET)
    public String showCreateGroup(@RequestParam(value = "id", required = false) Integer id, Model model) {

        model.addAttribute("username", userService.getCurrentDesjUser().getUsername());

        // This id is null if you haven't created a learning group and
        // becomes the real id of a learning group, after creating one!
        model.addAttribute("newlyCreatedLearningGroupId", id);
        if(id != null) {
            model.addAttribute("createdLearningGroup", learningGroupRepository.findOne(id));
        }
        model.addAttribute("learningGroup", new LearningGroup());
        return "CreateGroup";
    }

    @RequestMapping(value = "/createNewGroup", method = RequestMethod.POST)
    public String createNewGroup(@ModelAttribute("learningGroup") LearningGroup learningGroup) {

        User currentUser = userService.getCurrentDesjUser();

        learningGroupService.save(learningGroup, currentUser);
        userService.addLearningGroupToUser(learningGroup, currentUser.getId());

        return "redirect:/createGroup?id=" + learningGroup.getId().toString();
    }
}
