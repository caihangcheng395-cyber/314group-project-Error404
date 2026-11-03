package edu.uow.csit314.idp.config;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.controller.PasswordService;
import java.time.Instant;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/** One-time coursework sample data, kept outside schema migrations and request handling. */
@Component @Order(20)
public class PlatformDataInitializer implements CommandLineRunner {
 private final UserAccountRepository users;private final CompanyRepository companies;private final IDPRepository projects;private final IDPCategoryRepository categories;private final ApplicationSettingRepository settings;private final PasswordService passwords;
 @Value("${app.demo-seed-enabled:true}") private boolean enabled;
 public PlatformDataInitializer(UserAccountRepository u,CompanyRepository c,IDPRepository p,IDPCategoryRepository cats,ApplicationSettingRepository s,PasswordService pw){users=u;companies=c;projects=p;categories=cats;settings=s;passwords=pw;}
 @Override @Transactional public void run(String... args){if(!enabled||settings.existsByKeyName("forma.seed.v1"))return;
  user("Liao Yi Jou","liao.yijou@forma.demo",Role.DESIGNER,Set.of(Role.DESIGNER,Role.CUSTOMER),"Sprints 2, 3 · Stories #13–38");
  UserAccount crystal=user("Crystal Tan","designer@forma.demo",Role.DESIGNER,Set.of(Role.DESIGNER,Role.USER_ADMIN),"Sprints 1, 2 · Stories #1–29");
  user("Cai Hangcheng","cai.hangcheng@forma.demo",Role.PLATFORM_MANAGER,Set.of(Role.PLATFORM_MANAGER),"Sprint 4 · Stories #39–48");
  user("Li Shan","li.shan@forma.demo",Role.DESIGNER,Set.of(Role.DESIGNER),"Sprint 2 · Stories #13–29 (assumed ~KiitoZ)");
  user("Dhanyasri Kanagaraju","admin@forma.demo",Role.USER_ADMIN,Set.of(Role.USER_ADMIN),"Sprint 1 · Stories #1–12");
  user("Keith Low","keith.low@forma.demo",Role.CUSTOMER,Set.of(Role.CUSTOMER,Role.DESIGNER,Role.PLATFORM_MANAGER),"Sprints 3, 4 · Stories #30–48");
  user("Alex Morgan","alex@forma.demo",Role.CUSTOMER,Set.of(Role.CUSTOMER),"Sample customer");user("Jordan Lee","manager@forma.demo",Role.PLATFORM_MANAGER,Set.of(Role.PLATFORM_MANAGER),"Sample platform manager");
  Company c=new Company();c.setOwner(crystal);c.setName("Studio North");c.setEmail("hello@studionorth.demo");c.setPhone("+65 6123 4567");c.setAddress("43 Keong Saik Road, Singapore");c.setDescription("Thoughtful interiors for the way you live. Warm materials and quiet contemporary details.");companies.save(c);
  String[] titles={"The Quiet Residence","Light & Living","A Natural Connection","The Urban Retreat","Soft Sanctuary","Earth & Form"};String[] styles={"Scandinavian","Minimalist","Minimalist","Contemporary","Scandinavian","Industrial"};String[] places={"Tampines","River Valley","Punggol","Tanjong Pagar","Bishan","Serangoon"};String[] types={"HDB","Condominium","HDB","Condominium","HDB","Landed"};double[] budgets={68000,95000,58000,120000,74000,185000};double[] areas={112,138,95,156,121,230};String[] images={"photo-1600210492486-724fe5c67fb0","photo-1600607687920-4e2a09cf159d","photo-1600566753190-17f0baa2a6c3","photo-1600607687939-ce8a6c25118c","photo-1600210491892-03d54c0aaf87","photo-1600607688969-a5bfcd646154"};
  for(int i=0;i<titles.length;i++){IDP p=new IDP();p.setOwner(crystal);p.setCompany(c);p.setCategory(categories.findByNameIgnoreCase(styles[i]).orElseThrow());p.setTitle(titles[i]);p.setDescription("A considered home where natural textures and welcoming spaces come together, shaped around comfortable everyday living.");p.setPropertyType(types[i]);p.setLocation(places[i]);p.setBudget(budgets[i]);p.setArea(areas[i]);p.setProgress(35+i*10);p.changeStage(i%2==0?"CURRENT":"PAST");p.setImageUrl("https://images.unsplash.com/"+images[i]+"?auto=format&fit=crop&w=1200&q=85");p.setCreatedAt(Instant.now());projects.save(p);}
  ApplicationSetting marker=new ApplicationSetting();marker.setKeyName("forma.seed.v1");marker.setSettingValue(Instant.now().toString());settings.save(marker);
 }
 private UserAccount user(String name,String email,Role role,Set<Role> roles,String bio){Optional<UserAccount> old=users.findByEmailIgnoreCase(email);if(old.isPresent())return old.get();UserProfile p=new UserProfile();p.setName(name);p.setEmail(email);p.setBio(bio);p.setStatus("ACTIVE");UserAccount u=new UserAccount();u.setProfile(p);u.setEmail(email);u.setPasswordHash(passwords.encode("Demo123!"));u.setDefaultRole(role);u.setRoles(new HashSet<>(roles));u.setStatus("ACTIVE");return users.save(u);}
}
