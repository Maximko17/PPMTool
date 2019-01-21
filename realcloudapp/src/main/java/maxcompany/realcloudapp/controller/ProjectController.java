package maxcompany.realcloudapp.controller;

import maxcompany.realcloudapp.domain.Project;
import maxcompany.realcloudapp.service.MapValidationErrorService;
import maxcompany.realcloudapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap!=null)return errorMap;

       Project project1 = projectService.saveOrUpdate(project);
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable String id){

        Project project = projectService.findByProjectId(id.toUpperCase());

        return new ResponseEntity<>(project,HttpStatus.OK);
    }


}
