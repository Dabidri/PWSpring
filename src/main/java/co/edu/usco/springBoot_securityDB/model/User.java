package co.edu.usco.springBoot_securityDB.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(length = 45, nullable = false, unique = true)
	private String username;

	// Se guarda el hash BCrypt (60 caracteres), nunca la clave en texto plano
	@Column(length = 64, nullable = false)
	private String password;

	@Column(columnDefinition = "TINYINT(4)", nullable = false)
	private boolean enabled;

	// Un usuario puede tener muchos roles: tabla intermedia users_roles
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}
