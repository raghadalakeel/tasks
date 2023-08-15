package com.example.tasks.services;

import com.example.tasks.models.task;
import com.example.tasks.repositories.taskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class taskService {
    private taskRepository taskRepo ;
@Autowired
    public taskService(taskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }


    //show tasks of the user
    public List<task> getTasks(int userId){
    return taskRepo.findAllByUserId(userId);
    }

    //add new task to the task list
    public task addnewTask(task Task){
    task newTask = new task();
    newTask.setTitle(Task.getTitle());
    newTask.setDescription(Task.getDescription());
    newTask.setDueDate(Task.getDueDate());
    newTask.setCategory(Task.getCategory());
    newTask.setCompleted(false);
    newTask.setUserId(Task.getUserId());
    taskRepo.save(newTask);
    return newTask;
    }

    //mark the task as completed
    public void setCompleted (int id,boolean completed ){
        System.out.println("iam in set completed controller");
    task findTask= taskRepo.findById(id);
    findTask.setCompleted(completed);
    findTask.setId(id);
    taskRepo.save(findTask);
    }



    //edit the fields of the task
    public task Update (task Task){
        task findTask= taskRepo.findById(Task.getId());
        findTask.setCompleted(Task.isCompleted());
        findTask.setTitle(Task.getTitle());
        findTask.setDescription(Task.getDescription());
        findTask.setDueDate(Task.getDueDate());
        findTask.setCategory(Task.getCategory());
        findTask.setId(Task.getId());
        taskRepo.save(findTask);
        return findTask;
    }


    //search for task
    public List<task> SearchTask(String name,int userId){
    List<task> findTask = taskRepo.findAllByTitleAndUserId(name,userId);
    return findTask;
    }

    public List<task> getTaskBycategory(String category,int userId){
        List<task> findTask = taskRepo.findAllByCategoryAndUserId(category,userId);
        return findTask;
    }

    //assign catgeory
    public void assignCategory(String category,int id){
    task findTask = taskRepo.findById(id);
    findTask.setCategory(category);
    taskRepo.save(findTask);

    }
    //delete task
    public boolean deleteTask( int id){
    task findTask=taskRepo.findById(id);
    if (findTask==null){
        return false ;
    }
    taskRepo.delete(findTask);
    return true;
}


    public task getTaskById(int  taskId) {
   task findTask= taskRepo.findById(taskId);
           return findTask;
    }
}
