package maxcompany.realcloudapp.service;

import maxcompany.realcloudapp.domain.Project;
import maxcompany.realcloudapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdate(Project project){

        return projectRepository.save(project);
    }
}
