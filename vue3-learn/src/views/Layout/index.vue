<script setup>
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue'
import LayoutHead from '@/views/Layout/components/LayoutHead.vue'
import LayoutNav from '@/views/Layout/components/LayoutNav.vue';
</script>

<template>
  <LayoutNav />
  <LayoutHead />
  <RouterView />
  <LayoutFooter />
</template>

<style scoped></style>