#include <stdio.h>

int main(void)
{
	char ch = 'A'; // ������� ch�� ָ����ֵΪ 'A';
	
	printf("ch = %c\n", ch); // c: character %c ������ʾ�ַ��� ��ʽƥ�����
	
	ch = 'm'; // ������ch ��ֵ�� 'm', ���� ԭ���� 'A';
	
	printf("ch = %c\n", ch);
	
	ch = 97; // ʹ�� ��Χ�ڵ���ֵ 97 ���� ch ��ֵ��
	
	printf("ch = %c\n", ch); // ����ֵ97�������ַ���ʽ��ӡ�����
	
	ch = 98; // ʹ�� ��Χ�ڵ���ֵ 98 ���� ch ��ֵ��
	
	printf("ch = %c\n", ch); // ����ֵ98�������ַ���ʽ��ӡ�����
	
	return 0;
}