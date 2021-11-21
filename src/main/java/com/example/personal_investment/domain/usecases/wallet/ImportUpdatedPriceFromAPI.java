package com.example.personal_investment.domain.usecases.wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

/*
    TODO: ver com grupo como iremos usar essa informação, se apenas visualizar em tela ou atualizar
     os valores dos investimetos em carteira
 */

public class ImportUpdatedPriceFromAPI {

    StringBuilder ticker = new StringBuilder();
    final String myKey = "QOGH7LQOPK6527F6";

    public ImportUpdatedPriceFromAPI(String stockName){
        ticker = ticker.append(stockName).append(".SA");
    }

    public BigDecimal getData(){

        try {
            URL url = new URL("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker +
                    "&apikey=" + myKey);
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);

            String line = buff.readLine();
            while(line != null){
                // buscando a linha que contem o valor desejado
                if(line.contains("\"05. price\":")){
                    // tratando a linha para gerar a string com apenas o numero do preço atual
                    String[] dados = line.split(": " );
                    dados[1] = dados[1].substring(1, dados[1].length()-4);
                    return new BigDecimal(dados[1]);
                }

                //System.out.println(line);
                line = buff.readLine();

            }

            buff.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0");
    }

}
