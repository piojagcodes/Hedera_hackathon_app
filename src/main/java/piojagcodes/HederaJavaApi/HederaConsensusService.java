
package piojagcodes.HederaJavaApi;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Objects;


public final class HederaConsensusService {
    private static final AccountId YOUR_OPERATOR_ID = AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("YOUR_OPERATOR_ID")));
    private static final PrivateKey YOUR_OPERATOR_KEY = PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("YOUR_OPERATOR_KEY")));
    
    Client client = Client.forTestnet();
    
   // client.setOperator(OPERATOR_ID, OPERATOR_KEY);
    
}
