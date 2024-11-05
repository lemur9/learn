interface A {
  name: string;
}

interface B extends A {
  age: number;
}

let options: B = {
  name: "default",
  age: 12,
};
