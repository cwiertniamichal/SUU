import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String NODE_URL = "http://localhost:8545";
    private static final String PROP_FILE = "src/resources/app.properties";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static Map<String, Account> users = new HashMap<>();
    private static Account currentUser;
    private static Web3j web3j;
    private static List<RentOffer> rentOffers = new ArrayList<>();
    public static final Database db = new Database();
    public static final DBCollection offers = db.createCollection("Offers");

    static {
        for(Handler iHandler:LOGGER.getParent().getHandlers())
            LOGGER.getParent().removeHandler(iHandler);

        CustomRecordFormatter formatter = new CustomRecordFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
    }

    public static void main(String [] args) throws Exception {
        LOGGER.setLevel(Level.ALL);
        db.clearDb();

        loadUsersAccounts();

        web3j = Web3j.build(new HttpService(NODE_URL));
        LOGGER.info("Connected to Ethereum client version: " +
                web3j.web3ClientVersion().send().getWeb3ClientVersion());

        mainLoop();
    }

    private static void loadUsersAccounts(){
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream(PROP_FILE);

            // load a properties file
            prop.load(input);

            for(int i = 1; i < 4; i++){
                String username = "user" + i;
                Account account = new Account(prop.getProperty(username + "PubKey"),
                        prop.getProperty(username + "PrivKey"));
                users.put(username, account);
                LOGGER.info("Created user: " + username + "\n" +
                        "PubKey: " + account.getPublicKey() + "\n" +
                        "PrivKey: " + account.getPrivateKey() + "\n");
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    static void mainLoop(){
        DBObject obj = null;
        while (true) {
            Map<String, Object> params;

            System.out.println("Choose option and enter appropriate parameters: \n" +
                    "1) Login [username] \n" +
                    "2) Get currentUser balance \n" +
                    "3) Add new offer [availableFrom] [availableTo] [pricePerDay] [address]\n" +
                    "4) List offers matching parameters [availableFrom=<dd/MM/yyyy>] \n" +
                    "5) Choose offer to operate on [availableFrom=<dd/MM/yyyy>] \n" +
                    "6) Get contract for current status \n" +
                    "7) Sign contract current contract [rentalDate] [returnDate] \n");

            Scanner reader = new Scanner(System.in);
            String[] input = reader.nextLine().split(" ");

            switch (input[0]) {
                case "1":
                    currentUser = users.get(input[1]);
                    LOGGER.info("Switched to user: " + currentUser + "\n" +
                            "PubKey: " + currentUser.getPublicKey() + "\n" +
                            "PrivKey: " + currentUser.getPrivateKey() + "\n");
                    break;

                case "2":
                    currentUser.getAccountBalance(web3j);
                    break;

                case "3":
                    addNewRentOffer(input[1], input[2], input[3], input[4]);
                    break;

                case "4":
                    params = parseParams(input);
                    listMatchingOffers(params);
                    break;

                case "5":
                    params = parseParams(input);
                    obj = db.findOneDocument(offers, params);
                    break;

                case "6":
                    if(obj != null)
                        ContractLogic.loadContract((String)obj.get("contractAddress"), currentUser.getCredentials(), web3j).getContractStatus();
                    else
                        System.out.println("Please choose offer to operate on first");
                    break;

                case "7":
                    if(obj != null) {
                        ContractLogic contractLogic = ContractLogic.loadContract((String) obj.get("contractAddress"),
                                currentUser.getCredentials(), web3j);

                        try {
                            contractLogic.rentProperty(DATE_FORMAT.parse(input[1]).getTime(),
                                    DATE_FORMAT.parse(input[2]).getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        System.out.println("Please choose offer to operate on first");
                    break;
                default:
                    System.out.println("Unrecognized option");
            }
        }
    }

    private static void addNewRentOffer(String availableFrom, String availableTo, String pricePerDay, String address){
        try {
            RentOffer rentOffer = new RentOffer(DATE_FORMAT.parse(availableFrom), DATE_FORMAT.parse(availableTo),
                    Long.parseLong(pricePerDay), address);
            rentOffer.createContract(currentUser.getCredentials(), web3j);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void listMatchingOffers(Map<String, Object> params){
        db.listMatchingDocuments(offers, params);
    }

    private static Map<String, Object> parseParams(String[] input){
        Map<String, Object> parsedParams = new HashMap<>();
        for(int i = 1; i < input.length; i++) {
            String key = input[i].split("=")[0];
            String val = input[i].split("=")[1];

            if (key.equals("availableFrom") || key.equals("availableTo"))
                try {
                     parsedParams.put(key, DATE_FORMAT.parse(val).getTime());
                } catch (ParseException e){
                    e.printStackTrace();
                }
            else if(key.equals("pricePerDay")){
                    parsedParams.put(key, Long.parseLong(val));
            }
            else{
                parsedParams.put(key, val);
            }
        }
        return  parsedParams;
    }
}
