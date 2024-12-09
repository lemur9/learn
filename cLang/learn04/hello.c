#include <stdio.h>
// char *gets(char *s); // char * 等价于 char []

// 示例
int main(void) {
	char str[1024] = {0};
	printf("获取的字符串为：%s\n", gets(str));	
}