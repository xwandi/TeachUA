package com.softserve.teachua.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.teachua.converter.DtoConverter;
import com.softserve.teachua.dto.certificate.CertificateResponse;
import com.softserve.teachua.exception.NotExistException;
import com.softserve.teachua.model.Certificate;
import com.softserve.teachua.repository.CertificateRepository;
import com.softserve.teachua.service.ArchiveMark;
import com.softserve.teachua.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class CertificateServiceImpl implements CertificateService, ArchiveMark<Certificate> {

    private static final String CERTIFICATE_NOT_FOUND_BY_ID = "Certificate not found by id %s";
    private static final String CERTIFICATE_NOT_FOUND_BY_SERIAL_NUMBER = "Certificate not found by serial number %s";

    private final DtoConverter dtoConverter;
    private final ObjectMapper objectMapper;
    private final CertificateRepository certificateRepository;
    @Value("${baseURL}")
    private String baseUrl;

    @Autowired
    public CertificateServiceImpl(DtoConverter dtoConverter, ObjectMapper objectMapper, CertificateRepository certificateRepository) {
        this.dtoConverter = dtoConverter;
        this.objectMapper = objectMapper;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<CertificateResponse> getListOfCertificates() {
        List<CertificateResponse> certificateResponses = certificateRepository.findAll()
                .stream()
                .map(certificate -> (CertificateResponse) dtoConverter.convertToDto(certificate, CertificateResponse.class))
                .collect(Collectors.toList());

        log.debug("getting list of certificates {}", certificateResponses);
        return certificateResponses;
    }

    @Override
    public void archiveModel(Certificate certificate) {
        //TODO
    }

    @Override
    public void restoreModel(String archiveObject) throws JsonProcessingException {
        //TODO
    }

    @Override
    public Certificate getCertificateById(Long id) {
        Optional<Certificate> optionalCertificate = getOptionalCertificateById(id);

        if (!optionalCertificate.isPresent()){
            throw new NotExistException(String.format(CERTIFICATE_NOT_FOUND_BY_ID, id));
        }

        Certificate certificate = optionalCertificate.get();
        log.debug("getting certificate by id {}", certificate);
        return certificate;
    }

    @Override
    public Certificate getCertificateBySerialNumber(Long serialNumber) {
        Optional<Certificate> optionalCertificate = getOptionalCertificateBySerialNumber(serialNumber);

        if (!optionalCertificate.isPresent()){
            throw new NotExistException(String.format(CERTIFICATE_NOT_FOUND_BY_SERIAL_NUMBER, serialNumber));
        }

        Certificate certificate = optionalCertificate.get();
        log.debug("getting certificate by serial number {}", certificate);
        return certificate;
    }

    @Override
    public CertificateResponse getCertificateProfileById(Long id) {
        Certificate certificate = getCertificateById(id);
        return dtoConverter.convertToDto(certificate, CertificateResponse.class);
    }

    private Optional<Certificate> getOptionalCertificateById(Long id){
        return certificateRepository.findById(id);
    }

    private Optional<Certificate> getOptionalCertificateBySerialNumber(Long serialNumber){
        return certificateRepository.findBySerialNumber(serialNumber);
    }

    @Override
    public CertificateResponse generateSerialNumber(CertificateResponse response) {
        if (response.getType() == null || response.getDates().getCourseNumber() == null){
            // exception
        }

        String courseNumber = String.format("%02d", Integer.valueOf(response.getDates().getCourseNumber()));

        Long largestSerialNumber = certificateRepository.findMaxSerialNumber(response.getType().toString(), courseNumber);

        response.setSerialNumber(largestSerialNumber + 1);

        return response;
    }

    @Override
    public CertificateResponse updateCertificateWithSerialNumber(Long id, CertificateResponse response){
        Certificate certificate = getCertificateById(id);

        if (response.getSerialNumber() == null){
            response = generateSerialNumber(response);
        }

        Certificate newCertificate = dtoConverter.convertToEntity(response, certificate)
                .withCertificateType(certificate.getCertificateType())
                .withId(id)
                .withDates(certificate.getDates())
                .withSerialNumber(response.getSerialNumber())
                .withTemplate(certificate.getTemplate())
                .withUser(certificate.getUser())
                .withUserName(certificate.getUserName())
                .withUserEmail(certificate.getUserEmail());

        log.debug("updating serial number of certificate by id {}", newCertificate);

        return dtoConverter.convertToDto(certificateRepository.save(newCertificate), CertificateResponse.class);
    }
}
