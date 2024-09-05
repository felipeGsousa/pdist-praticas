import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoInterface extends Remote {
    String pesquisaConta (String conta) throws RemoteException;
    int quantidadeContas() throws RemoteException;
    void cadastro(String conta, double saldo) throws RemoteException;
    void removeConta(String conta) throws RemoteException;
}
