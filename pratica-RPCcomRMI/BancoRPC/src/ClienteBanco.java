import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry(args[0]);
        BancoInterface banco = (BancoInterface) registry.lookup("BancoService");

        menu();
        Scanner entrada = new Scanner(System.in);
        int opcao = entrada.nextInt();

        while(opcao != 9) {
            switch (opcao) {
                case 1: {
                    System.out.println("\nDigite o número da conta: ");
                    String conta = entrada.next();
                    System.out.println(banco.pesquisaConta(conta));
                    break;
                }
                case 2: {
                    System.out.println("\n"+banco.quantidadeContas()+"\n");
                    break;
                }
                case 3: {
                    System.out.print("\nDigite o número da conta: ");
                    String conta = entrada.next();
                    System.out.print("\nDigite o saldo da conta: ");
                    double saldo = entrada.nextDouble();
                    banco.cadastro(conta, saldo);
                    break;
                }
                case 4: {
                    System.out.print("\nDigite o número da conta: ");
                    String conta = entrada.next();
                    banco.removeConta(conta);
                    break;
                }
            }
            menu();
            opcao = entrada.nextInt();
        }
    }

    public static void menu() {
        System.out.println("Felipe Galdino de Sousa\n" + "=== Banco ===");
        System.out.println("1 - Encontrar conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Adicionar conta");
        System.out.println("4 - Remover conta");
        System.out.println("9 - Sair\n");
    }
}
