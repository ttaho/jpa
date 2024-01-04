package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SsafyCafe {

    public static void main(String[] args) {
        MenuService menuService = new MenuService();


        System.out.println("환영합니다! SSAFY 카페입니다.");
        System.out.println("추천 메뉴를 알려드릴게요!");
        System.out.println("숫자 키를 눌러 확인해보세요!");
        System.out.println("1: 파일에서 메뉴 읽기, 2: 캐시에서 메뉴 읽기, 3: 메뉴변경");


        while(true){
            try{
                long startTime = System.currentTimeMillis(); // 시작 시간
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int input = Integer.parseInt(br.readLine());

                switch (input){
                    case 1:
                        String menu = menuService.readMenu();
                        menuService.cacheMenu(menu); // 캐시에 저장
                        System.out.println("파일에서 읽은 메뉴 : " + menu);
                        break;
                    case 2:
                        System.out.println("캐시에서 읽은 메뉴: " + menuService.getMenuFromCache());
                        break;
                    case 3:
                        System.out.println("새로운 메뉴를 입력하세요 : ");
                        br = new BufferedReader(new InputStreamReader(System.in));
                        String newMenu = br.readLine();
                        menuService.updateMenu(newMenu);
                        System.out.println("메뉴 변경 완료!! : " + newMenu);
                        break;
                }
                long endTime = System.currentTimeMillis();
                System.out.println("소요 시간 : " + (endTime - startTime) + "ms");

            } catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
