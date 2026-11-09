[출석체크 안드로이드 앱]
1. 컴퓨터에 Android Studio 설치 (developer.android.com/studio)
2. Android Studio > Open > 이 AttendanceApp 폴더 선택 > 동기화(Sync) 끝날 때까지 대기
3. 메뉴 Build > Build Bundle(s)/APK(s) > Build APK(s)
4. 완료 알림의 locate 클릭 > app-debug.apk 를 폰으로 전송
5. 폰에서 APK 열어 설치 (출처를 알 수 없는 앱 허용), 알림 권한 허용

[Android Studio 없이 빌드: GitHub]
1. github.com 무료 가입 > New repository 생성
2. 압축 푼 AttendanceApp 폴더 안의 파일 전체를 저장소에 업로드 (.github 폴더 포함)
3. Actions 탭에서 빌드가 끝나길 기다린 뒤(약 5분), 결과의 app-debug 를 내려받아 압축을 풀면 APK
