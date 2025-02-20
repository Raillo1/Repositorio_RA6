package com.hlc.cliente_uno_a_muchos_pedido.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Categoria;
import com.hlc.cliente_uno_a_muchos_pedido.repositorio.CategoriaRepository;

@Component
public class CategoriaRepository implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaRepository.class);

    private final CategoriaRepository categoriaRepository;
    private final Faker faker = new Faker();

    public CategoriaRepository(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Categoria> categorias = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Categoria categoria = new Categoria();
            categoria.setNombre(faker.color().name());
            categoria.setDescripcion(faker.lorem().sentence());
            categorias.add(categoria);
        }

        // Para eliminar los nombres repetidos
        Set<String> nombresVistos = new HashSet<>();
        List<Categoria> categoriasUnicas = categorias.stream()
                .filter(categoria -> nombresVistos.add(categoria.getNombre()))
                .collect(Collectors.toList());

        // Guardar todas las categorías en la base de datos
        categoriaRepository.saveAll(categoriasUnicas);
        categoriaRepository.flush();

        // Comprobar si se guardaron correctamente
        if (categoriaRepository.findAll().size() > 0) {
            logger.info("✅ Categorías creadas y guardadas en la base de datos.");
        } else {
            logger.error("❌ Error durante la inicialización de datos.");
        }
    }
}
