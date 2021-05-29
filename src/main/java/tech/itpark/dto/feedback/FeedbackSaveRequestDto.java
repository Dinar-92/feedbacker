package tech.itpark.dto.feedback;

import lombok.Value;

@Value
public class FeedbackSaveRequestDto {
    private long id;
    private String authorId;
    private String product;
    private String content;
}
