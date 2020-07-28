package com.vdedejski.ppmtool.services;

import com.vdedejski.ppmtool.domain.Backlog;
import com.vdedejski.ppmtool.domain.Project;
import com.vdedejski.ppmtool.exceptions.ProjectIdException;
import com.vdedejski.ppmtool.repositories.BacklogRepository;
import com.vdedejski.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    // Logic to save users.. makeing sure to update operation

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            String projectIdentifier = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectIdentifier);

            // Setting project <-> backlog relationship
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
            }

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

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID' " + projectId.toUpperCase() + "' doesn't exsist");
        }
        projectRepository.delete(project);
    }
}
