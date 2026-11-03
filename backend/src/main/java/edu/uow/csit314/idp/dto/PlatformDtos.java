package edu.uow.csit314.idp.dto;
import edu.uow.csit314.idp.entity.Role;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
/** Explicit API DTOs; JPA entities and password hashes never leave the Control layer. */
public final class PlatformDtos {
 private PlatformDtos() {}
 public record ProfileRequest(@NotBlank @Size(max=250) String name,@NotBlank @Email @Size(max=250) String email,@Size(max=100) String phone,@Size(max=500) String address,@Size(max=2000) String bio) {}
 public record ProfileResponse(Long id,String name,String email,String phone,String address,String bio,String status,Long accountId) {}
 public record AccountRequest(@NotBlank @Email @Size(max=250) String email,@Size(max=128) String password,@NotNull Role defaultRole,Set<Role> roles,Long profileId,@Size(max=250) String name) {}
 public record AccountResponse(Long id,String name,String email,Role defaultRole,Role activeRole,Set<Role> roles,String status,ProfileResponse profile) {}
 public record LoginRequest(@NotBlank @Email String email,@NotBlank String password,Role workspace) {}
 public record DemoLoginRequest(@NotNull Long userId,@NotNull Role workspace) {}
 public record WorkspaceRequest(@NotNull Role workspace) {}
 public record CompanyRequest(@NotBlank @Size(max=250) String name,@NotBlank @Email @Size(max=250) String email,@Size(max=100) String phone,@Size(max=500) String address,@Size(max=5000) String description) {}
 public record CompanyResponse(Long id,Long ownerId,String name,String email,String phone,String address,String description) {}
 public record ProjectRequest(@NotBlank @Size(max=250) String title,@NotNull Long companyId,@NotNull Long categoryId,@NotBlank String stage,@NotBlank @Size(max=100) String propertyType,@Size(max=250) String location,@PositiveOrZero double budget,@PositiveOrZero double area,@Min(0) @Max(100) int progress,@Size(max=1500) String imageUrl,@Size(max=5000) String description) {}
 public record ProjectResponse(Long id,Long ownerId,Long companyId,String companyName,Long categoryId,String category,String title,String description,String stage,String propertyType,String location,double budget,double area,int progress,String imageUrl,Instant createdAt,long views,long shortlists,boolean shortlisted) {}
 public record CategoryRequest(@NotBlank @Size(max=100) String name,@Size(max=500) String description,boolean active) {}
 public record SignupRequest(@NotBlank @Size(max=250) String name,@NotBlank @Email @Size(max=250) String email,@NotNull Role role,@NotBlank @Size(min=8,max=128) String password,@NotBlank String confirmPassword,@Size(max=100) String phone,@Size(max=250) String companyName) {}
 public record RegistrationResponse(Long id,String name,String email,Role role,String phone,String companyName,String status,Instant createdAt,Long reviewerId,Long accountId) {}
 public record ReviewRequest(@NotNull Role role) {}
 public record DemoUserResponse(Long id,String name,String email,Set<Role> roles,List<Integer> sprints,String stories) {}
 public record ReportResponse(String period,Instant from,Instant to,long activities,long projectViews,long shortlistAdds,long shortlistRemovals,long newProjects,long logins,long approvals,long accounts,long projects) {}
}
