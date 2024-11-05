type IPerson = {
  name: string;
  age: number;
  sayHi(): void;
};

let person: IPerson = {
  name: "John",
  age: 33,
  sayHi(): void {
    console.log("sayHi");
  },
};

interface IPerson2 {
  name: string;
  age: number;
  sayHi(): void;
}

let person2: IPerson2 = {
  name: "Jon",
  age: 23,
  sayHi(): void {
    console.log("hi");
  },
};
