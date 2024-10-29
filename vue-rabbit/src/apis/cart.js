import httpInstance from "@/utils/http";

export function insertCartAPI({ skuId, count }) {
  return httpInstance({
    url: "/member/cart",
    method: "POST",
    data: {
      skuId,
      count,
    },
  });
}

export function deleteCartAPI(ids) {
  return httpInstance({
    url: "/member/cart",
    method: "DELETE",
    data: {
      ids,
    },
  });
}

export function findNewCartListAPI() {
  return httpInstance({
    url: "/member/cart",
  });
}

export function mergeCartAPI(data) {
  return httpInstance({
    url: "/member/cart/merge",
    method: "POST",
    data
  });
}