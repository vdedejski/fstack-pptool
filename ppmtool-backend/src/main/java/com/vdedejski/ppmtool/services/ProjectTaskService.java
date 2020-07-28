package com.vdedejski.ppmtool.services;

import com.vdedejski.ppmtool.domain.Backlog;
import com.vdedejski.ppmtool.domain.ProjectTask;
import com.vdedejski.ppmtool.repositories.BacklogRepository;
import com.vdedejski.ppmtool.repositories.ProjectRepository;
import com.vdedejski.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //PT to be added to specific project, project != null ==> project exists (backlog exists)
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //Set the backlog to the project task
        projectTask.setBacklog(backlog);

        //Project seq = proj identifier-id_within_project
        Integer backlogSequence = backlog.getPTSequence();

        //Update the Backlog Sequence
        backlogSequence++;

        //Add sequence to projectTask
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //Set initial priority
        if (projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }

        //Set initial status when status is null
        if (projectTask.getStatus() == null || projectTask.getStatus() == ""){
            //Possible breakpoint !!
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
