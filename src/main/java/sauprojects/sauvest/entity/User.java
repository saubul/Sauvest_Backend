package sauprojects.sauvest.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_email_uq", columnNames = "email"),
											@UniqueConstraint(name = "user_username_uq", columnNames = "username")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@SequenceGenerator(name =  "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long id;
	
	@NotBlank(message = "Name must not be null.")
	private String name;
	
	@NotBlank(message = "Surname must not be null.")
	private String surname;

	@Column(nullable = false)
	@NotBlank(message = "Username must not be null.")
	private String username;
	
	@Column(nullable = false)
	@NotBlank(message = "Password must not be null.")
	private String password;
	
	
	@NotBlank(message = "Email must not be null.")
	@Email
	private String email;
	
	@NotBlank
	@Column(name = "sso_token", nullable = false)
	private String ssoToken;
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",
			   joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))},
			   inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_id_fk"))})
	private Set<Role> roles = new HashSet<Role>();
	
	@NotBlank
	private boolean enabled;
	
	@OneToMany(targetEntity = Subscription.class, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Subscription> subscriptions = new HashSet<Subscription>();
	
}
