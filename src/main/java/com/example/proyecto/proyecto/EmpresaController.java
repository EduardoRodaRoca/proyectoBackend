package com.example.proyecto.proyecto;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmpresaController {
    // Devuelve todas las ofertas en formato JSON
    @GetMapping(value = "/oferta", produces = "application/json")
    public List<Map<String, Object>> getOfertas() {
        List<Map<String, Object>> ofertas = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            CollectionReference ofertaCollection = db.collection("oferta");

            ApiFuture<QuerySnapshot> querySnapshot = ofertaCollection.get();
            for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Map<String, Object> data = document.getData();
                data.put("id", document.getId()); // Agregar el ID del documento
                ofertas.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ofertas;
    }

    // Añade una nueva oferta
    @PostMapping("/oferta")
    public String addOferta(@RequestBody Map<String, Object> oferta) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("oferta").add(oferta);
            return "Oferta añadida correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al añadir la oferta: " + e.getMessage();
        }
    }

    // Elimina una oferta por ID
    @DeleteMapping("/oferta/{id}")
    public String deleteOferta(@PathVariable String id) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection("oferta").document(id).delete();
            return "Oferta eliminada correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar la oferta: " + e.getMessage();
        }
    }
}
