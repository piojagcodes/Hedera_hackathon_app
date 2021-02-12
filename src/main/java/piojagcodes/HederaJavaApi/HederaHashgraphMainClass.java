
package piojagcodes.HederaJavaApi;

import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.HederaPreCheckStatusException;
import com.hedera.hashgraph.sdk.HederaReceiptStatusException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransferTransaction;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.concurrent.TimeoutException;



public class HederaHashgraphMainClass {

   
    public static void main(String[] args) throws TimeoutException, HederaPreCheckStatusException, HederaReceiptStatusException, PrecheckStatusException, ReceiptStatusException {
        AccountId YOUR_Account_Id = AccountId.fromString(Dotenv.load().get("YOUR_ACCOUNT_ID"));
        PrivateKey YOUR_PRIVATE_KEY = PrivateKey.fromString(Dotenv.load().get("YOUR_PRIVATE_KEY"));
        
        Client client = Client.forTestnet();
        client.setOperator(YOUR_Account_Id, YOUR_PRIVATE_KEY);
        
        PrivateKey newAccountPrivateKey = PrivateKey.generate();
        PublicKey newAccountPublicKey = newAccountPrivateKey.getPublicKey();
        
        TransactionResponse newAccount = new AccountCreateTransaction()
     .setKey(newAccountPublicKey)
     .setInitialBalance( Hbar.fromTinybars(1000))
     .execute(client);
        
        
        AccountId newAccountId = newAccount.getReceipt(client).accountId;
        
          System.out.println("The new account ID is: " +newAccountId);

        //Check the new account's balance
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(newAccountId)
                .execute(client);

        System.out.println("The new account balance is: " +accountBalance.hbars);
        
        
        TransactionResponse sendHbar = new TransferTransaction()
     .addHbarTransfer(YOUR_Account_Id, Hbar.fromTinybars(-1000)) //Sending account
     .addHbarTransfer(newAccountId, Hbar.fromTinybars(1000)) //Receiving account
     .execute(client);
        
        
    System.out.println("The transfer transaction was: " +sendHbar.getReceipt(client).status);
    
    
    Hbar queryCost = new AccountBalanceQuery()
     .setAccountId(newAccountId)
     .getCost(client);

    System.out.println("The cost of this query is: " +queryCost);   
    
    AccountBalance accountBalanceNew = new AccountBalanceQuery()
     .setAccountId(newAccountId)
     .execute(client);

System.out.println("The new account balance is: " +accountBalanceNew.hbars);




    }
    
 
    
}
