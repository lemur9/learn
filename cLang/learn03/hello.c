#include <stdio.h>
#include <limits.h>

int main(void)
{
	// 获取无符号数取值范围
	printf("char 无符号 min = 0，max = %hhu\n", UCHAR_MAX);
	// 获取有符号数取值范围
	printf("char 有符号 min = %hhd，max = %hhd\n", CHAR_MIN, CHAR_MAX);
	// 获取 char 占用的字节数
	printf("char 大小 = %u\n", sizeof(char));
	// 获取 unsigned char 占用的字节数
	printf("unsigned char 大小 = %u\n", sizeof(unsigned char));
	
	return 0;
}