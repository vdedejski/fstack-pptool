package com.vdedejski.ppmtool.services;

import com.vdedejski.ppmtool.domain.Backlog;
import com.vdedejski.ppmtool.domain.Project;
import com.vdedejski.ppmtool.domain.ProjectTask;
import com.vdedejski.ppmtool.exceptions.ProjectNotFoundException;
import com.vdedejski.ppmtool.repositories.BacklogRepository;
import com.vdedejski.ppmtool.repositories.ProjectRepository;
import com.vdedejski.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PT to be added to specific project, project != null ==> project exists (backlog exists)
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //Set the backlog to the project task
            projectTask.setBacklog(backlog);

            //Project seq = proj identifier-id_within_project
            Integer backlogSequence = backlog.getPTSequence();

            //Update the Backlog Sequence
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            //Add sequence to projectTask
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //Set initial priority
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            //Set initial status when status is null
            if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
                //Possible breakpoint !!
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project == null){
            throw new ProjectNotFoundException("Project with ID: " + id + " does not exist!");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
