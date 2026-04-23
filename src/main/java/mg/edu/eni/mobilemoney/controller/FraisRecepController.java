package mg.edu.eni.mobilemoney.controller;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.FraisEnvoiNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.FraisRecepNotFoundException;
import mg.edu.eni.mobilemoney.model.Frais_envoi;
import mg.edu.eni.mobilemoney.model.Frais_recep;
import mg.edu.eni.mobilemoney.response.ApiResponse;
import mg.edu.eni.mobilemoney.service.frais_envoi.Frais_envoiService;
import mg.edu.eni.mobilemoney.service.frais_recep.Frais_recepService;
import mg.edu.eni.mobilemoney.service.frais_recep.IFrais_recepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/FraisRecep")
@RequiredArgsConstructor
public class FraisRecepController {
    private final Frais_recepService fraisRecepService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllFraisRecep() {
        try{
            List<Frais_recep> theFraisRecep = fraisRecepService.getAllFraisRecep();
            return ResponseEntity.ok(new ApiResponse("success",theFraisRecep));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/FraisRecep/{idRec}/FraisEnvoi")
    public ResponseEntity<ApiResponse> getFraisRecepById(@PathVariable String idRec) {
        try {
            Frais_recep theFraisRecep = fraisRecepService.getFraisRecepById(idRec);
            return ResponseEntity.ok(new ApiResponse("success", theFraisRecep));
        } catch (FraisRecepNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/FraisRecep/{idRec}/delete")
    public ResponseEntity<ApiResponse> deleteFraisRecep(@PathVariable String idRec){
        try {
            fraisRecepService.deleteFraisRecep(idRec);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch(FraisRecepNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFraisRecep(@RequestBody Frais_recep fraisRecep){
        Frais_recep theFraisRecep = fraisRecepService.addFraisRecep(fraisRecep) ;
        return ResponseEntity.ok(new ApiResponse("success", theFraisRecep));
    }

    @PutMapping("/FraisRecep/{idRec}/update")
    public ResponseEntity<ApiResponse> updateFraisRecep(@PathVariable String idRec, @RequestBody Frais_recep fraisRecep) {
        try {
            Frais_recep theFraisRecep = fraisRecepService.updateFraisRecepById(fraisRecep, idRec);
            return ResponseEntity.ok(new ApiResponse("Update Success", theFraisRecep));
        } catch (FraisRecepNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
