package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.IDPManagementController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/projects")
public class IDPManagementPage {
 private final IDPManagementController c;public IDPManagementPage(IDPManagementController c){this.c=c;}
 @GetMapping public List<ProjectResponse> list(@RequestParam(required=false) String query,@RequestParam(required=false) String stage,@RequestParam(defaultValue="false") boolean mine,HttpSession s){return c.list(query,stage,mine,s);}
 @GetMapping("/{id}") public ProjectResponse view(@PathVariable Long id,HttpSession s){return c.view(id,s);}
 @GetMapping("/metrics") public List<ProjectResponse> metrics(HttpSession s){return c.metrics(s);}
 @PostMapping public ProjectResponse create(@Valid @RequestBody ProjectRequest d,HttpSession s){return c.save(null,d,s);}
 @PutMapping("/{id}") public ProjectResponse update(@PathVariable Long id,@Valid @RequestBody ProjectRequest d,HttpSession s){return c.save(id,d,s);}
 @DeleteMapping("/{id}") public Map<String,Boolean> delete(@PathVariable Long id,HttpSession s){c.delete(id,s);return Map.of("ok",true);}
}
