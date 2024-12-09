#include <stdio.h>

#define PI 3.14

int main(void) {
	int a = 10;
	
	printf("%d\n", a); // %d：格式匹配符，匹配整数。
	printf("a = %d\n", a); // a = 在“”中，代表普通字符串， 原样输出。
	printf("%d\n", 100);
	
	printf("%f\n", PI); // %f：格式匹配符，匹配小数。
	printf("PI = %f\n", PI); // PI = 在“”中，代表普通字符串， 原样输出。
	printf("%f\n", 3.4567827); // %f：格式匹配符，匹配小数。 四舍五入，保留6位小数
	
	int b = 20;
	
	printf("%d + %d = %d\n", a, b, a+b); // +、= 在 “”中，代表普通字符串， 原样输出。
	printf("%d + %d = %d\n", 3, 7, 3+7);
	
	return 0;
}