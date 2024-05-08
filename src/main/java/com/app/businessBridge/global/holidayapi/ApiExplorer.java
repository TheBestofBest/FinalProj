package com.app.businessBridge.global.holidayapi;

import com.app.businessBridge.global.holidayapi.dto.HoliDayDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiExplorer {

    @Value("${custom.api_key}")
    private String api_key;

    // 연, 월을 매개변수로 받아 휴일만 return 하는 api
    public String getHoilDay(String year, String month) throws IOException {
        // 9월로 입력한 경우 09월로 변경 필요
        if(month.length() == 1) {
            month = "0" + month;
        }

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + this.api_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /*월*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*json으로 받기*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        conn.connect();

        HoliDayDto holiDayDto = new HoliDayDto();



        // 응답을 읽기 위한 버퍼
        StringBuilder sb = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            // JSON 응답을 HoliDayDto 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            holiDayDto = objectMapper.readValue(conn.getInputStream(), HoliDayDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sb);

        System.out.println(holiDayDto);

        conn.disconnect();

        return sb.toString();
    }

    // 연, 월에 해당하는 모든 날짜 요청
    // 주말 구분을 위해 필요 ㅠ
    public String getAllDay(String year, String month) throws IOException {

        if(month.length() == 1) {
            month = "0" + month;
        }

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService/getLunCalInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + this.api_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /*월*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("31", "UTF-8")); /*개수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*json으로 받기*/
        // urlBuilder.append("&" + URLEncoder.encode("solDay","UTF-8") + "=" + URLEncoder.encode("22", "UTF-8")); /*일*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        System.out.println(sb);

        return sb.toString();
    }


}
