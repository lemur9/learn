#include<stdio.h>

void main() {
    char str[10] = {0};
    int len = sizeof(str);
    printf("获取到的字符串为：%s\n", fgets(str, len, stdin));
}