#include <stdio.h>

#define PI 3.14

int main(void) {
	int a = 10;
	
	printf("%d\n", a); // %d����ʽƥ�����ƥ��������
	printf("a = %d\n", a); // a = �ڡ����У�������ͨ�ַ����� ԭ�������
	printf("%d\n", 100);
	
	printf("%f\n", PI); // %f����ʽƥ�����ƥ��С����
	printf("PI = %f\n", PI); // PI = �ڡ����У�������ͨ�ַ����� ԭ�������
	printf("%f\n", 3.4567827); // %f����ʽƥ�����ƥ��С���� �������룬����6λС��
	
	int b = 20;
	
	printf("%d + %d = %d\n", a, b, a+b); // +��= �� �����У�������ͨ�ַ����� ԭ�������
	printf("%d + %d = %d\n", 3, 7, 3+7);
	
	return 0;
}