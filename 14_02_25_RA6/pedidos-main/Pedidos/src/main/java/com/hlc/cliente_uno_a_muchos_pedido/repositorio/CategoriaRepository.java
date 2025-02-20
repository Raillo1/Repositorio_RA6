package com.hlc.cliente_uno_a_muchos_pedido.repositorio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Categoria;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Cliente;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> categorias = new ArrayList<>();

	for(int i = 0;i<10;i++)
	{
		Categoria categoria = new Categoria();
		categoria.setNombre(faker.color().name());
		categoria.setDescripcion(faker.lorem().sentence());
		categorias.add(categoria);
	} // Para eliminar los nombres repetidos
	Set<String> nombresVistos = new HashSet<>();
	List<Categoria> categoriasUnicas = categorias.stream().filter(categoria -> nombresVistos.add(categoria.getNombre()))
			.collect(Collectors.toList());
	// Guardar todas las categorias de una vez
	categoriaRepository.saveAll(categoriasUnicas);categoriaRepository.flush();
	// Comprobar
	if(categoriaRepository.findAll().size()>0)
	{
		logger.info(" ✅ Categorias creadas y guardadas en la base de datos.");
	}
}else{logger.error(" ❌ Error durante la inicialización de datos: {}","categoriaRepository");}
