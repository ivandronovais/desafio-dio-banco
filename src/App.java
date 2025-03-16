import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static Map<String, Conta> contas = new HashMap<>();
    public static final String VERDE = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String ROXO = "\u001B[35m";
    public static final String FUNDO_BRANCO = "\u001B[47m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String AZUL = "\u001B[34m";
    public static final String FUNDO_CIANO = "\u001B[46m";

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
                    Conta contaCliente = acessarConta(input);
                    if (contaCliente != null) {
                        System.out.println(
                                VERDE + "Bem vindo(a) de volta, " + contaCliente.getCliente().getNome() + RESET);
                        while (!retornarMenu) {
                            int servico = selecionarServico(input);
                            input.nextLine();
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
                                } else {
                                    System.out.println(VERMELHO + "Conta não encontrada!\n" + RESET);
                                }
                            } else if (servico == 4) {
                                contaCliente.imprimirExtrato();
                            } else {
                                retornarMenu = true;
                            }
                        }
                    } else {
                        System.out.println(VERMELHO + "Conta não encontrada!\n" + RESET);
                    }
                    break;
                case 2:
                    Conta conta = criarConta(inserirDadosPessoais(input), input);
                    contas.put(conta.cliente.getCpf(), conta);
                    System.out.println(VERDE + "Conta criada com sucesso!" + RESET);
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
        System.out.println(VERDE + FUNDO_CIANO + "Bem vindo (a) ao Banco Meu Dinheirinho" + RESET);
        System.out.println("Por favor, escolha uma opção");
        System.out.println(AZUL + "[1] " + RESET + "Já sou cliente");
        System.out.println(AZUL + "[2] " + RESET + "Quero abrir uma conta");
        System.out.println(AZUL + "[3] " + RESET + VERMELHO + "Sair" + RESET);
    }

    public static Conta acessarConta(Scanner input) {
        System.out.println("Insira o seu CPF: ");
        return contas.get(input.nextLine());
    }

    public static int selecionarServico(Scanner input) {
        System.out.println("Por favor, escolha um serviço abaixo: ");
        System.out.println(AZUL + "[1]" + RESET + " Depositar \n" +
                AZUL + "[2]" + RESET + " Sacar \n" +
                AZUL + "[3]" + RESET + " Transferir \n" +
                AZUL + "[4]" + RESET + " Consultar Saldo \n" +
                AZUL + "[5]" + RESET + " Sair");
        int servico = input.nextInt();
        return servico;
    }

    public static Cliente inserirDadosPessoais(Scanner input) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Que ótimo termos você conosco!");
        System.out.println("Por favor, insira seu " + VERDE + "nome: " + RESET);
        String nomeCliente = input.nextLine();
        System.out.println("Por favor, insira seu " + VERDE + "CPF: " + RESET);
        String cpfCliente = input.nextLine();
        System.out.println("Por favor, insira sua " + VERDE + "data de nascimento" + RESET + " no formato dia/mes/ano");
        Date nasCliente = null;
        try {
            nasCliente = sdf.parse(input.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Cliente(nomeCliente, cpfCliente, inserirDadosEndereco(input), nasCliente);
    }

    public static Endereco inserirDadosEndereco(Scanner input) {
        System.out.println("Agora, insira o nome da sua " + VERDE + "rua: " + RESET);
        String rua = input.nextLine();
        System.out.println("Insira o " + VERDE + " número" + RESET + " da sua casa: ");
        int numero = input.nextInt();
        input.nextLine();
        System.out.println("Ótimo! Agora, o nome da sua " + VERDE + "cidade: " + RESET);
        String cidade = input.nextLine();
        return new Endereco(rua, numero, cidade);
    }

    public static Conta criarConta(Cliente cli, Scanner input) {
        System.out.println("Selecione: ");
        System.out.println(AZUL + "[1] " + RESET + "Conta Corrente" + AZUL + "\n[2] " + RESET + "Conta Poupança");
        int tipoConta = input.nextInt();
        input.nextLine();
        switch (tipoConta) {
            case 1:
                return new ContaCorrente(cli);
            case 2:
                return new ContaPoupanca(cli);
            default:
                System.out.println(VERMELHO + "Opção inválida" + RESET);
                return null;
        }
    }
}
