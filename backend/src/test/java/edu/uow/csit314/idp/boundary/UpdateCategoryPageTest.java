package edu.uow.csit314.idp.boundary;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.uow.csit314.idp.controller.UpdateIDPCategoryController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UpdateCategoryPage.class)
class UpdateCategoryPageTest {
    @Autowired private MockMvc mvc;
    @MockBean private UpdateIDPCategoryController controller;

    @Test
    void validUpdateReturnsUpdatedCategory() throws Exception {
        when(controller.updateCategory(eq(3L), any(UpdateIDPCategoryRequest.class)))
                .thenReturn(new IDPCategoryResponse(3L, "Master Bedroom", "Updated", false));

        mvc.perform(put("/api/categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Master Bedroom\",\"description\":\"Updated\",\"active\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Master Bedroom"))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void invalidNameReturns400() throws Exception {
        mvc.perform(put("/api/categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"  \",\"description\":\"Updated\",\"active\":true}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingActiveFlagReturns400() throws Exception {
        mvc.perform(put("/api/categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Bedroom\",\"description\":\"Updated\"}"))
                .andExpect(status().isBadRequest());
    }
}
