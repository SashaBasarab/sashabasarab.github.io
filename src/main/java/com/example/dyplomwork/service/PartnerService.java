package com.example.dyplomwork.service;

import com.example.dyplomwork.entity.Partner;
import com.example.dyplomwork.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public Optional<Partner> getPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Long id, Partner updatedPartner) {
        return partnerRepository.findById(id).map(partner -> {
            partner.setName(updatedPartner.getName());
            partner.setDescription(updatedPartner.getDescription());
            partner.setDiscountConditions(updatedPartner.getDiscountConditions());
            partner.setDiscountPercentage(updatedPartner.getDiscountPercentage());
            return partnerRepository.save(partner);
        }).orElseThrow(() -> new RuntimeException("Партнер не знайдений"));
    }

    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}

