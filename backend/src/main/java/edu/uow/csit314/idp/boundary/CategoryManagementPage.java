package edu.uow.csit314.idp.boundary;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uow.csit314.idp.controller.CategoryManagementController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.dto.PlatformDtos.CategoryRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
/** Complements, and does not replace, the original read-only category Boundary. */
@RestController @RequestMapping("/api/categories")
public class CategoryManagementPage {
 private final CategoryManagementController c;public CategoryManagementPage(CategoryManagementController c){this.c=c;}
 @PostMapping public IDPCategoryResponse create(@Valid @RequestBody CategoryRequest d,HttpSession s){return c.save(null,d,s);}
 
 @DeleteMapping("/{id}") public Map<String,Boolean> delete(@PathVariable Long id,HttpSession s){c.delete(id,s);return Map.of("ok",true);}
}
