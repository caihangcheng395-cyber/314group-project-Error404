package edu.uow.csit314.idp.boundary;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.uow.csit314.idp.controller.ViewIDPCategoryController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViewIDPCategoryPage.class)
class ViewIDPCategoryPageTest {
    @Autowired private MockMvc mvc;
    @MockBean private ViewIDPCategoryController controller;

    @Test
    void shouldExposeCategoriesAsJson() throws Exception {
        when(controller.viewAllCategories()).thenReturn(List.of(
                new IDPCategoryResponse(1L, "Living Room", "A living space", true)));

        mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Living Room"))
                .andExpect(jsonPath("$[0].active").value(true));
    }
}
