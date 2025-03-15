import java.util.Date;

public class Cliente {

	private String nome;
	private String cpf;
	private Endereco endereco;
	private Date dataNascimento;
	
	public Cliente(String nome, String cpf, Endereco endereco, Date dataNascimento) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
	}
	public Cliente(String nome, String cpf, Date dataNascimento) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	@Override
	public String toString() {
		return "Cliente => Nome = " + nome + ", CPF =" + cpf + ", Endereço =" + endereco + ", Nascimento"
				+ dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
