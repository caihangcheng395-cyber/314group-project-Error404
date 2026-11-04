package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.ApplicationSetting;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting,Long> { boolean existsByKeyName(String keyName); }
