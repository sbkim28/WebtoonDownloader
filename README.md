# WebtoonDownloader
���̹��� ���� ������ �ٿ�ε�

## Usage
java -jar WebtoonManager.jar

-d : ���� �ٿ�ε�
-d comicType[naver, daum] webtoonName path (index)

-do : ������ �� ȭ���� �ٿ�ε�
-do comicType[naver, daum] webtoonName path index

-i : �ε��� ���� ����
-i path

-r : �ٿ�ε�� ���� �б�
-r path page

### Usage Example
java -jar WebtoonManager.jar -d naver "���ѵ��� ��������" "C://webtoons/2018/"

���ѵ��� �������� �̸��� ���̹� ���� ��ü �ٿ�ε�
���� ȭ�� �ش��ϴ� ������ 'C://webtoons/2018/' �Ʒ��� ������
������ ������ ��¥�� ���� �ε��� ���� �ڵ� ����

java -jar WebtoonManager.jar -d naver "���ѵ��� ��������" "C://webtoons/2018/" 2

���ѵ��� �������� �̸��� ���̹� ���� 3ȭ���� ������ �ٿ�ε�
(0�� 1ȭ�� �ǹ���)
�������� ���� ������

java -jar WebtoonManager.jar -do naver "���ѵ��� ��������" "C://webtoons/2018/" 2

���ѵ��� �������� �̸��� ���̹� ���� 3ȭ�� �ٿ�ε�
'C://webtoons/2018/' �Ʒ��� ���� ������
�ε��� ������ �������� ����

java -jar WebtoonManager.jar -i "C://webtoons/2018/"

'C://webtoons/2018/' �Ʒ��� ������ ��¥�� ���� �ε��� ���� ����

java -jar WebtoonManager.jar -r "C://webtoons/2018/" 2

�ε��� ������ �����Ͽ� 3��° ���� �б�

