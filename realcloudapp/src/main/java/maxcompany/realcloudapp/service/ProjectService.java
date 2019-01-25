package maxcompany.realcloudapp.service;

import maxcompany.realcloudapp.domain.Backlog;
import maxcompany.realcloudapp.domain.Project;
import maxcompany.realcloudapp.exceptions.ProjectIdException;
import maxcompany.realcloudapp.repository.BacklogRepository;
import maxcompany.realcloudapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdate(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return  projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID "+project.getProjectIdentifier().toUpperCase()+" already exists");
        }
    }

    public Project findByProjectId(String id){
        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());

        if (project==null){
            throw new ProjectIdException("Project with id "+ id + " doesn't exist");
        }
        return project;
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectById(String id){
        Project project = projectRepository.findByProjectIdentifier(id);

        if (project==null){
            throw new ProjectIdException("Cannot find project with id "+id);
        }

        projectRepository.delete(project);
    }
}
