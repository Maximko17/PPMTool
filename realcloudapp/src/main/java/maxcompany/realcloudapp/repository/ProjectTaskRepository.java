package maxcompany.realcloudapp.repository;

import maxcompany.realcloudapp.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectTaskRepository extends CrudRepository<Project,Long> {
}
