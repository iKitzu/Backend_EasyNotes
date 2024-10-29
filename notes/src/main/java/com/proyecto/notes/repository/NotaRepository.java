package com.proyecto.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.notes.model.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findByUsuarioId(Integer usuarioId);
}
