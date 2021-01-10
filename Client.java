package payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Client {

	private @Id @GeneratedValue Long id;
	private String name;
	private String bouquet;

	Client() {}

	Client(String name, String bouquet) {

		this.name = name;
		this.bouquet = bouquet;
	}



	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getBouquet() {
		return this.bouquet;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBouquet(String bouquet) {
		this.bouquet = bouquet;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Client))
			return false;
		Client client = (Client) o;
		return Objects.equals(this.id, client.id) && Objects.equals(this.name, client.name)
				&& Objects.equals(this.bouquet, client.bouquet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.bouquet);
	}

	@Override
	public String toString() {
		return "Client{" + "id=" + this.id + ", name='" + this.name + '\'' + ", bouquet='" + this.bouquet + '\'' + '}';
	}
}
