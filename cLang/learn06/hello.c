#include<stdio.h>
void main()
{   int  i,n,score;
    scanf("%d",&n);                      /*输入学生人数*/
    for(i=0;i<n;i++)
    {   scanf("%d",&score);             /*依次输入所有的成绩*/
        if(score<80)printf("%d",score); /*判断每个成绩是否低于80分*/
    }
}
