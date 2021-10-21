package com.softserve.teachua.dto.challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.teachua.dto.marker.Convertible;
import com.softserve.teachua.dto.task.TaskPreview;
import com.softserve.teachua.dto.user.UserPreview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChallengeProfile implements Convertible {
    private Long id;
    private String name;
    private String title;
    private String description;
    private String picture;
    private Long sortNumber;
    private Boolean isActive;
    private Page<TaskPreview> tasks;
    private UserPreview user;
}
