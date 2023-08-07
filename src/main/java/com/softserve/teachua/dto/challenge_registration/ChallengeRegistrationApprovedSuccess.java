package com.softserve.teachua.dto.challenge_registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChallengeRegistrationApprovedSuccess {
    private Long id;
    private boolean isApproved;
}
