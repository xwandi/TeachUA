package com.softserve.teachua.repository;

import com.softserve.teachua.model.CertificateDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateDatesRepository extends JpaRepository<CertificateDates, Integer> {

    List<CertificateDates> findAll();

    Optional<CertificateDates> findById(Integer integer);

    Optional<CertificateDates> findByDuration(String duration);
}
