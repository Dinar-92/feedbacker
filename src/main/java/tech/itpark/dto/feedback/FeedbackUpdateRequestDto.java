package tech.itpark.dto.feedback;

import lombok.Value;

@Value
public class FeedbackUpdateRequestDto {
    private long id;
    private long authorId;
    private String product;
    private String content;
}
