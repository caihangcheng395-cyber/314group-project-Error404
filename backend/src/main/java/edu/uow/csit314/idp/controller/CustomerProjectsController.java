package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class CustomerProjectsController {
 private final FavouriteRepository favourites;private final ProjectViewEventRepository views;private final IDPManagementController projects;private final AuthenticationController auth;private final ActivityController activity;
 public CustomerProjectsController(FavouriteRepository f,ProjectViewEventRepository v,IDPManagementController p,AuthenticationController a,ActivityController e){favourites=f;views=v;projects=p;auth=a;activity=e;}
 public List<ProjectResponse> shortlist(String query,HttpSession s){UserAccount u=auth.require(s,Role.CUSTOMER);String q=query==null?"":query.toLowerCase();return favourites.findByCustomerId(u.getId()).stream().map(Favourite::getProject).filter(p->(p.getTitle()+" "+p.getLocation()+" "+p.getCategory().getName()).toLowerCase().contains(q)).map(p->projects.dto(p,u)).toList();}
 public ProjectResponse save(Long id,HttpSession s){UserAccount u=auth.require(s,Role.CUSTOMER);IDP p=projects.find(id);if(favourites.findByCustomerIdAndProjectId(u.getId(),id).isEmpty()){Favourite f=new Favourite();f.setCustomer(u);f.setProject(p);f.setCreatedAt(Instant.now());favourites.saveAndFlush(f);activity.record("shortlist_added",u,id);}return projects.dto(p,u);}
 public void remove(Long id,HttpSession s){UserAccount u=auth.require(s,Role.CUSTOMER);favourites.findByCustomerIdAndProjectId(u.getId(),id).ifPresent(f->{favourites.delete(f);activity.record("shortlist_removed",u,id);});}
 public ProjectResponse recordView(Long id,HttpSession s){UserAccount u=auth.require(s,Role.CUSTOMER);IDP p=projects.find(id);ProjectViewEvent e=new ProjectViewEvent();e.setProject(p);e.setViewer(u);e.setCreatedAt(Instant.now());views.saveAndFlush(e);activity.record("project_viewed",u,id);return projects.dto(p,u);}
}
