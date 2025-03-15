import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        mensagemInicial();
        int escolha = input.nextInt();
        input.nextLine();
        switch (escolha) {
            case 1:
                boolean sair = false;
                while (!sair) {
                    if (selecionarServico() == 1) {
                        //Depositar
                    } else if (selecionarServico() == 2) {
                        //Sacar
                    } else if(selecionarServico() == 3){
                        //Transferir
                    }else{
                        System.out.println("Insira uma opção válida!");
                    }
                }
                break;
            case 2:
                Conta conta = criarConta(inserirDadosPessoais());
                System.out.println("Conta criada com sucesso!");
                escolha = 1;
                break;
            default:
                System.out.println("Saindo do aplicativo...");
                break;
        }
    }

    public static void mensagemInicial() {
        System.out.println("Bem vindo (a) ao Banco Meu Dinheirinho");
        System.out.println("Por favor, escolha uma opção");
        System.out.println("[1] Já sou cliente");
        System.out.println("[2] Quero abrir uma conta");
    }

    public static int selecionarServico() {
        Scanner input = new Scanner(System.in);
        System.out.println("Por favor, escolha um serviço abaixo: ");
        System.out.println("[1] Depositar \n[2] Sacar \n[3] Transferir");
        return input.nextInt();
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
        System.out.println("[1] Conta Corrente \n [2] Conta Poupança");
        int tipoConta = input.nextInt();
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
