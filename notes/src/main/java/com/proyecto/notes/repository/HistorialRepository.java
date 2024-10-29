package com.proyecto.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.notes.model.Historial;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer> {

    List<Historial> findByNotaId(Integer notaId);
}
