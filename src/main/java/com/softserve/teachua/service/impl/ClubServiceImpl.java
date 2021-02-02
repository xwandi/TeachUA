package com.softserve.teachua.service.impl;

import com.softserve.teachua.converter.DtoConverter;
import com.softserve.teachua.dto.club.ClubResponse;
import com.softserve.teachua.dto.club.SuccessCreatedClub;
import com.softserve.teachua.dto.search.SearchClubProfile;
import com.softserve.teachua.dto.search.SearchPossibleResponse;
import com.softserve.teachua.dto.club.ClubProfile;
import com.softserve.teachua.exception.AlreadyExistException;
import com.softserve.teachua.exception.NotExistException;
import com.softserve.teachua.model.Club;
import com.softserve.teachua.repository.ClubRepository;
import com.softserve.teachua.service.ClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ClubServiceImpl implements ClubService {
    private static final String CLUB_ALREADY_EXIST = "Club already exist with name: %s";
    private static final String CLUB_NOT_FOUND_BY_ID = "Club not found by id: %s";
    private static final String CLUB_NOT_FOUND_BY_NAME = "Club not found by name: %s";

    private final ClubRepository clubRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, DtoConverter dtoConverter) {
        this.clubRepository = clubRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public ClubResponse getClubProfileById(Long id) {
        return dtoConverter.convertToDto(getClubById(id), ClubResponse.class);
    }

    @Override
    public Club getClubById(Long id) {
        if (!isClubExistById(id)) {
            String clubNotFoundById = String.format(CLUB_NOT_FOUND_BY_ID, id);
            log.error(clubNotFoundById);
            throw new NotExistException(clubNotFoundById);
        }

        Club club = clubRepository.getById(id);
        log.info("**/getting club by id = " + club.getId());
        return club;
    }

    @Override
    public Club getClubByName(String name) {
        if (!isClubExistByName(name)) {
            String clubNotFoundById = String.format(CLUB_NOT_FOUND_BY_NAME, name);
            log.error(clubNotFoundById);
            throw new NotExistException(clubNotFoundById);
        }

        Club club = clubRepository.findByName(name);
        log.info("**/getting club by name = " + club.getName());
        return club;
    }

    @Override
    public ClubProfile updateClub(ClubProfile clubProfile) {
        Club club = clubRepository.save(dtoConverter.convertToEntity(clubProfile, new Club()));
        log.info("**/updating club = " + club);
        return dtoConverter.convertToDto(club, ClubProfile.class);
    }

    @Override
    public ClubResponse getClubProfileByName(String name) {
        return dtoConverter.convertToDto(getClubByName(name), ClubResponse.class);
    }

    @Override
    public SuccessCreatedClub addClub(ClubProfile clubProfile) {
        if (isClubExistByName(clubProfile.getName())) {
            String clubAlreadyExist = String.format(CLUB_ALREADY_EXIST, clubProfile.getName());
            log.error(clubAlreadyExist);
            throw new AlreadyExistException(clubAlreadyExist);
        }

        Club club = clubRepository.save(dtoConverter.convertToEntity(clubProfile, new Club()));

        log.info("**/adding club with name = " + clubProfile.getName());
        return dtoConverter.convertToDto(club, SuccessCreatedClub.class);
    }

    @Override
    public List<ClubResponse> getListOfClubs() {
        List<ClubResponse> clubResponses = clubRepository.findAll()
                .stream()
                .map(club -> (ClubResponse) dtoConverter.convertToDto(club, ClubResponse.class))
                .collect(Collectors.toList());

        log.info("/**getting list of clubs = " + clubResponses);
        return clubResponses;
    }

    @Override
    public Page<ClubResponse> getClubsBySearchParameters(SearchClubProfile searchClubProfile, Pageable pageable) {
        Page<Club> clubResponses = clubRepository.findAllByParameters(
                searchClubProfile.getClubName(),
                searchClubProfile.getCityName(),
                searchClubProfile.getCategoryName(),
                pageable);

        return new PageImpl<>(clubResponses
                .stream()
                .map(club -> (ClubResponse) dtoConverter.convertToDto(club, ClubResponse.class))
                .collect(Collectors.toList()),
                clubResponses.getPageable(), clubResponses.getTotalElements());
    }

    @Override
    public List<SearchPossibleResponse> getPossibleClubByName(String text) {
        return clubRepository.findRandomTop3ByName(text)
                .stream()
                .map(category -> (SearchPossibleResponse) dtoConverter.convertToDto(category, SearchPossibleResponse.class))
                .collect(Collectors.toList());
    }

    private boolean isClubExistById(Long id) {
        return clubRepository.existsById(id);
    }

    private boolean isClubExistByName(String name) {
        return clubRepository.existsByName(name);
    }
}
