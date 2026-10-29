package edu.uow.csit314.idp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class UpdateIDPCategoryControllerTest {
    private final IDPCategoryRepository repository = mock(IDPCategoryRepository.class);
    private final UpdateIDPCategoryController controller = new UpdateIDPCategoryController(repository);

    @Test
    void updatesExistingCategoryAndTrimsInput() {
        IDPCategory category = new IDPCategory("Bedroom", "Old description", true);
        when(repository.findById(5L)).thenReturn(Optional.of(category));
        when(repository.saveAndFlush(any(IDPCategory.class))).thenAnswer(call -> call.getArgument(0));

        var result = controller.updateCategory(5L,
                new UpdateIDPCategoryRequest("  Master Bedroom  ", "  New description  ", false));

        assertThat(result.name()).isEqualTo("Master Bedroom");
        assertThat(result.description()).isEqualTo("New description");
        assertThat(result.active()).isFalse();
        verify(repository).existsByNameIgnoreCaseAndIdNot("Master Bedroom", 5L);
        verify(repository).saveAndFlush(category);
    }

    @Test
    void returns404WhenCategoryDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.updateCategory(999L,
                new UpdateIDPCategoryRequest("New name", "", true)))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error -> assertThat(((ResponseStatusException) error).getStatusCode())
                        .isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void returns409ForDuplicateCategoryName() {
        when(repository.findById(5L)).thenReturn(Optional.of(new IDPCategory("Bedroom", "", true)));
        when(repository.existsByNameIgnoreCaseAndIdNot("Kitchen", 5L)).thenReturn(true);

        assertThatThrownBy(() -> controller.updateCategory(5L,
                new UpdateIDPCategoryRequest("Kitchen", "", true)))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error -> assertThat(((ResponseStatusException) error).getStatusCode())
                        .isEqualTo(HttpStatus.CONFLICT));
    }
}
