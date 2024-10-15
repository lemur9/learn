import { getCategoryAPI } from "@/apis/category";

import { onMounted, ref, watchEffect } from "vue";
import { onBeforeRouteUpdate, useRoute } from "vue-router";

export function useCategory() {
  const route = useRoute();
  const category = ref({});
  const getCategory = async (id = route.params.id) => {
    const res = await getCategoryAPI(id);
    category.value = res.result;
  };

  onMounted(() => {
    getCategory();
  });

  onBeforeRouteUpdate((to) => {
    getCategory(to.params.id);
  });

  return {
    category
  }
}
