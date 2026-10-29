package edu.uow.csit314.idp;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UpdateCategoryIntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private IDPCategoryRepository repository;

    @Test
    void updatesH2DatabaseThroughHttpEndpoint() throws Exception {
        var category = repository.saveAndFlush(new IDPCategory("Temporary demo category", "Before", true));
        Long id = category.getId();

        mvc.perform(put("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated demo category\",\"description\":\"After\",\"active\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated demo category"));

        var stored = repository.findById(id).orElseThrow();
        assertThat(stored.getName()).isEqualTo("Updated demo category");
        assertThat(stored.getDescription()).isEqualTo("After");
        assertThat(stored.isActive()).isFalse();
    }

    @Test
    void duplicateNameReturns409() throws Exception {
        var category = repository.saveAndFlush(new IDPCategory("Unique test category", "Before", true));

        mvc.perform(put("/api/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Kitchen\",\"description\":\"After\",\"active\":true}"))
                .andExpect(status().isConflict());
    }
}
