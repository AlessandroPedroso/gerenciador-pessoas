package gerenciador.pessoa.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gerenciador.pessoa.rest.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

	@Query(value = "select e from Endereco e where e.enderecoPrincipal = :principal")
	public List<Endereco> enderecosPrincipal(@Param("principal") Boolean principal);
	
	@Query(value = "select e from Endereco e where e.enderecoPrincipal = :principal and e.pessoa.id = :id")
	public List<Endereco> enderecosPrincipalUsuario(@Param("principal") Boolean principal,@Param("id") Long id);
}
