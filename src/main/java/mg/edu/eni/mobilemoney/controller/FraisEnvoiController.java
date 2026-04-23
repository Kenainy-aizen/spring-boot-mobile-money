package mg.edu.eni.mobilemoney.controller;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.FraisEnvoiNotFoundException;
import mg.edu.eni.mobilemoney.model.Frais_envoi;
import mg.edu.eni.mobilemoney.response.ApiResponse;
import mg.edu.eni.mobilemoney.service.frais_envoi.Frais_envoiService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/FraisEnvoi")
@RequiredArgsConstructor
public class FraisEnvoiController {
    private final Frais_envoiService fraisEnvoiService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllFraisEnvoi() {
        try{
            List<Frais_envoi> theFraisEnvoi = fraisEnvoiService.getAllFraisEnvoi();
            return ResponseEntity.ok(new ApiResponse("success",theFraisEnvoi));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/FraisEnvoi/{idEnv}/FraisEnvoi")
    public ResponseEntity<ApiResponse> getFraisEnvoiById(@PathVariable String idEnv) {
        try {
            Frais_envoi theFraisEnvoi = fraisEnvoiService.getFraisEnvoiById(idEnv);
            return ResponseEntity.ok(new ApiResponse("success", theFraisEnvoi));
        } catch (FraisEnvoiNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/FraisEnvoi/{idEnv}/delete")
    public ResponseEntity<ApiResponse> deleteFraisEnvoi(@PathVariable String idEnv){
        try {
            fraisEnvoiService.deleteFraisEnvoi(idEnv);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch(FraisEnvoiNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFraisEnvoi(@RequestBody Frais_envoi fraisEnvoi){
        Frais_envoi theFraisEnvoi = fraisEnvoiService.addFraisEnvoi(fraisEnvoi) ;
        return ResponseEntity.ok(new ApiResponse("success", theFraisEnvoi));
    }

    @PutMapping("/FraisEnvoi/{idEnv}/update")
    public ResponseEntity<ApiResponse> updateFraisEnvoi(@PathVariable String idEnv, @RequestBody Frais_envoi fraisEnvoi) {
        try {
            Frais_envoi theFraisEnvoi = fraisEnvoiService.updateFraisEnvoiById(fraisEnvoi, idEnv);
            return ResponseEntity.ok(new ApiResponse("Update Success", theFraisEnvoi));
        } catch (FraisEnvoiNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
