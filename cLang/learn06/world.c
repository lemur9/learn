#include<stdio.h>
#define N 30
void main()
{   int i,score[N];
    float ave=0;
    printf("Enter scores one by one?");
    for(i=0;i<N;i++)scanf("%d",&score[i]);
    for(i=0;i<N;i++)ave+=score[i];
    ave=ave/N;                                   /*计算平均成绩*/
    printf("Average is%.1f\n",ave);
    printf("Less than average:");
    for(i=0;i<N;i++)
        if(score[i]<ave)printf("%d",score[i]);/*打印低于平均值的成绩*/
    printf("\n");
}
