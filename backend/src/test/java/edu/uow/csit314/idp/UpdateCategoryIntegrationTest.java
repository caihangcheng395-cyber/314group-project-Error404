
package edu.uow.csit314.idp;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UpdateCategoryIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IDPCategoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    record Client(MockHttpSession session, String csrf) {}

    // Obtain a real CSRF token and log in as Platform Manager.
    private Client loginAsPlatformManager() throws Exception {

        MvcResult csrfResponse = mvc.perform(
                get("/api/auth/csrf"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpSession session =
                (MockHttpSession) csrfResponse
                        .getRequest()
                        .getSession(false);

        String csrf = objectMapper
                .readTree(csrfResponse.getResponse()
                        .getContentAsString())
                .get("token")
                .asText();

        mvc.perform(post("/api/auth/login")
                        .session(session)
                        .header("X-CSRF-Token", csrf)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "email": "manager@forma.demo",
                              "password": "Demo123!",
                              "workspace": "PLATFORM_MANAGER"
                            }
                            """))
                .andExpect(status().isOk());

        return new Client(session, csrf);
    }

    // Send an authenticated PUT request with CSRF protection.
    private ResultActions putCategory(
            Client client,
            Long id,
            String body) throws Exception {

        return mvc.perform(put("/api/categories/{id}", id)
                .session(client.session())
                .header("X-CSRF-Token", client.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }

    @Test
    void updatesH2DatabaseThroughHttpEndpoint() throws Exception {

        Client manager = loginAsPlatformManager();

        IDPCategory category = repository.saveAndFlush(
                new IDPCategory(
                        "Temporary demo category",
                        "Before",
                        true));

        Long id = category.getId();

        putCategory(manager, id, """
                {
                  "name": "Updated demo category",
                  "description": "After",
                  "active": false
                }
                """)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Updated demo category"))
                .andExpect(jsonPath("$.active")
                        .value(false));

        IDPCategory stored =
                repository.findById(id).orElseThrow();

        assertThat(stored.getName())
                .isEqualTo("Updated demo category");

        assertThat(stored.getDescription())
                .isEqualTo("After");

        assertThat(stored.isActive())
                .isFalse();
    }

    @Test
    void duplicateNameReturns409() throws Exception {

        Client manager = loginAsPlatformManager();

        repository.saveAndFlush(
                new IDPCategory(
                        "Reserved test category",
                        "Existing category",
                        true));

        IDPCategory category = repository.saveAndFlush(
                new IDPCategory(
                        "Unique test category",
                        "Before",
                        true));

        putCategory(manager, category.getId(), """
                {
                  "name": "Reserved test category",
                  "description": "After",
                  "active": true
                }
                """)
                .andExpect(status().isConflict());

        IDPCategory stored = repository
                .findById(category.getId())
                .orElseThrow();

        assertThat(stored.getName())
                .isEqualTo("Unique test category");
    }
}
