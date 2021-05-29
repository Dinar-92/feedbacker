package tech.itpark.repository;

import tech.itpark.model.Feedback;

import java.util.List;

public interface FeedbackRepository {
    List<Feedback> getAll();

    Feedback create(Feedback feedback);

    Feedback update(Feedback feedback);

    void remove(Long id, Boolean removed);
}
