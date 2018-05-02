import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;


public class Account {
    private String publicKey;
    private String privateKey;
    private Credentials credentials;

    public Account(String publicKey, String privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.credentials = Credentials.create(this.privateKey);
    }

    public void getAccountBalance(Web3j web3j) {
        try {
            EthGetBalance ethGetBalance = web3j
                    .ethGetBalance(this.publicKey, DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();
            System.out.println("Balance for account with public key: " + this.publicKey + "\n " +
                    ethGetBalance.getBalance() + " weis");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
