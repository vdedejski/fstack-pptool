package com.vdedejski.ppmtool.services;

import com.vdedejski.ppmtool.domain.Project;
import com.vdedejski.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        // Logic to save users.. makeing sure to update operation

        return projectRepository.save(project);
    }
}
