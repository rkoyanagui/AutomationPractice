package br.com.rkoyanagui.core.domain;

public class PessoaFisica extends Usuario
{
	private String nome,
				   sobrenome,
				   endereco,
				   cidade,
				   estado,
				   codigoPostal,
				   pais,
				   celular,
				   referencia;

	public PessoaFisica(String email, String senha, String nome, String sobrenome, String endereco,
			String cidade, String estado, String codigoPostal, String pais, String celular, String referencia) {
		super(email, senha);
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.celular = celular;
		this.referencia = referencia;
	}
	
	public PessoaFisica(String id, String email, String senha, String nome, String sobrenome, String endereco,
			String cidade, String estado, String codigoPostal, String pais, String celular, String referencia) {
		super(id, email, senha);
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.celular = celular;
		this.referencia = referencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return "PessoaFisica [nome=" + nome + ", sobrenome=" + sobrenome + ", endereco=" + endereco + ", cidade="
				+ cidade + ", estado=" + estado + ", codigoPostal=" + codigoPostal + ", pais=" + pais + ", celular="
				+ celular + ", referencia=" + referencia + ", email=" + email + ", senha=" + senha + "]";
	}
}
