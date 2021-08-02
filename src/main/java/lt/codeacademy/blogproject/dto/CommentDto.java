package lt.codeacademy.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
public class CommentDto {

    @NotBlank(message= "add your comment")
    private String commentBody;
}
