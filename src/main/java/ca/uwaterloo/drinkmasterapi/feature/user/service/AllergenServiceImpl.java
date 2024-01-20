package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.dao.Allergen;
import ca.uwaterloo.drinkmasterapi.dao.UserAllergen;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.AllergenResponseDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllergenServiceImpl implements IAllergenService{
    private final AllergenRepository allergenRepository;
    private final UserRepository userRepository;
    private final UserAllergenRepository userAllergenRepository;

    @Autowired
    public AllergenServiceImpl(AllergenRepository allergenRepository,
                               UserRepository userRepository,
                               UserAllergenRepository userAllergenRepository) {
        this.allergenRepository = allergenRepository;
        this.userRepository = userRepository;
        this.userAllergenRepository = userAllergenRepository;
    }

    @Override
    public List<AllergenResponseDTO> getAllergenOptions() {
        List<Allergen> allergens = allergenRepository.findAll();

        return allergens.stream()
                .map(AllergenResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllergenResponseDTO> getAllergenByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        List<UserAllergen> userAllergens = userAllergenRepository.findUserAllergensByUserId(userId);

        return userAllergens.stream()
                .map(userAllergen -> {
                    Allergen allergen = userAllergen.getAllergen();
                    return new AllergenResponseDTO(allergen);
                })
                .collect(Collectors.toList());
    }
}
