package com.example.org.controllers;

import com.example.org.models.Month;
import com.example.org.models.Tasks;
import com.example.org.services.MonthService;
import com.example.org.services.TaskService;
import com.example.org.services.UserService;
import com.example.org.validators.MonthValidator;
import com.example.org.validators.TaskValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskManagerController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskValidator taskValidator;
    private final MonthService monthService;
    private final MonthValidator monthValidator;

    @Autowired
    public TaskManagerController(TaskService taskService, UserService userService, TaskValidator taskValidator, MonthService monthService, MonthValidator monthValidator) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskValidator = taskValidator;
        this.monthService = monthService;
        this.monthValidator = monthValidator;
    }

    @GetMapping()
    public String getTasks(Model model) throws Exception {
        model.addAttribute("userTasks", taskService.getTasks(userService.findByContext()));
        return "tasks";
    }

    @GetMapping("/add")
    public String addTask(@ModelAttribute ("task") Tasks task, Model model) {
        model.addAttribute("months", monthService.getAll());
        model.addAttribute("month", new Month());
        return "add";
    }

    @PostMapping("/add")
    public String submitTask(@ModelAttribute ("task") @Valid Tasks task,
                             @ModelAttribute ("month") Month monthWithId, Model model,
                             BindingResult result) throws Exception {
        Month month = monthService.findById(monthWithId.getId());
        task.setMonth(month);
        taskValidator.validate(task, result);

        if (result.hasErrors()) {
            model.addAttribute("task", task);
            model.addAttribute("months", monthService.getAll());
            return "add";
        }

        taskService.saveTask(task, userService.findByContext());

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
//        model.addAttribute("userTasks", taskService.getTasks(userService.findByContext()));
//        return "update";
//    }


    @GetMapping("/process_update/{id}")
    public String updatePage(Model model, @PathVariable ("id") int id) throws Exception {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("month", new Month());
        model.addAttribute("months", monthService.getAll());
        return "updatePage";
    }

    @PostMapping("/submit_update")
    public String submitUpdate(@ModelAttribute ("task") @Valid Tasks tasks,
                                @ModelAttribute ("month") Month monthWithId, Model model,
                                BindingResult result) throws Exception {
        tasks.setMonth(monthService.findById(monthWithId.getId()));
        taskValidator.validate(tasks, result);

        if (result.hasErrors()) {
            model.addAttribute("task", tasks);
            model.addAttribute("months", monthService.getAll());
            return "updatePage";
        }

        taskService.saveTask(tasks, userService.findByContext());

        return "redirect:/tasks";
    }

    @GetMapping ("/completed/{id}")
    public String completeTask(@PathVariable ("id") int id) throws Exception {
        taskService.setCompleted(id);
        userService.addCompletedTask(userService.findByContext());

        return "redirect:/tasks";
    }
}
