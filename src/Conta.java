public abstract class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
    }

    @Override
    public void sacar(double valor) {
        if (getSaldo() >= valor && valor > 0) {
            saldo -= valor;
            System.out.println(App.VERDE + "Você sacou " + valor + " R$ da sua conta" + App.RESET);
            return;
        }
        System.out.println(App.VERMELHO + "Você não possui saldo suficiente ou digitou valor inválido para saque" + App.RESET);
    }

    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println(App.VERDE + "Depósito de " + valor + " R$ concluído com sucesso!" + App.RESET);
            return;
        }
        System.out.println(App.VERMELHO + "Valor de depósito inválido" + App.RESET);
    }

    @Override
    public void transferir(double valor, IConta contaDestino) {
        if (this.getSaldo() >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println(App.VERDE + "Transferência de " + valor + " R$ realizado com sucesso!" + App.RESET);
            return;
        }
        System.out.println(App.VERMELHO + "Você não possui saldo suficiente para realizar essa operação" + App.RESET);
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    protected void imprimirInfosComuns() {
        System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        System.out.println(String.format("Agencia: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numero));
        System.out.println(String.format("Saldo: %.2f", this.saldo));
    }
}