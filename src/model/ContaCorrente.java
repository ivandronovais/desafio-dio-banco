package model;

public class ContaCorrente extends Conta {
	private final double taxaDeManutencao = 49.90;
	
	public ContaCorrente(Cliente cliente) {
		super(cliente);
	}

	@Override
	public void imprimirExtrato() {
		System.out.println("=== Extrato Conta Corrente ===");
		super.imprimirInfosComuns();
		System.out.println("=== === === === === === === ===");
	}

	public double getTaxaDeManutencao() {
		return taxaDeManutencao;
	}
}