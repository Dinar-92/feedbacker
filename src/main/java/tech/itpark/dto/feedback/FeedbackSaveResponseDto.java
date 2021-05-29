package tech.itpark.dto.feedback;

import lombok.Value;

@Value
public class FeedbackSaveResponseDto {
    private long id;
    private long authorId;
    private String product;
    private String content;


}
