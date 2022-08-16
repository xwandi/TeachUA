package com.softserve.teachua.dto.test.questionCategory;

import com.softserve.teachua.utils.validations.CheckRussian;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionCategoryProfile extends RepresentationModel<QuestionCategoryProfile> {
<<<<<<< HEAD
    @NotBlank(message = "Назва категорії тесту не може бути порожньою.")
    @CheckRussian(message = "Назва категорії тесту містить недопустимі символи.")
    @Size(min = 3, message = "Назва категорії тесту повинна містити більше ніж 3 символи." )
=======
    @NotBlank(message = "Назва категорії не може бути порожньою.")
    @CheckRussian(message = "Назва категорії містить недопустимі символи.")
    @Size(min = 3, message = "Назва категорії повинна містити більше ніж 3 символи." )
>>>>>>> fedf2288ce4c9f8f8af264a86081bb244ebdefbb
    private String title;
}
