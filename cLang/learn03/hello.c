#include <stdio.h>
#include <limits.h>

int main(void)
{
	// ��ȡ�޷�����ȡֵ��Χ
	printf("char �޷��� min = 0��max = %hhu\n", UCHAR_MAX);
	// ��ȡ�з�����ȡֵ��Χ
	printf("char �з��� min = %hhd��max = %hhd\n", CHAR_MIN, CHAR_MAX);
	// ��ȡ char ռ�õ��ֽ���
	printf("char ��С = %u\n", sizeof(char));
	// ��ȡ unsigned char ռ�õ��ֽ���
	printf("unsigned char ��С = %u\n", sizeof(unsigned char));
	
	return 0;
}