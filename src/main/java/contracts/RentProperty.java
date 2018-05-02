package contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class RentProperty extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516080806104da833981016040908152815160208301519183015160609093015160008054600160a060020a033316600160a060020a03199091161790556004919091556005919091556006919091556007556008805460ff1916905561045b8061007f6000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166317fb7db981146100b357806318af3219146100da5780631c43cd04146100ef5780632e88ab0b146101045780636358afbd14610135578063783a112b1461014c5780638da5cb5b1461015a578063ac81f6371461016f578063e1ad9b7514610184578063ee7a3b8e146101ad578063fc590d09146101c2575b600080fd5b3480156100bf57600080fd5b506100c86101d7565b60408051918252519081900360200190f35b3480156100e657600080fd5b506100c86101dd565b3480156100fb57600080fd5b506100c86101e3565b34801561011057600080fd5b506101196101e9565b60408051600160a060020a039092168252519081900360200190f35b34801561014157600080fd5b5061014a6101f8565b005b61014a6004356024356102a9565b34801561016657600080fd5b506101196103d2565b34801561017b57600080fd5b506100c86103e1565b34801561019057600080fd5b506101996103e7565b604080519115158252519081900360200190f35b3480156101b957600080fd5b506100c86103f0565b3480156101ce57600080fd5b506100c86103f6565b60055481565b60045481565b60075481565b600154600160a060020a031681565b60085460ff16151561020957600080fd5b60005433600160a060020a039081169116148015610228575060035442115b80610241575060015433600160a060020a039081169116145b151561024c57600080fd5b60085460ff16151561025d57600080fd5b600154604080514281529051600160a060020a03909216917fdfc5c0e822ca4bd6c981a2df67d25718cdaebc3838b1286c353a7da29f8664a69181900360200190a26102a76103fc565b565b60085460009060ff16156102bc57600080fd5b600034116102c957600080fd5b60045483101580156102dd57506005548211155b15156102e857600080fd5b60088054600160ff199091168117909155805473ffffffffffffffffffffffffffffffffffffffff191633600160a060020a0316179055600283905560038290556305265c0083830360008054600754604051949093049450600160a060020a03169291840280156108fc02929091818181858888f19350505050158015610374573d6000803e3d6000fd5b5060015460025460035460075460408051938452602084019290925284028282015251600160a060020a03909216917fb03c9e82198a44a54b2a93499c246bad28b278c3b5c5d4649b8d4f5e148fee3b9181900360600190a2505050565b600054600160a060020a031681565b60025481565b60085460ff1681565b60065481565b60035481565b6008805460ff191690556001805473ffffffffffffffffffffffffffffffffffffffff19169055600060028190556003555600a165627a7a72305820153cf723c9d1667d56cd488bdc7a91efd14d4a4af43ad9137857893ab0bce3f20029";

    protected RentProperty(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RentProperty(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<E_RentEventResponse> getE_RentEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("E_Rent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<E_RentEventResponse> responses = new ArrayList<E_RentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            E_RentEventResponse typedResponse = new E_RentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._rentalDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._returnDate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._rentalPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<E_RentEventResponse> e_RentEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("E_Rent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, E_RentEventResponse>() {
            @Override
            public E_RentEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                E_RentEventResponse typedResponse = new E_RentEventResponse();
                typedResponse.log = log;
                typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._rentalDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._returnDate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._rentalPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<E_ReturnRentalEventResponse> getE_ReturnRentalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("E_ReturnRental", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<E_ReturnRentalEventResponse> responses = new ArrayList<E_ReturnRentalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            E_ReturnRentalEventResponse typedResponse = new E_ReturnRentalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._returnDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<E_ReturnRentalEventResponse> e_ReturnRentalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("E_ReturnRental", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, E_ReturnRentalEventResponse>() {
            @Override
            public E_ReturnRentalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                E_ReturnRentalEventResponse typedResponse = new E_ReturnRentalEventResponse();
                typedResponse.log = log;
                typedResponse._renter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._returnDate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> availableTo() {
        final Function function = new Function("availableTo", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> availableFrom() {
        final Function function = new Function("availableFrom", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> rentalPrice() {
        final Function function = new Function("rentalPrice", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> renter() {
        final Function function = new Function("renter", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> endRentProperty() {
        final Function function = new Function(
                "endRentProperty", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rent(BigInteger _startRentalDate, BigInteger _endRentalDate, BigInteger weiValue) {
        final Function function = new Function(
                "rent", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startRentalDate), 
                new org.web3j.abi.datatypes.generated.Uint256(_endRentalDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> rentalDate() {
        final Function function = new Function("rentalDate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> rented() {
        final Function function = new Function("rented", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> propertyId() {
        final Function function = new Function("propertyId", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> returnDate() {
        final Function function = new Function("returnDate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<RentProperty> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _availableFrom, BigInteger _availableTo, BigInteger _propertyId, BigInteger _rentalPrice) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_availableFrom), 
                new org.web3j.abi.datatypes.generated.Uint256(_availableTo), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_rentalPrice)));
        return deployRemoteCall(RentProperty.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<RentProperty> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _availableFrom, BigInteger _availableTo, BigInteger _propertyId, BigInteger _rentalPrice) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_availableFrom), 
                new org.web3j.abi.datatypes.generated.Uint256(_availableTo), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_rentalPrice)));
        return deployRemoteCall(RentProperty.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RentProperty load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentProperty(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static RentProperty load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentProperty(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class E_RentEventResponse {
        public Log log;

        public String _renter;

        public BigInteger _rentalDate;

        public BigInteger _returnDate;

        public BigInteger _rentalPrice;
    }

    public static class E_ReturnRentalEventResponse {
        public Log log;

        public String _renter;

        public BigInteger _returnDate;
    }
}
