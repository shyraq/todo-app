package com.example.org.services;

import com.example.org.models.Tasks;
import com.example.org.models.User;
import com.example.org.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        fillTask(task, user);
        taskRepo.save(task);
    }


    public List<Tasks> getTasks(User user) {
        return taskRepo.getAllByUser(user);
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

    public void fillTask(Tasks tasks, User user){
        tasks.setAddedAt(LocalDateTime.now());
        tasks.setUser(user);
    }
}
