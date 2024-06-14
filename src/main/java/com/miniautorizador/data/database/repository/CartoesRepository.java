package com.miniautorizador.data.database.repository;

import com.miniautorizador.data.models.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartoesRepository extends JpaRepository<CartaoModel, String> {
    Optional<CartaoModel> findByNumeroCartao(String numeroCartao);
}
