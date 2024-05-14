package com.app.businessBridge.global.holidayapi;

import com.app.businessBridge.global.holidayapi.dto.AllDayDto;
import com.app.businessBridge.global.holidayapi.dto.HoliDayDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Null;
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
    public HoliDayDto getHoilDay(String year, String month) throws IOException {
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

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 휴일이 없는 달 dto 파싱에러 예외 처리 로직
            String msb = sb.toString().replace("\"items\":\"\"","\"items\":null");

            // 휴일이 하루만 있는 달 List<>형태로 DTO에서 받기위한 예외처리 로직
            msb = msb.replace("\"item\":{","\"item\":[{");
            msb = msb.replace("\"seq\":1}}","\"seq\":1}]}");

            System.out.println(msb);

            holiDayDto = objectMapper.readValue(msb, HoliDayDto.class);

            // 휴일 총 일수
            System.out.println(year + "년도 "+month+"월 휴일은 총 :"+holiDayDto.getResponse().getBody().getTotalCount()+"일");

            return holiDayDto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        return holiDayDto;
    }

    // 연, 월에 해당하는 모든 날짜 요청
    // 주말 구분을 위해 필요 ㅠ
    public AllDayDto getAllDay(String year, String month) throws IOException {

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


        conn.connect();

        AllDayDto allDayDto = new AllDayDto();

        // 응답을 읽기 위한 버퍼
        StringBuilder sb = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            // JSON 응답을 HoliDayDto 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            allDayDto = objectMapper.readValue(sb.toString(), AllDayDto.class);

            System.out.println(year + "년도 "+month+"월 일수는 총 :"+allDayDto.getResponse().getBody().getTotalCount()+"일");
            System.out.println(allDayDto.getResponse().getBody().getItems().getItem().get(1).getSolWeek());

            return allDayDto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        return allDayDto;
    }


}
