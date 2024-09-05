import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Banco {
    public static void main(String[] args) throws RemoteException {
        BancoInterface bancoService = new BancoService();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("BancoService", bancoService);
        System.out.println("Felipe Galdino de Sousa\n" + "Service de banco registrado ....");
    }
}
