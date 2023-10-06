package com.example.org.services;

import com.example.org.models.Tasks;
import com.example.org.models.User;
import com.example.org.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public void saveTask(Tasks task, User user){
        fillToSaveTask(task, user);
        taskRepo.save(task);
    }

    public void updateTask(Tasks tasks, User user){
        fillToUpdateTask(tasks, user);
        taskRepo.save(tasks);
    }

    public void setCompleted(int id) throws Exception {
        Optional<Tasks> tasks = taskRepo.findById(id);
        if(tasks.isPresent()){
            Tasks presentTasks = tasks.get();
            presentTasks.setCompleted(true);
            taskRepo.save(presentTasks);
        }else{
            throw new Exception("Task not found!");
        }
    }


    public List<Tasks> getTasks(User user) {
        List<Tasks> tasksList = taskRepo.getAllByUser(user);
        tasksList.sort(Comparator.comparingInt(Tasks::getId));
        return tasksList;
    }

    public void deleteTaskById(int id){
        taskRepo.deleteById(id);
    }

    public Tasks findById(int id) throws Exception {
        Optional<Tasks> tasksOptional = taskRepo.findById(id);
        if(tasksOptional.isPresent())
            return tasksOptional.get();
        throw new Exception("Task not found");
    }

    public void fillToSaveTask(Tasks tasks, User user){
        tasks.setAddedAt(LocalDateTime.now());
        tasks.setUser(user);
    }

    public void fillToUpdateTask(Tasks tasks, User user){
        tasks.setUser(user);
        tasks.setAddedAt(taskRepo.findById(tasks.getId()).get().getAddedAt());
        tasks.setCompleted(taskRepo.findById(tasks.getId()).get().isCompleted());
    }
}
