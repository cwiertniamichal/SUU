import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Main {
    public static void main(String [] args){
        Web3j web3 = Web3j.build(new HttpService("https://morden.infura.io/your-token"));
    }
}
