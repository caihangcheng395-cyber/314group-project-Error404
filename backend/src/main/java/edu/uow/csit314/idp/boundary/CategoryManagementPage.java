package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.CategoryManagementController;
import edu.uow.csit314.idp.dto.PlatformDtos.CategoryRequest;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
/** Complements, and does not replace, the original read-only category Boundary. */
@RestController @RequestMapping("/api/categories")
public class CategoryManagementPage {
 private final CategoryManagementController c;public CategoryManagementPage(CategoryManagementController c){this.c=c;}
 @PostMapping public IDPCategoryResponse create(@Valid @RequestBody CategoryRequest d,HttpSession s){return c.save(null,d,s);}
 @PutMapping("/{id}") public IDPCategoryResponse update(@PathVariable Long id,@Valid @RequestBody CategoryRequest d,HttpSession s){return c.save(id,d,s);}
 @DeleteMapping("/{id}") public Map<String,Boolean> delete(@PathVariable Long id,HttpSession s){c.delete(id,s);return Map.of("ok",true);}
}
