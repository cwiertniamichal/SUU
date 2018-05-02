import org.bson.types.ObjectId;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentOffer {
    Date availableFrom;
    Date availableTo;
    Long price;
    String address;
    ContractLogic contract;
    Map<String, Object> offerParams;
    ObjectId offerId;

    public RentOffer(Date availableFrom, Date availableTo, Long price, String address){
            this.availableFrom = availableFrom;
            this.availableTo = availableTo;
            this.price = price;
            this.address = address;

            this.offerParams = new HashMap<>();
            offerParams.put("availableFrom", availableFrom.getTime());
            offerParams.put("availableTo", availableTo.getTime());
            offerParams.put("pricePerDay", price);
            offerParams.put("address", address);

            offerId = Main.db.saveInsert(Main.offers, offerParams);
            offerParams.put("_id", offerId);
    }

    public void createContract(Credentials credentials, Web3j web3j){
        this.contract = ContractLogic.deployNewContract(availableFrom.getTime(), availableTo.getTime(),
                price, web3j, credentials);
        Map<String, Object> contractParams = new HashMap<>(offerParams);
        contractParams.put("contractAddress", this.contract.getContractAddress());
        Main.db.saveUpdate(Main.offers, offerParams, contractParams);
    }
}
