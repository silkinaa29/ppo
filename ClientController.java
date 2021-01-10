package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class ClientController {

	private final ClientRepository repository;

	ClientController(ClientRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/clients")
	CollectionModel<EntityModel<Client>> all() {

		List<EntityModel<Client>> clients = repository.findAll().stream()
				.map(client -> EntityModel.of(client,
						linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
						linkTo(methodOn(ClientController.class).all()).withRel("clients")))
				.collect(Collectors.toList());

		return CollectionModel.of(clients, linkTo(methodOn(ClientController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/clients")
	Client newClient(@RequestBody Client newClient) {
		return repository.save(newClient);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/clients/{id}")
	EntityModel<Client> one(@PathVariable Long id) {

		Client client = repository.findById(id) //
				.orElseThrow(() -> new ClientNotFoundException(id));

		return EntityModel.of( client, //
				linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
				linkTo(methodOn(ClientController.class).all()).withRel("clients"));
	}
	// end::get-single-item[]

	@PutMapping("/clients/{id}")
	Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

		return repository.findById(id) //
				.map(client -> {
					client.setName(newClient.getName());
					client.setBouquet(newClient.getBouquet());
					return repository.save(client);
				}) //
				.orElseGet(() -> {
					newClient.setId(id);
					return repository.save(newClient);
				});
	}

	@DeleteMapping("/clients/{id}")
	void deleteClient(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
