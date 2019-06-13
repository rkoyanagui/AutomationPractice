package br.com.rkoyanagui.core.domain;

public class Usuario {
	protected String id;
	protected String email;
	protected String senha;
	
	public Usuario() {
	}
	
	public Usuario(
			String email,
			String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario(String id,
			String email,
			String senha) {
		this.id = id;
		this.email = email;
		this.senha = senha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		String texto = "ID: %s"
				+ " | Usuário: %s"
				+ " | Senha: %s\n";
		return String.format(texto,
				this.getId(),
				this.getEmail(),
				this.getSenha());
	}
}
