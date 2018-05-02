import contracts.RentProperty;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;
import java.util.Date;
import java.util.logging.Logger;


public class ContractLogic {
    Web3j web3j;
    RentProperty contract;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final int MICROSECONDS = 1000;
    private static final int SECONDS = 60;
    private static final int MINUTES = 60;
    private static final int HOURS = 60;

    private ContractLogic(Web3j web3j){
        this.web3j = web3j;
    }

    public static ContractLogic deployNewContract(long availableFrom, long availableTo, long price,
                                                  Web3j web3j, Credentials credentials) {
        ContractLogic contractLogic = new ContractLogic(web3j);
        try {
            RentProperty contract = RentProperty.deploy(contractLogic.web3j, credentials,
                    ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, BigInteger.valueOf(availableFrom),
                    BigInteger.valueOf(availableTo), BigInteger.valueOf(1), BigInteger.valueOf(price)).send();
            LOGGER.info("Deployed contract");
            contractLogic.contract = contract;
            return contractLogic;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ContractLogic loadContract(String contractAddress, Credentials credentials, Web3j web3j){
        ContractLogic contractLogic = new ContractLogic(web3j);
        RentProperty loadedContract = RentProperty.load(contractAddress, contractLogic.web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        LOGGER.info("Contract loaded");
        contractLogic.contract = loadedContract;
        return contractLogic;
    }

    public void getContractStatus() {
        try {
            LOGGER.info("Contract status: \n" +
                    "is rented: " + contract.rented().send() + "\n" +
                    "available from: " + new Date(contract.availableFrom().send().longValue()) + "\n" +
                    "available to: " + new Date(contract.availableTo().send().longValue()) + "\n" +
                    "price: " + contract.rentalPrice().send() + "\n" +
                    "propertyID: " + contract.propertyId().send() + "\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getContractAddress(){
        String contractAddress = contract.getContractAddress();
        LOGGER.info("Contract address is " + contractAddress);
        return contractAddress;
    }

    public void rentProperty(long rentalDate, long returnDate) {
        try {
            if(contract.rented().send()){
                System.out.println("Property is already rented");
                return;
            }

            long price = ((returnDate - rentalDate) / (MICROSECONDS * SECONDS * MINUTES * HOURS)) *
                    contract.rentalPrice().send().intValue();
            System.out.println("Calculated price: " + price);

            contract.rent(BigInteger.valueOf(rentalDate), BigInteger.valueOf(returnDate),
                    BigInteger.valueOf(price)).send();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
