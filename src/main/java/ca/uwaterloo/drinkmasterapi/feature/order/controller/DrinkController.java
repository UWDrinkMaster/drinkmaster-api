package ca.uwaterloo.drinkmasterapi.feature.order.controller;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.*;
import ca.uwaterloo.drinkmasterapi.feature.order.service.IDrinkService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/drink")
public class DrinkController {
    private final IDrinkService drinkService;

    @Autowired
    public DrinkController(IDrinkService drinkService) { this.drinkService = drinkService; }

    @GetMapping(value = "/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = IngredientResponseDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> getAllIngredients() {
        List<IngredientResponseDTO> responseDTOs = drinkService.getAllIngredients();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = DrinkResponseDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> getDrinkManuByUserId(@PathVariable Long userId) {
        List<DrinkResponseDTO> responseDTOs = drinkService.getDrinkManuByUserId(userId);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/customize", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Customized drink created successful"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> createCustomizedDrink(@Valid @RequestBody DrinkRequestDTO drinkRequestDTO) {
        drinkService.createCustomizedDrink(drinkRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
