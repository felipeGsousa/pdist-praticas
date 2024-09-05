import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class BancoService extends UnicastRemoteObject implements BancoInterface{
    private List<Conta> contas;

    public BancoService() throws RemoteException {
        contas = new ArrayList<>();
        contas.add(new Conta("1", 100.0));
        contas.add(new Conta("2", 156.0));
        contas.add(new Conta("3", 950.0));
    }

    @Override
    public String pesquisaConta(String numConta) throws RemoteException {
        if (!contas.isEmpty()) {
            for(Conta conta: contas) {
                if (conta != null && conta.getNumero().equals(numConta))
                    return new String("Numero da conta: " + conta.getNumero() + "\nSaldo da conta: " + conta.getSaldo()+ "\n");
            }
        }
        return "Conta inexistente\n";
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        if (!contas.isEmpty()) return contas.size();
        return 0;
    }

    @Override
    public void cadastro(String numConta, double saldo) throws RemoteException {
        Conta conta = new Conta(numConta, saldo);
        contas.add(conta);
        System.out.println(String.format("Conta de número %s criada", numConta) + "\n");
    }

    @Override
    public void removeConta(String numConta) throws RemoteException {
        for(Conta conta: contas){
            if (conta != null && conta.getNumero().equals(numConta)) {
                contas.remove(conta);
                System.out.println(String.format("Conta de número %s removida", numConta) + "\n");
            }
        }
    }

}
