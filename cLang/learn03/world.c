#include <stdio.h>

int main(void)
{
	char ch = 'A'; // 定义变量 ch， 指定初值为 'A';
	
	printf("ch = %c\n", ch); // c: character %c 用来显示字符的 格式匹配符。
	
	ch = 'm'; // 给变量ch 赋值成 'm', 覆盖 原来的 'A';
	
	printf("ch = %c\n", ch);
	
	ch = 97; // 使用 范围内的数值 97 ，给 ch 赋值。
	
	printf("ch = %c\n", ch); // 将数值97，按照字符格式打印输出。
	
	ch = 98; // 使用 范围内的数值 98 ，给 ch 赋值。
	
	printf("ch = %c\n", ch); // 将数值98，按照字符格式打印输出。
	
	return 0;
}