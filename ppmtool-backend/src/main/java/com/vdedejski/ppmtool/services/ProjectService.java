package com.vdedejski.ppmtool.services;

import com.vdedejski.ppmtool.domain.Project;
import com.vdedejski.ppmtool.exceptions.ProjectIdException;
import com.vdedejski.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    // Logic to save users.. makeing sure to update operation

    @Autowired
    private ProjectRepository projectRepository;


    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");

        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID' " + projectId.toUpperCase() + "' doesn't exsist");
        }
        
        return project;
    }
}
