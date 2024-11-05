class Person1 {
  age: number
  gender: string

  constructor(age: number, gender: string) {
    this.age = age
    this.gender = gender
  }
}

const pg = new Person1(18, '男')
console.log(pg.age, pg.gender)
