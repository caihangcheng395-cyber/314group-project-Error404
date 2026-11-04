
package edu.uow.csit314.idp.controller;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.entity.Role;
import edu.uow.csit314.idp.entity.UserAccount;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import jakarta.servlet.http.HttpSession;

class UpdateIDPCategoryControllerTest {

    private final IDPCategoryRepository repository =
            mock(IDPCategoryRepository.class);

    private final AuthenticationController auth =
            mock(AuthenticationController.class);

    private final ActivityController activity =
            mock(ActivityController.class);

    private final HttpSession session =
            mock(HttpSession.class);

    private final UserAccount user =
            mock(UserAccount.class);

    private final UpdateIDPCategoryController controller =
            new UpdateIDPCategoryController(repository, auth, activity);

    private void allowPlatformManager() {
        when(auth.require(session, Role.PLATFORM_MANAGER))
                .thenReturn(user);
    }

    @Test
    void updatesExistingCategoryAndTrimsInput() {

        allowPlatformManager();

        IDPCategory category =
                new IDPCategory("Bedroom", "Old description", true);

        when(repository.findById(5L))
                .thenReturn(Optional.of(category));

        when(repository.saveAndFlush(any(IDPCategory.class)))
                .thenAnswer(call -> call.getArgument(0));

        var result = controller.updateCategory(
                5L,
                new UpdateIDPCategoryRequest(
                        "  Master Bedroom  ",
                        "  New description  ",
                        false),
                session);

        assertThat(result.name()).isEqualTo("Master Bedroom");
        assertThat(result.description()).isEqualTo("New description");
        assertThat(result.active()).isFalse();

        verify(auth).require(session, Role.PLATFORM_MANAGER);

        verify(repository)
                .existsByNameIgnoreCaseAndIdNot(
                        "Master Bedroom", 5L);

        verify(repository).saveAndFlush(category);

        verify(activity).record("category_updated", user, null);
    }

    @Test
    void returns404WhenCategoryDoesNotExist() {

        allowPlatformManager();

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                controller.updateCategory(
                        999L,
                        new UpdateIDPCategoryRequest(
                                "New name", "", true),
                        session))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error ->
                        assertThat(
                                ((ResponseStatusException) error)
                                        .getStatusCode())
                                .isEqualTo(HttpStatus.NOT_FOUND));

        verify(repository, never())
                .saveAndFlush(any(IDPCategory.class));
    }

    @Test
    void returns409ForDuplicateCategoryName() {

        allowPlatformManager();

        when(repository.findById(5L))
                .thenReturn(Optional.of(
                        new IDPCategory("Bedroom", "", true)));

        when(repository.existsByNameIgnoreCaseAndIdNot(
                "Kitchen", 5L))
                .thenReturn(true);

        assertThatThrownBy(() ->
                controller.updateCategory(
                        5L,
                        new UpdateIDPCategoryRequest(
                                "Kitchen", "", true),
                        session))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error ->
                        assertThat(
                                ((ResponseStatusException) error)
                                        .getStatusCode())
                                .isEqualTo(HttpStatus.CONFLICT));

        verify(repository, never())
                .saveAndFlush(any(IDPCategory.class));
    }

    @Test
    void rejectsUnauthorizedUser() {

        when(auth.require(session, Role.PLATFORM_MANAGER))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Platform Manager access required"));

        assertThatThrownBy(() ->
                controller.updateCategory(
                        5L,
                        new UpdateIDPCategoryRequest(
                                "Kitchen", "", true),
                        session))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error ->
                        assertThat(
                                ((ResponseStatusException) error)
                                        .getStatusCode())
                                .isEqualTo(HttpStatus.FORBIDDEN));

        verifyNoInteractions(repository, activity);
    }
}
