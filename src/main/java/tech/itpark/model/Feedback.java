package tech.itpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feedback {
    private long id;
    private long authorId;
    private String product;
    private String content;
}
