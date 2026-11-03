package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.CustomerProjectsController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class CustomerProjectsPage {
 private final CustomerProjectsController c;public CustomerProjectsPage(CustomerProjectsController c){this.c=c;}
 @GetMapping("/favourites") public List<ProjectResponse> list(@RequestParam(required=false) String query,HttpSession s){return c.shortlist(query,s);}
 @PostMapping("/favourites/{id}") public ProjectResponse save(@PathVariable Long id,HttpSession s){return c.save(id,s);}
 @DeleteMapping("/favourites/{id}") public Map<String,Boolean> remove(@PathVariable Long id,HttpSession s){c.remove(id,s);return Map.of("ok",true);}
 @PostMapping("/projects/{id}/views") public ProjectResponse view(@PathVariable Long id,HttpSession s){return c.recordView(id,s);}
}
