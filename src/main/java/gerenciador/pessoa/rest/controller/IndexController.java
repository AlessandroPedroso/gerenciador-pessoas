package gerenciador.pessoa.rest.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gerenciador.pessoa.rest.model.Endereco;
import gerenciador.pessoa.rest.model.Pessoa;
import gerenciador.pessoa.rest.repository.ApiJsonCep;
import gerenciador.pessoa.rest.repository.ApiJsonCepImpl;
import gerenciador.pessoa.rest.repository.EnderecoRepository;
import gerenciador.pessoa.rest.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoa")
public class IndexController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	private ApiJsonCep apiJsonCep = new ApiJsonCepImpl();
	
	@PostMapping(value="/", produces = "application/json")
	public ResponseEntity<Pessoa> cadastro(@RequestParam(value="dataNascimento") String dataNascimento, @RequestBody Pessoa pessoa) throws ParseException{
		
		Pessoa pessoaSave = new Pessoa();
		pessoaSave.setNome(pessoa.getNome());
		pessoaSave.setData_nascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));

		
		Pessoa pessoaNovo =  pessoaRepository.save(pessoaSave);

		
		return new ResponseEntity<Pessoa>(pessoaNovo, HttpStatus.OK);	
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Pessoa> editarPessoa(@RequestBody Pessoa pessoa){
		
		Pessoa pessoaUpdate = pessoaRepository.save(pessoa);
		
		
		return new ResponseEntity<Pessoa>(pessoaUpdate,HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Pessoa> consultarUmaPessoa(@PathVariable (value = "id") Long id){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		
		return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nome/{nome}", produces = "application/json")
	public ResponseEntity<Pessoa> consultaPorNome(@PathVariable (value = "nome") String nome){
		
		Pessoa pessoa = pessoaRepository.buscaPorNomeParm(nome);
		
		return new ResponseEntity<Pessoa>(pessoa,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Pessoa>> consultaTodos(){
		
		List<Pessoa> listaPessoas = (List<Pessoa>) pessoaRepository.findAll();
		
		return new ResponseEntity<List<Pessoa>>(listaPessoas, HttpStatus.OK);
	}
	
	@PostMapping(value="/endereco/", produces = "application/json")
	public ResponseEntity<Endereco> cadastroEndereco(@RequestParam (value="id") Long id,@RequestParam(value="cep") String cep,@RequestParam(value="numero") String numero, @RequestParam(value ="principal") Boolean principal) throws Exception{
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		Endereco endereco = apiJsonCep.buscaEndereco(cep);
		endereco.setNumero(numero);
		endereco.setPessoa(pessoa.get());
		endereco.setEnderecoPrincipal(principal);
		enderecoRepository.save(endereco);
		
		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);	
	}
	
	@GetMapping(value = "/endereco", produces = "application/json")
	public ResponseEntity<List<Endereco>> consultaTodosEndereco(){
		
		List<Endereco> listaEndereco = (List<Endereco>) enderecoRepository.findAll();

		return new ResponseEntity<List<Endereco>>(listaEndereco, HttpStatus.OK);
	}
	
	@GetMapping(value = "/enderecoPrincipal", produces = "application/json")
	public ResponseEntity<List<Endereco>> consultaTodosEnderecoPrincipal(){
		
		List<Endereco> listaEndereco = (List<Endereco>) enderecoRepository.enderecosPrincipal(true);

		return new ResponseEntity<List<Endereco>>(listaEndereco, HttpStatus.OK);
	}
	
	@GetMapping(value = "/enderecoPrincipal/{id}", produces = "application/json")
	public ResponseEntity<List<Endereco>> consultaTodosEnderecoPrincipalUsuario(@PathVariable(value = "id") Long id){
		
		List<Endereco> listaEndereco = (List<Endereco>) enderecoRepository.enderecosPrincipalUsuario(true, id);

		return new ResponseEntity<List<Endereco>>(listaEndereco, HttpStatus.OK);
	}	
	
}
