package com.example.todo.presentation.task

import com.example.todo.domain.task.Task
import com.example.todo.domain.task.TaskRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("task")
class TaskController(
        private val taskRepository: TaskRepository
) {
    @GetMapping
    fun list(modelMap: ModelMap): String {
        modelMap["taskList"] = taskRepository.findAll(
                Sort.by("id").descending()
        )
        modelMap["newTask"] = Task()
        return "task/index"
    }

    @PostMapping
    fun create(
            attributes: RedirectAttributes, @ModelAttribute task: Task
    ): String {
        taskRepository.save(task)
        attributes.addFlashAttribute(
                "success", "タスクを追加しました。"
        )
        return "redirect:/task"
    }

    @DeleteMapping("{id}")
    fun delete(
            attributes: RedirectAttributes, @PathVariable id: Long
    ): String {
        taskRepository.deleteById(id)
        attributes.addFlashAttribute(
                "success", "タスクを削除しました。"
        )
        return "redirect:/task"
    }

}