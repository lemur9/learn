#include<stdio.h>

void main() {
    char str[10] = {0};
    int len = sizeof(str);
    printf("��ȡ�����ַ���Ϊ��%s\n", fgets(str, len, stdin));
}