function mySlice(start: number, end?: number): void {
  console.log("起始索引：", start, "结束索引：", end);
}

const aa = (start: number, end?: number): void => {
  console.log("起始索引：", start, "结束索引：", end);
};

const bb: (start: number, end?: number) => void = (start, end) => {
  console.log("起始索引：", start, "结束索引：", end);
};
