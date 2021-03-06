package maxcompany.realcloudapp.repository;

import maxcompany.realcloudapp.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectIdentifier(String Id);
}
