package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuService {

    private static final String FILE_PATH = "menu_hour.csv";
    private Map<String,String> cache = new HashMap<>(); //캐시

    // csv에서 추천 메뉴 불러오는 메소드
    public String readMenu() throws InterruptedException, IOException {
        Thread.sleep(3000); // 3초 대기
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        return br.readLine();
    }

    // 메모리에 메뉴를 캐싱하는 메소드
    public void cacheMenu(String menu){
        cache.put("menu", menu);
    }

    // 메모리에서 메뉴 읽어오는 메소드
    public String getMenuFromCache(){
        return cache.get("menu");
    }

    // 메뉴 변경 메소드 csv,cache 둘 다 업데이트
    public void updateMenu(String menu) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH));
        bufferedWriter.write(menu);
        bufferedWriter.flush();
        cacheMenu(menu);
    }

}
