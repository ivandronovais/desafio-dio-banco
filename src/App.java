import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static Map<String, Conta> contas = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        mensagemInicial();
        boolean sair = false;
        while (!sair) {
            int escolha = input.nextInt();
            input.nextLine();
            switch (escolha) {
                case 1:
                    boolean retornarMenu = false;
                    Conta contaCliente = acessarConta();
                    if (contaCliente != null) {
                        while (!retornarMenu) {
                            int servico = selecionarServico();
                            if (servico == 1) {
                                System.out.println("Digite o valor de depósito: ");
                                double deposito = input.nextDouble();
                                contaCliente.depositar(deposito);
                            } else if (servico == 2) {
                                System.out.println("Digite o valor de saque: ");
                                double saque = input.nextDouble();
                                input.nextLine();
                                contaCliente.sacar(saque);
                            } else if (servico == 3) {
                                System.out.println("Digite o CPF da conta de destino: ");
                                String cpf = input.nextLine();
                                if (verificarSeExiste(cpf)) {
                                    System.out.println("Digite o valor de depósito: ");
                                    double valTransferencia = input.nextDouble();
                                    contaCliente.transferir(valTransferencia, contas.get(cpf));
                                    break;
                                }else{System.out.println("Conta não encontrada!\n");}
                            } else if (servico == 4) {
                                contaCliente.imprimirExtrato();
                            } else {
                                retornarMenu = true;
                            }
                        }
                    }
                    System.out.println("Conta não encontrada!\n");
                    break;
                case 2:
                    Conta conta = criarConta(inserirDadosPessoais());
                    contas.put(conta.cliente.getCpf(), conta);
                    System.out.println("Conta criada com sucesso!");
                    break;
                default:
                    System.out.println("Saindo do aplicativo...");
                    sair = true;
            }
            mensagemInicial();
        }
    }

    public static boolean verificarSeExiste(String cpf) {
        if (contas.get(cpf) != null) {
            return true;
        }
        return false;
    }

    public static void mensagemInicial() {
        System.out.println("Bem vindo (a) ao Banco Meu Dinheirinho");
        System.out.println("Por favor, escolha uma opção");
        System.out.println("[1] Já sou cliente");
        System.out.println("[2] Quero abrir uma conta");
        System.out.println("[3] Sair");
    }

    public static Conta acessarConta() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira o seu CPF: ");
        return contas.get(input.nextLine());
    }

    public static int selecionarServico() {
        Scanner input = new Scanner(System.in);
        System.out.println("Por favor, escolha um serviço abaixo: ");
        System.out.println("[1] Depositar \n[2] Sacar \n[3] Transferir \n[4] Consultar Saldo \n[5] Sair");
        int servico = input.nextInt();
        input.nextLine();
        return servico;
    }

    public static Cliente inserirDadosPessoais() {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Que ótimo termos você conosco!");
        System.out.println("Por favor, insira seu nome: ");
        String nomeCliente = input.nextLine();
        System.out.println("Por favor, insira seu CPF: ");
        String cpfCliente = input.nextLine();
        System.out.println("Por favor, insira sua data de nascimento no formato dia/mes/ano");
        Date nasCliente = null;
        try {
            nasCliente = sdf.parse(input.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Cliente(nomeCliente, cpfCliente, inserirDadosEndereco(), nasCliente);
    }

    public static Endereco inserirDadosEndereco() {
        Scanner input = new Scanner(System.in);
        System.out.println("Agora, insira o nome da sua rua: ");
        String rua = input.nextLine();
        System.out.println("Insira o número da sua casa: ");
        int numero = input.nextInt();
        input.nextLine();
        System.out.println("Ótimo! Agora, o nome da sua cidade: ");
        String cidade = input.nextLine();
        return new Endereco(rua, numero, cidade);
    }

    public static Conta criarConta(Cliente cli) {
        Scanner input = new Scanner(System.in);
        System.out.println("Selecione: ");
        System.out.println("[1] Conta Corrente \n[2] Conta Poupança");
        int tipoConta = input.nextInt();
        input.nextLine();
        switch (tipoConta) {
            case 1:
                return new ContaCorrente(cli);
            case 2:
                return new ContaPoupanca(cli);
            default:
                System.out.println("Opção inválida");
                return null;
        }
    }
}
