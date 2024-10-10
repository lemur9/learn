# Vue3入门 Day01

## 1. 创建vue3项目

~~~shell
npm init vue@latest
~~~

## 2.组合式API

**用函数调用的方式生成响应式数据**

reactive函数

- 不能处理简单类型的数据

ref函数

- 可以处理简单类型数据
- 但是必须通过.value访问修改简单数据的值

~~~vue
<script setup>
import { ref } from "vue";
const count = ref(0)
const addCount = () => count.value++
</script>

<template>
  <button @click="addCount">{{ count }}</button>
</template>

<style scoped></style>
~~~

## 3. 计算函数 computed

~~~vue
<script setup>
import { ref, computed } from "vue";
const count = ref(0)
const addCount = () => count.value++
const sum = computed(() => count.value + 5)
</script>

<template>
  count = <button @click="addCount">{{ count }}</button>
  <br />
  count + 5 = <button>{{ sum }}</button>
</template>

<style scoped></style>
~~~

## 4. 监听函数 watch

- 单值监听

~~~vue
<script setup>
import { ref, watch } from "vue";
const count = ref(0)
const addCount = () => count.value++
watch(count, (newValue, oldValue) => console.log("count 变化了", newValue, oldValue))
</script>

<template>
  count = <button @click="addCount">{{ count }}</button>
</template>

<style scoped></style>
~~~

- 多值监听

~~~vue
<script setup>
import { ref, watch } from "vue";
const count = ref(0)
const name = ref("lemur")
const addCount = () => count.value++
const change = () => name.value = "limu"
watch(
  [count, name],
  (
    [newCount, newName],
    [oldCount, oldName]
  ) => console.log("count和name 变化了",
    [newCount, newName],
    [oldCount, oldName]))
</script>

<template>
  count = <button @click="addCount">{{ count }}</button>
  <br>
  name = <button @click="change">{{ name }}</button>
</template>

<style scoped></style>
~~~

- 立即触发-immediate

~~~vue
<script setup>
import { ref, watch } from "vue";
const count = ref(0)
const addCount = () => count.value++
watch(count,
  (newValue, oldValue) => console.log("count 变化了", newValue, oldValue),
  { immediate: true })

</script>

<template>
  count = <button @click="addCount">{{ count }}</button>
</template>

<style scoped></style>
~~~

- 深度监听

~~~vue
<script setup>
import { ref, watch } from "vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = () => count.value.age++
watch(count,
  (newValue, oldValue) => console.log("count 变化了", newValue, oldValue),
  {
    deep: true
  })

</script>

<template>
  count = <button @click="addCount">{{ count.age }}</button>
</template>

<style scoped></style>
~~~

**注意：此时监听的新老值是对象，对象中的值是一样的，因为对象地址没发生改变，只是对象中的属性进行了修改**

- 精确监听某个属性

~~~vue
<script setup>
import { ref, watch } from "vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = () => count.value.age++
const change = () => count.value.name = "limu"
watch(() => count.value.age,
  (newValue, oldValue) => console.log("count 变化了", newValue, oldValue))
</script>

<template>
  count = <button @click="addCount">{{ count.age }}</button>
  <br>
  name = <button @click="change">{{ count.name }}</button>
</template>

<style scoped></style>
~~~

**注意：此时第一个参数为一个function函数**

## 5. 生命周期

|        选项式API         |      组合式API      |
| :----------------------: | :-----------------: |
| ==beforeCreate/created== |      ==setup==      |
|       beforeMount        |    onBeforeMount    |
|         mounted          |      onMounted      |
|       beforeUpdate       |   onBeforeUpdate    |
|         updated          |      onUpdated      |
|    ==beforeUnmount==     | ==onBeforeUnmount== |
|      ==unmounted==       |   ==onUnmounted==   |

## 6. 父子通信-父传子 defineProps

- 父组件-App.vue

~~~vue
<script setup>
import { ref, watch } from "vue";
import Son from "./components/Son.vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = () => count.value.age++
</script>

<template>
  name = <button @click="addCount">{{ count.name }}</button>
  <br>
  <Son :age="count.age"></Son>
</template>

<style scoped></style>
~~~

- 子组件-Son.vue

~~~vue
<script setup>
defineProps({
  age: Number
})
</script>

<template>
  age = <button>{{ age }}</button>
</template>

<style scoped></style>
~~~

## 7. 父子通信-子传父 defineEmits

- 父组件-App.vue

~~~Vue
<script setup>
import { ref, watch } from "vue";
import Son from "./components/Son.vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = (age) => count.value.age = age
</script>

<template>
  age = <button>{{ count.age }}</button>
  <br>
  <Son :name="count.name" @getAddCount="addCount"></Son>
</template>

<style scoped></style>
~~~

- 子组件-Son.vue

~~~vue
<script setup>
defineProps({
  name: String
})
const emit = defineEmits(['getAddCount'])

const sendMsg = () => {
  emit('getAddCount', 24)
}
</script>

<template>
  name = <button @click="sendMsg">{{ name }}</button>
</template>

<style scoped></style>
~~~

## 8. 模板引用-ref属性 defineExpose

- 父组件-App.vue

~~~vue
<script setup>
import { ref } from "vue";
import Son from "./components/Son.vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = (age) => count.value.age = age

const sonRef = ref(null)
const print = () => console.log(sonRef.value);

</script>

<template>
  age = <button @click="print">{{ count.age }}</button>
  <br>
  <Son :name="count.name" @getAddCount="addCount" ref="sonRef"></Son>
</template>

<style scoped></style>
~~~

- 子组件-Son.vue

~~~vue
<script setup>
defineProps({
  name: String
})
const emit = defineEmits(['getAddCount'])

const sendMsg = () => {
  emit('getAddCount', 24)
}

defineExpose({
  emit,
  sendMsg
})
</script>

<template>
  name = <button @click="sendMsg">{{ name }}</button>
</template>

<style scoped></style>
~~~

## 9. 跨组件通信 provide、inject

- 父组件-App.vue

~~~vue
<script setup>
import { ref, provide, computed } from "vue";
import Son from "./components/Son.vue";
const count = ref({
  age: 20,
  name: "lemur"
})
const addCount = (age) => count.value.age = age
              
const sonRef = ref(null)
const print = () => console.log(sonRef.value);
provide('message-key', computed(() => count.value.age))

</script>

<template>
  age = <button @click="print">{{ count.age }}</button>
  <br>
  <Son :name="count.name" @getAddCount="addCount" ref="sonRef"></Son>
</template>

<style scoped></style>
~~~

- 子组件-Son.vue

~~~vue
<script setup>
import Grandson from "./Grandson.vue";
defineProps({
  name: String
})
const emit = defineEmits(['getAddCount'])

const sendMsg = () => {
  emit('getAddCount', 24)
}

defineExpose({
  emit,
  sendMsg
})
</script>

<template>
  name = <button @click="sendMsg">{{ name }}</button>
  <Grandson></Grandson>
</template>

<style scoped></style>
~~~

- 孙组件-Grandson.vue

~~~vue
<script setup>
import { inject } from 'vue'
const messages = inject('message-key')
</script>

<template>
  <h1> {{ messages }}</h1>
</template>

<style scoped></style>
~~~



