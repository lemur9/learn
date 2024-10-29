import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { useUserStore } from "./userStore";
import { deleteCartAPI, findNewCartListAPI, insertCartAPI } from "@/apis/cart";

export const useCartStore = defineStore(
  "cart",
  () => {
    const userStore = useUserStore();
    const isLogin = computed(() => userStore.userInfo.token);

    const cartList = ref([]);

    const addCart = async (goods) => {
      const { skuId, count } = goods;
      if (isLogin) {
        await insertCartAPI({ skuId, count });
        updateNewList();
      } else {
        const cart = cartList.value.find((item) => item.skuId === goods.skuId);

        if (cart) {
          cart.count += goods.count;
        } else {
          cartList.value.push(goods);
        }
      }
    };

    const delCart = async (skuId) => {
      if (isLogin) {
        await deleteCartAPI([skuId]);
        updateNewList();
      } else {
        const index = cartList.value.findIndex((item) => item.skuId === skuId);
        cartList.value.splice(index, 1);
      }
    };

    const cartCount = computed(() =>
      cartList.value.reduce((cartCount, item) => cartCount + item.count, 0)
    );

    const cartPrice = computed(() =>
      cartList.value.reduce(
        (cartPrice, item) => cartPrice + item.count * item.price,
        0
      )
    );

    const singleCheck = (skuId, selected) => {
      const cart = cartList.value.find((item) => item.skuId === skuId);
      cart.selected = selected;
    };

    const allCheck = (selected) => {
      cartList.value.map((item) => (item.selected = selected));
    };

    const isAll = computed(() => cartList.value.every((item) => item.selected));

    const allCount = computed(() =>
      cartList.value
        .filter((item) => item.selected)
        .reduce((allCount, item) => allCount + item.count, 0)
    );

    const allPrice = computed(() =>
      cartList.value
        .filter((item) => item.selected)
        .reduce((allPrice, item) => allPrice + item.count * item.price, 0)
    );

    const updateNewList = async () => {
      const res = await findNewCartListAPI();
      cartList.value = res.result;
    };

    const clearCart = () => {
      cartList.value = [];
    };

    return {
      cartList,
      cartCount,
      cartPrice,
      isAll,
      allCount,
      allPrice,
      clearCart,
      updateNewList,
      addCart,
      delCart,
      singleCheck,
      allCheck,
    };
  },
  {
    persist: true,
  }
);
