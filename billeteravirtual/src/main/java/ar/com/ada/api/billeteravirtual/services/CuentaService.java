package ar.com.ada.api.billeteravirtual.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.repo.CuentaRepository;

/**
 * CuentaService
 */
@Service
public class CuentaService {

    @Autowired
    CuentaRepository repo;
}