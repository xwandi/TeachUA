package com.softserve.teachua.dto.test.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

    private Long id;

    @NotNull
    private boolean correct;

    @NotBlank(message = "Текст відповіді не може бути пустим.")
    private String text;

    private int value;

}
