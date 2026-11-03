package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import java.util.Set;
public final class DtoMapper {
 private DtoMapper(){}
 public static ProfileResponse profile(UserProfile p,Long accountId){return p==null?null:new ProfileResponse(p.getId(),p.getName(),p.getEmail(),p.getPhone(),p.getAddress(),p.getBio(),p.getStatus(),accountId);}
 public static AccountResponse account(UserAccount u,Role active){return new AccountResponse(u.getId(),u.getProfile()==null?"Profile removed":u.getProfile().getName(),u.getEmail(),u.getDefaultRole(),active,Set.copyOf(u.getRoles()),u.getStatus(),profile(u.getProfile(),u.getId()));}
 public static CompanyResponse company(Company c){return new CompanyResponse(c.getId(),c.getOwner().getId(),c.getName(),c.getEmail(),c.getPhone(),c.getAddress(),c.getDescription());}
 public static RegistrationResponse registration(RegistrationRequest r){return new RegistrationResponse(r.getId(),r.getName(),r.getEmail(),r.getRequestedRole(),r.getPhone(),r.getCompanyName(),r.getStatus(),r.getCreatedAt(),r.getReviewer()==null?null:r.getReviewer().getId(),r.getCreatedAccount()==null?null:r.getCreatedAccount().getId());}
}
