package com.softserve.teachua.controller;

import com.softserve.teachua.controller.marker.Api;
import com.softserve.teachua.dto.challenge.*;
import com.softserve.teachua.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name="challenge", description="the Challenge API")
public class ChallengeController implements Api {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * Use this endpoint to get all challenges, either active or non-active challenges.
     * The controller returns {@code List<ChallengePreview>}.
     * @param active - Ignore this param to get all challenges, or put true/false to get active or not challenges.
     * @return {@code List<ChallengePreview>}.
     */
//    @Operation(summary = "Get all challenges")
    @GetMapping("/challenges")
    public List<ChallengePreview> getAllChallenges(@RequestParam(required = false) Boolean active) {
        return challengeService.getAllChallenges(active);
    }

    /**
     * Use this endpoint to get full information about challenge by its id with tasks that have already begun.
     * The controller returns {@code ChallengeProfile}.
     * @param id - put challenge id here.
     * @return {@code ChallengeProfile}.
     */
    @GetMapping("/challenge/{id}")
    public ChallengeProfile getChallenge(@PathVariable Long id) {
        return challengeService.getChallenge(id);
    }

    /**
     * Use this endpoint to create new challenge.
     * The controller returns {@code SuccessCreatedChallenge}.
     * This feature available only for admins.
     * @param createChallenge    - put required parameters here.
     * @param httpServletRequest - autowired by spring to get user from request.
     * @return {@code SuccessCreatedChallenge}.
     */
    @PostMapping("/challenge")
    public SuccessCreatedChallenge createChallenge(
            @Valid @RequestBody CreateChallenge createChallenge,
            HttpServletRequest httpServletRequest) {
        return challengeService.createChallenge(createChallenge, httpServletRequest);
    }

    /**
     * Use this endpoint to update some values of challenge.
     * The controller returns {@code SuccessUpdatedChallenge}.
     * This feature available only for admins.
     *
     * @param id              - put challenge id here.
     * @param updateChallenge - put new and old parameters here.
     * @return {@code SuccessUpdatedChallenge} - shows result of updating challenge.
     */
    @PutMapping("/challenge/{id}")
    public SuccessUpdatedChallenge updateChallenge(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChallenge updateChallenge) {
        return challengeService.updateChallenge(id, updateChallenge);
    }

    /**
     * Use this endpoint to archive challenge and its tasks.
     * The controller returns {@code ChallengeDeleteResponse}.
     * This feature available only for admins.
     *
     * @param id - put challenge id here.
     * @return {@code ChallengeDeleteResponse} - shows which challenge and tasks was removed.
     */
    @DeleteMapping("/challenge/{id}")
    public ChallengeDeleteResponse deleteChallenge(@PathVariable Long id) {
        return challengeService.deleteChallenge(id);
    }

    /**
     * Use this endpoint to update some values of challenge.
     * The controller returns {@code SuccessUpdateChallengePreview}.
     * This feature available only for admins.
     *
     * @param id - put challenge id here.
     * @param updateChallengePreview - put new and old parameters here.
     * @return {@code SuccessUpdateChallengePreview} - shows result of updating challenge.
     */
    @PatchMapping("/challenge/{id}")
    public SuccessUpdateChallengePreview updateChallengePreview(
            @PathVariable Long id,
            @Valid @RequestBody SuccessUpdateChallengePreview updateChallengePreview) {
        return challengeService.updateChallengePreview(id, updateChallengePreview);
    }

}
