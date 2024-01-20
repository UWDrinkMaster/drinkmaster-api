package ca.uwaterloo.drinkmasterapi.feature.user.controller;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.AllergenResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.dto.UserAllergenRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.user.service.IAllergenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/allergen")
public class AllergenController {
    private final IAllergenService allergenService;

    @Autowired
    public AllergenController(IAllergenService allergenService) {
        this.allergenService = allergenService;
    }
    @GetMapping(value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AllergenResponseDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> getAllergenOptions() {
        List<AllergenResponseDTO> responseDTOs = allergenService.getAllergenOptions();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AllergenResponseDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        List<AllergenResponseDTO> responseDTOs = allergenService.getAllergenByUserId(userId);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/associate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "User allergen associations created successfully", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<String> associateUserWithAllergens(@RequestBody UserAllergenRequestDTO requestDTO) {
        allergenService.associateUserWithAllergens(requestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User allergen associations created successfully");
    }

}
