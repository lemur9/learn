#include <stdio.h>
// char *gets(char *s); // char * �ȼ��� char []

// ʾ��
int main(void) {
	char str[1024] = {0};
	printf("��ȡ���ַ���Ϊ��%s\n", gets(str));	
}