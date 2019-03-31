package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Category category;
    private User user;

    public enum Category {
        SPORT, NEWS, SOCIAL
    }
}
