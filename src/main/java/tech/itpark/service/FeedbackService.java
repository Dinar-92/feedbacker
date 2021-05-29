package tech.itpark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.dto.feedback.*;
import tech.itpark.exception.PermissionDeniedException;
import tech.itpark.model.Feedback;
import tech.itpark.repository.FeedbackRepository;
import tech.itpark.security.Auth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;

    public List<Feedback> getAllFeedback() {
        return repository.getAll();
    }

    public FeedbackSaveResponseDto createFeedback (Auth auth, FeedbackSaveRequestDto dto) {
        if (auth.isAnonymous()) {
            throw new PermissionDeniedException();
        }
        final var saved = repository.create(new Feedback(
                dto.getId(),
                auth.getId(),
                dto.getAuthorId(),
                dto.getProduct()
        ));
        return new FeedbackSaveResponseDto(
                saved.getId(),
                saved.getAuthorId(),
                saved.getProduct(),
                saved.getContent()
        );
    }

    public FeedbackUpdateResponseDto updateFeedback (Auth auth, FeedbackUpdateRequestDto dto) {
        if (!auth.hasAnyRole("ROLE_ADMIN", "ROLE_MODERATOR")) {
            throw new PermissionDeniedException();
        }
        final var update = repository.update(new Feedback(
                dto.getId(),
                dto.getAuthorId(),
                dto.getProduct(),
                dto.getContent()
        ));
        return new FeedbackUpdateResponseDto(
                update.getId(),
                update.getAuthorId(),
                update.getProduct(),
                update.getContent());
    }

    public FeedbackRemoveResponseDto removeByIdFeedback(Auth auth, FeedbackRemoveRequestDto dto) {
        final var removedId = dto.getId();
        if (auth.isAnonymous()) {
            throw new PermissionDeniedException();
        }
        repository.remove(removedId, true);
        return new FeedbackRemoveResponseDto(dto.getId());
    }

}
