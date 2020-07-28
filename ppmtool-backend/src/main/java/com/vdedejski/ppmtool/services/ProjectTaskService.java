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


    public ProjectTask addProjectTask(){
        //PT to be added to specific project, project != null ==> project exists (backlog exists)
        //Set the backlog to the project task
        //Project seq = proj identifier-id_within_project
        //Update the Backlog Sequence

        //Set initial priority
        //Set initial status when status is null

    }

}
