package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.feature.user.dto.AllergenResponseDTO;

import java.util.List;

public interface IAllergenService {
    List<AllergenResponseDTO> getAllergenOptions();

    List<AllergenResponseDTO> getAllergenByUserId(Long userId);
}
