package mg.edu.eni.mobilemoney.controller;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.EnvoiNotFoundException;
import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.response.ApiResponse;
import mg.edu.eni.mobilemoney.service.envoi.EnvoiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/envoi")
public class EnvoiController {
    private final EnvoiService envoiService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllEnvoi() {
        try {
            List<Envoi> theEnvoi = envoiService.getAllEnvoi();
            return ResponseEntity.ok(new ApiResponse("success", theEnvoi));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/envoi/{idEnv}/envoi")
    public ResponseEntity<ApiResponse> getEnvoiById(@PathVariable String idEnv) {
        try {
            Envoi theEnvoi = envoiService.getEnvoiById(idEnv);
            return ResponseEntity.ok(new ApiResponse("success",theEnvoi));
        } catch (EnvoiNotFoundException e)  {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEnvoi(@RequestBody Envoi envoi) {
        Envoi theEnvoi = envoiService.addEnvoi(envoi);
        return ResponseEntity.ok(new ApiResponse("success",theEnvoi));
    }

    @DeleteMapping("/envoi/{idEnv}/delete")
    public ResponseEntity<ApiResponse> deleteEnvoi(@PathVariable String idEnv) {
        try {
            envoiService.deleteEnvoi(idEnv);
            return ResponseEntity.ok(new ApiResponse("success", null));
        } catch (EnvoiNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/envoi/{idEnv}/update")
    public ResponseEntity<ApiResponse> updateEnvoi(@RequestBody Envoi envoi,@PathVariable String idEnv){
        try {
            Envoi theEnvoi = envoiService.updateEnvoi(envoi, idEnv);
            return ResponseEntity.ok(new ApiResponse("success", theEnvoi));
        } catch (EnvoiNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
