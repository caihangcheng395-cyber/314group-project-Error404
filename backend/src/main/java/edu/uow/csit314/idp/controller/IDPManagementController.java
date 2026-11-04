package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.uow.csit314.idp.controller.AuthenticationController.error;
@Service @Transactional
public class IDPManagementController {
 private final IDPRepository projects;private final CompanyRepository companies;private final IDPCategoryRepository categories;private final FavouriteRepository favourites;private final ProjectViewEventRepository views;private final AuthenticationController auth;private final ActivityController activity;
 public IDPManagementController(IDPRepository p,CompanyRepository c,IDPCategoryRepository cats,FavouriteRepository f,ProjectViewEventRepository v,AuthenticationController a,ActivityController e){projects=p;companies=c;categories=cats;favourites=f;views=v;auth=a;activity=e;}
 public ProjectResponse dto(IDP p,UserAccount viewer){return new ProjectResponse(p.getId(),p.getOwner().getId(),p.getCompany().getId(),p.getCompany().getName(),p.getCategory().getId(),p.getCategory().getName(),p.getTitle(),p.getDescription(),p.getStage(),p.getPropertyType(),p.getLocation(),p.getBudget(),p.getArea(),p.getProgress(),p.getImageUrl(),p.getCreatedAt(),views.countByProjectId(p.getId()),favourites.countByProjectId(p.getId()),viewer!=null&&favourites.findByCustomerIdAndProjectId(viewer.getId(),p.getId()).isPresent());}
 public List<ProjectResponse> list(String query,String stage,boolean mine,HttpSession s){UserAccount u=mine?auth.require(s,Role.DESIGNER):auth.optional(s);String q=query==null?"":query.toLowerCase();List<IDP> rows=mine?projects.findByOwnerId(u.getId()):projects.findAllByOrderByCreatedAtDesc();return rows.stream().filter(p->(stage==null||stage.isBlank()||p.getStage().equals(stage))&&(p.getTitle()+" "+p.getDescription()+" "+p.getCategory().getName()+" "+p.getCompany().getName()+" "+p.getLocation()).toLowerCase().contains(q)).map(p->dto(p,u)).toList();}
 public ProjectResponse view(Long id,HttpSession s){return dto(find(id),auth.optional(s));}
 public IDP find(Long id){return projects.findById(id).orElseThrow(()->error(404,"Project not found"));}
 private IDP owned(Long id,UserAccount u){IDP p=find(id);if(!p.belongsTo(u))throw error(403,"This project belongs to another designer");return p;}
 public ProjectResponse save(Long id,ProjectRequest d,HttpSession s){UserAccount u=auth.require(s,Role.DESIGNER);IDP p=id==null?new IDP():owned(id,u);Company c=companies.findById(d.companyId()).orElseThrow(()->error(404,"Company not found"));if(!c.getOwner().getId().equals(u.getId()))throw error(403,"Choose a company you own");IDPCategory cat=categories.findById(d.categoryId()).orElseThrow(()->error(404,"Category not found"));if(!cat.isActive())throw error(400,"Choose an active category");String image=d.imageUrl()==null?"":d.imageUrl().trim();if(!image.isEmpty()&&!image.startsWith("https://"))throw error(400,"Use an HTTPS image URL");if(!Set.of("HDB","Condominium","Landed","Commercial").contains(d.propertyType()))throw error(400,"Choose a valid property type");if(id==null){p.setOwner(u);p.setCreatedAt(Instant.now());}p.setCompany(c);p.setCategory(cat);p.setTitle(d.title().trim());p.setDescription(d.description());p.setProgress(d.progress());p.changeStage(d.stage());p.setPropertyType(d.propertyType());p.setLocation(d.location());p.setBudget(d.budget());p.setArea(d.area());p.setImageUrl(image);projects.save(p);activity.record(id==null?"project_created":"project_updated",u,p.getId());return dto(p,u);}
 public void delete(Long id,HttpSession s){UserAccount u=auth.require(s,Role.DESIGNER);IDP p=owned(id,u);favourites.deleteByProjectId(id);views.deleteByProjectId(id);projects.delete(p);activity.record("project_deleted",u,id);}
 public List<ProjectResponse> metrics(HttpSession s){UserAccount u=auth.require(s,Role.DESIGNER);return projects.findByOwnerId(u.getId()).stream().map(p->dto(p,u)).toList();}
}
