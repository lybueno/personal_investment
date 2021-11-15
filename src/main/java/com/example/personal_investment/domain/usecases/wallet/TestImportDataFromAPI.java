package com.example.personal_investment.domain.usecases.wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TestImportDataFromAPI {

    String ticker = "";
    final String stockSearch = ticker + ".SA";
    final String myKey = "QOGH7LQOPK6527F6";

    public TestImportDataFromAPI(String stockName){
        ticker = stockName;
    }



    public void getData(){
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockSearch +
                    "&interval" +
                    "=5min&apikey=" + myKey);
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);

            String line = buff.readLine();
            while(line != null){
                System.out.println(line);
                line = buff.readLine();
            }

            buff.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
