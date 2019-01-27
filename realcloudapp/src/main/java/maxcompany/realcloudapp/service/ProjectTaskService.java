package maxcompany.realcloudapp.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import maxcompany.realcloudapp.domain.Backlog;
import maxcompany.realcloudapp.domain.Project;
import maxcompany.realcloudapp.domain.ProjectTask;
import maxcompany.realcloudapp.exceptions.NotFoundException;
import maxcompany.realcloudapp.repository.BacklogRepository;
import maxcompany.realcloudapp.repository.ProjectRepository;
import maxcompany.realcloudapp.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask){ try{
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;

            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority()==0||projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus().equals("")||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }
        }catch (Exception ex){
            throw new NotFoundException("Project with ID: '"+projectIdentifier.toUpperCase()+"' not found");
        }


        return projectTaskRepository.save(projectTask); }

    public List<ProjectTask> findBacklogicByID(String backlog_id) {
        Project project = projectRepository.findByProjectIdentifier(backlog_id);
        if (project==null){
            throw new NotFoundException("Project with ID: '"+ backlog_id.toUpperCase()+"' not found");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findByProjectIDAndProjectSequence(String project_id,String sequence){

        Backlog backlog = backlogRepository.findByProjectIdentifier(project_id);
        if (backlog == null){
            throw new NotFoundException("Project with ID: '"+ project_id.toUpperCase()+"' not found");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectIdentifierAndProjectSequence(project_id,sequence);
        if (projectTask== null){
            throw new NotFoundException("Project Task with ID: '"+ sequence.toUpperCase()+"' not found in project '"+project_id.toUpperCase()+"' ");
        }
        return projectTask ;
    }

    public ProjectTask updateProjectTask(ProjectTask projectTask,String project_id,String sequence){
        ProjectTask updateTask = findByProjectIDAndProjectSequence(project_id,sequence);

        updateTask = projectTask;

        return projectTaskRepository.save(updateTask);
    }

    public void deleteProjectTask(String project_id,String sequence){
        ProjectTask projectTask =  findByProjectIDAndProjectSequence(project_id,sequence);

        projectTaskRepository.delete(projectTask);
    }
}
