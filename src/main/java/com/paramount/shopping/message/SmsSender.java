package com.paramount.shopping.message;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACc7c7d2afe6b95214759efcec25e4c3a0";
    public static final String AUTH_TOKEN =
            "fcd036fcee7260ba512e58652078f81b";

    public  String  sendMessage(String phone, String code ) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Message message = Message
                .creator(new PhoneNumber("+64" + phone), // to
                        new PhoneNumber("+17865746079"), // from
                        "Boxlot: the verification code "+ code +", it will expire in 5 minutes. "+dtf.format(now))
                .create();

        return message.getSid();
    }


}