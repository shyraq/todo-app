package com.example.org.controllers;

import com.example.org.models.Tasks;
import com.example.org.models.User;
import com.example.org.services.TaskService;
import com.example.org.services.UserService;
import com.example.org.validators.TaskValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskManagerController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskValidator taskValidator;

    @Autowired
    public TaskManagerController(TaskService taskService, UserService userService, TaskValidator taskValidator) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskValidator = taskValidator;
    }

    @GetMapping()
    public String getTasks(Model model) throws Exception {
        model.addAttribute("userTasks", taskService.getTasks(findByContext()));
        return "tasks";
    }

    @GetMapping("/add")
    public String addTask(@ModelAttribute ("task") Tasks task) {
        return "add";
    }

    @PostMapping("/add")
    public String submitTask(@ModelAttribute ("task") @Valid Tasks task, BindingResult result) throws Exception {
        taskValidator.validate(task, result);

        if (result.hasErrors())
            return "add";

        taskService.saveTask(task, findByContext());

        return "redirect:/tasks";
    }

//    @GetMapping("/delete")
//    public String deleteTasks(Model model, @ModelAttribute ("tasks") Tasks tasks) throws Exception {
//        model.addAttribute("userTasks", taskService.getTasks(findByContext()));
//        return "delete";
//    }

    @GetMapping("/delete/{id}")
    public String submitDelete(@PathVariable ("id") int id){
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

//    @GetMapping("/update")
//    public String updateTask(Model model) throws Exception {
//        model.addAttribute("tasks", new Tasks());
//        model.addAttribute("userTasks", taskService.getTasks(findByContext()));
//        return "update";
//    }


    @GetMapping("/process_update/{id}")
    public String updatePage(Model model, @PathVariable ("id") int id) throws Exception {
        model.addAttribute("task",taskService.findById(id));
        return "updatePage";
    }

    @PostMapping("/submit_update")
    public String submitUpdate(@ModelAttribute ("task") Tasks tasks, BindingResult result) throws Exception {
        taskValidator.validate(tasks, result);

        if(result.hasErrors())
            return "updatePage";
        taskService.saveTask(tasks, findByContext());

        return "redirect:/tasks";
    }

    @GetMapping ("/completed/{id}")
    public String completeTask(@PathVariable ("id") int id) throws Exception {
        taskService.setCompleted(id);

        return "redirect:/tasks";
    }

    public User findByContext() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent())
            return user.get();
        throw new Exception("User not found");
    }
}
