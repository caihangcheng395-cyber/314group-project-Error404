package edu.uow.csit314.idp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ViewIDPCategoryControllerTest {
    private final IDPCategoryRepository repository = mock(IDPCategoryRepository.class);
    private final ViewIDPCategoryController controller = new ViewIDPCategoryController(repository);

    @Test
    void shouldListCategoriesFromRepository() {
        when(repository.findAllByOrderByNameAsc()).thenReturn(List.of(
                new IDPCategory("Bedroom", "Quiet interiors", true),
                new IDPCategory("Kitchen", "Storage and cooking", true)));

        var results = controller.viewAllCategories();

        assertThat(results).hasSize(2);
        assertThat(results).extracting("name").containsExactly("Bedroom", "Kitchen");
    }

    @Test
    void shouldReturn404WhenCategoryDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.viewCategory(999L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error -> assertThat(((ResponseStatusException) error).getStatusCode())
                        .isEqualTo(HttpStatus.NOT_FOUND));
    }
}
