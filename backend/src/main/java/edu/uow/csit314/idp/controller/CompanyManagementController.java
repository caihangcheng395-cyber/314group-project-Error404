package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.uow.csit314.idp.controller.AuthenticationController.error;
@Service @Transactional
public class CompanyManagementController {
 private final CompanyRepository companies;private final IDPRepository projects;private final AuthenticationController auth;private final ActivityController activity;
 public CompanyManagementController(CompanyRepository c,IDPRepository p,AuthenticationController a,ActivityController e){companies=c;projects=p;auth=a;activity=e;}
 public List<CompanyResponse> list(String query,HttpSession s){UserAccount actor=auth.require(s,Role.DESIGNER);String q=query==null?"":query.toLowerCase();return companies.findByOwnerId(actor.getId()).stream().filter(c->(c.getName()+" "+c.getEmail()+" "+c.getAddress()).toLowerCase().contains(q)).map(DtoMapper::company).toList();}
 private Company owned(Long id,UserAccount u){Company c=companies.findById(id).orElseThrow(()->error(404,"Company not found"));if(!c.getOwner().getId().equals(u.getId()))throw error(403,"This company belongs to another designer");return c;}
 public CompanyResponse publicView(Long id){return DtoMapper.company(companies.findById(id).orElseThrow(()->error(404,"Company not found")));}
 public CompanyResponse view(Long id,HttpSession s){return DtoMapper.company(owned(id,auth.require(s,Role.DESIGNER)));}
 public CompanyResponse save(Long id,CompanyRequest d,HttpSession s){UserAccount u=auth.require(s,Role.DESIGNER);Company c=id==null?new Company():owned(id,u);if(id==null)c.setOwner(u);c.setName(d.name().trim());c.setEmail(d.email().trim().toLowerCase());c.setPhone(d.phone());c.setAddress(d.address());c.setDescription(d.description());companies.save(c);activity.record(id==null?"company_created":"company_updated",u,null);return DtoMapper.company(c);}
 public void delete(Long id,HttpSession s){UserAccount u=auth.require(s,Role.DESIGNER);Company c=owned(id,u);if(projects.existsByCompanyId(id))throw error(409,"Remove or reassign this company's projects first");companies.delete(c);activity.record("company_deleted",u,null);}
}
