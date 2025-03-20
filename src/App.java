import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Endereco;
import util.*;

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
                    Conta contaCliente = acessarConta(input);
                    if (contaCliente != null) {
                        System.out.println(
                                util.ConsoleColors.VERDE + "Bem vindo(a) de volta, " + contaCliente.getCliente().getNome() + util.ConsoleColors.RESET);
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
                                    System.out.println(util.ConsoleColors.VERMELHO + "Conta não encontrada!\n" + util.ConsoleColors.RESET);
                                }
                            } else if (servico == 4) {
                                contaCliente.imprimirExtrato();
                            } else {
                                retornarMenu = true;
                            }
                        }
                    } else {
                        System.out.println(util.ConsoleColors.VERMELHO + "Conta não encontrada!\n" + util.ConsoleColors.RESET);
                    }
                    break;
                case 2:
                    Conta conta = criarConta(inserirDadosPessoais(input), input);
                    contas.put(conta.getCliente().getCpf(), conta);
                    System.out.println(util.ConsoleColors.VERDE + "Conta criada com sucesso!" + util.ConsoleColors.RESET);
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
        System.out.println(util.ConsoleColors.VERDE + util.ConsoleColors.FUNDO_CIANO + "Bem vindo (a) ao Banco Meu Dinheirinho" + util.ConsoleColors.RESET);
        System.out.println("Por favor, escolha uma opção");
        System.out.println(util.ConsoleColors.AZUL + "[1] " + util.ConsoleColors.RESET + "Já sou cliente");
        System.out.println(util.ConsoleColors.AZUL + "[2] " + util.ConsoleColors.RESET + "Quero abrir uma conta");
        System.out.println(util.ConsoleColors.AZUL + "[3] " + util.ConsoleColors.RESET + util.ConsoleColors.VERMELHO + "Sair" + util.ConsoleColors.RESET);
    }

    public static Conta acessarConta(Scanner input) {
        System.out.println("Insira o seu CPF: ");
        return contas.get(input.nextLine());
    }

    public static int selecionarServico(Scanner input) {
        System.out.println("Por favor, escolha um serviço abaixo: ");
        System.out.println(util.ConsoleColors.AZUL + "[1]" + util.ConsoleColors.RESET + " Depositar \n" +
        util.ConsoleColors.AZUL + "[2]" + util.ConsoleColors.RESET + " Sacar \n" +
        util.ConsoleColors.AZUL + "[3]" + util.ConsoleColors.RESET + " Transferir \n" +
        util.ConsoleColors.AZUL + "[4]" + util.ConsoleColors.RESET + " Consultar Saldo \n" +
        util.ConsoleColors.AZUL + "[5]" + util.ConsoleColors.RESET + " Sair");
        int servico = input.nextInt();
        return servico;
    }

    public static Cliente inserirDadosPessoais(Scanner input) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Que ótimo termos você conosco!");
        System.out.println("Por favor, insira seu " + util.ConsoleColors.VERDE + "nome: " + util.ConsoleColors.RESET);
        String nomeCliente = input.nextLine();
        System.out.println("Por favor, insira seu " + util.ConsoleColors.VERDE + "CPF: " + util.ConsoleColors.RESET);
        String cpfCliente = input.nextLine();
        System.out.println("Por favor, insira sua " + util.ConsoleColors.VERDE + "data de nascimento" + util.ConsoleColors.RESET + " no formato dia/mes/ano");
        Date nasCliente = null;
        try {
            nasCliente = sdf.parse(input.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Cliente(nomeCliente, cpfCliente, inserirDadosEndereco(input), nasCliente);
    }

    public static Endereco inserirDadosEndereco(Scanner input) {
        System.out.println("Agora, insira o nome da sua " + util.ConsoleColors.VERDE + "rua: " + util.ConsoleColors.RESET);
        String rua = input.nextLine();
        System.out.println("Insira o " + util.ConsoleColors.VERDE + " número" + util.ConsoleColors.RESET + " da sua casa: ");
        int numero = input.nextInt();
        input.nextLine();
        System.out.println("Ótimo! Agora, o nome da sua " + util.ConsoleColors.VERDE + "cidade: " + util.ConsoleColors.RESET);
        String cidade = input.nextLine();
        return new Endereco(rua, numero, cidade);
    }

    public static Conta criarConta(Cliente cli, Scanner input) {
        System.out.println("Selecione: ");
        System.out.println(util.ConsoleColors.AZUL + "[1] " + util.ConsoleColors.RESET + "Conta Corrente" + util.ConsoleColors.AZUL + "\n[2] " + util.ConsoleColors.RESET + "Conta Poupança");
        int tipoConta = input.nextInt();
        input.nextLine();
        switch (tipoConta) {
            case 1:
                return new ContaCorrente(cli);
            case 2:
                return new ContaPoupanca(cli);
            default:
                System.out.println(util.ConsoleColors.VERMELHO + "Opção inválida" + util.ConsoleColors.RESET);
                return null;
        }
    }
}
