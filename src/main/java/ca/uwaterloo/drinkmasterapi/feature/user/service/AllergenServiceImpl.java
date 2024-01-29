package ca.uwaterloo.drinkmasterapi.feature.user.service;

import ca.uwaterloo.drinkmasterapi.dao.Allergen;
import ca.uwaterloo.drinkmasterapi.dao.User;
import ca.uwaterloo.drinkmasterapi.dao.UserAllergen;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.AllergenResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.UserAllergenRequestDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public void associateUserWithAllergens(UserAllergenRequestDTO userAllergenRequestDTO) {
        User user = userRepository.findById(userAllergenRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userAllergenRequestDTO.getUserId() + " not found."));

        List<Long> inputAllergenIds = userAllergenRequestDTO.getAllergenIds();
        List<Allergen> inputAllergens = new ArrayList<>();
        for (Long allergenId : inputAllergenIds) {
            inputAllergens.add(allergenRepository.findById(allergenId)
                    .orElseThrow(() -> new ResourceNotFoundException("Allergen with ID " + allergenId + " not found.")));
        }

        List<UserAllergen> existingUserAllergens = userAllergenRepository.findUserAllergensByUserId(userAllergenRequestDTO.getUserId());
        List<UserAllergen> userAllergensToDelete = existingUserAllergens.stream()
                .filter(userAllergen -> !inputAllergenIds.contains(userAllergen.getAllergen().getId()))
                .collect(Collectors.toList());

        LocalDateTime currentTime = LocalDateTime.now().withNano(0);
        List<UserAllergen> userAllergensToSave = inputAllergens.stream()
                .filter(allergen -> existingUserAllergens.stream().noneMatch(userAllergen -> userAllergen.getAllergen().getId().equals(allergen.getId())))
                .map(allergen -> {
                    UserAllergen userAllergen = new UserAllergen();
                    userAllergen.setUser(user);
                    userAllergen.setAllergen(allergen);
                    userAllergen.setCreatedAt(currentTime);
                    userAllergen.setModifiedAt(currentTime);
                    return userAllergen;
                })
                .collect(Collectors.toList());

        if (!userAllergensToDelete.isEmpty()) {
            userAllergenRepository.deleteInBatch(userAllergensToDelete);
        }
        if (!userAllergensToSave.isEmpty()) {
            userAllergenRepository.saveAll(userAllergensToSave);
        }
    }
}
