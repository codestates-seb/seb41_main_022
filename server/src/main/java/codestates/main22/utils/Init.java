package codestates.main22.utils;

import java.util.List;

public class Init {
    // 초기화 태그 리스트
    public static List<String> tagList = List.of(
            "청소년",
            "대학생",
            "직장인",
            "단기간",
            "장기간",
            "취업",
            "자격증",
            "공무원",
            "IT",
            "제2외국어",
            "취미",
            "독서",
            "운동",
            "예술",
            "자기개발",
            "기타"
    );

    // 이미지 저장소 S3의 주소
    public static String S3Url = "https://seb41-main-022.s3.ap-northeast-2.amazonaws.com/";

    // 트리 이미지 URL
    public static String treeFirstImage = S3Url + "February1.png"; // 새싹 이미지
    public static String treeSecondImage = S3Url + "February2.png"; // 어린 나무 이미지
    public static String treeThirdImage = S3Url + "February3.png"; // 꽃 나무 이미지

    // 월별 트리 이미지 URL(2023)
    public static List<String> tree2023Image = List.of(
            S3Url + "February5.png", // 1월 : 황금 사과 나무
            S3Url + "February4.png", // 2월 : 사과 나무
            S3Url + "February5.png", // 3월 : 황금 사과 나무
            S3Url + "February4.png", // 4월 : 사과 나무
            S3Url + "February5.png", // 5월 : 황금 사과 나무
            S3Url + "February4.png", // 6월 : 사과 나무
            S3Url + "February5.png", // 7월 : 황금 사과 나무
            S3Url + "February4.png", // 8월 : 사과 나무
            S3Url + "February5.png", // 9월 : 황금 사과 나무
            S3Url + "February4.png", // 10월 : 사과 나무
            S3Url + "February5.png", // 11월 : 황금 사과 나무
            S3Url + "February4.png"  // 12월 : 사과 나무
    );

    // 트리 업그레이드 기준 점수
    public static int treeFirstUpgradeScore = 100;
    public static int treeSecondUpgradeScore = 300;
    public static int treeFinalUpgradeScore = 600;
}
