package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.CategoryRequest;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.uow.csit314.idp.controller.AuthenticationController.error;
@Service @Transactional
public class CategoryManagementController {
 private final IDPCategoryRepository categories;private final IDPRepository projects;private final AuthenticationController auth;private final ActivityController activity;
 public CategoryManagementController(IDPCategoryRepository c,IDPRepository p,AuthenticationController a,ActivityController e){categories=c;projects=p;auth=a;activity=e;}
 public IDPCategoryResponse save(Long id,CategoryRequest d,HttpSession s){UserAccount u=auth.require(s,Role.PLATFORM_MANAGER);IDPCategory c=id==null?new IDPCategory(d.name().trim(),d.description(),d.active()):categories.findById(id).orElseThrow(()->error(404,"Category not found"));categories.findByNameIgnoreCase(d.name().trim()).filter(x->!x.getId().equals(id)).ifPresent(x->{throw error(409,"Category name already exists");});c.rename(d.name().trim());c.changeDescription(d.description());c.setActive(d.active());categories.save(c);activity.record(id==null?"category_created":"category_updated",u,null);return IDPCategoryResponse.from(c);}
 public void delete(Long id,HttpSession s){UserAccount u=auth.require(s,Role.PLATFORM_MANAGER);IDPCategory c=categories.findById(id).orElseThrow(()->error(404,"Category not found"));if(projects.existsByCategoryId(id))throw error(409,"Reassign projects using this category before deleting it");categories.delete(c);activity.record("category_deleted",u,null);}
}
