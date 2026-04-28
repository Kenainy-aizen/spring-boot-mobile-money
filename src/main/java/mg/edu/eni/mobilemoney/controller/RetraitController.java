package mg.edu.eni.mobilemoney.controller;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.RetraitNotFoundException;
import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.model.Retrait;
import mg.edu.eni.mobilemoney.response.ApiResponse;
import mg.edu.eni.mobilemoney.service.StatistiqueService;
import mg.edu.eni.mobilemoney.service.retrait.RetraitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/retrait")
@RequiredArgsConstructor

public class RetraitController {
    private final RetraitService retraitService;
    private final StatistiqueService statistiqueService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRetrait() {
        try {
            List<Retrait> theRetrait = retraitService.getAllRetrait();
            return ResponseEntity.ok(new ApiResponse("success", theRetrait));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
        }
    }

    @GetMapping("retrait/{idRecep}/retrait")
    public ResponseEntity<ApiResponse> getRetraitById(@PathVariable String idRecep) {
        try {
            Retrait theRetrait = retraitService.getRetraitById(idRecep);
            return ResponseEntity.ok(new ApiResponse("success", theRetrait));
        } catch (RetraitNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/retrait/{idRecep}/update")
    public ResponseEntity<ApiResponse> updateRetrait(@RequestBody Retrait retrait, @PathVariable String idRecep) {
        try {
            Retrait theRetrait = retraitService.updateRetrait(retrait, idRecep);
            return ResponseEntity.ok(new ApiResponse("success", theRetrait));
        } catch (RetraitNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/retrait/{idRecep}/delete")
    public ResponseEntity<ApiResponse> deleteRetrait(@PathVariable String idRecep){
        try {
            retraitService.deleteRetrait(idRecep);
            return ResponseEntity.ok(new ApiResponse("success", null));
        } catch (RetraitNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRetrait(@RequestBody Retrait retrait){
        Retrait theRetrait = retraitService.addRetrait(retrait);
        return ResponseEntity.ok(new ApiResponse("success",theRetrait));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Retrait>> searchEnvoi(@RequestParam("date") LocalDate date) {
        List<Retrait> resultat = retraitService.searchByDate(date);
        return ResponseEntity.ok(resultat);
    }

    @GetMapping("/recette")
    public ResponseEntity<Integer> recette(){
        return ResponseEntity.ok(statistiqueService.calculerRecetteTotale());
    }

}
