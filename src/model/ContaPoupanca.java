package model;
public class ContaPoupanca extends Conta {
	private final double taxaRendimentoMensal = 0.5;

	public ContaPoupanca(Cliente cliente) {
		super(cliente);
	}

	public double getTaxaRendimento() {
		return taxaRendimentoMensal;
	}

	@Override
	public void imprimirExtrato() {
		System.out.println("=== Extrato Conta Poupança ===");
		super.imprimirInfosComuns();
		System.out.println("=== === === === === === === ===");
	}
}