package com.example.org.controllers;

import com.example.org.dto.MonthDTO;
import com.example.org.dto.TasksDTO;
import com.example.org.models.Month;
import com.example.org.models.Tasks;
import com.example.org.services.MonthService;
import com.example.org.services.TaskService;
import com.example.org.services.UserService;
import com.example.org.validators.MonthValidator;
import com.example.org.validators.TaskValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskManagerController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskValidator taskValidator;
    private final MonthService monthService;
    private final MonthValidator monthValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskManagerController(TaskService taskService, UserService userService, TaskValidator taskValidator, MonthService monthService, MonthValidator monthValidator, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskValidator = taskValidator;
        this.monthService = monthService;
        this.monthValidator = monthValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getTasks(Model model) throws Exception {
        model.addAttribute("userTasks", taskService.getTasks(userService.findByContext()));
        return "tasks";
    }

    @GetMapping("/add")
    public String addTask(Model model) {
        model.addAttribute("months", monthService.getAll().stream().map(this::convertToMonthDTO).collect(Collectors.toList()));
        model.addAttribute("tasks", new TasksDTO());
        return "add";
    }

    @PostMapping("/add")
    public String submitTask(@ModelAttribute ("tasks") @Valid TasksDTO tasksDTO, Model model,
                             BindingResult result) throws Exception {
        Tasks tasks = convertToTasks(tasksDTO);
        monthValidator.validate(tasks.getMonth(), result);
        taskValidator.validate(tasks, result);

        if (result.hasErrors()) {
            model.addAttribute("months", monthService.getAll().stream().map(this::convertToMonthDTO).collect(Collectors.toList()));
            return "add";
        }

        taskService.saveTask(tasks, userService.findByContext());

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
        model.addAttribute("tasks", convertToTasksDTO(taskService.findById(id)));
        model.addAttribute("months", monthService.getAll().stream().map(this::convertToMonthDTO).collect(Collectors.toList()));
        return "updatePage";
    }

    @PostMapping("/submit_update")
    public String submitUpdate(@ModelAttribute ("tasks") @Valid TasksDTO tasksDTO, Model model,
                                BindingResult result) throws Exception {
        Tasks tasks = convertToTasks(tasksDTO);
        monthValidator.validate(tasks.getMonth(), result);
        taskValidator.validate(tasks, result);

        if (result.hasErrors()) {
            model.addAttribute("months", monthService.getAll().stream().map(this::convertToMonthDTO).collect(Collectors.toList()));
            return "updatePage";
        }

        taskService.updateTask(tasks, userService.findByContext());

        return "redirect:/tasks";
    }

    @GetMapping ("/completed/{id}")
    public String completeTask(@PathVariable ("id") int id) throws Exception {
        taskService.setCompleted(id);
        userService.addCompletedTask(userService.findByContext());

        return "redirect:/tasks";
    }

    private MonthDTO convertToMonthDTO(Month month){
        return this.modelMapper.map(month, MonthDTO.class);
    }

    private Month convertToMonth(MonthDTO monthDTO){
        return this.modelMapper.map(monthDTO, Month.class);
    }

    private Tasks convertToTasks(TasksDTO tasksDTO){
        return this.modelMapper.map(tasksDTO, Tasks.class);
    }

    private TasksDTO convertToTasksDTO(Tasks tasks){
        return this.modelMapper.map(tasks, TasksDTO.class);
    }
}
