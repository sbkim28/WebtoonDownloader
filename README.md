# WebtoonDownloader
웹툰을 다운로드

## Usage
java -jar WebtoonManager.jar

-d : 웹툰 다운로드
-d comicType[naver, daum, lezhin, kakao] webtoonName path (index)

-do : 웹툰의 한 화차만 다운로드
-do comicType[naver, daum, lezhin, kakao] webtoonName path index

-i : 인덱스 파일 생성
-i path

-r : 다운로드된 웹툰 읽기
-r path page

-rf : 다운로드된 웹툰 읽기 - 파일 이름으로 검색
-rf path filename
### Usage Example
java -jar WebtoonManager.jar -d naver "무한도전 릴레이툰" "C://webtoons/2018/"

무한도전 릴레이툰 이름의 네이버 웹툰 전체 다운로드
각각 화에 해당하는 폴더가 'C://webtoons/2018/' 아래에 생성됨
생성된 폴더의 날짜에 따라 인덱스 파일 자동 생성

java -jar WebtoonManager.jar -d naver "무한도전 릴레이툰" "C://webtoons/2018/" 2

무한도전 릴레이툰 이름의 네이버 웹툰 3화부터 끝까지 다운로드
(0이 1화를 의미함)
나머지는 위와 동일함

java -jar WebtoonManager.jar -do naver "무한도전 릴레이툰" "C://webtoons/2018/" 2

무한도전 릴레이툰 이름의 네이버 웹툰 3화만 다운로드
'C://webtoons/2018/' 아래에 폴더 생성됨
인덱스 파일은 생성하지 않음

java -jar WebtoonManager.jar -i "C://webtoons/2018/"

'C://webtoons/2018/' 아래의 폴더의 날짜에 따라 인덱스 파일 생성

java -jar WebtoonManager.jar -r "C://webtoons/2018/" 2

인덱스 파일을 참고하여 3번째 폴더 읽기

java -jar WebtoonManager.jar -rf "C://webtoons/2018/" "1화"

'C://webtoons/2018/' 아래의 폴더 중 이름이 '1화'인 폴더를 찾아 읽기