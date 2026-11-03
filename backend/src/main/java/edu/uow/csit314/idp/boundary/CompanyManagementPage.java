package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.CompanyManagementController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/companies")
public class CompanyManagementPage {
 private final CompanyManagementController c;public CompanyManagementPage(CompanyManagementController c){this.c=c;}
 @GetMapping public List<CompanyResponse> list(@RequestParam(required=false) String query,HttpSession s){return c.list(query,s);}
 @GetMapping("/{id}") public CompanyResponse view(@PathVariable Long id,HttpSession s){return c.view(id,s);}
 @GetMapping("/{id}/public") public CompanyResponse publicView(@PathVariable Long id){return c.publicView(id);}
 @PostMapping public CompanyResponse create(@Valid @RequestBody CompanyRequest d,HttpSession s){return c.save(null,d,s);}
 @PutMapping("/{id}") public CompanyResponse update(@PathVariable Long id,@Valid @RequestBody CompanyRequest d,HttpSession s){return c.save(id,d,s);}
 @DeleteMapping("/{id}") public Map<String,Boolean> delete(@PathVariable Long id,HttpSession s){c.delete(id,s);return Map.of("ok",true);}
}
